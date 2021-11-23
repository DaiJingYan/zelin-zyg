package com.zyg.manager.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyg.common.utils.PageUtils;
import com.zyg.common.utils.Query;

import com.zyg.manager.dao.SpecificationDao;
import com.zyg.manager.entity.SpecificationEntity;
import com.zyg.manager.service.SpecificationService;


@Service("specificationService")
public class SpecificationServiceImpl extends ServiceImpl<SpecificationDao, SpecificationEntity> implements SpecificationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpecificationEntity> page = this.page(
                new Query<SpecificationEntity>().getPage(params),
                new QueryWrapper<SpecificationEntity>()
        );

        return new PageUtils(page);
    }

}