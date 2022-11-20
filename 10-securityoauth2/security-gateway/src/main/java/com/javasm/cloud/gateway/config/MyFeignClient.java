package com.javasm.cloud.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-20-05:39
 * Description:
 */
@Component
public class MyFeignClient {

    static class Config {
        @Bean
        public Decoder feignDecoder() {
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            List<MediaType> mediaTypes = converter.getSupportedMediaTypes();
            ArrayList<MediaType> mediaTypeList = new ArrayList<>(mediaTypes.size() + 1);
            mediaTypeList.addAll(mediaTypes);
            mediaTypeList.add(MediaType.APPLICATION_OCTET_STREAM);
            converter.setSupportedMediaTypes(mediaTypeList);
            ObjectFactory<HttpMessageConverters> objectFactory = new ObjectFactory<HttpMessageConverters>() {
                @Override
                public HttpMessageConverters getObject() throws BeansException {
                    return new HttpMessageConverters(converter);
                }
            };
            return new ResponseEntityDecoder(new SpringDecoder(objectFactory));

        }

        private static ObjectMapper customObjectMapper(){
            return new ObjectMapper();
        }
    }


}
