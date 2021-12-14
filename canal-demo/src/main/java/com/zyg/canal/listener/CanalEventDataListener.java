package com.zyg.canal.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.xpand.starter.canal.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

@CanalEventListener
public class CanalEventDataListener {

    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 功能: 监听插入事件
     * 参数:
     * 返回值: void
     * 时间: 2021/8/10 13:07
     */
    // @InsertListenPoint
    // public void onEventInsert(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
    //     System.out.println("onEventInsert");
    //     System.out.println("------------------------------------------------------------------------------------");
    //     rowData.getAfterColumnsList().stream().forEach(c-> System.out.println(c.getName() + "->" + c.getValue()));
    // }
    /**
     * 功能: 监听修改事件
     * 参数:
     * 返回值: void
     * 时间: 2021/8/10 13:07
     */
    // @UpdateListenPoint
    // public void onEventUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
    //     System.out.println("onEventUpdate");
    //     System.out.println("------------------------------------------------------------------------------------");
    //     rowData.getAfterColumnsList().stream().forEach(c-> System.out.println(c.getName() + "->" + c.getValue()));
    //
    // }
    /**
     * 功能: 监听删除事件
     * 参数:
     * 返回值: void
     * 时间: 2021/8/10 13:07
     */
    // @DeleteListenPoint
    // public void onEventDelete(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
    //     System.out.println("onEventDelete");
    //     System.out.println("------------------------------------------------------------------------------------");
    //     rowData.getBeforeColumnsList().stream().forEach(c-> System.out.println( c.getName() + "->" + c.getValue()));
    // }

    /**
     * 功能: 自定义监听
     * 参数:
     * 返回值: void
     * 时间: 2021/8/10 13:06
     */
    @ListenPoint(destination = "example",schema = "zyg_manager",table = {"tb_content"},
            eventType = {
                CanalEntry.EventType.UPDATE,
                CanalEntry.EventType.INSERT,
                CanalEntry.EventType.DELETE}
                )
    public void onEventCustomUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        System.out.println("从redis删除广告列表。。。");
        redisTemplate.delete("contentList");
    }

}
