package com.zcbdqn.controller;

import com.alibaba.fastjson.JSON;
import com.zcbdqn.exception.AuthFailException;
import com.zcbdqn.pojo.User;
import com.zcbdqn.service.UserService;
import com.zcbdqn.util.PageSupport;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private Logger logger= Logger.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @RequestMapping(value = "/login.html")
    public String login(){
        return "login";
    }


    @RequestMapping(value = "/dologin.html")
    public String doLogin(@RequestParam(value = "userCode") String userCode,
                          @RequestParam(value = "userPassword")String userPassword,
                          HttpSession session, HttpServletRequest request){
        User user = userService.findLoginUser(userCode, userPassword);
        session.setAttribute("user",user);
        return "redirect:/main.html";
    }

    @RequestMapping(value = "/logout.html")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/user/login.html";
    }


   /* @ExceptionHandler(LoginFailException.class)
    public String handlerException(LoginFailException ex,HttpServletRequest request){
        request.setAttribute("error",ex.getMessage());
        //ex.printStackTrace();
        return "forward:/user/login.html";
    }*/


   @RequestMapping(value = "/list.html")
   public String list(@RequestParam(value = "userName",required = false)String userName,
                      @RequestParam(value = "userRole",required = false)Integer userRole,
                      @RequestParam(value = "pageIndex",required = false,defaultValue = "1")Integer pageIndex,
                      @RequestParam(value = "pageSize",required = false,defaultValue = "5")Integer pageSize, Model model){
       PageSupport<User> pageSupport = userService.findUsers(userName, userRole, pageIndex, pageSize);
       model.addAttribute("pageSupport",pageSupport);
       return "userlist";

   }

   @RequestMapping(value = "/add.html")
    public String addUser(){
       return "useradd";
   }

   @RequestMapping(value = "/addsave.html")
    public String doAddUser(User user, HttpSession session, HttpServletRequest request,
                            @RequestParam("attaches") MultipartFile multipartFile){
       User currentLoginUser = (User) session.getAttribute("user");
       if (null==currentLoginUser){
           throw new AuthFailException("清先登录后再操作");
       }
       user.setCreatedBy(currentLoginUser.getId());
       user.setCreationDate(new Date());
       //文件上传
       if (!multipartFile.isEmpty()){
           //1.保存到什么地方？
           String realPath = session.getServletContext().getRealPath("/statics"+File.separator+"uploadFiles/");
           new File(realPath).mkdirs();
           //2.验证文件是否符合要求
           //验证文件的后缀
           String filename = multipartFile.getOriginalFilename();//获得文件名字
           String fileSuffix = FilenameUtils.getExtension(filename);//获得文件名的后缀
           List<String> extensions = Arrays.asList("jpg","png","jped","bmp","gif");
           if (!extensions.contains(fileSuffix)){
               //不符合要求
               request.setAttribute("msg","图片的格式不正确！");
               return "useradd";
           }
           if (multipartFile.getSize()>1024*1024){
               request.setAttribute("msg","图片不能大于1MB！");
               return "useradd";
           }
           //3.取随机的文件名
           String randomFileName = UUID.randomUUID().toString().replace("-", "");
           try {
               multipartFile.transferTo(new File(realPath,randomFileName+"."+fileSuffix));
               user.setIdPicPath("/statics"+File.separator+"uploadFiles/"+randomFileName+"."+fileSuffix);
               logger.debug(user.getIdPicPath());
           } catch (IOException e) {
               e.printStackTrace();
               return "useradd";
           }
       }
       int i = userService.addUser(user);
       if (i>0){
        //跳转到用户列表页面
           return "redirect:/user/list.html";
       }
       //增加用户页
       request.setAttribute("msg","增加用户失败！");
       return "useradd";
   }
   @RequestMapping(value = "/modify.html")
   public String modifyUser(@RequestParam(value = "uid") Integer uid, Model model){
       User user = userService.findUserById(uid);
       model.addAttribute(user);
       return "usermodify";
   }
   @RequestMapping(value = "/modifySave.html")
    public String modifyUser(@Valid User user, BindingResult result,HttpServletRequest request,HttpSession session){
       if (result.hasFieldErrors("userName")||
               result.hasFieldErrors("phone") ||
               result.hasFieldErrors("birthday") ){
           logger.info("validating error===============================");
           return "usermodify";
       }
       User currentLoginUser = (User) session.getAttribute("user");
       if (null==currentLoginUser){
           throw new AuthFailException("清先登录后再操作");
       }
       user.setModifyBy(currentLoginUser.getId());
       user.setModifyDate(new Date());
       int i = userService.updateUser(user);
       if (i>0){
           return "redirect:/user/list.html";
       }
       request.setAttribute("msg","修改用户失败！");
       return "usermodify";
    }

    @RequestMapping(value = "/viewUser/{id}",method = RequestMethod.GET)
    public String viewUser(@PathVariable String id, Model model){
        User user = userService.findUserById(Integer.valueOf(id));
        model.addAttribute(user);
        return "userview";
    }

    @RequestMapping(value = "/viewUser",method = RequestMethod.GET)
    @ResponseBody
    public Object view(@RequestParam String uid){
        User user = userService.findUserById(Integer.valueOf(uid));
        return user;
    }

    @RequestMapping(value = "isExists.html")
    @ResponseBody
    public Object checkUserCode(@RequestParam String userCode){
        int countByUserCode = userService.findCountByUserCode(userCode);
        HashMap<String, Object> map = new HashMap<>();
        if (countByUserCode>0){
            map.put("result","false");
            map.put("message","该用户账号存在");
        }else {
            map.put("result","true");
            map.put("message","该用户账号可用");
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object deleteUser(@RequestParam String uid){
        Map<String,String> map=new HashMap<>();
        try {
            int i = userService.deleteUser(Integer.valueOf(uid));
            if (i>0){
                map.put("delResult","true");
            }else {
                map.put("delResult","notexist");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            map.put("delResult","false");
        }
        return JSON.toJSONString(map);
    }
    @RequestMapping(value = "userpwd")
    public String modifyPwd(){
       return "userpwd";
    }
    @RequestMapping(value = "modifyPwd")
    public String modifyPwd(@RequestParam String oldUserPwd,@RequestParam String newUserPwd,HttpSession session){
        User user = (User) session.getAttribute("user");

        return "userpwd";
    }
}
