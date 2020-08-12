package com.willi.service;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: sakura
 * @description:
 * @author: Hoodie_Willi
 * @create: 2020-08-12 13:25
 **/
@Slf4j
public class ProviderRegister {
    /**
     * 服务名称和其对象
     */
    private static final Map<String, Object> SERVICE_MAP = new ConcurrentHashMap<>();

    /**
     * 添加 RPC Provider 端的服务
     */
    public <T> void addService(T service, Class<T> clazz) {
        // getCanonicalName() 是获取所传类从java语言规范定义的格式输出
        String serviceName = clazz.getCanonicalName();
        log.info("添加服务，名称是 {}", serviceName);
        if (!SERVICE_MAP.containsKey(serviceName)) {
            // 将服务名和服务对应的对象添加到 SERVICE_MAP
            SERVICE_MAP.put(serviceName, service);
        }
    }
    /**
     * 获取 RPC Provider 端的服务
     */
    public Object getService(String serviceName) {
        Object service = SERVICE_MAP.get(serviceName);
        if (service == null) {
            log.debug("没有找到该 PRC 服务");
            return null;
        }
        log.info("找到服务 {}", serviceName);
        return service;
    }
}
