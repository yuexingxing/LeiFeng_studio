package com.tajiang.leifeng.model;

import java.io.Serializable;

/**
 * Created by hexiuhui.
 */
public class TraceInfo implements Serializable {
    private String createDate;
    private String eventCode;
    private String eventContent;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }
}
