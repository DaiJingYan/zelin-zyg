package com.zyg.page.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyg.page.entity.GoodsDescEntity;
import com.zyg.page.entity.GoodsEntity;
import com.zyg.page.entity.ItemEntity;
import com.zyg.page.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
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
    @Autowired
    private ItemCatService itemCatService;
    @Autowired
    private ItemService itemService;
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
        //1.7 查询一级分类、二级分类、三级分类名称
        Long category1Id = goodsEntity.getCategory1Id();            //一级分类id
        Long category2Id = goodsEntity.getCategory2Id();            //二级分类id
        Long category3Id = goodsEntity.getCategory3Id();            //三级分类id
        String category1Name = itemCatService.getById(category1Id).getName();       //一级分类名称
        String category2Name = itemCatService.getById(category2Id).getName();       //二级分类名称
        String category3Name = itemCatService.getById(category3Id).getName();       //三级分类名称
        //1.8 将一、二、三级分类名称放到dataMap中
        dataMap.put("category1Name",category1Name);
        dataMap.put("category2Name",category2Name);
        dataMap.put("category3Name",category3Name);
        //1.9 根据goodsId查询sku列表
        List<ItemEntity> itemList = itemService.list(new QueryWrapper<ItemEntity>().eq("goods_id", id));
        //1.10 放到dataMap中
        dataMap.put("itemList",itemList);

        //1.5 构造输出流对象，指定输出的静态页面的位置
        Writer writer = new FileWriter("D:\\nginx-1.8.0\\html\\item\\" + id + ".html");

        //1.6 绑定集合到上下文对象中
        context.setVariables(dataMap);
        //参数1：代表模板的视图名 参数2：模板工作的上下文环境，参数3：指定输出的静态页面的输出流
        engine.process("item",context,writer);
    }
}
