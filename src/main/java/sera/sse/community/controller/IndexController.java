package sera.sse.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sera.sse.community.dto.PaginationDTO;
import sera.sse.community.service.InvitationService;

@Controller
public class IndexController {

    @Autowired
    private InvitationService invitationService;
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size)
    {


        PaginationDTO pagination = invitationService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }

}
