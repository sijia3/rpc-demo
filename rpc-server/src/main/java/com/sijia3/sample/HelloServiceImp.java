package com.sijia3.sample;

import com.sijia3.api.HelloService;
import com.sijia3.server.RpcService;

/**
 * @author sijia3
 * @date 2019/12/19 14:12
 */
@RpcService(value = HelloService.class)
public class HelloServiceImp implements HelloService {
    public String hello(String name) {
        return "hello,"+name;
    }
}
