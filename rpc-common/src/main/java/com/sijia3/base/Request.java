package com.sijia3.base;


import java.io.Serializable;

/**
 * @author sijia3
 * @date 2019/12/19 15:53
 */
public class Request implements Serializable{

    private String requestId;
    private String className;
    private String methodName;
    private String version;

    private Object[] parameters;
    private Class<?>[] prameterTypes;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public Class<?>[] getPrameterTypes() {
        return prameterTypes;
    }

    public void setPrameterTypes(Class<?>[] prameterTypes) {
        this.prameterTypes = prameterTypes;
    }
}
