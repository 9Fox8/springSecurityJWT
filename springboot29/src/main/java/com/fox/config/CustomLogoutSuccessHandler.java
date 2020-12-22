package com.fox.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fox.pojo.SysUser;
import com.fox.vo.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Date 2020-12-22-下午 04:06
 * @Author fox
 */
@Slf4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        SysUser user = (SysUser) authentication.getPrincipal();
        String username = user.getUsername();
        log.info("username: {}  is offline now", username);

        responseJsonWriter(httpServletResponse, ResultUtil.sucess("退出成功"));
    }
    private static void responseJsonWriter(HttpServletResponse response, ResultUtil rest) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        String resBody = objectMapper.writeValueAsString(rest);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resBody);
        printWriter.flush();
        printWriter.close();
    }
}
