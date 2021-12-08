package com.zyg.page.service.impl;

import com.zyg.page.entity.GoodsDescEntity;
import com.zyg.page.entity.GoodsEntity;
import com.zyg.page.service.GoodsDescService;
import com.zyg.page.service.GoodsService;
import com.zyg.page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/8-14:45
 * ------------------------------
 */
@Service
public class PageServiceImpl implements PageService {
    @Autowired
    private TemplateEngine engine;          //引入thymeleaf的模板引擎
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsDescService goodsDescService;
    //1. 根据id生成html页面
    @Override
    public void createHtml(Long id) throws IOException {       //id：代表spu商品id
        //1.1  定义模板工作的上下文对象
        Context context = new Context();
        //1.2 定义上下文环境关联的数据集合
        Map<String, Object> dataMap = new HashMap<>();


        //1.3 查询spu商品对象
        GoodsEntity goodsEntity = goodsService.getById(id);
        System.out.println("goodsEntity = " + goodsEntity);
        //1.4 将商品放到dataMap中
        dataMap.put("goods",goodsEntity);
        //1.5 根据id查询商品描述对象
        GoodsDescEntity goodsDescEntity = goodsDescService.getById(id);
        //1.6 放到map中
        dataMap.put("goodsDesc",goodsDescEntity);

        //1.5 构造输出流对象，指定输出的静态页面的位置
        Writer writer = new FileWriter("D:\\nginx-1.8.0\\html\\item\\" + id + ".html");

        //1.6 绑定集合到上下文对象中
        context.setVariables(dataMap);
        //参数1：代表模板的视图名 参数2：模板工作的上下文环境，参数3：指定输出的静态页面的输出流
        engine.process("item",context,writer);
    }
}
