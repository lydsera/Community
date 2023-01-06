package sera.sse.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sera.sse.community.dto.AccessTokenDTO;
import sera.sse.community.dto.GithubUser;
import sera.sse.community.provider.GithubProvider;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state)
    {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
//        accessTokenDTO.setClient_id("4baac59f57313f3bdbdd");
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setCode(code);
//        accessTokenDTO.setClient_secret("bc323959eba052a1b37c2d015cd5927b66de1071");
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setState(state);
//        accessTokenDTO.setRedirect_url("http://localhost:8887/callback");
        accessTokenDTO.setRedirect_url(redirectUrl);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
