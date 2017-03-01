package com.universal.provider.message.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(SmsProperties.PREFIX)
public class SmsProperties {

    public static final String PREFIX = "sms";

    private int maxSendingTimes;

    private final Dayu dayu = new Dayu();

    private final Sse9 sse9 = new Sse9();

    private final Yunxin yunxin = new Yunxin();

    private Map<String, String> templates = new HashMap<>();

    public Dayu getDayu() {
        return dayu;
    }

    public Sse9 getSse9() {
        return sse9;
    }

    public Yunxin getYunxin() {
        return yunxin;
    }

    public int getMaxSendingTimes() {
        return maxSendingTimes;
    }

    public void setMaxSendingTimes(int maxSendingTimes) {
        this.maxSendingTimes = maxSendingTimes;
    }

    public Map<String, String> getTemplates() {
        return templates;
    }

    public void setTemplates(Map<String, String> templates) {
        this.templates = templates;
    }

    public static class Dayu {

        private String url;

        private String appkey;

        private String secret;
        private int maxSendingTimes;

        public int getMaxSendingTimes() {
            return maxSendingTimes;
        }

        public void setMaxSendingTimes(int maxSendingTimes) {
            this.maxSendingTimes = maxSendingTimes;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAppkey() {
            return appkey;
        }

        public void setAppkey(String appkey) {
            this.appkey = appkey;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

    }

    public static class Sse9 {

        private int maxSendingTimes;

        public int getMaxSendingTimes() {
            return maxSendingTimes;
        }

        public void setMaxSendingTimes(int maxSendingTimes) {
            this.maxSendingTimes = maxSendingTimes;
        }
    }

    public static class Yunxin {

        private String url;
        private String account;
        private String password;
        private int maxSendingTimes;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getMaxSendingTimes() {
            return maxSendingTimes;
        }

        public void setMaxSendingTimes(int maxSendingTimes) {
            this.maxSendingTimes = maxSendingTimes;
        }
    }

    public static class Chuanglan {

        private String url;
        private String account;
        private String password;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
