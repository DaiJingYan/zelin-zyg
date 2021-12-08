package com.zyg.page.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyg.common.utils.PageUtils;
import com.zyg.common.utils.Query;
import com.zyg.page.dao.GoodsDao;
import com.zyg.page.entity.GoodsEntity;
import com.zyg.page.service.GoodsService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsDao, GoodsEntity> implements GoodsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //1. 包装查询条件
        QueryWrapper<GoodsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("audit_status","0");
        //2. 开始条件带分页查询
        IPage<GoodsEntity> page = this.page(
                new Query<GoodsEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    /**
     * 功能: 商品审核
     * 参数:
     * 返回值: void
     * 时间: 2021/10/26 16:15
     */
    @Override
    public void updateStatus(Long id, String status) {
        //1. 根据商品id查询出商品
        GoodsEntity goodsEntity = baseMapper.selectById(id);
        //2. 修改商品的状态值
        goodsEntity.setAuditStatus(status);
        //3. 修改商品
        baseMapper.updateById(goodsEntity);
    }

}