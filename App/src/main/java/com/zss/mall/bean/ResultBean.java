package com.zss.mall.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 公用返回
 *
 * @author zm
 */
public class ResultBean {

    private int code;  //返回码

    private String error; //错误内容

    private String data; //返回数据

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getData() {
        return this.data;
    }

    public JSONObject getJSONObject() {
        try {
            return new JSONObject(this.data);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public void setData(String data) {
        this.data = data;
    }

}
