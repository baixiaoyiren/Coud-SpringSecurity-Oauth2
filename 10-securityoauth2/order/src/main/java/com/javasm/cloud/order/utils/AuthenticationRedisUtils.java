package com.javasm.cloud.order.utils;

import com.javasm.cloud.common.entity.Authentication;
import com.javasm.cloud.common.entity.Constant;
import com.javasm.cloud.common.entity.Permission;
import com.javasm.cloud.common.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-11-16:41
 * Description:
 */
@Component
@Slf4j
public class AuthenticationRedisUtils {

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private RedisCache redisCache;

    @Value("${server.servlet.context-path}")
    private String root;

    /**
     *
     * 项目启动的时候将url的权限都存到redis当中
     */
    @PostConstruct
    public void initData(){
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        //获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        Map<String, List<String>> permissionUrlMap = new HashMap<>();



        // 先将redis中旧的权限和路径删除 然后再更新当前权限
        Map<String, List<String>> permissionMap = redisCache.getCacheMap(Constant.PATH_PERMISSION);
        List<String> deletePaths = new ArrayList<>();
        for (Map.Entry<String, List<String>> stringListEntry : permissionMap.entrySet()) {
            // 如果是当前服务的路径，就删除重置
            String key = stringListEntry.getKey();
            if (key.contains(root)){
                deletePaths.add(key);
            }
        }

        for (String deletePath : deletePaths) {
            redisCache.delCacheMapValue(Constant.PATH_PERMISSION,deletePath);
        }


        //获取url的Set集合，一个方法可能对应多个url
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            //一个方法可能有多个url指向它
            Set<String> urls = entry.getKey().getPatternsCondition().getPatterns();
            // 获取权限
            Permission permissionAnnotation = entry.getValue().getMethodAnnotation(Permission.class);
            List<String> permissions = new ArrayList<>();
            if (Objects.nonNull(permissionAnnotation)){
                Authentication[] authentications = permissionAnnotation.value();
                List<String> list = Arrays.stream(authentications).map(Authentication::getValue).collect(Collectors.toList());
                permissions.addAll(list);
            }

            //将路径和权限都存入到redis当中
            urls.stream().distinct().forEach(url->{
                permissionUrlMap.put(root+url,permissions);
            });
            log.info(permissionUrlMap.toString());
            redisCache.setCacheMap(Constant.PATH_PERMISSION,permissionUrlMap);
        }

    }

}
