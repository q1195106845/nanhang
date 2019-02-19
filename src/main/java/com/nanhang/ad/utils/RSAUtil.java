package com.nanhang.ad.utils;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import com.nanhang.ad.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Component
public class RSAUtil {
    @Autowired
    private RsaConfig rsaConfig;

    //生成秘钥对
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    //获取公钥(Base64编码)
    public static String getPublicKey(KeyPair keyPair) {
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return byte2Base64(bytes);
    }

    //获取私钥(Base64编码)
    public static String getPrivateKey(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return byte2Base64(bytes);
    }

    //将Base64编码后的公钥转换成PublicKey对象
    public static PublicKey string2PublicKey(String pubStr) throws Exception {
        byte[] keyBytes = base642Byte(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    //将Base64编码后的私钥转换成PrivateKey对象
    public static PrivateKey string2PrivateKey(String priStr) throws Exception {
        byte[] keyBytes = base642Byte(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    //公钥加密
    public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    //私钥解密
    public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    //字节数组转Base64编码
    public static String byte2Base64(byte[] bytes) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    //Base64编码转字节数组
    public static byte[] base642Byte(String base64Key) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(base64Key);
    }

    public  void encoderUser(User user) throws Exception{
        //将Base64编码后的私钥转换成PrivateKey对象
        PrivateKey privateKey = RSAUtil.string2PrivateKey(rsaConfig.privateKey);

        //加密后的内容Base64解码
        byte[] base642Byte = RSAUtil.base642Byte(user.getPassword());
        //用私钥解密
        byte[] privateDecrypt = RSAUtil.privateDecrypt(base642Byte, privateKey);
        user.setPassword(new String(privateDecrypt));
    }

    public static void main(String[] args) {
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhZXsufSZVYBIXDnJg1eT7lWkjMOzLkos\n" +
                "JGjuuJpDIa/MH2R1pkfKGZ73fW53Tw7NVb5cIdR5b4XYlQSXyY6bxLhIk8l945sXIIz2iPck9JT1\n" +
                "o2L3iqPN+hiSY8yz18As4QgOMbHV5iZwjJvAkLGfxsFXvJWPUCZM+NU/mJpGKwUG15r0PAY/tObV\n" +
                "VaDMWwzke1ErRKL1Ot57SXmNHlSsI4TWbCO/F9/TCMM8Sa9RdpPKO9EiR78JJ5kiSlrd+6SCfKwB\n" +
                "PwoglljxPYFewYz3U8ImX9b+iIhCcCMzOkYTNXWc2Apxd+nUpymppPniXNfIaA6VVYAj229DKKKf\n" +
                "2yXKWQIDAQAB";
        String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCFley59JlVgEhcOcmDV5PuVaSM\n" +
                "w7MuSiwkaO64mkMhr8wfZHWmR8oZnvd9bndPDs1Vvlwh1HlvhdiVBJfJjpvEuEiTyX3jmxcgjPaI\n" +
                "9yT0lPWjYveKo836GJJjzLPXwCzhCA4xsdXmJnCMm8CQsZ/GwVe8lY9QJkz41T+YmkYrBQbXmvQ8\n" +
                "Bj+05tVVoMxbDOR7UStEovU63ntJeY0eVKwjhNZsI78X39MIwzxJr1F2k8o70SJHvwknmSJKWt37\n" +
                "pIJ8rAE/CiCWWPE9gV7BjPdTwiZf1v6IiEJwIzM6RhM1dZzYCnF36dSnKamk+eJc18hoDpVVgCPb\n" +
                "b0Moop/bJcpZAgMBAAECggEAZHw8kDzwQqK2y7kkXko4muWNW92yITXmMpVETVSs/uAQeeTFowkJ\n" +
                "e3Ba1t2LncqSptcE0uXTWxKhsqa7zwAZKEc/QPwmWFnHcdLXeHwALoMGq5q8fmtte52UMMJA3iBP\n" +
                "Ig8/Lh6JuhBcRUi795vFhmrVpqQSI8ZLzDoNFqul8KFHHzAbGPki4WlPWDS8jiKf3SurcK0srUTY\n" +
                "K6w0YVbZ1xC7jQ+twMehZ0Z1wgYD/lq++R2heUhesI6rA3QCbuSAINHNcczu6xTpaZgQaZpGwfPv\n" +
                "EhNBU9YJdxtaHbMSlePQcuaWLCRxuwvLh3ZwvewmS6OJwILGRqO42tEK25ytAQKBgQC7xiVigtz+\n" +
                "BHMEvD8zF8Xp8jTPGynOV2Dx5P3ier60chn/HAqrkpUZ/yEqJyx/rknlKOBjzB6UDDLwxysLTkcx\n" +
                "2GlkysM3/qoA8Eun8o7h+xWY7DpQMTttiWlfbCMFdRF4txHaOAXscHWJqtJVbcXPNS6+zNtsaKvM\n" +
                "t9Fvcn7fUQKBgQC2H26j8d3TPl+kQ71H3VhAEdes6PFm+ipEIHbjiO3t5k8w2Z7cs7LsOYCE7+bw\n" +
                "S0KmJaRibb3kGG6jqY6daf9MG0gyhVwU3f8IEnXAxTHywqsH6SWJvFjXXf4LokgmfvVdP0AvYJI7\n" +
                "0nj5hbghYwiaswPz4kKJDtIhS4MG8fnIiQKBgD9BD4Tw6nsTip90vqF6h6w4cDu4PcwJuVmjNzdc\n" +
                "+/MTYNiEXL/RYDLitAxcJ+xXDQOKUpSrxT1AIsTjgweK9ga/jTlr6Hlra19nk0TfO+y6RVs/cKFl\n" +
                "BEzw9jMz3VRQTHNq0DO/nZc5hw66IaKvmOwlUTrrDZumYwFShtXRLxYRAoGARkMyKEXz0r/JnlIU\n" +
                "AFnA5lb74lWsbJIvJEFyHK6DPLkR7ELJK1SYvtIgZyk265XdjMQQ+3hjPlC+vp13y/tM50vPOs6A\n" +
                "i/xpm++jRk/NyV6ZJWApf04O6Gf2zv4438EzYFo6pQyY3kOBMKMKOrc+ZpCSAdFU4hO5teawEkng\n" +
                "mCECgYBEHhiT43LfAbenVCKjrufF1Geqb/06BdbS6V7vDVX5+XgeMbXg4ppjzwQ+fFf6JZ/zjWO6\n" +
                "VtvRvycjxHp3WC5srT0aCrP0m5RIdavjU+ZUVxYqJulJnBhFpD4xmsM8k8RuLkNRWVLPq2XSbVFR\n" +
                "7GFG0x14ScqZtVN7u4l5pFFpIA==";


    }

}