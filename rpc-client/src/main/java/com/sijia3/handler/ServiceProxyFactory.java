package com.sijia3.handler;

import com.sijia3.registry.ServerDiscovery;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sijia3
 * @date 2019/12/26 9:21
 */
public class ServiceProxyFactory<T> implements FactoryBean<T> {

    @Autowired
    private ServerDiscovery serverDiscovery;

    private Class<T> interfaceClass;

    private String version;

    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public T getObject() throws Exception {
        return (T) new ServiceProxy().bind(interfaceClass, version, serverDiscovery);
    }

    public Class<?> getObjectType() {
        return interfaceClass;
    }

    public boolean isSingleton() {
        // 单例模式
        return true;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
