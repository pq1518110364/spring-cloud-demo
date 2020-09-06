package configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;

/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2020/7/17 15:13
 * @Description:
 */
@Configuration
public class WebConfigBeans {

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    /**
     * 增加字符串转日期的功能
     */

    @PostConstruct
    public void initEditableAvlidation() {

        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer)handlerAdapter.getWebBindingInitializer();
        assert initializer != null;
        if(initializer.getConversionService()!=null) {
            GenericConversionService genericConversionService = (GenericConversionService)initializer.getConversionService();
            genericConversionService.addConverter(new DateConverter());

        }

    }
}
