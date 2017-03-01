package com.universal.provider.message.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.universal.provider.message.sms.SmsSender;
import com.universal.provider.message.sms.handler.SmsMultipleChannelHandler;
import com.universal.provider.message.sms.sender.dayu.SmsDayuReporter;
import com.universal.provider.message.sms.sender.dayu.SmsDayuSender;
import com.universal.provider.message.sms.sender.sse9.Sms33e9Reporter;
import com.universal.provider.message.sms.sender.sse9.Sms33e9Sender;
import com.universal.provider.message.sms.sender.yunxin.SmsYunxinReporter;
import com.universal.provider.message.sms.sender.yunxin.SmsYunxinSender;

@Configuration
@PropertySource("classpath:sms.properties")
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfiguration {

    @Autowired
    private SmsProperties properties;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public SmsMultipleChannelHandler handler() {

        SmsSender dayuSender = applicationContext.getBean(SmsDayuSender.class);
        SmsSender sse9Sender = applicationContext.getBean(Sms33e9Sender.class);
        SmsSender yunxinSender = applicationContext.getBean(SmsYunxinSender.class);

        final Map<String, SmsSender> channels = new HashMap<>();
        channels.put("P1", dayuSender);
        channels.put("P2", sse9Sender);
        channels.put("P3", yunxinSender);

        final SmsMultipleChannelHandler channelHandler = new SmsMultipleChannelHandler(channels);
        channelHandler.setMaxSendingTimes(properties.getMaxSendingTimes());

        return channelHandler;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public SmsDayuSender dayu(Sms33e9Sender hander) {

        final SmsProperties.Dayu properties = this.properties.getDayu();

        final SmsDayuSender sender = new SmsDayuSender(properties.getUrl(), properties.getAppkey(), properties.getSecret());
        sender.setMaxSendingTimes(properties.getMaxSendingTimes());

        final SmsDayuReporter reporter = new SmsDayuReporter();
        reporter.setFailedHander(hander);
        reporter.setAppkey(properties.getAppkey());
        reporter.setSecret(properties.getSecret());
        sender.setReporter(reporter);

        return sender;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Sms33e9Sender sse9(SmsYunxinSender hander) {

        final SmsProperties.Sse9 properties = this.properties.getSse9();

        final Sms33e9Sender sender = new Sms33e9Sender();
        sender.setMaxSendingTimes(properties.getMaxSendingTimes());

        final Sms33e9Reporter reporter = new Sms33e9Reporter();
        reporter.setFailedHander(hander);
        sender.setReporter(reporter);

        return sender;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public SmsYunxinSender yunxin() {

        final SmsProperties.Yunxin properties = this.properties.getYunxin();

        final SmsYunxinSender sender = new SmsYunxinSender(properties.getUrl(), properties.getAccount(), properties.getPassword());
        sender.setMaxSendingTimes(properties.getMaxSendingTimes());

        final SmsYunxinReporter reporter = new SmsYunxinReporter();
        reporter.setFailedHander(null);
        sender.setReporter(reporter);

        return sender;
    }
}
