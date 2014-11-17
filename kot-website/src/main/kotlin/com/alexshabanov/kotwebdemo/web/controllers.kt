package com.alexshabanov.kotwebdemo.web.controllers

import org.springframework.stereotype.Controller as controller
import org.springframework.web.bind.annotation.RequestMapping as req
import org.springframework.web.bind.annotation.RequestParam as param
import javax.servlet.http.HttpServletResponse
import java.io.OutputStreamWriter

//
// Spring MVC controllers
//

controller class PublicController {
  req(array("/index.html")) fun index(): String = "index"

  req(array("/hello.html")) fun hello(r : HttpServletResponse,
                                      param("greeting", defaultValue = "Hello") greeting : String) {
    val writer = OutputStreamWriter(r.getOutputStream())
    writer.append("<p>" + greeting + " from Spring!</p>")
    writer.close() // ugly :(
  }
}
