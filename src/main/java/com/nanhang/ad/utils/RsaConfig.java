package com.nanhang.ad.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author yangjianghe
 * @Date 2018/10/30 15:18
 **/
@Component
@ConfigurationProperties(prefix = "rsa")
@PropertySource(value = "classpath:/rsa.properties")
public class RsaConfig {
    public String privateKey;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
