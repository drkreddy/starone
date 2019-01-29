package com.raditech.star;


import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExampleController {
    private static String authorizationRequestBaseUri = "oauth2/authorization";
    Map<String,String> oauth2AuthUrls = new HashMap<>();

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;


    @RequestMapping("/")
    public String email(Principal principal) {
        return "Hello " + principal.getName();
    }

    @GetMapping("/oauth_login")
    public String getLoginPage(Model model) {
        // ...
        Iterable<ClientRegistration> clientRegistrations= null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
        if(type!=ResolvableType.NONE &&
        ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])){
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }
        clientRegistrations.forEach(clientRegistration ->
                oauth2AuthUrls.put(clientRegistration.getClientName(),
                        authorizationRequestBaseUri + "/" + clientRegistration.getRegistrationId()));
        model.addAttribute("urls",oauth2AuthUrls);
        System.out.println(oauth2AuthUrls);
        model.addAttribute("msg","ravindra");

        return "oauth_login";
    }

    @GetMapping("/loginSuccess")
    public String getLoginInfo(Model model, OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());
        //...
        return "loginSuccess";
    }
    @GetMapping("/")
    public String getLoginInfo() {
        //...
        return "loginSuccess";
    }

}