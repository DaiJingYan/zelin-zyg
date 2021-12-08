package com.zyg.page.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyg.common.utils.PageUtils;
import com.zyg.common.utils.Query;
import com.zyg.page.dao.ItemCatDao;
import com.zyg.page.entity.ItemCatEntity;
import com.zyg.page.service.ItemCatService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("itemCatService")
public class ItemCatServiceImpl extends ServiceImpl<ItemCatDao, ItemCatEntity> implements ItemCatService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ItemCatEntity> page = this.page(
                new Query<ItemCatEntity>().getPage(params),
                new QueryWrapper<ItemCatEntity>()
        );

        return new PageUtils(page);
    }

}