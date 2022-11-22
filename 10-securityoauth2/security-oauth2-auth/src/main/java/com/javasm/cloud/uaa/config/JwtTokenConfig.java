package com.javasm.cloud.uaa.config;

import com.javasm.cloud.uaa.entity.AuthUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-05-16:20
 * Description:
 */
@Configuration
public class JwtTokenConfig {

    /**
     * jwt内容增强器
     *
     * 这里我们把用户的id添加到jwt当中
     *
     * @return
     */
    @Bean
    public TokenEnhancer tokenEnhancer(){
        return new TokenEnhancer() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                Map<String,Object> info = new HashMap<>();
                AuthUser securityUser = (AuthUser) authentication.getPrincipal();
                info.put("enhance","这是增强的内容");
                info.put("id",securityUser.getId());

                // OAuth2AccessToken这是一个接口，要转成它的实现类才能调用set方法
                ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);

                return accessToken;
            }
        };
    }


    @Bean
    public TokenStore jwtTokenStore(JwtAccessTokenConverter jwtAccessTokenConverter){
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    /**
     * 配置该类用于TokenStore内部token和jwt的相互转换
     * //jwt转换器使用RSA非对称加密
     *

     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        //设置对称签名
        //accessTokenConverter.setSigningKey("modebing_key");
        // 配置秘钥（非对称签名）
        accessTokenConverter.setKeyPair(keyPair());
        return accessTokenConverter;
    }




    /**
     * 从classpath下的密钥库中获取密钥对(公钥+私钥) RSA
     *
     * keytool -genkeypair -alias oauth2 -keyalg RSA -keypass oauth2 -keystore oauth2.jks -storepass oauth2
     * 生成rsa证书，-storepass oauth2 ，-storepass后面的oauth2是密码
     *
     * -alias:密钥的别名
     * -keyalg:使用的hash算法
     * -keypass:密钥的访问密码
     * -keystore:密钥库文件名，changgou.jks保存了生成的证书
     * -storepass:密钥库的访问密码
     */
    @Bean
    public KeyPair keyPair()
    {
        //从classpath下的证书中获取秘钥对
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("oauth2.jks"),
                "modebing".toCharArray());
        return keyStoreKeyFactory.getKeyPair("modebing", "modebing".toCharArray());
    }


}
