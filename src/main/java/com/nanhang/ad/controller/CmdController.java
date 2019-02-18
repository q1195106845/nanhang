package com.nanhang.ad.controller;

import com.nanhang.ad.bean.User;
import com.nanhang.ad.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.beans.Transient;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author yangjianghe
 * @Date 2018/10/20 10:13
 **/
@RestController
@Slf4j
public class CmdController {
    @Autowired
    private RsaConfig rsaConfig;

    /**
     * 修改用户
     *
     * @param user
     */
    @RequestMapping("mod_user")
    public ResultMsg modUser(User user) {

        try {
            /**
             * 先查询用户所在组 如果不存在 则新增用户
             */
            if (Objects.isNull(user)) {
                LogStack.error(user, "用户信息为空");
                return ResultMsg.error("用户信息为空");
            }
            /**
             * 查询用户信息
             */
            LinkedList<String> lst = CmdUtils.getUserCN(user);
            if (lst.isEmpty()) {
                CmdUtils.addUser(user, rsaConfig.privateKey);
            }else{
                user.setCn(lst.get(0));
                CmdUtils.updateUser(user, rsaConfig.privateKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogStack.error(user, e.getMessage());
            return ResultMsg.error("更新失败，具体原因请查看日志");
        }
        LogStack.warn(user);
        Map<String,String> map = new HashMap<>();
        map.put("pass","pass");
        return ResultMsg.success("用户更新成功");
    }


    /**
     * 修改用户
     *
     * @param user
     */
    @RequestMapping("test_add")
    public ResultMsg testUser(User user) throws Exception{
        CmdUtils.addUser(user, rsaConfig.privateKey);
        return ResultMsg.success("用户更新成功");
    }


}
