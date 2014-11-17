package com.alexshabanov.kotwebdemo.launcher

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.DefaultServlet
import org.springframework.web.filter.CharacterEncodingFilter
import java.util.EnumSet
import javax.servlet.DispatcherType
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.servlet.DispatcherServlet


private fun initSpringContext(context : ServletContextHandler) {
  context.setInitParameter("contextConfigLocation", "classpath:/spring/service-context.xml")

  val cef = context.addFilter(javaClass<CharacterEncodingFilter>(), "/*", EnumSet.allOf(javaClass<DispatcherType>()))
  cef.setInitParameter("encoding", "UTF-8")
  cef.setInitParameter("forceEncoding", "true")

  context.addEventListener(ContextLoaderListener())

  val ds = context.addServlet(javaClass<DispatcherServlet>(), "*.html")
  ds.setInitParameter("contextConfigLocation", "classpath:/spring/webmvc-context.xml")
}

/**
 * @author Alexander Shabanov
 */
fun main(args: Array<String>) {
  val server = Server(8080)
  val context = ServletContextHandler(ServletContextHandler.NO_SESSIONS)
  context.setContextPath("/")

  initSpringContext(context)

  val defaultServletHolder = context.addServlet(javaClass<DefaultServlet>(), "/")
  defaultServletHolder.setInitParameter("resourceBase", "src/main/webapp")

  server.setHandler(context)

  server.start()
  server.join()
}

