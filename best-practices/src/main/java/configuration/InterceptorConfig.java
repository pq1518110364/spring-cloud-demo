//package configuration;
//
//
//import interceptor.AuthInterceptor;
//import interceptor.LogInterceptor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @author wei.xiang
// * @email wei.xiang@einyun.com
// * @date 2020/6/12 16:26
// * @Description:
// */
//@Configuration
//@Slf4j
//public class InterceptorConfig implements WebMvcConfigurer {
//
//    @Autowired
//    AuthInterceptor authInterceptor;
//    @Autowired
//    LogInterceptor logInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(logInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/css/**","/error/**","/images/**","/js/**",
//                        "/v2/api-docs", "/swagger-resources/configuration/ui",
//                        "/swagger-resources", "/swagger-resources/configuration/security", "/swagger-ui.html");
//        registry.addInterceptor(authInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/css/**","/error/**","/images/**","/js/**",
//                        "/v2/api-docs", "/swagger-resources/configuration/ui",
//                        "/swagger-resources", "/swagger-resources/configuration/security", "/swagger-ui.html","/pushReturnVisitWorkOrder");
//        //registry.addInterceptor(loginInterceptor())
//        //        .addPathPatterns("/**")
//        //        .excludePathPatterns("/css/**","/images/**","/js/**","/login.html");
//        // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录或者通过excludePathPatterns配置不需要拦截的路径
//        //多拦截器配置
//    }
//}