package com.zyg.search.service;

import com.zyg.search.entity.ItemVo;

import java.util.Map;

/**
 * Created by WF on 2021/12/3 10:30
 */
public interface ItemSearchService {
    Map<String, Object> search(ItemVo vo);
}
