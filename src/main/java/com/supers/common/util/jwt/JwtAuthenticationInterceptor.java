package com.supers.common.util.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.supers.common.util.jwt.annotation.Access;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author gaoyang
 * 拦截器
 */
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        logger.info(httpServletRequest.getRequestURL().toString());
        // 从请求头中取出 token  这里需要和前端约定好把jwt放到请求头一个叫token的地方
        String authorization = httpServletRequest.getHeader("Authorization");
        String token = StringUtils.isNotEmpty(authorization) && authorization.startsWith("Bearer") ?
                authorization.replace("Bearer ","") : null;

        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(Access.class)) { // 检查是否自定义注解 accessNoToken == true，有则跳过认证
            Access access = method.getAnnotation(Access.class);
            if (access.accessNoToken()) {
                return true;
            }
        } else {
            // 执行认证
            if (null == token) {
                System.out.println("400");
            }

            Map<String, Claim> claim = JwtUtils.verifyToken(token);

            // 解析出token信息
            httpServletRequest.setAttribute("id", claim.get("id"));
            httpServletRequest.setAttribute("loginName", claim.get("loginName"));
            httpServletRequest.setAttribute("realName", claim.get("realName"));
            httpServletRequest.setAttribute("email", claim.get("email"));
            httpServletRequest.setAttribute("organizationId", claim.get("organizationId"));
            httpServletRequest.setAttribute("phone", claim.get("phone"));
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
