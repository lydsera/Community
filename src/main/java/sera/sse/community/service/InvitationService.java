package sera.sse.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sera.sse.community.dto.InvitationDTO;
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
    public List<InvitationDTO> list() {
        List<Invitation> invitations = invitationMapper.list();
        List<InvitationDTO> invitationDTOList = new ArrayList<>();
        for (Invitation invitation : invitations) {
            User user = userMapper.findById(invitation.getCreator());
            InvitationDTO invitationDTO = new InvitationDTO();
            BeanUtils.copyProperties(invitation,invitationDTO);
            invitationDTO.setUser(user);
            invitationDTOList.add(invitationDTO);
        }
        return invitationDTOList;
    }
}
