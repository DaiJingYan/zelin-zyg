package com.zyg.order.service;

import com.zyg.common.entity.PayAsyncVo;

import java.util.Map;

/**
 * Created by WF on 2021/12/24 15:14
 */
public interface PayService {
    String validSignature(PayAsyncVo vo, Map<String, String> params);
}
