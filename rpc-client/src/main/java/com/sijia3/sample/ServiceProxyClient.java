package com.sijia3.sample;

import com.sijia3.api.HelloService;
import com.sijia3.api.TestService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 采用注入bean的测试方法
 */
public class ServiceProxyClient {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        HelloService helloService = applicationContext.getBean(HelloService.class);
        String str = helloService.hello("ddddd");
        System.out.println(str);

        TestService testService = applicationContext.getBean(TestService.class);
        String result = testService.test("sijia3");
        System.out.println(result);
        System.exit(0);
    }

}
