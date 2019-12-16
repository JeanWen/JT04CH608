package com.zcbdqn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @RequestMapping(value = "/index.html",method = RequestMethod.GET)//index.html?username=admin
    public ModelAndView index(@RequestParam(value="username" ,required = false,defaultValue = "test") String username,
                              @RequestParam(value="password" ,required = false) String password){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("msg","index__HelloSpringMVC!!!");
        modelAndView.addObject("hehe");

        Map<String,Object> map=new HashMap<>();
        map.put("test1","test1");
        map.put("test2","test2");
        modelAndView.addAllObjects(map);

        modelAndView.setViewName("index");
        System.out.println("index-param:"+username);
        System.out.println("index-param:"+password);

        return modelAndView;
    }


    @RequestMapping(value = "/index1.html",method = RequestMethod.GET)//index.html?username=admin
    public String index1(Model model){
        model.addAttribute("msg","index1__HelloSpringMVC!!!");
        model.addAttribute("hehe","hehe");//model.addAttribute("string","hehe");
        Map<String,Object> map=new HashMap<>();
        map.put("test1","test1");
        map.put("test2","test2");
        model.addAllAttributes(map);

        List list=new ArrayList<>();
        list.add("xixi");//model.addAttribute("string","xixi");
        list.add(1000);

        model.addAllAttributes(list);
        //视图名
        return "index";
    }
    @RequestMapping(value = "/index2.html")//代表请求MIME类型,    .html
    public String index2(Map<String,Object> map){

        map.put("msg","index1__HelloSpringMVC!!!");
        map.put("hehe","hehe");//model.addAttribute("string","hehe");
        map.put("test1","test1");
        map.put("test2","test2");

        //视图名
        return "success";
    }


    @RequestMapping(value = "/test.html")
    public ModelAndView test(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("msg","test__HelloSpringMVC!!!");
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/main.html")
    public String main(){
        return "frame";
    }
}
