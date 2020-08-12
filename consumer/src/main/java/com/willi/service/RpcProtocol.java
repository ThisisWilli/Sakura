package com.willi.service;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @program: sakura
 * @description:
 * @author: Hoodie_Willi
 * @create: 2020-08-11 23:34
 **/

public class RpcProtocol implements Serializable {

    private static final long serialVersionUID = -8020775750799142361L;
    private Class<?> requestInterface;
    // 使用的方法名
    private String methodName;
    // 使用的方法
    private Object[] paramValue;
    // 使用的方法
    private Class<?>[] paraType;

    public RpcProtocol(Class<?> requestInterface, String methodName, Object[] paramValue, Class<?>[] paraType) {
        this.requestInterface = requestInterface;
        this.methodName = methodName;
        this.paramValue = paramValue;
        this.paraType = paraType;
    }

    public Class<?> getRequestInterface() {
        return requestInterface;
    }

    public void setRequestInterface(Class<?> requestInterface) {
        this.requestInterface = requestInterface;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParamValue() {
        return paramValue;
    }

    public void setParamValue(Object[] paramValue) {
        this.paramValue = paramValue;
    }

    public Class<?>[] getParaType() {
        return paraType;
    }

    public void setParaType(Class<?>[] paraType) {
        this.paraType = paraType;
    }
}
