package com.universal.provider.message.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.universal.api.message.bean.SmsBean;
import com.universal.api.message.service.MessageService;
import com.universal.provider.message.entity.Sms;
import com.universal.provider.message.sms.handler.SmsHandler;

@NoCache
@Path("/")
@Service
public class MessageServiceImpl implements MessageService {

    private final static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private SmsHandler smsHandler;

    private final Map<String, String> smsTemplateCache = new ConcurrentHashMap<>();

    @Override
    public void send(String channel, String ip, SmsBean... smsBeans) {

        for (SmsBean smsBean : smsBeans) {

            Sms sms = new Sms();
            sms.setChannel(channel);
            sms.setTo(smsBean.getPhone());
            sms.setCreateTime(DateTime.now().toDate());
            sms.setIp(ip);
            sms.setFrom(StringUtils.EMPTY);

            try {
                sms.setContent(generateSmsContent(smsBean));
                smsHandler.send(sms);
            } catch (Exception e) {
                logger.error("Sending failed: {}", sms);
            }
        }
    }

    @Override
    @POST
    @Path("/sms.shtml")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({ MediaType.APPLICATION_FORM_URLENCODED + ";charset=UTF-8" })
    public String sms(@FormParam("channel") final String channel, @FormParam("ip") final String ip, @FormParam("json-smses") final String jsonSmses) {

        List<SmsBean> smsBeans = null;

        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            smsBeans = objectMapper.readValue(jsonSmses, new TypeReference<List<SmsBean>>() {
            });
        } catch (IOException e) {
            logger.error("Parsing json failed: " + jsonSmses, e);
            return "FAL";
        }

        send(channel, ip, smsBeans.toArray(new SmsBean[smsBeans.size()]));

        return "OK";
    }

    @Override
    public void send(String channel, String ip, SmsBean smsBean) throws Exception {

        Sms sms = new Sms();
        sms.setChannel(channel);
        sms.setTo(smsBean.getPhone());
        sms.setCreateTime(DateTime.now().toDate());
        sms.setIp(ip);
        sms.setFrom(StringUtils.EMPTY);
        sms.setContent(generateSmsContent(smsBean));
        
        smsHandler.send(sms);
    }

    @Override
    @GET
    @Path("/")
    public String welcome() {

        return "OK";
    }

    private String generateSmsContent(SmsBean smsBean) throws Exception {

        String smsContent = StringUtils.EMPTY;
        String templateCode = smsBean.getTemplateCode();
        Map<String, String> parameters = smsBean.getParameters();

        if (smsTemplateCache.containsKey(templateCode)) {
            smsContent = smsTemplateCache.get(templateCode);
        } else {

            final Properties properties = this.findPropertiesFile("sms.properties");
            smsContent = properties.getProperty("sms.template." + templateCode);

            if (StringUtils.isBlank(smsContent)) {
                throw new Exception("Template " + templateCode + " is empty");
            }

            smsTemplateCache.put(templateCode, smsContent);
        }

        if (parameters != null) {
            for (Map.Entry<String, String> parameter : parameters.entrySet()) {
                smsContent = smsContent.replace("#{" + parameter.getKey() + "}", parameter.getValue());
            }
        }

        return smsContent;
    }

    private Properties findPropertiesFile(String fileName) throws Exception {

        final Properties properties = new Properties();
        final ClassLoader classLoader = this.getClass().getClassLoader();

        properties.load(new InputStreamReader(classLoader.getResourceAsStream(fileName), "UTF-8"));

        return properties;
    }

}
