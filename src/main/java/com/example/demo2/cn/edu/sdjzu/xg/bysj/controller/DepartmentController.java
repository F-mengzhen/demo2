package com.example.demo2.cn.edu.sdjzu.xg.bysj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo2.cn.edu.sdjzu.xg.bysj.domain.Department;
import com.example.demo2.cn.edu.sdjzu.xg.bysj.service.DepartmentService;
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
public class DepartmentController{
  @RequestMapping(value = "/department.ctl",method = RequestMethod.GET,produces={"application/json;charset=UTF-8"})
  public String doGet(@RequestParam(value = "id",required = false) String id_str){
    JSONObject message=new JSONObject();
    try{
      if (id_str==null){
        return reponseDepartment();
      }else {
        return responseDepartment(Integer.parseInt(id_str));
      }
    }catch (SQLException e){
      message.put("message","数据库异常");
    }catch (Exception e){
      message.put("message","网络异常");
    }
    return message.toString();
  }
  @RequestMapping(value = "/department.ctl",method = RequestMethod.DELETE)
  public JSONObject doDelete(@RequestParam(value = "id",required = false) String id_str){
    JSONObject message=new JSONObject();
    try{
      DepartmentService.getInstance().delete(Integer.parseInt(id_str));
      message.put("message","删除成功");
    }catch (SQLException e){
      message.put("message","数据库异常");
    }catch (Exception e){
      message.put("message","网络异常");
    }
    return message;
  }
  @RequestMapping(value = "/department.ctl",method = RequestMethod.POST,produces={"application/json;charset=UTF-8"})
  public JSONObject doPost(HttpServletRequest request) throws IOException {
    //根据request对象，获得代表参数的json字串
    String department_json= JSONUtil.getJSON(request);
    //将json字串解析为department对象
    Department departmentToAdd=JSON.parseObject(department_json,Department.class);
    //用大于4的随机数给DepartmentToAdd的id赋值
    departmentToAdd.setId(4 + (int) (1000 * Math.random()));
    //创建json对象，以便于向前台相应数据
    JSONObject message=new JSONObject();
    try{
      DepartmentService.getInstance().add(departmentToAdd);
      message.put("message","添加成功");
    }catch (SQLException e){
      message.put("message","数据库异常");
    }catch (Exception e){
      message.put("message","网络异常");
    }
    return message;
  }
  @RequestMapping(value = "/department.ctl",method = RequestMethod.PUT)
  public JSONObject doPut(HttpServletRequest request) throws IOException {
    String department_json=JSONUtil.getJSON(request);
    Department departmentToUpdate=JSON.parseObject(department_json,Department.class);
    JSONObject message=new JSONObject();
    try {
      DepartmentService.getInstance().update(departmentToUpdate);
      message.put("message","更新成功");
    }catch (SQLException e){
      message.put("message","数据库异常");
    }catch (Exception e){
      message.put("message","网络异常");
    }
    return message;
  }
  private String responseDepartment(int id)throws SQLException{
    Department department= DepartmentService.getInstance().find(id);
    String department_json= JSON.toJSONString(department);
    return department_json;
  }
  private String reponseDepartment()throws SQLException {
    Collection<Department> departments = DepartmentService.getInstance().findAll();
    String departments_json = JSON.toJSONString(departments, SerializerFeature.DisableCircularReferenceDetect);
    return departments_json;
  }
}
