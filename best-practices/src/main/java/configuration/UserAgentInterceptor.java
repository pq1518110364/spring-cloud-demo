//package configuration;
//
//import com.hypersmart.system.integration.callcenter.service.BaseService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpRequest;
//import org.springframework.http.client.ClientHttpRequestExecution;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.ClientHttpResponse;
//import org.springframework.http.client.support.HttpRequestWrapper;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
///**
// * @author wei.xiang
// * @email wei.xiang@einyun.com
// * @date 2020/7/17 17:30
// * @Description:
// */
////@Component
//@Slf4j
//public class UserAgentInterceptor implements ClientHttpRequestInterceptor {
//   // @Autowired
//    private BaseService baseService;
//
//    /**
//     * Intercept the given request, and return a response. The given
//     * {@link ClientHttpRequestExecution} allows the interceptor to pass on the
//     * request and response to the next entity in the chain.
//     * <p>A typical implementation of this method would follow the following pattern:
//     * <ol>
//     * <li>Examine the {@linkplain HttpRequest request} and body</li>
//     * <li>Optionally {@linkplain HttpRequestWrapper
//     * wrap} the request to filter HTTP attributes.</li>
//     * <li>Optionally modify the body of the request.</li>
//     * <li><strong>Either</strong>
//     * <ul>
//     * <li>execute the request using
//     * {@link ClientHttpRequestExecution#execute(HttpRequest, byte[])},</li>
//     * <strong>or</strong>
//     * <li>do not execute the request to block the execution altogether.</li>
//     * </ul>
//     * <li>Optionally wrap the response to filter HTTP attributes.</li>
//     * </ol>
//     *
//     * @param request   the request, containing method, URI, and headers
//     * @param body      the body of the request
//     * @param execution the request execution
//     * @return the response
//     * @throws IOException in case of I/O errors
//     */
//    @Override
//    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
//       //设置通用请求头 tanent-id
//        return null;
//    }
//}
