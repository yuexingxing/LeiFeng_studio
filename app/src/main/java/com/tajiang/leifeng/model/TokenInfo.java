package com.tajiang.leifeng.model;

/**
 * Created by hexiuhui on 2017/7/24.
 */
public class TokenInfo {
    private int timeout;
    private String token;

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
