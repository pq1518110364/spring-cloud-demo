package configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author xiangwei
 * @date 2020-08-16 10:31 上午
 */
//@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 视图控制器配置
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/index");//设置默认跳转视图为 /index
        registry.addViewController("/error/500").setViewName("/error/500");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);

    }

}
