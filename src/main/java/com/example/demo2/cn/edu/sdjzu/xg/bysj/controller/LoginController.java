package com.example.demo2.cn.edu.sdjzu.xg.bysj.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo2.cn.edu.sdjzu.xg.bysj.domain.User;
import com.example.demo2.cn.edu.sdjzu.xg.bysj.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

//@WebServlet("/login.ctl")
@RestController
public class LoginController extends HttpServlet {
@RequestMapping(value = "login.ctl",method = RequestMethod.POST)
    public JSONObject doPost(@RequestParam(value = "username",required = false) String username,@RequestParam(value = "password",required = false) String password,HttpServletRequest request)throws ServletException, IOException {
//      String uaername = request.getParameter("username");
//      String password = request.getParameter("password");
  JSONObject message = new JSONObject();
  try {
    User loggeduser = UserService.getInstance().login(username, password);
    if (loggeduser != null) {
      message.put("message", "登录成功");
      HttpSession session = request.getSession();
      //十分钟没有操作，则使session无效
      session.setMaxInactiveInterval(10 * 60);
      session.setAttribute("currentUser", loggeduser);
    } else {
      message.put("message", "用户名或密码错误");
    }
  } catch (SQLException e) {
    message.put("message", "数据库操作异常");
  } catch (Exception e) {
    message.put("message", "网络异常");

  }
  return message;
}


    }
