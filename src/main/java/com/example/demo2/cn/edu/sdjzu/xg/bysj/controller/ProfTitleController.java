package com.example.demo2.cn.edu.sdjzu.xg.bysj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo2.cn.edu.sdjzu.xg.bysj.domain.ProfTitle;
import com.example.demo2.cn.edu.sdjzu.xg.bysj.service.ProfTitleService;
import com.example.demo2.util.JSONUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@RestController
public class ProfTitleController{
  @RequestMapping(value = "/profTitle.ctl",method = RequestMethod.GET,produces={"application/json;charset=UTF-8"})
  public String doGet(@RequestParam(value = "id",required = false) String id_str){
    JSONObject message=new JSONObject();
    try{
      if (id_str==null){
        return reponseProfTitle();
      }else {
        return responseProfTitle(Integer.parseInt(id_str));
      }
    }catch (SQLException e){
      message.put("message","数据库异常");
    }catch (Exception e){
      message.put("message","网络异常");
    }
    return message.toString();
  }
  @RequestMapping(value = "/profTitle.ctl",method = RequestMethod.DELETE)
  public JSONObject doDelete(@RequestParam(value = "id",required = false) String id_str){
    JSONObject message=new JSONObject();
    try{
      ProfTitleService.getInstance().delete(Integer.parseInt(id_str));
      message.put("message","删除成功");
    }catch (SQLException e){
      message.put("message","数据库异常");
    }catch (Exception e){
      message.put("message","网络异常");
    }
    return message;
  }
  @RequestMapping(value = "/profTitle.ctl",method = RequestMethod.POST,produces={"application/json;charset=UTF-8"})
  public JSONObject doPost(HttpServletRequest request) throws IOException {
    //根据request对象，获得代表参数的json字串
    String profTitle_json= JSONUtil.getJSON(request);
    //将json字串解析为profTitle对象
    ProfTitle profTitleToAdd=JSON.parseObject(profTitle_json,ProfTitle.class);
    //用大于4的随机数给ProfTitleToAdd的id赋值
    profTitleToAdd.setId(4 + (int) (1000 * Math.random()));
    //创建json对象，以便于向前台相应数据
    JSONObject message=new JSONObject();
    try{
      ProfTitleService.getInstance().add(profTitleToAdd);
      message.put("message","添加成功");
    }catch (SQLException e){
      message.put("message","数据库异常");
    }catch (Exception e){
      message.put("message","网络异常");
    }
    return message;
  }
  @RequestMapping(value = "/profTitle.ctl",method = RequestMethod.PUT)
  public JSONObject doPut(HttpServletRequest request) throws IOException {
    String profTitle_json=JSONUtil.getJSON(request);
    ProfTitle profTitleToUpdate=JSON.parseObject(profTitle_json,ProfTitle.class);
    JSONObject message=new JSONObject();
    try {
      ProfTitleService.getInstance().update(profTitleToUpdate);
      message.put("message","更新成功");
    }catch (SQLException e){
      message.put("message","数据库异常");
    }catch (Exception e){
      message.put("message","网络异常");
    }
    return message;
  }
  private String responseProfTitle(int id)throws SQLException{
    ProfTitle profTitle= ProfTitleService.getInstance().find(id);
    String profTitle_json= JSON.toJSONString(profTitle);
    return profTitle_json;
  }
  private String reponseProfTitle()throws SQLException {
    Collection<ProfTitle> profTitles = ProfTitleService.getInstance().findAll();
    String profTitles_json = JSON.toJSONString(profTitles, SerializerFeature.DisableCircularReferenceDetect);
    return profTitles_json;
  }
}
