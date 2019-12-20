//package com.sijia3.client;
//
//import com.sijia3.base.Request;
//import com.sijia3.base.Response;
//import com.sijia3.registry.ServerDiscovery;
//import com.sijia3.utils.StringUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//import java.util.UUID;
//
///**
// * RPC 代理（用于创建 RPC 服务代理）
// *
// * @author huangyong
// * @since 1.0.0
// */
//public class RpcProxy {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(RpcProxy.class);
//
//    private String serviceAddress;
//
//    private ServerDiscovery serviceDiscovery;
//
//    public RpcProxy(String serviceAddress) {
//        this.serviceAddress = serviceAddress;
//    }
//
//    public RpcProxy(ServerDiscovery serviceDiscovery) {
//        this.serviceDiscovery = serviceDiscovery;
//    }
//
//    @SuppressWarnings("unchecked")
//    public <T> T create(final Class<?> interfaceClass) {
//        return create(interfaceClass, "");
//    }
//
//    @SuppressWarnings("unchecked")
//    public <T> T create(final Class<?> interfaceClass, final String serviceVersion) {
//        // 创建动态代理对象
//        return (T) Proxy.newProxyInstance(
//                interfaceClass.getClassLoader(),
//                new Class<?>[]{interfaceClass},
//                new InvocationHandler() {
//                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                        // 创建 RPC 请求对象并设置请求属性
//                        Request request = new Request();
//                        request.setRequestId(UUID.randomUUID().toString());
//                        request.setClassName(method.getDeclaringClass().getName());
//                        request.setVersion(serviceVersion);
//                        request.setMethodName(method.getName());
//                        request.setPrameterTypes(method.getParameterTypes());
//                        request.setParameters(args);
//                        // 获取 RPC 服务地址
//                        if (serviceDiscovery != null) {
//                            String serviceName = interfaceClass.getName();
//                            if (StringUtil.isNotEmpty(serviceVersion)) {
//                                serviceName += "-" + serviceVersion;
//                            }
//                            serviceAddress = serviceDiscovery.findServerByName(serviceName);
//                            LOGGER.debug("discover service: {} => {}", serviceName, serviceAddress);
//                        }
//                        if (StringUtil.isEmpty(serviceAddress)) {
//                            throw new RuntimeException("server address is empty");
//                        }
//                        // 从 RPC 服务地址中解析主机名与端口号
//                        String[] array = StringUtil.split(serviceAddress, ":");
//                        String host = array[0];
//                        int port = Integer.parseInt(array[1]);
//                        // 创建 RPC 客户端对象并发送 RPC 请求
//                        RpcClient client = new RpcClient(host, port);
//                        long time = System.currentTimeMillis();
//                        Response response = client.send(request);
//                        LOGGER.debug("time: {}ms", System.currentTimeMillis() - time);
//                        if (response == null) {
//                            throw new RuntimeException("response is null");
//                        }
//                        // 返回 RPC 响应结果
//                        if (response.hasException()) {
//                            throw response.getException();
//                        } else {
//                            return response.getResult();
//                        }
//                    }
//                }
//        );
//    }
//}
