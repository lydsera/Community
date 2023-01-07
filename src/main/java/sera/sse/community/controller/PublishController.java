package sera.sse.community.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sera.sse.community.mapper.InvitationMapper;
import sera.sse.community.mapper.UserMapper;
import sera.sse.community.model.Invitation;
import sera.sse.community.model.User;


@Controller
public class PublishController {
    @Autowired
    private InvitationMapper invitationMapper;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/publish")
    public String publish(){
        return  "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value="title",required = false) String title,
            @RequestParam(value="description",required = false) String description,
            @RequestParam(value="tag",required = false) String tag,
            HttpServletRequest request,
            Model model)
    {
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title==null||title==""){
            model.addAttribute("error","标题不能为空！");
            return "publish";
        }
        if(description==null||description==""){
            model.addAttribute("error","描述不能为空！");
            return "publish";
        }
        if(tag==null||tag==""){
            model.addAttribute("error","标签不能为空！");
            return "publish";
        }



        User user = null;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length!=0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token"))
                {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if(user != null)
                    {
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        if(user==null){
            model.addAttribute("error","用户未登录！");
            return "publish";
        }

        Invitation invitation = new Invitation();
        invitation.setTitle(title);
        invitation.setDescription(description);
        invitation.setTag(tag);
        invitation.setCreator(user.getId());
        invitation.setGmtCreate(System.currentTimeMillis());
        invitation.setGmtModified(invitation.getGmtCreate());

        invitationMapper.create(invitation);
        return "redirect:/";
    }
}