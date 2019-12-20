//package com.sijia3.sample;
//
//import com.sijia3.api.HelloService;
//import com.sijia3.client.RpcProxy;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//public class HelloClient {
//
//    public static void main(String[] args) throws Exception {
//        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
//        RpcProxy rpcProxy = context.getBean(RpcProxy.class);
//
//        HelloService helloService = rpcProxy.create(HelloService.class);
//        String result = helloService.hello("World");
//        System.out.println(result);
//
////        HelloService helloService2 = rpcProxy.create(HelloService.class, "sample.hello2");
////        String result2 = helloService2.hello("世界");
////        System.out.println(result2);
//
//        System.exit(0);
//    }
//}
