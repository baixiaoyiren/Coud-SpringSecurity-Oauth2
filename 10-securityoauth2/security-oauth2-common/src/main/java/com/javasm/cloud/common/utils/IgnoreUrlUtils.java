package com.javasm.cloud.common.utils;

import com.javasm.cloud.common.entity.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-11-17:20
 * Description: url和权限白名单
 */
@Component
@Slf4j
public class IgnoreUrlUtils {
    @Autowired
    private RedisCache redisCache;

    /**
     * 获取白名单
     *
     * @return
     */
    public List<String> ignoreUrlByRedis(){
        Map<String, List<String>> map = redisCache.getCacheMap(Constant.PATH_PERMISSION);
        if (ObjectUtils.isEmpty(map)){
            return new ArrayList<>();
        }
        List<String> paths = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            List<String> permissions = entry.getValue();
            if (CollectionUtils.isEmpty(permissions)){
                paths.add(entry.getKey());
            }
            paths.add("/auth/rsa/publicKey");
        }
        return paths;
    }

}
