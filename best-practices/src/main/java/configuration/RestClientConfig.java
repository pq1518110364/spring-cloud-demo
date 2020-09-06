package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2020/6/11 11:17
 * @Description:
 */
@Configuration
public class RestClientConfig {
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {

        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        //restTemplate.setInterceptors(Collections.singletonList(new UserAgentInterceptor()));
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
//       SimpleClientHttpRequestFactory factory=new SimpleClientHttpRequestFactory();
//      上一行被注释掉的是Spring自己的实现，下面是依赖了httpclient包后的实现
        //TODO pacth 提交有问题,更改工厂类
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        return factory;
    }
}

