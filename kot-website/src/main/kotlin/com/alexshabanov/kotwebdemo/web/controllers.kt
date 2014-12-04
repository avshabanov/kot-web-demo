package com.alexshabanov.kotwebdemo.web.controllers

import org.springframework.stereotype.Controller as controller
import org.springframework.web.bind.annotation.RequestMapping as req
import org.springframework.web.bind.annotation.RequestParam as param
import org.springframework.web.bind.annotation.ResponseBody as respBody
import javax.servlet.http.HttpServletResponse
import java.io.OutputStreamWriter
import org.springframework.ui.Model
import org.springframework.web.servlet.ModelAndView
import com.alexshabanov.kotwebdemo.service.UserService
import com.alexshabanov.kotwebdemo.service.GetUserProfilesResponse
import com.alexshabanov.kotwebdemo.service.GetUserProfilesRequest
import java.util.Arrays
import com.alexshabanov.kotwebdemo.service.UserProfile

//
// Spring MVC controllers
//

req(array("/g")) controller class PublicController {
  req(array("/index")) fun test(): String = "index"

  req(array("/hello")) fun hello(param("greeting", defaultValue = "Hello") greeting: String): ModelAndView =
      ModelAndView("hello", "greeting", greeting)
}

req(array("/rest")) controller class RestController(val userService: UserService) {

  req(array("/user")) respBody fun getUserProfiles(param("id") ids: Array<Long>): GetUserProfilesResponse =
      userService.getUserProfiles(GetUserProfilesRequest(userIds = ids.toArrayList())).get()
}
