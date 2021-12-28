package com.zyg.user.service.impl;

import com.zyg.common.entity.TbPayLog;
import com.zyg.user.dao.TbPayLogMapper;
import com.zyg.user.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/22-16:00
 * ------------------------------
 */
@Service
public class PayLogServiceImpl implements PayLogService {
    
    @Autowired
    private TbPayLogMapper payLogMapper;
    
    //1. 添加支付日志
    @Override
    @Transactional
    public void add(TbPayLog payLog) {
        // int i = 10 / 0;
        payLogMapper.insert(payLog);
    }

    //2. 修改支付日志
    @Override
    @Transactional
    public void update(TbPayLog payLog) {
        payLogMapper.updateById(payLog);
    }

    @Override
    public TbPayLog findById(String outTradeNo) {
        return payLogMapper.selectById(outTradeNo);
    }
}
