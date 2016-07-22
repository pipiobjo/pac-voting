package com.prodyna.pac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.prodyna.pac.repo.UserGraphRepository;

@RestController
public class UserController {

	  @Autowired
	    UserGraphRepository userRepository;
}
