package com.sijia3.handler;

import com.sijia3.base.Request;
import com.sijia3.base.Response;
import com.sijia3.client.RpcClient;
import com.sijia3.registry.ServerDiscovery;
import com.sijia3.registry.impl.ServerDiscoveryImpl;
import com.sijia3.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;
import java.util.UUID;

/**
 * @author sijia3
 * @date 2019/12/26 9:16
 */
public class ServiceProxy implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(ServiceProxy.class);

    private Class<?> interfaceClass;
    private String version;

    private ServerDiscovery serverDiscovery;
    private String serverAddress;

    public Object bind(Class<?> cls, String version, ServerDiscovery serverDiscovery) {
        this.interfaceClass = cls;
        this.version = version;
        this.serverDiscovery = serverDiscovery;
        return Proxy.newProxyInstance(cls.getClassLoader(), new Class[] {interfaceClass}, this);
    }

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
            serverName = interfaceClass.getName();
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

}
