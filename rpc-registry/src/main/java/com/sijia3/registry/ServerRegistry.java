package com.sijia3.registry;

/**
 * 服务注册中心接口
 * @author sijia3
 * @date 2019/12/19 10:50
 */
public interface ServerRegistry {
    void registry(String serverName, String address);

}
