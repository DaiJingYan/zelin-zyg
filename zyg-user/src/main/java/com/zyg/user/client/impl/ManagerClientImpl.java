package com.zyg.user.client.impl;

import com.zyg.common.utils.R;
import com.zyg.user.client.ManagerClient;
import org.springframework.stereotype.Component;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/15-14:53
 * ------------------------------
 */
@Component
public class ManagerClientImpl implements ManagerClient {
    @Override
    public R findAll() {
        return R.error("zyg-manager服务挂了【fallback】、。。。");
    }
}
