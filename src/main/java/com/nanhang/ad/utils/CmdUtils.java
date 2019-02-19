package com.nanhang.ad.utils;

import com.nanhang.ad.bean.User;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.security.PrivateKey;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;

/**
 * @Author yangjianghe
 * @Date 2018/10/30 15:22
 **/
public class CmdUtils{

    /**
     * 执行CMD
     *
     * @param command
     * @return
     */
    public static LinkedList<String> execCMD(String command) {
        LinkedList<String> strings = new LinkedList<>();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                strings.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strings;
    }

    /**
     * 更新用户
     */
    public static void updateUser(User user, String key) throws Exception {
        //将Base64编码后的私钥转换成PrivateKey对象
        PrivateKey privateKey = RSAUtil.string2PrivateKey(key);
        //加密后的内容Base64解码
        byte[] base642Byte = RSAUtil.base642Byte(user.getPassword());
        //用私钥解密
        byte[] privateDecrypt = RSAUtil.privateDecrypt(base642Byte, privateKey);
        String modiftyUser = "dsmod user \"" + user.getCn() + "\" -pwd " + new String(privateDecrypt);
        if("0".equals(user.getDisabled())){
            modiftyUser += " -Disabled no";
        }else if("1".equals(user.getDisabled())){
            modiftyUser +=" -Disabled yes";
        }
        if(!StringUtils.isEmpty(user.getName())){
            modiftyUser += " -fn "+user.getName()+" -display "+user.getName()+" -desc "+user.getName()+" -ln "+user.getName();
        }
        System.out.println(modiftyUser);
        CmdUtils.execCMD(modiftyUser);
    }

    /**
     * 查询用户的CN信息
     */
    public static LinkedList<String> getUserCN(User user) {
        String queryCN = "dsquery user -name " + user.getUserName();
        return CmdUtils.execCMD(queryCN);

    }

    /**
     * 新增用户
     */
    public static void addUser(User user, String key) throws Exception {
        if(!validate(user)){
            throw  new RuntimeException("用户信息不完整");
        }
        //将Base64编码后的私钥转换成PrivateKey对象
        PrivateKey privateKey = RSAUtil.string2PrivateKey(key);
        //加密后的内容Base64解码
        byte[] base642Byte = RSAUtil.base642Byte(user.getPassword());
        //用私钥解密
        byte[] privateDecrypt = RSAUtil.privateDecrypt(base642Byte, privateKey);

        String cmd = "dsadd user \""+"CN="+user.getUserName()+","+user.getCn()+"\" -upn Solo -desc Solo -display" +
                " Solo -memberof \""+user.getGroup()+"\" -pwd "+new String(privateDecrypt)+" ";
        if("0".equals(user.getDisabled())){
            cmd += " -Disabled no";
        }else if("1".equals(user.getDisabled())){
            cmd +=" -Disabled yes";
        }

        if(!StringUtils.isEmpty(user.getName())){
            cmd += " -fn "+user.getName()+" -display "+user.getName()+" -desc "+user.getName()+" -ln "+user.getName();
        }
        System.out.println(cmd);
        CmdUtils.execCMD(cmd);
    }

    public static Boolean validate(Object t) throws IllegalAccessException {
        Class clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f: fields) {
            f.setAccessible(true);
            if(Objects.isNull(f.get(t))){
                return false;
            }
        }
        return true;
    }

}
