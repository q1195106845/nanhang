package com.nanhang.ad.Task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nanhang.ad.bean.User;
import com.nanhang.ad.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author yangjianghe
 * @Date 2018/10/20 10:36
 **/
@Component
@Slf4j
public class UserTask {
    @Autowired
    private RsaConfig rsaConfig;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Value("${url}")
    private String url;


    @Scheduled(cron = "0 0/1 * * * ?")
    public void getUser() {
        System.out.println("进入定时器============================");
        threadPoolExecutor.execute(() -> {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<String>("", requestHeaders);
            ResponseEntity<String> str = restTemplate.postForEntity(url,
                    requestEntity, String.class);
            String users = str.getBody();
            log.warn("Task获取到的结构信息：" + users);
            List<User> userList = JSONArray.parseArray(users, User.class);
            List<Map> errorList = new ArrayList<>();
            userList.parallelStream().forEach(x -> {
                Map<String, String> map = new HashMap<String, String>(2);
                map.put("username", x.getUserName());
                try {
                    LinkedList<String> cnList = CmdUtils.getUserCN(x);
                    if (cnList.isEmpty()) {
                        CmdUtils.addUser(x, rsaConfig.privateKey);
                    } else {
                        x.setCn(cnList.get(0));
                        CmdUtils.updateUser(x, rsaConfig.privateKey);
                    }
                    map.put("status", "0");
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("status", "1");
                    LogStack.error(x, e.getMessage());
                }
                errorList.add(map);
            });
        });
    }
}

