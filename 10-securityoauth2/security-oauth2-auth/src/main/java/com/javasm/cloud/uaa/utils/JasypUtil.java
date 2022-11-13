package com.javasm.cloud.uaa.utils;
 
/**
 * @author 杨帅帅
 * @time 2021/12/5 - 1:00
 */

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

public class JasypUtil {
 
    private static final String PBEWITHHMACSHA512ANDAES_256 = "PBEWITHHMACSHA512ANDAES_256";
 
    /**
     * @Description: Jasyp 加密（PBEWITHHMACSHA512ANDAES_256）
     * @Author:      Rambo
     * @CreateDate:  2020/7/25 14:34
     * @UpdateUser:  Rambo
     * @UpdateDate:  2020/7/25 14:34
     * @param		 plainText  待加密的原文
     * @param		 factor     加密秘钥
     * @return       java.lang.String
     * @Version:     1.0.0
     */
    public static String encryptWithSHA512(String plainText, String factor) {
        // 1. 创建加解密工具实例
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        // 2. 加解密配置
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(factor);
        config.setAlgorithm(PBEWITHHMACSHA512ANDAES_256);
        // 为减少配置文件的书写，以下都是 Jasyp 3.x 版本，配置文件默认配置
        config.setKeyObtentionIterations( "1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        // 3. 加密
        return encryptor.encrypt(plainText);
    }
 
    /**
     * @Description: Jaspy解密（PBEWITHHMACSHA512ANDAES_256）
     * @Author:      Rambo
     * @CreateDate:  2020/7/25 14:40
     * @UpdateUser:  Rambo
     * @UpdateDate:  2020/7/25 14:40
     * @param		 encryptedText  待解密密文
     * @param		 factor         解密秘钥
     * @return       java.lang.String
     * @Version:     1.0.0
     */
    public static String decryptWithSHA512(String encryptedText, String factor) {
        // 1. 创建加解密工具实例
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        // 2. 加解密配置
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(factor);
        config.setAlgorithm(PBEWITHHMACSHA512ANDAES_256);
        // 为减少配置文件的书写，以下都是 Jasyp 3.x 版本，配置文件默认配置
        config.setKeyObtentionIterations( "1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        // 3. 解密
        return encryptor.decrypt(encryptedText);
    }

    public static void main(String[] args) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        // 类似于加盐
        String password = "modebing";
        String text = "";
        standardPBEStringEncryptor.setConfig(config);
        standardPBEStringEncryptor.setPassword(password);
        String encrypt = standardPBEStringEncryptor.encrypt(text);
        System.out.println(encrypt);
    }
}