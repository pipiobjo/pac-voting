package com.shardis.ui.controllers.web;

import com.shardis.utils.EnvironmentProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Tomasz Kucharzyk
 */

@Controller
@RequestMapping("/")
public class MainPageController {

  @Autowired
  EnvironmentProvider environmentProvider;

  @ResponseStatus(HttpStatus.OK)
  @RequestMapping("/")
  public ModelAndView mainPage() {
      return new ModelAndView("forward://index.html");
  }
}
