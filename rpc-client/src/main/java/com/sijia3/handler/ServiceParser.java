package com.sijia3.handler;

import com.sijia3.registry.ServerDiscovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author sijia3
 * @date 2019/12/27 15:50
 */
public class ServiceParser implements BeanDefinitionParser {

    // 注册bean
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String clsName = element.getAttribute("class");
        String beanId = element.getAttribute("id");
        String version = element.getAttribute("version");
        Class<?> cls = null;
        try {
             cls = Class.forName(clsName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        RootBeanDefinition definition = new RootBeanDefinition();
        definition.getPropertyValues().add("interfaceClass",cls);
        definition.getPropertyValues().add("version",version);
        definition.setBeanClass(ServiceProxyFactory.class);
        definition.setAutowireMode(RootBeanDefinition.AUTOWIRE_BY_TYPE);
        parserContext.getRegistry().registerBeanDefinition(beanId, definition);
        return definition;
    }
}
