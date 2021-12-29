package com.zyg.user.client.factory;

import com.zyg.common.utils.R;
import com.zyg.user.client.ManagerClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/15-15:01
 * ------------------------------
 */
@Component
public class ManagerClientFallbackFactory implements FallbackFactory<ManagerClient> {
    @Override
    public ManagerClient create(Throwable throwable) {
        return () -> R.error("zyg-manager服务挂了【fallbackFactory】。。。");
    }
}
