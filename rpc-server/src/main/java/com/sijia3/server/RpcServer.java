package com.sijia3.server;

import com.sijia3.code.MarshallingCodeCFactory;
import com.sijia3.registry.ServerRegistry;
import com.sijia3.utils.StringUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author sijia3
 * @date 2019/12/19 12:03
 */
public class RpcServer implements ApplicationContextAware,InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(RpcService.class);

    private String address;
    private ServerRegistry serverRegistry;

    private Map<String, Object> hashMap = new HashMap<String, Object>();

    public RpcServer(String serviceAddress) {
        this.address = serviceAddress;
    }

    public RpcServer(String address, ServerRegistry serverRegistry){
        System.out.println("init");
        this.address = address;
        this.serverRegistry = serverRegistry;
    }

    /**
     * 实现ApplicationContextAware的类，可以在spring容器初始化的时候调用setApplicationContext方法，从而获得ApplicationContext中的所有bean
     * @param ctx
     * @throws BeansException
     */
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        Map<String, Object> beans = ctx.getBeansWithAnnotation(RpcService.class);
        if (MapUtils.isNotEmpty(beans)){
            for (Object serviceBean: beans.values()) {
                System.out.println(serviceBean);
                RpcService rpcService = serviceBean.getClass().getAnnotation(RpcService.class);
                String serviceName = rpcService.value().getName();
                String serviceVersion = rpcService.version();
                if (StringUtil.isNotEmpty(serviceVersion)){
                    serviceName = serviceName + "-"+serviceVersion;
                }
                hashMap.put(serviceName, serviceBean);
            }
        }
    }

    /**
     * 凡是继承InitializingBean，在初始化bean的时候都会执行该方法
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast(new RpcDecoder(Request.class));
//                            ch.pipeline().addLast(new RpcEncoder(Response.class));
                            ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                            ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                            ch.pipeline().addLast(new ServerHandler(hashMap));
                        }
                    }).option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 绑定端口，并启动服务器
            String[] ipAndPort = StringUtil.split(address, ":");
            String ip = ipAndPort[0];
            Integer port = Integer.valueOf(ipAndPort[1]);
            ChannelFuture f = b.bind(ip, port).sync();

            // 向zookeeper注册服务
            if (serverRegistry != null){
                for (String interfaceName:hashMap.keySet()) {
                    serverRegistry.registry(interfaceName, address);
                    logger.info("注册服务{}-->{}",interfaceName,address);
                }
            }
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
