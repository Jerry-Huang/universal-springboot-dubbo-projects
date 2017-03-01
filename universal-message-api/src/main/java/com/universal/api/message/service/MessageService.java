package com.universal.api.message.service;

import com.universal.api.message.bean.SmsBean;

public interface MessageService {

    String welcome();

    void send(final String channel, final String ip, final SmsBean... smsBeans);
    
    String sms(final String channel, final String ip, final String jsonSmses);
    
    void send(final String channel, final String ip, final SmsBean smsBean) throws Exception;
}
