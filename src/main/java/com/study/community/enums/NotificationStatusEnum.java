package com.study.community.enums;

public enum NotificationStatusEnum {
    UNREAD(0),READ(1);
    private Integer status;

    NotificationStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}