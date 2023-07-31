/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.cloudrun.web;

import com.alipay.cloudrun.aop.annotation.ControllerPointCut;
import com.alipay.cloudrun.client.SimpleFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StreamUtils;
import java.nio.charset.StandardCharsets;
import java.io.IOException;


/**
 * 测试服务访问
 */
@RestController
public class HttpTestController {

    @Autowired
    private SimpleFeignClient simpleFeignClient;

    /**
     * 微服务接口，用户可根据实际情况调用
     *
     * @return
     */
    @ControllerPointCut
    @GetMapping("/service2")
    public String service2() {
        return simpleFeignClient.service();
    }

    @ControllerPointCut
    @GetMapping("/service")
    public String service() {
        String version = System.getenv("PUB_SERVICE_REVISION") == null ? "springboot-demo" : System.getenv("PUB_SERVICE_REVISION");
        String hostName = System.getenv("HOSTNAME") == null ? "springboot-demo" : System.getenv("HOSTNAME");
        String result = "欢迎使用云托管!&服务版本：" + version + "&实例主机：" + hostName;
        return result;
    }

    @ControllerPointCut
    @PostMapping("/vcs/webhook")
    public String echo(HttpServletRequest request) {
         String payload = null;
         try{
           payload = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
         } catch (IOException e) {
            e.printStackTrace();
         }
         System.out.println(payload);
         return "test";
    }
}
