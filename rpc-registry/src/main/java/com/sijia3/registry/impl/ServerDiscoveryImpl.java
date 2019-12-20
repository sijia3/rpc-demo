package com.sijia3.registry.impl;

import com.sijia3.registry.ServerDiscovery;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * @author sijia3
 * @date 2019/12/19 10:57
 */
public class ServerDiscoveryImpl implements ServerDiscovery {
    private String address;

    public ServerDiscoveryImpl(String address){
        this.address = address;
    }

    public String findServerByName(String serverName) {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181",5000, 1000);
        System.out.println("连接zookeeper");
        try {
            String serverPath = "/registry"+"/"+serverName;
            if (!zkClient.exists(serverPath)){
                System.out.println("尚未注册该"+serverName+"节点");
                return null;
            }
            List<String> addressList = zkClient.getChildren(serverPath);
            if (addressList.isEmpty()){
                System.out.println("节点列表为空。");
                return null;
            }
            String address;
            address = addressList.get(0);
            String addressPath = serverPath + "/"+ address;
            zkClient.readData(addressPath);
            return zkClient.readData(addressPath);
        }finally {
            zkClient.close();
        }

    }
}
