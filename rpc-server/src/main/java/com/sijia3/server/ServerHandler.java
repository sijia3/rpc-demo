package com.sijia3.server;

import com.sijia3.api.HelloService;
import com.sijia3.base.Request;
import com.sijia3.base.Response;
import com.sijia3.sample.HelloServiceImp;
import com.sijia3.utils.StringUtil;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sijia3
 * @date 2019/12/19 12:05
 */
public class ServerHandler extends SimpleChannelInboundHandler<Request> {
    private static Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    private final Map<String, Object> handleMap;

    public ServerHandler(Map hashMap){
        this.handleMap = hashMap;
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {

        logger.info("{}, {}, {}", request.getClassName(), request.getMethodName(), request.getRequestId());
        String className = request.getClassName();
        String version = request.getVersion();
        if(StringUtil.isNotEmpty(version)){
            className = className + "-"+version;
        }
        Object object = "lalalal";
//        Object serviceBean = handleMap.get(className);
//        if (serviceBean == null){
//            throw new RuntimeException("servicebean not find");
//        }
        // 通过反射获取对象
//        Class<?> serviceClass = serviceBean.getClass();
//        String methodName = request.getMethodName();
//        Class<?>[] parameterTypes = request.getPrameterTypes();
//        Object[] parameters = request.getParameters();
//        // 执行方法
//        FastClass fastClass =FastClass.create(serviceClass);
//        FastMethod fastMethod = fastClass.getMethod(methodName, parameterTypes);
//        Object object = fastMethod.invoke(serviceBean, parameters);

        // 构造repsonse
        Response response = new Response();
        response.setRequestId(request.getRequestId());
        response.setResult(object);

        channelHandlerContext.writeAndFlush(response).addListeners(ChannelFutureListener.CLOSE);
//        channelHandlerContext.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("error ..");
        ctx.close();
    }

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        Class<?> serviceClass = HelloServiceImp.class;
        // 执行方法
        FastClass fastClass =FastClass.create(serviceClass);
        FastMethod fastMethod = fastClass.getMethod("hello", new Class[]{String.class});
        HelloServiceImp helloServiceImp = (HelloServiceImp) serviceClass.newInstance();
        Object object = fastMethod.invoke(helloServiceImp, new Object[]{"ll"});
        return;
    }
}
