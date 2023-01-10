package sera.sse.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    INVITATION_NOT_FOUND("帖子不存在或已被删除！");
    private String message;
    @Override
    public String getMessage()
    {
        return message;
    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }
}
