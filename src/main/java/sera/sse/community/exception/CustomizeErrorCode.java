package sera.sse.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    INVITATION_NOT_FOUND(2001,"帖子不存在或已被删除！"),
    TARGET_PARAM_NOT_FOUND(2002,"请选择要回复的帖子或评论！"),
    NO_LOGIN(2003,"当前操作需要登录，请登录后重试！"),
    SYS_ERROR(2004,"服务繁忙，请稍后再试！"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在！"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在或已被删除！"),
    CONTENT_IS_EMPTY(2007, "输入内容不能为空！"),
    READ_NOTIFICATION_FAIL(2008, "是否读错信息？请重试！"),
    NOTIFICATION_NOT_FOUND(2009, "消息不存在！"),
    FILE_UPLOAD_FAIL(2010, "图片上传失败");
    private String message;
    private Integer code;



    CustomizeErrorCode(Integer code,String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
