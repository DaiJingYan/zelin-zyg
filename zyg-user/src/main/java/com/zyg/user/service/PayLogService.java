package com.zyg.user.service;

import com.zyg.common.entity.TbPayLog;

/**
 * Created by WF on 2021/12/22 16:00
 */
public interface PayLogService {
    void add(TbPayLog payLog);

    void update(TbPayLog payLog);

    TbPayLog findById(String outTradeNo);
}
