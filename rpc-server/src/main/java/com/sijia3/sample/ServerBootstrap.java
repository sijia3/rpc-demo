package com.sijia3.sample;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author sijia3
 * @date 2019/12/19 12:25
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring.xml");
    }
}
