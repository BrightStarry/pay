package com.jia.pay.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.jia.pay.interceptor.BlankIpInterceptor;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @author  ZhengXing
 * createTime: 2018/7/3
 * desc:  配置类
 */
@Configuration
public class Config extends WebMvcConfigurerAdapter{

    private final BlankIpInterceptor blankIpInterceptor;

    public Config(BlankIpInterceptor blankIpInterceptor) {
        this.blankIpInterceptor = blankIpInterceptor;
    }

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(blankIpInterceptor).addPathPatterns("/pay/**","/query/**");
        super.addInterceptors(registry);
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * SQL执行效率插件, 以及打印日志
     */
    @Bean
    @Profile({"local"})// 设置 local 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    /**
     * 404异常处理路径
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        return container -> {
            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
        };
    }
}
