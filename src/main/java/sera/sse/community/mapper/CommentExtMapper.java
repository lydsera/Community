package sera.sse.community.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import sera.sse.community.model.Comment;
import sera.sse.community.model.CommentExample;
import sera.sse.community.model.Invitation;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}