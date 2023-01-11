package sera.sse.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sera.sse.community.dto.CommentDTO;
import sera.sse.community.enums.CommentTypeEnum;
import sera.sse.community.exception.CustomizeErrorCode;
import sera.sse.community.exception.CustomizeException;
import sera.sse.community.mapper.CommentMapper;
import sera.sse.community.mapper.InvitationExtMapper;
import sera.sse.community.mapper.InvitationMapper;
import sera.sse.community.mapper.UserMapper;
import sera.sse.community.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private InvitationMapper invitationMapper;
    @Autowired
    private InvitationExtMapper invitationExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Transactional
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

    public List<CommentDTO> listByInvitaionId(Long id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(CommentTypeEnum.INVITATION.getType());
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if(comments.size()==0){
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment->comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人并转换成Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long,User> userMap = users.stream().collect(Collectors.toMap(user->user.getId(),user->user));

        //转换comment to commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
