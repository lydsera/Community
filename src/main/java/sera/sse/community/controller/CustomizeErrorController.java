package sera.sse.community.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class CustomizeErrorController implements ErrorController {
//    @Override
//    public String getErrorPath(){
//        return "error";
//    }
    @RequestMapping(produces = {"text/html"})
    public ModelAndView errorHtml(HttpServletRequest request,
                                  Model model) {
        HttpStatus status = this.getStatus(request);
        if(status.is4xxClientError())
        {
            model.addAttribute("message","请求有误，请修改后重试！");
        }
        if(status.is5xxServerError()){
            model.addAttribute("message","服务繁忙，请稍后再试！");
        }
        return new ModelAndView("error");
    }
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute("jakarta.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception var4) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }
}
