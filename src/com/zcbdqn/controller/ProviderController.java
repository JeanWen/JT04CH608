package com.zcbdqn.controller;

import com.mysql.jdbc.StringUtils;
import com.zcbdqn.exception.AuthFailException;
import com.zcbdqn.pojo.Provider;
import com.zcbdqn.pojo.User;
import com.zcbdqn.service.ProviderService;
import com.zcbdqn.util.PageSupport;
import org.apache.commons.io.FilenameUtils;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/provider")
public class ProviderController {
    private Logger logger= Logger.getLogger("ProviderController");
    @Resource
    private ProviderService providerService;

   @RequestMapping(value = "/list.html")
   public String list(@RequestParam(value = "proCode",required = false)String proCode,
                      @RequestParam(value = "proName",required = false)String proName,
                      @RequestParam(value = "pageIndex",required = false,defaultValue = "1")Integer pageIndex,
                      @RequestParam(value = "pageSize",required = false,defaultValue = "5")Integer pageSize, Model model){
       PageSupport<Provider> pageSupport = providerService.findProviders(proCode, proName, pageIndex, pageSize);
       model.addAttribute("pageSupport",pageSupport);
       return "providerlist";
   }
    @RequestMapping(value = "/addProvider.html")
    public String addProvider(){
       return "provideradd";
    }

    @RequestMapping(value = "/addProvider2.html",method = RequestMethod.GET)
    public String addProvider2(@ModelAttribute("provider") Provider provider){
        return "user/provideradd";
    }

    @RequestMapping(value = "/provideraddsave.html")
    public String addProviderSave(@Valid Provider provider, BindingResult result, HttpSession session, HttpServletRequest request,
                                  @RequestParam("attaches_provider") MultipartFile multipartFile){
       if (result.hasFieldErrors()){
           logger.info("add provider validated has errors=====================");
           return "user/provideradd";
       }
        User currentLoginUser = (User) session.getAttribute("user");
        if (null==currentLoginUser){
            throw new AuthFailException("清先登录后再操作");
        }
        provider.setCreatedBy(currentLoginUser.getId());
        provider.setCreationDate(new Date());
        String orgPicPath=null;
        if (!multipartFile.isEmpty()){
            String realPath = session.getServletContext().getRealPath("statics" + File.separator + "uploadFiles");
            new File(realPath).mkdirs();
            String filename = multipartFile.getOriginalFilename();
            String extension = FilenameUtils.getExtension(filename);
            List<String> suffixString = Arrays.asList("jpg", "bmp", "pneg", "png", "jpeg");
            if (!suffixString.contains(extension)){
                request.setAttribute("msg","图片的格式不正确！");
                return "useradd";
            }
            if (multipartFile.getSize()>1024*1024){
                request.setAttribute("msg","图片大小不超过1M！");
                return "useradd";
            }
            String randomFileName = UUID.randomUUID().toString().replace("-", "");
            try {
                multipartFile.transferTo(new File(realPath,randomFileName+"."+extension));
                provider.setOrgPicPath("statics"+File.separator+"uploadFiles"+File.separator+randomFileName+"."+extension);
            } catch (IOException e) {
                e.printStackTrace();
                return "useradd";
            }
        }
        int count = providerService.addProvider(provider);
        if (count>0){
            return "redirect:/provider/list.html";
        }else {
            request.setAttribute("msg","增加供应商失败！");
            return "provideradd";
        }
    }

    @RequestMapping(value = "/modify/{proid}")
    public String modifyProvider(@PathVariable Integer proid,Model model){
        Provider provider = providerService.findProviderById(proid);
        model.addAttribute(provider);
        return "providermodify";
    }

    @RequestMapping(value = "/modifySave.html")
    public String modifyProvider(@Valid Provider provider,BindingResult result,HttpSession session,HttpServletRequest request){
        if (result.hasFieldErrors("proName") ||
                result.hasFieldErrors("proContact") ||
                result.hasFieldErrors("proPhone") ){
            logger.info("validating provider has error");
            return "providermodify";
        }
        User user = (User) session.getAttribute("user");
        if (StringUtils.isNullOrEmpty(user.toString())){
            throw new RuntimeException("请先登录");
        }
        provider.setModifyBy(user.getId());
        provider.setModifyDate(new Date());
        int i = providerService.updateProvider(provider);
        if (i>0){
            return "redirect:/provider/list.html";
        }
        request.setAttribute("msg","修改供应商失败！");
        return "providermodify";
    }

    @RequestMapping(value = "/viewProvider/{id}",method = RequestMethod.GET)
    public String viewProvider(@PathVariable String id,Model model){
        Provider provider = providerService.findProviderById(Integer.valueOf(id));
        model.addAttribute(provider);
        return "providerview";
    }
}
