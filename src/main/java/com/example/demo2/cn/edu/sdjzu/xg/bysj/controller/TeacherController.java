package com.example.demo2.cn.edu.sdjzu.xg.bysj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo2.cn.edu.sdjzu.xg.bysj.domain.Teacher;
import com.example.demo2.cn.edu.sdjzu.xg.bysj.service.TeacherService;
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
public class TeacherController{
  @RequestMapping(value = "/teacher.ctl",method = RequestMethod.GET,produces={"application/json;charset=UTF-8"})
  public String doGet(@RequestParam(value = "id",required = false) String id_str){
    JSONObject message=new JSONObject();
    try{
      if (id_str==null){
        return reponseTeacher();
      }else {
        return responseTeacher(Integer.parseInt(id_str));
      }
    }catch (SQLException e){
      message.put("message","数据库异常");
    }catch (Exception e){
      message.put("message","网络异常");
    }
    return message.toString();
  }
  @RequestMapping(value = "/teacher.ctl",method = RequestMethod.DELETE)
  public JSONObject doDelete(@RequestParam(value = "id",required = false) String id_str){
    JSONObject message=new JSONObject();
    try{
      TeacherService.getInstance().delete(Integer.parseInt(id_str));
      message.put("message","删除成功");
    }catch (SQLException e){
      message.put("message","数据库异常");
    }catch (Exception e){
      message.put("message","网络异常");
    }
    return message;
  }
  @RequestMapping(value = "/teacher.ctl",method = RequestMethod.POST,produces={"application/json;charset=UTF-8"})
  public JSONObject doPost(HttpServletRequest request) throws IOException {
    //根据request对象，获得代表参数的json字串
    String teacher_json= JSONUtil.getJSON(request);
    //将json字串解析为teacher对象
    Teacher teacherToAdd=JSON.parseObject(teacher_json,Teacher.class);
    //用大于4的随机数给TeacherToAdd的id赋值
    teacherToAdd.setId(4 + (int) (1000 * Math.random()));
    //创建json对象，以便于向前台相应数据
    JSONObject message=new JSONObject();
    try{
      TeacherService.getInstance().add(teacherToAdd);
      message.put("message","添加成功");
    }catch (SQLException e){
      message.put("message","数据库异常");
    }catch (Exception e){
      message.put("message","网络异常");
    }
    return message;
  }
  @RequestMapping(value = "/teacher.ctl",method = RequestMethod.PUT)
  public JSONObject doPut(HttpServletRequest request) throws IOException {
    String teacher_json=JSONUtil.getJSON(request);
    Teacher teacherToUpdate=JSON.parseObject(teacher_json,Teacher.class);
    JSONObject message=new JSONObject();
    try {
      TeacherService.getInstance().update(teacherToUpdate);
      message.put("message","更新成功");
    }catch (SQLException e){
      message.put("message","数据库异常");
    }catch (Exception e){
      message.put("message","网络异常");
    }
    return message;
  }
  private String responseTeacher(int id)throws SQLException{
    Teacher teacher= TeacherService.getInstance().find(id);
    String teacher_json= JSON.toJSONString(teacher);
    return teacher_json;
  }
  private String reponseTeacher()throws SQLException {
    Collection<Teacher> teachers = TeacherService.getInstance().findAll();
    String teachers_json = JSON.toJSONString(teachers, SerializerFeature.DisableCircularReferenceDetect);
    return teachers_json;
  }
}
