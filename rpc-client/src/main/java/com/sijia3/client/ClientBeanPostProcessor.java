//package com.sijia3.client;
//
//import com.sijia3.api.HelloService;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.stereotype.Component;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//
///**
// * @author sijia3
// * @date 2019/12/25 12:07
// */
//@Component
//public class ClientBeanPostProcessor implements BeanPostProcessor {
//
//    @Autowired
//    private RpcProxy rpcProxy;
//
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
////        System.out.println("spring中bean实例:" + beanName + " 初始化之前处理......");
//        return bean;
//    }
//
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        System.out.println("spring中bean实例:" + beanName + " 初始化之后处理......");
//        Class<?> cls = bean.getClass();
//        Field[] fields = cls.getDeclaredFields();
//        for (Field field:fields){
//            for (Annotation annotation:field.getDeclaredAnnotations()){
//                if (annotation.annotationType().equals(CXXX.class)){
//                    field.setAccessible(true);
////                    System.out.println(field.getType());
//                    Object object = rpcProxy.create(HelloService.class);
//                    try {
//                        field.set(bean, object);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                }
//            }
//        }
//        return bean;
//    }
//}
