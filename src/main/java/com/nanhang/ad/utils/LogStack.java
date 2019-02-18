package com.nanhang.ad.utils;

import com.nanhang.ad.bean.User;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author yangjianghe
 * @Date 2018/10/30 20:55
 **/
@Slf4j
public class LogStack {

    public static void warn(User user){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.warn("=========修改用户===========");
        log.warn("开始时间:"+simpleDateFormat.format(new Date()));
        log.warn("传入参数:"+user.toString());
        log.warn("修改成功");
        log.warn("===========================");
    }


    public static void error(User user,String context){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.error("=========修改用户===========");
        log.error("开始时间:"+simpleDateFormat.format(new Date()));
        log.error("传入参数:"+user.toString());
        log.error("失败原因:"+context);
        log.error("===========================");
    }
}
