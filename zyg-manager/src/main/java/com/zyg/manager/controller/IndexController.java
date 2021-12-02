package com.zyg.manager.controller;

import com.zyg.manager.entity.ContentEntity;
import com.zyg.manager.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/2-10:29
 * ------------------------------
 */
@Controller
@RequestMapping
public class IndexController {
    @Autowired
    private ContentService contentService;
    //1. 默认页面
    @GetMapping({"/","/index.html"})
    public String index(Model model){
        //1.1 查询广告
        List<ContentEntity> list = contentService.findAll();
        //1.2 放到model中
        model.addAttribute("contentList",list);
        return "index";   // 物理视图：/templates/index.html
    }

}
