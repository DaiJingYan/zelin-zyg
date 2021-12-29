package com.zyg.user.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.zyg.user.dao.TbUserMapper;
import com.zyg.user.entity.TbUser;
import com.zyg.user.service.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * ------------------------------
 * 功能：
 * 作者：WF
 * 微信：hbxfwf13590332912
 * 创建时间：2021/12/13-14:44
 * ------------------------------
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private TbUserMapper userMapper;
    /**
     * 功能:
     * 1. 生成验证，同时发送信息给阿里大于【利用RabbitMQ发送消息】
     * 2. 将生成的验证放到redis中（以手机号为key）
     * 参数:
     * 返回值:
     * 时间: 2021/12/13 14:45
     */
    @Override
    public void createCode(String phone) {
        //1.1 生成一个随机的验证码
        int min = 100000;   //六位数的最小值
        int max = 999999;   //六位数的最大值
        Random random = new Random();
        int code = random.nextInt(max-min);  //得到一个[0,max-min)范围的随机数
        if(code < min){
            code = code + min ;
        }
        System.out.println("生成的六位数的验证码是：" + code);

        //1.2 将随机数保存到redis中
        redisTemplate.opsForValue().set(phone,code + "",10, TimeUnit.DAYS);

        //1.3 通过rabbitmq发送消息给消息的监听方
        //1.3.1 组织数据
        Map<String,String> map = new HashMap<>();
        map.put("phone",phone);
        map.put("code",code + "");
        //1.3.2 发送数据
        rabbitTemplate.convertAndSend("sms",map);

    }

    //2. 校验验证码查否合法
    @Override
    public boolean isExistsCode(String validcode, String phone) {
        String code = redisTemplate.opsForValue().get(phone);
        if(StrUtil.isNotBlank(code) && code.equals(validcode)){
            return true;
        }
        return false;
    }

    //3. 添加用户
    @Override
    public void add(TbUser user) {
        //3.1 加密密码
        String s = SecureUtil.md5(user.getPassword());
        //3.2 重新设置回去
        user.setPassword(s);
        //3.3 保存用户
        userMapper.insert(user);
    }

    //4. 根据用户id查询用户对象
    @Override
    public TbUser findById(Long id) {
        return userMapper.selectById(id);
    }
}
