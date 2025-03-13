package cn.mu00.tools.framework.interceptor;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenInterceptor implements WebMvcConfigurer {
    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handle -> {
                    System.out.println("拦截器被触发");
                    StpUtil.checkLogin();
                }))
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/oauth/login", "/**.interface");
    }
}
