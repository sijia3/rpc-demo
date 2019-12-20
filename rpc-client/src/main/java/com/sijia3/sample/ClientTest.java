package com.sijia3.sample;

import com.sijia3.api.HelloService;
import com.sijia3.client.RpcProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        RpcProxy rpcProxy = applicationContext.getBean(RpcProxy.class);
        HelloService helloService = rpcProxy.create(HelloService.class);
        String result = helloService.hello("sijia3");
        System.out.println(result);
        System.exit(0);
    }

}
