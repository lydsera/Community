package sera.sse.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sera.sse.community.dto.InvitationDTO;
import sera.sse.community.service.InvitationService;

@Controller
public class InvitationController {
    @Autowired
    private InvitationService invitationService;
    @GetMapping("/invitation/{id}")
    public String invitation(@PathVariable(name = "id") Integer id,
                             Model model){
        InvitationDTO invitationDTO = invitationService.getById(id);
        model.addAttribute("invitation",invitationDTO);
        return "invitation";
    }
}
