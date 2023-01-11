package sera.sse.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sera.sse.community.dto.CommentCreateDTO;
import sera.sse.community.dto.CommentDTO;
import sera.sse.community.dto.InvitationDTO;
import sera.sse.community.service.CommentService;
import sera.sse.community.service.InvitationService;

import java.util.List;

@Controller
public class InvitationController {
    @Autowired
    private InvitationService invitationService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/invitation/{id}")
    public String invitation(@PathVariable(name = "id") Long id,
                             Model model){
        InvitationDTO invitationDTO = invitationService.getById(id);
        List<CommentDTO> comments = commentService.listByInvitaionId(id);
        //增加阅读数
        invitationService.incView(id);
        model.addAttribute("invitation",invitationDTO);
        model.addAttribute("comments",comments);
        return "invitation";
    }
}
