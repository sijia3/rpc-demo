package com.sijia3.handler;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author sijia3
 * @date 2019/12/27 15:48
 */
public class ServiceHandler extends NamespaceHandlerSupport {
    public void init() {
        registerBeanDefinitionParser("service", new ServiceParser());
    }
}
