package com.example.demo2.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
@Order(3)
@WebFilter(filterName = "Filter20",urlPatterns = "/*"/*该过滤器适用于所有资源*/)
public class Filter20 implements Filter {
  private Set<String> exclude=new HashSet<>();
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    System.out.println("Filter20开始");
    exclude.add("/login.ctl");
    HttpServletRequest request =(HttpServletRequest)req;

    String path=request.getRequestURI();
    if (!exclude.contains(path)){

      HttpSession session=request.getSession(false);
      JSONObject message=new JSONObject();
      if (session==null||session.getAttribute("currentUser")==null){
        message.put("message","您没有登录，请登录");
        res.getWriter().println(message);
        return;
    }
    } chain.doFilter(req, res);
    System.out.println("Filter20结束");

  }

  @Override
  public void destroy() {

  }
}
