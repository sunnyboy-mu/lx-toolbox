package cn.mu00.tools.framework.interceptor;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.context.annotation.Bean;
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

    /**
     * 注册 [Sa-Token 全局过滤器] 解决CORS跨域问题
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()

                // 指定 [拦截路由] 与 [放行路由]
                .addInclude("/**").addExclude("/favicon.ico")

                // 认证函数: 每次请求执行
                .setAuth(obj -> {
                    SaManager.getLog().debug("----- 请求path={}  提交token={}", SaHolder.getRequest().getRequestPath(), StpUtil.getTokenValue());
                })

                // 异常处理函数：每次认证函数发生异常时执行此函数
                .setError(e -> SaResult.error(e.getMessage()))

                // 前置函数：在每次认证函数之前执行
                .setBeforeAuth(obj -> {

                    // 获得客户端domain
                    SaRequest request = SaHolder.getRequest();
                    String origin = request.getHeader("Origin");
                    if (origin == null) {
                        origin = request.getHeader("Referer");
                    }

                    // ---------- 设置跨域响应头 ----------
                    SaHolder.getResponse()
                            // 允许第三方 Cookie
                            .setHeader("Access-Control-Allow-Credentials", "true")
                            // 允许指定域访问跨域资源
                            .setHeader("Access-Control-Allow-Origin", origin)
                            // 允许所有请求方式
                            .setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE")
                            // 允许的header参数
                            .setHeader("Access-Control-Allow-Headers", "x-requested-with,x-token")
                            // 有效时间
                            .setHeader("Access-Control-Max-Age", "3600")
                    ;

                    // 如果是预检请求，则立即返回到前端
                    SaRouter.match(SaHttpMethod.OPTIONS)
                            .free(r -> System.out.println("--------OPTIONS预检请求，不做处理"))
                            .back();
                })
                ;
    }
}
