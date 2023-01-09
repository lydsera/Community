package sera.sse.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sera.sse.community.dto.InvitationDTO;
import sera.sse.community.dto.PaginationDTO;
import sera.sse.community.mapper.InvitationMapper;
import sera.sse.community.mapper.UserMapper;
import sera.sse.community.model.Invitation;
import sera.sse.community.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvitationService {
    @Autowired
    private InvitationMapper invitationMapper;
    @Autowired
    private UserMapper userMapper;
    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = invitationMapper.count();

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
        List<Invitation> invitations = invitationMapper.list(offset,size);
        List<InvitationDTO> invitationDTOList = new ArrayList<>();


        for (Invitation invitation : invitations) {
            User user = userMapper.findById(invitation.getCreator());
            InvitationDTO invitationDTO = new InvitationDTO();
            BeanUtils.copyProperties(invitation,invitationDTO);
            invitationDTO.setUser(user);
            invitationDTOList.add(invitationDTO);
        }
        paginationDTO.setInvitations(invitationDTOList);

        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = invitationMapper.countByUserId(userId);

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
        List<Invitation> invitations = invitationMapper.listByUserId(userId,offset,size);
        List<InvitationDTO> invitationDTOList = new ArrayList<>();


        for (Invitation invitation : invitations) {
            User user = userMapper.findById(invitation.getCreator());
            InvitationDTO invitationDTO = new InvitationDTO();
            BeanUtils.copyProperties(invitation,invitationDTO);
            invitationDTO.setUser(user);
            invitationDTOList.add(invitationDTO);
        }
        paginationDTO.setInvitations(invitationDTOList);

        return paginationDTO;
    }
}
