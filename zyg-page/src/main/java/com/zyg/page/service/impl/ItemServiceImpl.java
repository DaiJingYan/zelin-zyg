package com.zyg.page.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jayway.jsonpath.Criteria;
import com.zyg.common.utils.PageUtils;
import com.zyg.common.utils.Query;
import com.zyg.page.dao.ItemDao;
import com.zyg.page.entity.ItemEntity;
import com.zyg.page.service.GoodsService;
import com.zyg.page.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("itemService")
public class ItemServiceImpl extends ServiceImpl<ItemDao, ItemEntity> implements ItemService {


}