package com.javasm.cloud.uaa.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javasm.cloud.common.entity.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebUtils
{
    /**
     * 将字符串渲染到客户端
     * 
     * @param response 渲染对象
     * @param string 待渲染的字符串
     * @return null
     */
    public static void renderString(HttpServletResponse response, String string) {
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public static void accessDeniedHandler(HttpServletResponse response, Response resp) {
        try
        {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] bytes = objectMapper.writeValueAsBytes(resp);
            response.getWriter().print(new String(bytes));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
