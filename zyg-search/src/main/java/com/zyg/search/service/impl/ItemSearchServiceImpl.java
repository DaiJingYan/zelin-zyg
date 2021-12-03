package com.zyg.search.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.zyg.search.entity.ItemEntity;
import com.zyg.search.entity.ItemVo;
import com.zyg.search.service.ItemSearchService;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/3-10:31
 * ------------------------------
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService {
    @Autowired
    private ElasticsearchRestTemplate restTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;

    //1. 根据查询参数得到结果
    @Override
    public Map<String, Object> search(ItemVo vo) {
       //1.1 定义返回的Map结果
        Map<String, Object> resultMap = new HashMap<>();
        //1.1.1 定义存放高亮数据的集合
        List<ItemEntity> highlights = new ArrayList<>();
        //1.2 得到查询关键字
        String keywords = vo.getKeywords();
        //1.3 判断是否存在
        if(StrUtil.isBlank(keywords)){
            keywords = "";
        }
        //1.4 定义查询构建器对象
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //1.5 多字段查询(代表在下面指定的三个字段中只要出现了待查询的关键字，此记录都会被查询出来)
        builder.withQuery(QueryBuilders.multiMatchQuery(keywords,"title","category","brand"));
        //1.6 进行高亮查询
        builder.withHighlightBuilder(new HighlightBuilder().field("title")      //高亮查询的字段名
                .preTags("<span style='color:red'>")                            //前缀
                .postTags("</span>"));                                          //后缀

        //1.7 构造查询结果
        NativeSearchQuery query = builder.build();
        //1.8 分析查询结果(参数1：代表查询对象 参数2：代表返回的结果对象 参数3：代表索引库名称)
        SearchHits<ItemEntity> searchHits = restTemplate.search(query, ItemEntity.class, IndexCoordinates.of("item"));
        long totalHits = searchHits.getTotalHits();         //得到总记录数
        for (SearchHit<ItemEntity> searchHit : searchHits) {
            //1.8.1 得到原始数据的内容
            ItemEntity content = searchHit.getContent();
            //1.8.2 得到高亮查询标题
            List<String> title = searchHit.getHighlightField("title");
            //1.8.3 为原始的数据修改高亮数据
            if(title != null && title.size() > 0){
                content.setTitle(title.get(0));
                highlights.add(content);
            }

        }
        //1.9 将高亮集合放到resultMap中
        resultMap.put("rows",highlights);
        return resultMap;
    }
}


