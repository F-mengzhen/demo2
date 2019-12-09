package com.example.demo2.cn.edu.sdjzu.xg.bysj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo2.cn.edu.sdjzu.xg.bysj.domain.Degree;
import com.example.demo2.cn.edu.sdjzu.xg.bysj.service.DegreeService;
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
public class DegreeController{
  @RequestMapping(value = "/degree.ctl",method = RequestMethod.GET,produces={"application/json;charset=UTF-8"})
  public String doGet(@RequestParam(value = "id",required = false) String id_str){
    JSONObject message=new JSONObject();
    try{
      if (id_str==null){
        return reponseDegree();
      }else {
        return responseDegree(Integer.parseInt(id_str));
      }
    }catch (SQLException e){
      message.put("message","数据库异常");
    }catch (Exception e){
      message.put("message","网络异常");
    }
    return message.toString();
  }
  @RequestMapping(value = "/degree.ctl",method = RequestMethod.DELETE)
  public JSONObject doDelete(@RequestParam(value = "id",required = false) String id_str){
    JSONObject message=new JSONObject();
    try{
      DegreeService.getInstance().delete(Integer.parseInt(id_str));
      message.put("message","删除成功");
    }catch (SQLException e){
      message.put("message","数据库异常");
    }catch (Exception e){
      message.put("message","网络异常");
    }
    return message;
  }
  @RequestMapping(value = "/degree.ctl",method = RequestMethod.POST,produces={"application/json;charset=UTF-8"})
  public JSONObject doPost(HttpServletRequest request) throws IOException {
    //根据request对象，获得代表参数的json字串
    String degree_json= JSONUtil.getJSON(request);
    //将json字串解析为degree对象
    Degree degreeToAdd=JSON.parseObject(degree_json,Degree.class);
    //用大于4的随机数给DepartmentToAdd的id赋值
    degreeToAdd.setId(4 + (int) (1000 * Math.random()));
    //创建json对象，以便于向前台相应数据
    JSONObject message=new JSONObject();
    try{
      DegreeService.getInstance().add(degreeToAdd);
      message.put("message","添加成功");
    }catch (SQLException e){
      message.put("message","数据库异常");
    }catch (Exception e){
      message.put("message","网络异常");
    }
    return message;
  }
  @RequestMapping(value = "/degree.ctl",method = RequestMethod.PUT)
  public JSONObject doPut(HttpServletRequest request) throws IOException {
    String degree_json=JSONUtil.getJSON(request);
    Degree degreeToUpdate=JSON.parseObject(degree_json,Degree.class);
    JSONObject message=new JSONObject();
    try {
      DegreeService.getInstance().update(degreeToUpdate);
      message.put("message","更新成功");
    }catch (SQLException e){
      message.put("message","数据库异常");
    }catch (Exception e){
      message.put("message","网络异常");
    }
    return message;
  }
  private String responseDegree(int id)throws SQLException{
    Degree degree= DegreeService.getInstance().find(id);
    String degree_json= JSON.toJSONString(degree);
    return degree_json;
  }
  private String reponseDegree()throws SQLException {
    Collection<Degree> degrees = DegreeService.getInstance().findAll();
    String degrees_json = JSON.toJSONString(degrees, SerializerFeature.DisableCircularReferenceDetect);
    return degrees_json;
  }
}
