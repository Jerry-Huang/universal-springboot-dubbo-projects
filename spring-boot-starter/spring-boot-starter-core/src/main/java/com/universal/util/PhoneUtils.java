package com.universal.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class PhoneUtils {

    public static String mask(String phone) {
        
        if (StringUtils.isBlank(phone)) {
            return StringUtils.EMPTY;
        }

        Pattern pattern = Pattern.compile("^(1\\d{2})\\d{4}(\\d{4})$");
        Matcher matcher = pattern.matcher(phone);
        
        if (matcher.matches()) {
            
            return String.format("%s****%s", matcher.group(1), matcher.group(2));
        }

        return phone;
    }
}
