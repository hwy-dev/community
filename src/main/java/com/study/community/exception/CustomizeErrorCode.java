package com.study.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"你找的问题不存在了，请换个试试"),
    TARGET_PARAM_NOT_FOUND(2002,"未选择任何问题不能评论"),
    NO_LOGIN(2003,"当前操作需要登录，请先登录重试"),
    SYS_ERROR(2004,"服务异常了，稍后再试"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复评论不存在了，请换个试试"),
    CONTENT_IS_EMPTY(2007,"回复内容不能为空，请填写内容"),
    READ_NOTIFICATION_FAIL(2008,"不能操作别人的信息"),
    NOTIFICATION_NOT_FUND(2009,"消息不存在")
    ;

    private Integer code;
    private String message;

    @Override
    public String getMessage() {
        return message;
    }
    @Override
    public Integer getCode(){
        return  code;
    }

    CustomizeErrorCode(Integer code, String message){
        this.message=message;
        this.code=code;
    }

}
