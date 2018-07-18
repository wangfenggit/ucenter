package com.ucenter.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by wangfeng on 2018/6/28.
 */
@ConfigurationProperties(prefix = "com.ucenter")
public class ConfigBean {

    private String name;

    private String version;

    private String secret;

    private String number;

    private String bignumber;

    private String uuid;

    private String ten;

    private String range;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBignumber() {
        return bignumber;
    }

    public void setBignumber(String bignumber) {
        this.bignumber = bignumber;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String toString() {
        return "name=:" + name + "," + "version=:" + version + "," + "secret=:" + secret
                + "," + "number=:" + number + "," + "bignumber=:" + bignumber + "," + "uuid=:" + uuid
                + "," + "ten=:" + ten + "," + "range=:" + range;

    }
}
