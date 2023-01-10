package sera.sse.community.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sera.sse.community.dto.InvitationDTO;
import sera.sse.community.mapper.InvitationMapper;
import sera.sse.community.model.Invitation;
import sera.sse.community.model.User;
import sera.sse.community.service.InvitationService;


@Controller
public class PublishController {
    @Autowired
    private InvitationService invitationService;
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name="id") Long id,
                       Model model){
        InvitationDTO invitation = invitationService.getById(id);
        model.addAttribute("title",invitation.getTitle());
        model.addAttribute("description",invitation.getDescription());
        model.addAttribute("tag",invitation.getTag());
        model.addAttribute("id",invitation.getId());
        return "publish";
    }
    @GetMapping("/publish")
    public String publish(){
        return  "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value="title",required = false) String title,
            @RequestParam(value="description",required = false) String description,
            @RequestParam(value="tag",required = false) String tag,
            @RequestParam(value="id",required = false) Long id,
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



        User user = (User) request.getSession().getAttribute("user");

        if(user==null){
            model.addAttribute("error","用户未登录！");
            return "publish";
        }

        Invitation invitation = new Invitation();
        invitation.setTitle(title);
        invitation.setDescription(description);
        invitation.setTag(tag);
        invitation.setCreator(user.getId());
        invitation.setId(id);

        invitationService.createOrUpdate(invitation);
        return "redirect:/";
    }
}
