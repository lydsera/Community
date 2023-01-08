package sera.sse.community.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sera.sse.community.dto.InvitationDTO;
import sera.sse.community.mapper.InvitationMapper;
import sera.sse.community.mapper.UserMapper;
import sera.sse.community.model.Invitation;
import sera.sse.community.model.User;
import sera.sse.community.service.InvitationService;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private InvitationService invitationService;
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model)
    {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length!=0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token"))
                {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if(user != null)
                    {
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        List<InvitationDTO> invitationList = invitationService.list();
        model.addAttribute("invitations",invitationList);
        return "index";
    }

}
