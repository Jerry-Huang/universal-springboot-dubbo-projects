package com.universal.spring.boot.starter.web.interceptor;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONPath;
import com.universal.spring.boot.metadata.annotation.Validation;
import com.universal.spring.boot.metadata.exception.Code;
import com.universal.spring.boot.metadata.exception.ValidationException;
import com.universal.spring.boot.metadata.message.Headers;
import com.universal.spring.boot.starter.web.CachedBodyHttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public abstract class AbstractValidationInterceptor implements HandlerInterceptor {

    private final static Map<String, Rule> RULES = new ConcurrentReferenceHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {

            return true;
        }

        final Method method = ((HandlerMethod) handler).getMethod();

        if (!method.isAnnotationPresent(Validation.class)) {

            return true;
        }

        String authToken = request.getHeader(Headers.AUTH_TOKEN);
        CachedBodyHttpServletRequestWrapper cachedBodyRequest = (CachedBodyHttpServletRequestWrapper) request;

        String rawJSONBody = StringUtils.defaultString(cachedBodyRequest.getBodyString(), "{}");

        String[] rawRules = method.getAnnotation(Validation.class).value();

        if (rawRules != null && rawRules.length >= 1) {
            this.validate(authToken, rawJSONBody, rawRules);
        }

        return true;
    }

    private void validate(String authToken, String rawJSONBody, String[] rawRules) throws ValidationException {

        Rule[] rules = Rule.generate(rawRules);

        for (Rule rule : rules) {

            if (rule.isLogined()) {

                if (!hasLogined(authToken)) {
                    throw new ValidationException(Code.USR_LOGIN_REQUIRED, StringUtils.defaultIfBlank(rule.getMessageOnUnlogined(), Code.USR_LOGIN_REQUIRED.getText()));
                } else {
                    continue;
                }
            }

            String jsonValue = getJsonValue(rule, rawJSONBody);

            if (rule.isRequired() && StringUtils.isEmpty(jsonValue)) {
                throw new ValidationException(Code.SYS_ILLEGAL_ARGUMENT, StringUtils.defaultIfBlank(rule.getMessageOnRequired(), Code.SYS_ILLEGAL_ARGUMENT.getText()),
                        "Not found " + rule.getJsonPath());
            }

            if (StringUtils.isNotEmpty(jsonValue) && StringUtils.isNotBlank(rule.getExpression()) && !jsonValue.matches(rule.getExpression())) {
                throw new ValidationException(Code.SYS_ILLEGAL_ARGUMENT, StringUtils.defaultIfBlank(rule.getMessageOnExpression(), Code.SYS_ILLEGAL_ARGUMENT.getText()),
                        String.format("%s [%s] doesn't match %s", jsonValue, rule.getJsonPath(), rule.getExpression()));
            }
        }
    }

    private String getJsonValue(final Rule rule, String rawJSONBody) {

        JSONObject jsonObject = JSON.parseObject(rawJSONBody);

        if (Objects.nonNull(rule.jsonPathObject)) {

            return rule.jsonPathObject.eval(jsonObject).toString();
        }

        return JSONPath.eval(jsonObject, rule.getJsonPath()).toString();
    }

    protected abstract boolean hasLogined(final String token);

    private static class Rule {

        private String jsonPath;
        private JSONPath jsonPathObject;
        private boolean logined;
        private String messageOnUnlogined;
        private boolean required;
        private String messageOnRequired;
        private String expression;
        private String messageOnExpression;

        private static Rule generate(final String rawRule) throws ValidationException {

            String[] slices = rawRule.split(",");

            if (slices.length < 1 || slices.length > 5) {
                throw new ValidationException(Code.SYS_SERVICE_ERROR, "Invalid rule " + rawRule);
            }

            Rule rule = new Rule();

            if (slices[0].equalsIgnoreCase("login-required")) {
                rule.setLogined(true);

                if (slices.length >= 2) {
                    rule.setMessageOnUnlogined(slices[1]);
                }

                return rule;
            }

            rule.setJsonPath(slices[0]);
            rule.jsonPathObject = JSONPath.of(slices[0]);

            if (slices[1].equalsIgnoreCase("required")) {
                rule.setRequired(true);

                if (slices.length >= 3) {
                    rule.setMessageOnRequired(slices[2]);
                }
            } else {
                rule.setExpression(slices[1]);

                if (slices.length >= 3) {
                    rule.setMessageOnExpression(slices[2]);
                }
            }

            if (slices.length >= 4) {
                rule.setExpression(slices[3]);

                if (slices.length >= 5) {
                    rule.setMessageOnExpression(slices[4]);
                }
            }

            return rule;
        }

        public static Rule[] generate(final String[] rawRules) throws ValidationException {

            List<Rule> rules = new ArrayList<>();

            for (String rawRule : rawRules) {

                Rule rule = RULES.get(rawRule);

                if (rule == null) {

                    rule = Rule.generate(rawRule);
                    RULES.put(rawRule, rule);
                }

                rules.add(rule);
            }

            return rules.toArray(new Rule[0]);
        }

        public String getJsonPath() {
            return jsonPath;
        }

        public void setJsonPath(String jsonPath) {
            this.jsonPath = jsonPath;
        }

        public boolean isRequired() {
            return required;
        }

        public void setRequired(boolean required) {
            this.required = required;
        }

        public boolean isLogined() {
            return logined;
        }

        public void setLogined(boolean logined) {
            this.logined = logined;
        }

        public String getMessageOnUnlogined() {
            return messageOnUnlogined;
        }

        public void setMessageOnUnlogined(String messageOnUnlogined) {
            this.messageOnUnlogined = messageOnUnlogined;
        }

        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }

        public String getMessageOnRequired() {
            return messageOnRequired;
        }

        public void setMessageOnRequired(String messageOnRequired) {
            this.messageOnRequired = messageOnRequired;
        }

        public String getMessageOnExpression() {
            return messageOnExpression;
        }

        public void setMessageOnExpression(String messageOnExpression) {
            this.messageOnExpression = messageOnExpression;
        }
    }
}
