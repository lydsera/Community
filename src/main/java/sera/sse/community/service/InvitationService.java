package sera.sse.community.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sera.sse.community.dto.InvitationDTO;
import sera.sse.community.dto.PaginationDTO;
import sera.sse.community.exception.CustomizeErrorCode;
import sera.sse.community.exception.CustomizeException;
import sera.sse.community.mapper.InvitationExtMapper;
import sera.sse.community.mapper.InvitationMapper;
import sera.sse.community.mapper.UserMapper;
import sera.sse.community.model.Invitation;
import sera.sse.community.model.InvitationExample;
import sera.sse.community.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvitationService {
    @Autowired
    private InvitationMapper invitationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private InvitationExtMapper invitationExtMapper;
    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = (int) invitationMapper.countByExample(new InvitationExample());

        if(totalCount%size==0) {
            totalPage = totalCount/size;
        } else {
            totalPage = totalCount/size+1;
        }


        if(page<1) page=1;
        if(page>totalPage) page=totalPage;
        paginationDTO.setPagination(totalPage,page);

        Integer offset = size * (page-1);
        if(offset<0) offset=0;
        InvitationExample invitationExample = new InvitationExample();
        invitationExample.setOrderByClause("gmt_create desc");
        List<Invitation> invitations = invitationMapper.selectByExampleWithBLOBsWithRowbounds(invitationExample,new RowBounds(offset,size));
        List<InvitationDTO> invitationDTOList = new ArrayList<>();


        for (Invitation invitation : invitations) {
            User user = userMapper.selectByPrimaryKey(invitation.getCreator());
            InvitationDTO invitationDTO = new InvitationDTO();
            BeanUtils.copyProperties(invitation,invitationDTO);
            invitationDTO.setUser(user);
            invitationDTOList.add(invitationDTO);
        }
        paginationDTO.setData(invitationDTOList);

        return paginationDTO;
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;

        InvitationExample invitationExample = new InvitationExample();
        invitationExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int) invitationMapper.countByExample(invitationExample);

        if(totalCount%size==0) {
            totalPage = totalCount/size;
        } else {
            totalPage = totalCount/size+1;
        }


        if(page<1) page=1;
        if(page>totalPage) page=totalPage;

        paginationDTO.setPagination(totalPage,page);

        Integer offset = size * (page-1);
        if(offset<0) offset=0;

        InvitationExample example = new InvitationExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Invitation> invitations = invitationMapper.selectByExampleWithBLOBsWithRowbounds(example,new RowBounds(offset,size));
        List<InvitationDTO> invitationDTOList = new ArrayList<>();


        for (Invitation invitation : invitations) {
            User user = userMapper.selectByPrimaryKey(invitation.getCreator());
            InvitationDTO invitationDTO = new InvitationDTO();
            BeanUtils.copyProperties(invitation,invitationDTO);
            invitationDTO.setUser(user);
            invitationDTOList.add(invitationDTO);
        }
        paginationDTO.setData(invitationDTOList);

        return paginationDTO;
    }

    public InvitationDTO getById(Long id) {
        Invitation invitation = invitationMapper.selectByPrimaryKey(id);
        if(invitation == null){
            throw new CustomizeException(CustomizeErrorCode.INVITATION_NOT_FOUND);
        }
        InvitationDTO invitationDTO = new InvitationDTO();
        BeanUtils.copyProperties(invitation,invitationDTO);
        User user = userMapper.selectByPrimaryKey(invitation.getCreator());
        invitationDTO.setUser(user);
        return invitationDTO;
    }

    public void createOrUpdate(Invitation invitation) {
        if(invitation.getId()==null){
            //发新帖子
            invitation.setGmtCreate(System.currentTimeMillis());
            invitation.setGmtModified(invitation.getGmtCreate());
            invitation.setViewCount(0);
            invitation.setLikeCount(0);
            invitation.setCommentCount(0);
            invitationMapper.insertSelective(invitation);
        }
        else{
            //编辑已有帖子
            Invitation updateInvitation = new Invitation();
            updateInvitation.setGmtModified(System.currentTimeMillis());
            updateInvitation.setTitle(invitation.getTitle());
            updateInvitation.setDescription(invitation.getDescription());
            updateInvitation.setTag(invitation.getTag());
            InvitationExample example = new InvitationExample();
            example.createCriteria()
                    .andIdEqualTo(invitation.getId());
            int updated = invitationMapper.updateByExampleSelective(updateInvitation,example);
            if(updated!=1){
                throw new CustomizeException(CustomizeErrorCode.INVITATION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Invitation invitation = new Invitation();
        invitation.setId(id);
        invitation.setViewCount(1);
        invitationExtMapper.incView(invitation);
    }

    public List<InvitationDTO> selectRelated(InvitationDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), "，");
        String regexpTag = Arrays
                .stream(tags)
//                .filter(StringUtils::isNotBlank)
//                .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
//                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining("|"));
        Invitation invitation = new Invitation();
        invitation.setId(queryDTO.getId());
        invitation.setTag(regexpTag);

        List<Invitation> invitations = invitationExtMapper.selectRelated(invitation);
        List<InvitationDTO> invitationDTOS = invitations.stream().map(q -> {
            InvitationDTO invitationDTO = new InvitationDTO();
            BeanUtils.copyProperties(q, invitationDTO);
            return invitationDTO;
        }).collect(Collectors.toList());
        return invitationDTOS;
    }
}
