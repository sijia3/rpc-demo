package com.sijia3.registry.impl;

import com.sijia3.registry.ServerRegistry;
import org.I0Itec.zkclient.ZkClient;

/**
 * 服务注册实现类
 * @author sijia3
 * @date 2019/12/19 10:58
 */
public class ServerRegistryImpl implements ServerRegistry {


    private final ZkClient zkClient;

    public ServerRegistryImpl(String address) {
        zkClient = new ZkClient("127.0.0.1:2181", 5000, 1000);
        System.out.println("初始化注册zkclient");
    }

    public void registry(String serverName, String address) {
        String registryPath = "/registry";
        if (!zkClient.exists(registryPath)){
            zkClient.createPersistent(registryPath);
            System.out.println("注册/registry");
        }
        String serverPath = registryPath + "/"+serverName;
        if (!zkClient.exists(serverPath)){
            zkClient.createPersistent(serverPath);
            System.out.println("注册/registry/"+serverName);
        }

        String tempPath = serverPath + "/address-";
        String addressNode = zkClient.createEphemeralSequential(tempPath, address);
        System.out.println("注册临时节点(地址) "+ addressNode);
    }
}
