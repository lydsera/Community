package sera.sse.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sera.sse.community.enums.CommentTypeEnum;
import sera.sse.community.exception.CustomizeErrorCode;
import sera.sse.community.exception.CustomizeException;
import sera.sse.community.mapper.CommentMapper;
import sera.sse.community.mapper.InvitationExtMapper;
import sera.sse.community.mapper.InvitationMapper;
import sera.sse.community.model.Comment;
import sera.sse.community.model.Invitation;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private InvitationMapper invitationMapper;
    @Autowired
    private InvitationExtMapper invitationExtMapper;
    public void insert(Comment comment) {
        if(comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType()))
        {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
        }
        else{
            //回复帖子
            Invitation invitation = invitationMapper.selectByPrimaryKey(comment.getParentId());
            if(invitation==null){
                throw new CustomizeException(CustomizeErrorCode.INVITATION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            invitation.setCommentCount(1);
            invitationExtMapper.incCommentCount(invitation);
        }
    }
}
