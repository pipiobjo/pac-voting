package com.prodyna.pac.sso;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * Created by bjoern on 02.07.16.
 */
@org.springframework.stereotype.Controller
@CrossOrigin(maxAge=3600)
public class Controller {
    @RequestMapping("/user")
    @ResponseBody
    public Principal user(Principal user) {
        return user;
    }
}
