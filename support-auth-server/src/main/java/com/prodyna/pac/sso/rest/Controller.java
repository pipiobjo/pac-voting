package com.prodyna.pac.sso.rest;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by bjoern on 02.07.16.
 */
@org.springframework.stereotype.Controller
public class Controller {
    @RequestMapping("/user")
    @ResponseBody
    public Principal user(Principal user) {
        return user;
    }
}
