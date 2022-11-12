package com.javasm.cloud.uaa.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-11-16:34
 * Description: 获取RSA公钥地址
 */
@RestController
public class KeyPairController {
    @Autowired
    private KeyPair keyPair;

    /**
     * 获取RSA公钥地址
     *
     * @return Map<String, Object>
     */
    @GetMapping("/rsa/publicKey")
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
}
