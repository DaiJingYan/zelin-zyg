package com.zyg.search.controller;

import com.zyg.search.entity.ItemVo;
import com.zyg.search.service.ItemSearchService;
import org.elasticsearch.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/3-10:21
 * ------------------------------
 */
@Controller
public class SearchController {
    @Autowired
    private ItemSearchService searchService;
    //1. 映射默认页面
    @GetMapping({"/","/search.html","/search"})
    public String index(Model model, ItemVo vo){    //vo：用于查询参数
        //2.1 通过前端传入的参数查询到数据
        Map<String,Object> resultMap = searchService.search(vo);
        //2.2 将查询结果放到model中
        model.addAttribute("resultMap",resultMap);
        // System.out.println("resultMap = " + resultMap);
        model.addAttribute("vo",vo);
        //1.3 返回逻辑视图
        return "search";
    }

}
