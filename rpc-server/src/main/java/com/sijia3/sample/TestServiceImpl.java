package com.sijia3.sample;

import com.sijia3.api.TestService;
import com.sijia3.server.RpcService;

/**
 * @author sijia3
 * @date 2019/12/30 15:48
 */
@RpcService(value = TestService.class, version = "")
public class TestServiceImpl implements TestService{
    public String test(String str) {
        return "test,"+str;
    }
}
