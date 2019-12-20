package com.sijia3.client;

import com.sijia3.base.Response;
import com.sijia3.base.Request;
import com.sijia3.code.MarshallingCodeCFactory;
import com.sijia3.code.RpcDecoder;
import com.sijia3.code.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sijia3
 * @date 2019/12/20 11:43
 */
public class RpcClient extends SimpleChannelInboundHandler<Response> {

    private static Logger logger = LoggerFactory.getLogger(RpcClient.class);

    private final String host;
    private final int port;

    private Response response;

    public RpcClient(String host, int port){
        this.host = host;
        this.port = port;
    }


    protected void channelRead0(ChannelHandlerContext ctx, Response response) throws Exception {
        this.response = response;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("error ...");
        ctx.close();
    }

    public Response send(Request request) throws InterruptedException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
//                    ch.pipeline().addLast(new RpcEncoder(Request.class));
//                    ch.pipeline().addLast(new RpcDecoder(Response.class));
                    ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                    ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                    ch.pipeline().addLast(RpcClient.this);
                }
            });
            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();
//            for (int i=0; i<10;i++){         // 测试是否支持粘包
//                f.channel().writeAndFlush(request);
//            }
            f.channel().writeAndFlush(request).sync();
            logger.info("写入成功");
            f.channel().closeFuture().sync();
            logger.info("关闭成功");
            return response;
        } finally {
            workerGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        RpcClient rpcClient = new RpcClient("127.0.0.1", 8080);
        Request request = new Request();
        request.setClassName("Hello");
        request.setMethodName("fun");
        request.setRequestId("12343");

        Response response = (rpcClient.send(request));
        return;
    }
}
