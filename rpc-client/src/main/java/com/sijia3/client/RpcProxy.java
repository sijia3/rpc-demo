package com.sijia3.client;

import com.sijia3.api.HelloService;
import com.sijia3.base.Request;
import com.sijia3.base.Response;
import com.sijia3.registry.ServerDiscovery;
import com.sijia3.utils.StringUtil;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * sijia3
 */
public class RpcProxy {
    private static Logger logger = LoggerFactory.getLogger(RpcProxy.class);

    private ServerDiscovery serverDiscovery;
    private String serverAddress;

    public RpcProxy(ServerDiscovery serverDiscovery){
        this.serverDiscovery = serverDiscovery;
    }

    private Map<Class<?>, Object> proxy = new HashMap<Class<?>, Object>();

    @SuppressWarnings("unchecked")
    public <T> T create(final Class<?> clazz){
        return create(clazz, "");
    }

    @SuppressWarnings("unchecked")
    public <T> T create(final Class<?> clazz, final String version){
        if (proxy.containsKey(clazz)){
            logger.info("取出map中的proxy");
            return (T)proxy.get(clazz);
        }
        Object object = Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz} , new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Request request = new Request();
                request.setRequestId(UUID.randomUUID().toString());
                request.setClassName(method.getDeclaringClass().getName());
                request.setVersion(version);
                request.setMethodName(method.getName());
                request.setParameters(args);
                request.setPrameterTypes(method.getParameterTypes());

                // 获取服务
                String serverName = "";
                if (serverDiscovery != null) {
                    serverName = clazz.getName();
                    if (StringUtil.isNotEmpty(version)) {
                        serverName = serverName + "-" + version;
                    }
                    serverAddress = serverDiscovery.findServerByName(serverName);
                }
                if (StringUtil.isEmpty(serverAddress)){
                    logger.error("{}--找不到地址",serverName);
                }

                String[] ipAndPort = StringUtil.split(serverAddress, ":");
                String ip = ipAndPort[0];
                Integer port = Integer.valueOf(ipAndPort[1]);
                RpcClient rpcClient = new RpcClient(ip, port);
                long time = new Date().getTime();
                Response response = rpcClient.send(request);
                logger.info("此次调用花费：{}ms",System.currentTimeMillis()-time);
                if (response == null){
                    logger.error("返回为空");
                }
                if (response.hasException()){
                    logger.error("返回中带有异常");
                    throw new RuntimeException("返回有异常");
                }else{
                    return response.getResult();
                }
            }
        });
        proxy.put(clazz, object);
        logger.info("创建{}",clazz);
        return (T)object;
    }



}
