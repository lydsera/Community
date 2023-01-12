package sera.sse.community.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sera.sse.community.dto.NotificationDTO;
import sera.sse.community.enums.NotificationTypeEnum;
import sera.sse.community.model.User;
import sera.sse.community.service.NotificationService;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Long id) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id, user);

        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_INVITATION.getType() == notificationDTO.getType()) {
            return "redirect:/invitation/" + notificationDTO.getOuterid();
        } else {
            return "redirect:/";
        }
    }
}
