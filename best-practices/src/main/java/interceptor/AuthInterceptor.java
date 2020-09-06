package interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import utils.AuthUtil;
import utils.RequestUriUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @author wei.xiang
 * @email wei.xiang@einyun.com
 * @date 2020/6/12 16:01
 * @Description:
 */
@Slf4j
//@Component
public class AuthInterceptor extends BaseInterceptor {

    @Value("${oldCss.request.url}")
    private String oldCssRequestUrl;
    //@Autowired
    private RestTemplate restTemplate;



    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

//        if (!(handler instanceof HandlerMethod)) {
//            return true;
//        }
        //        获得客户机信息
        //　　getRequestURL方法返回客户端发出请求时的完整URL。
        //　　getRequestURI方法返回请求行中的资源名部分。
        //　　getQueryString 方法返回请求行中的参数部分。
        //　　getPathInfo方法返回请求URL中的额外路径信息。额外路径信息是请求URL中的位于Servlet的路径之后和查询参数之前的内容，它以“/”开头。
        //　　getRemoteAddr方法返回发出请求的客户机的IP地址。
        //　　getRemoteHost方法返回发出请求的客户机的完整主机名。
        //　　getRemotePort方法返回客户机所使用的网络端口号。
        //　　getLocalAddr方法返回WEB服务器的IP地址。
        //　　getLocalName方法返回WEB服务器的主机名。

        log.info("请求地址:{}",request.getRequestURI());
        //获取token,token校验
        String token = request.getHeader("AuthToken");
        String name = request.getHeader("UserName");
        String businessId = request.getHeader("BusinessId");
        if (StringUtils.isBlank(token)) {
            setResponse(request, response, "ACCESS_TOKEN_IS_NULL_CODE", "Error: token is null");
            return false;
        }
        if (StringUtils.isBlank(name) || StringUtils.isBlank(businessId)) {
            setResponse(request, response, "MISSING_PARAMETER", "Error:  Missing parameter");
            return false;
        }
        // 请求体打印
        RequestWrapper requestWrapper= new RequestWrapper(request);
        log.debug("请求体:{}", requestWrapper.getBody());
        String s = AuthUtil.encryteToken(name + businessId);
        log.info("new token:{}", s);
        // 3.查询token是否正确
        if (!token.equals(s)) {
            setResponse(request, response, "ACCESS_TOKEN_IS_INVALID_CODE", "Error: token is invalid");
            return false;
        }
        final boolean containUri = RequestUriUtil.containUri(request.getRequestURI());
        log.info("是否为本系统URI:{}",containUri);
        String url = oldCssRequestUrl + request.getRequestURI();
        if(!containUri){
            //默认重定向地址
            log.info("重定向地址:{}", url);
            //response.sendRedirect(oldCssRequestUrl+request.getRequestURI());
            sendRedirect(request,response,url,requestWrapper);
            return false;
        }
        // 5. 新老用户判断
        boolean b =true;// ownerInformationService.validateTenant(businessId);
        log.info("是否调用新CSS系统:{}",b);
        if (!b){
            log.info("重定向地址:{}", url);
            response.setHeader("method",request.getMethod());
            response.sendRedirect(url);
            return false;
        }
        return true;
    }



    private void sendRedirect(HttpServletRequest request, HttpServletResponse response,String url,RequestWrapper requestWrapper){
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("AuthToken",request.getHeader("AuthToken"));
        headers.add("BusinessId",request.getHeader("BusinessId"));
        headers.add("UserName",request.getHeader("UserName"));
        //是否为get请求
        ResponseEntity<String> resEntity;
        if (HttpMethod.GET.matches(request.getMethod())){
            HttpEntity<String> entity = new HttpEntity<>(headers);
            resEntity = restTemplate.exchange(url, HttpMethod.GET, entity,String.class);
        }else {
            HttpEntity<String> entity = new HttpEntity<>(requestWrapper.getBody(),headers);
            resEntity = restTemplate.exchange(url, Objects.requireNonNull(HttpMethod.resolve(request.getMethod())), entity,String.class);
        }
        if (!resEntity.getStatusCode().isError()){
            String body = resEntity.getBody();
            log.info("请求地址{}",url);
            log.info("结果:{}",body);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            try (PrintWriter out = response.getWriter()) {
                out.append(body);
            } catch (IOException e) {
                e.printStackTrace();
                setResponse(request, response, "500", e.getMessage());
            }
        }else {
            setResponse(request, response, resEntity.getStatusCode().value()+"", resEntity.getStatusCode().getReasonPhrase());
        }
    }

}

