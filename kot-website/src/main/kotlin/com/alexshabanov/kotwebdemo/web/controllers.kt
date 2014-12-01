package com.alexshabanov.kotwebdemo.web.controllers

import org.springframework.stereotype.Controller as controller
import org.springframework.web.bind.annotation.RequestMapping as req
import org.springframework.web.bind.annotation.RequestParam as param
import javax.servlet.http.HttpServletResponse
import java.io.OutputStreamWriter
import org.springframework.ui.Model
import org.springframework.web.servlet.ModelAndView

//
// Spring MVC controllers
//

controller class PublicController {
  req(array("/test.html")) fun test(): String = "test"

  req(array("/hello.html")) fun hello(param("greeting", defaultValue = "Hello") greeting: String): ModelAndView =
      ModelAndView("hello", "greeting", greeting)
}
