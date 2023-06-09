package com.zyg.search.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.zyg.search.entity.ItemEntity;
import com.zyg.search.entity.ItemVo;
import com.zyg.search.service.ItemSearchService;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.apache.lucene.search.BooleanQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.domain.PageRequest;
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
import java.util.stream.Collectors;

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
        System.out.println("vo = " + vo);
        /*-------------------------------- 第一部分： 关键字查询 --------------------------------*/
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

        /*-------------------------------- 第二部分： 高亮查询与聚合查询 --------------------------------*/
        //2.1 定义查询构建器对象
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //2.2 多字段查询(代表在下面指定的三个字段中只要出现了待查询的关键字，此记录都会被查询出来)
        builder.withQuery(QueryBuilders.multiMatchQuery(keywords,"title","category","brand"));

        //2.4 进行高亮查询
        builder.withHighlightBuilder(new HighlightBuilder().field("title")      //高亮查询的字段名
                .preTags("<span style='color:red'>")                            //前缀
                .postTags("</span>"));                                          //后缀
        //2.5 进行分组查询,添加聚合查询
        builder.addAggregation(AggregationBuilders.terms("categoryGroup").field("category.keyword").size(50));

        /*-------------------------------- 第三部分： 过滤查询 --------------------------------*/
        //开始过滤查询
        //组合多个查询条件
        BoolQueryBuilder booleanQuery = QueryBuilders.boolQuery();
        //3.1 进行分类的过滤查询
        if(StrUtil.isNotBlank(vo.getCategory())){
            booleanQuery.filter(QueryBuilders.termQuery("category.keyword",vo.getCategory()));
        }
        //3.2 进行品牌过滤查询
        if(StrUtil.isNotBlank(vo.getBrand())){
            booleanQuery.filter(QueryBuilders.termQuery("brand.keyword",vo.getBrand()));
        }
        //3.3 规格过滤查询
        if(vo.getSpec() != null && vo.getSpec().size() > 0){
            //3.1 遍历字符串
            for (String s : vo.getSpec()) {
                String[] split = s.split(":");
                booleanQuery.filter(QueryBuilders.termQuery("specMap." + split[0] + ".keyword",split[1]));
            }
        }
        //3.4 价格区间查询
        if(StrUtil.isNotBlank(vo.getPrice())){
            //3.4.1 拆分价格
            String price = vo.getPrice();
            String[] split = price.split("-");
            //3.4.2 添加过滤查询
            if(!split[1].equals("*")){
                booleanQuery.filter(QueryBuilders.rangeQuery("price").gte(split[0]).lt(split[1]));
            }else{
                booleanQuery.filter(QueryBuilders.rangeQuery("price").gte(split[0]));
            }
        }
        //3.5 进行排序查询
        if(StrUtil.isNotBlank(vo.getSort())){
            String sort = vo.getSort();
            //3.5.1 进行拆分
            String[] split = sort.split(":"); //split[0]: 排序字段 split[1]：排序的升还是降
            //3.5.2 进行排序
            builder.withSort(SortBuilders.fieldSort(split[0]).order(split[1].equals("asc") ? SortOrder.ASC : SortOrder.DESC));
        }
        //将布尔查询连接的多个条件与builder进行绑定
        builder.withFilter(booleanQuery);
        //3.6 开始分页
        builder.withPageable(PageRequest.of(vo.getPage()-1,vo.getPageSize()));
        //3.7 构造查询结果
        NativeSearchQuery query = builder.build();

        /*-------------------------------- 第四部分： 分析查询结果 --------------------------------*/
        //4.1 分析查询结果(参数1：代表查询对象 参数2：代表返回的结果对象 参数3：代表索引库名称)
        SearchHits<ItemEntity> searchHits = restTemplate.search(query, ItemEntity.class, IndexCoordinates.of("item"));
        long total = searchHits.getTotalHits();                             //得到总记录数
        int totalPage = (int)Math.ceil(total/(double)vo.getPageSize());  //计算总页数
        for (SearchHit<ItemEntity> searchHit : searchHits) {
            //4.1.1 得到原始数据的内容
            ItemEntity content = searchHit.getContent();
            //4.1.2 得到高亮查询标题
            List<String> title = searchHit.getHighlightField("title");
            //4.1.3 为原始的数据修改高亮数据
            if(title != null && title.size() > 0){
                content.setTitle(title.get(0));
                highlights.add(content);
            }
        }
        //4.2 得到聚合查询的结果
        ParsedStringTerms parsedStringTerms = searchHits.getAggregations().get("categoryGroup");
        List<? extends Terms.Bucket> buckets = parsedStringTerms.getBuckets();
        List<String> keys = new ArrayList<>();
        for (Terms.Bucket bucket : buckets) {
            String keyAsString = bucket.getKeyAsString();
            keys.add(keyAsString);
        }
        System.out.println("keys = " + keys);
        List<String> categoryList = buckets.stream().map(m -> m.getKeyAsString()).collect(Collectors.toList());
        System.out.println("categoryList = " + categoryList);
        //1.4 得到分类
        String category = vo.getCategory();
        Map brandAndSpecMap = new HashMap();
        if(StrUtil.isBlank(category)){       //说明前台没有提交分类
            if(categoryList != null && categoryList.size() > 0) {
                category = categoryList.get(0);
                System.out.println("category = " + category);
                //根据分类找到模板id，进而找到品牌列表及规格列表
                brandAndSpecMap = findBrandAndSpecMap(category);
            }else{
                brandAndSpecMap.put("brandList",new ArrayList<>());
                brandAndSpecMap.put("specList",new ArrayList<>());
            }
        }else{
            System.out.println("category = " + category);
            //根据分类找到模板id，进而找到品牌列表及规格列表
            brandAndSpecMap = findBrandAndSpecMap(category);
        }

        /*-------------------------------- 第五部分： 将查询结果放到结果Map --------------------------------*/
        //5.1 将高亮集合放到resultMap中
        resultMap.put("rows",highlights);
        //5.2 将分类放到大集合中
        resultMap.put("categoryList",categoryList);
        //5.3 将品牌及规格列表放到map中
        resultMap.putAll(brandAndSpecMap);
        //5.4 添加分页信息
        resultMap.put("total",total);
        resultMap.put("totalPage",totalPage);
        return resultMap;
    }

    //2. 根据分类名称找到品牌列表及规格列表
    private Map findBrandAndSpecMap(String category) {
        //2.1 根据分类名称找到模板id
        String typeId = (String) redisTemplate.boundHashOps("itemCats").get(category);
        //2.2 根据模板id，找到品牌列表及规格列表
        String brands = (String) redisTemplate.boundHashOps("brandList").get(typeId);
        List<Map> brandList = JSON.parseArray(brands, Map.class);
        String specs = (String) redisTemplate.boundHashOps("specList").get(typeId);
        List<Map> specList = JSON.parseArray(specs, Map.class);
        //2.3 定义返回 的map
        Map map = new HashMap();
        map.put("brandList",brandList);
        map.put("specList",specList);
        //2.4 返回
        return map;
    }
}


