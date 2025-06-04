package com.universal.spring.boot.metadata.message;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONPath;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

public class SimpleApiRequest extends GenericApiRequest<JSONObject> {

    public SimpleApiRequest() {

    }

    public SimpleApiRequest(final String json) throws Exception {

        super(json);
        this.payload = JSON.parseObject(json);
    }

    public String getString(final String name) {

        if (this.payload != null) {

            return StringUtils.defaultString(this.payload.getString(name));
        }

        return StringUtils.EMPTY;
    }

    public JSONObject get(final String name) {

        if (this.payload != null) {
            return this.payload.getJSONObject(name);
        }

        return null;
    }

    public String path(final String path) {

        if (this.payload != null) {
            Object jsonObject = JSONPath.eval(this.payload, path);

            if (jsonObject != null) {
                return jsonObject.toString();
            }
        }

        return null;
    }

    public JSONArray getArray(final String name) {

        if (this.payload != null) {
            return this.payload.getJSONArray(name);
        }

        return null;
    }

    public int getInt(final String name) {

        return NumberUtils.toInt(getString(name));
    }

    public double getDouble(final String name) {

        return NumberUtils.toDouble(getString(name));
    }

    public BigDecimal getBigDecimal(final String name) {

        final String stringValue = getString(name);

        if (StringUtils.isNotBlank(stringValue) && !stringValue.equals("null")) {
            return new BigDecimal(stringValue);
        } else {
            return BigDecimal.ZERO;
        }
    }

}
