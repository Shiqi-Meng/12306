package org.skyemoon.index12306.frameworks.starter.user.core;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.skyemoon.index12306.framework.starter.bases.constant.UserConstant;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URLDecoder;

import static java.nio.charset.StandardCharsets.UTF_8;

public class UserTransmitFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String userId = httpServletRequest.getHeader(UserConstant.USER_ID_KEY);
        if (StringUtils.hasText(userId)) {
            String userName = httpServletRequest.getHeader(UserConstant.USER_NAME_KEY);
            String realName = httpServletRequest.getHeader(UserConstant.REAL_NAME_KEY);
            if (StringUtils.hasText(userName)) {
                userName = URLDecoder.decode(userName, UTF_8);
            }
            if (StringUtils.hasText(realName)) {
                realName = URLDecoder.decode(realName, UTF_8);
            }
            // 为什么 token 不需要验证非空？
            // 四项信息是怎么加入请求头的？
            String token = httpServletRequest.getHeader(UserConstant.USER_TOKEN_KEY);
            UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                    .userId(userId)
                    .username(userName)
                    .realName(realName)
                    .token(token)
                    .build();
            UserContext.setUser(userInfoDTO);
        }
        try {
            // filterChain做了什么工作?
            // 执行下一个过滤器，如果不是过滤器则执行servlet
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            // 为什么需要清除ThreadLocal?
            UserContext.removeUser();
        }
    }
}
