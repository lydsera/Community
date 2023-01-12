package sera.sse.community.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import sera.sse.community.dto.PaginationDTO;
import sera.sse.community.model.User;
import sera.sse.community.service.InvitationService;
import sera.sse.community.service.NotificationService;

@Controller
public class ProfileController {
    @Autowired
    private InvitationService invitationService;
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        if("invitations".equals(action)){
            model.addAttribute("section","invitations");
            model.addAttribute("sectionName","我的帖子");
            PaginationDTO paginationDTO = invitationService.list(user.getId(), page, size);
            model.addAttribute("pagination",paginationDTO);
        }else if("replies".equals(action)){
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("section","replies");
            model.addAttribute("pagination", paginationDTO);
            model.addAttribute("sectionName","最新回复");
        }


        return "profile";
    }
}
