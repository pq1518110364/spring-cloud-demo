package utils;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;

/**
 * 获取所有requestmapping的url路径
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2020/7/17 9:38
 * @Description:
 */
@Component
@Slf4j
public class RequestUriUtil {

    @Autowired
    WebApplicationContext applicationContext;

    private static final Set<String> Uris= Sets.newHashSet();

    @PostConstruct
    public void initUri(){
        RequestMappingHandlerMapping mapping = applicationContext
                .getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping
                .getHandlerMethods();
        for (RequestMappingInfo info : map.keySet()) {
            // 获取url的Set集合，一个方法可能对应多个url
            Set<String> patterns = info.getPatternsCondition().getPatterns();
            //把结果存入静态变量中程序运行一次次方法之后就不用再次请求次方法
            Uris.addAll(patterns);
        }
    }

    /**
     * 是否包含请求地址
     * @author : wei.xiang@einyun.com
     * @date :  2020/7/17 9:44
     *
     * @param uri uri
     * @return boolean
     */
    public static boolean containUri(String uri){
        return Uris.contains(uri);
    }
}
