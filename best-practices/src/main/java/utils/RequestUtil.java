//package utils;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.google.common.collect.Lists;
//import com.hypersmart.base.exception.BaseException;
//import com.hypersmart.base.util.Base64;
//import com.hypersmart.base.util.BeanUtils;
//import com.hypersmart.system.integration.callcenter.bo.RequestBO;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.utils.URIBuilder;
//import org.apache.http.message.BasicNameValuePair;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Component;
//import org.springframework.util.DigestUtils;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URI;
//import java.util.List;
//import java.util.Objects;
//import java.util.Set;
//
//@Component
//@Slf4j
//public class RequestUtil {
//    @Value("${mdm.sign.access_key}")
//    private String ACCESS_KEY;
//    @Value("${mdm.sign.access_secret}")
//    private String ACCESS_SECRET;
//    @Autowired
//    private RestTemplate restTemplate;
//
//    /**
//     * 数据请求
//     * @author : wei.xiang@einyun.com
//     * @date :  2020/6/11 11:06
//     * @param request ob封装
//     * @return java.lang.String
//     */
//    public String queryFilterRequest(RequestBO request)  {
//        String method = request.getRequestMethod();
//        String url = request.getRequestUrl();
//        log.info("请求地址:{}",url);
//        String params = null;
//        if (request.getRequestParam() != null) {
//            params = JSONArray.toJSONString(request.getRequestParam());
//        }
//        String tenantId = request.getTenantId();
//        log.info("tenantId:{}",tenantId);
//        String timestamp = String.valueOf(System.currentTimeMillis());
//        log.info("请求参数:{}",params);
//        String sign = getSign(params, timestamp, method, url);
//        return requestBody(method, url ,params, tenantId, timestamp, sign);
//    }
//
//    /**
//     * POST 请求封装
//     * @author : wei.xiang@einyun.com
//     * @date :  2020/6/29 10:48
//     * @param request RequestBO
//     * @return java.lang.String
//     */
//    public String postRequest(RequestBO request){
//        String method = HttpMethod.POST.name();
//        String requestUrl =request.getRequestUrl();
//        log.info("请求地址:{}",requestUrl);
//        String params = null;
//        if (request.getParam() != null) {
//            params = request.getParam().toJSONString();
//        }
//        log.info("请求参数:{}",params);
//        String tenantId = request.getTenantId();
//        log.info("tenantId:{}",tenantId);
//        String timestamp = String.valueOf(System.currentTimeMillis());
//        String sign = getSign(params, timestamp, method, requestUrl);
//        log.info("sign:{}",sign);
//        HttpHeaders headers = getHttpHeaders(tenantId, timestamp, sign, "tenant-id", "TenantId");
////        HttpHeaders headers = new HttpHeaders();
////        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
////        headers.setContentType(type);
////        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
////        if (requestUrl.contains("localhost")){
////            headers.add("Authorization", "Raw system");
////        }else {
////            headers.add("Authorization", "Sign " + sign);
////        }
////        headers.add("Timestamp", timestamp);
////        headers.add("accessKeys", ACCESS_KEY);
////        // 租户id
////        if(BeanUtils.isNotEmpty(tenantId)){
////            headers.add("tenant-id", tenantId);
////            headers.add("TenantId", tenantId);
////        }
//        HttpEntity<String> entity = new HttpEntity<>(params, headers);
//        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(requestUrl, entity, String.class);
//        return getResult(requestUrl, stringResponseEntity);
//    }
//
//    /**
//     * patch 参数封装
//     * @author : wei.xiang@einyun.com
//     * @date :  2020/6/29 14:29
//     * @param request request
//     * @return java.lang.String
//     */
//    public String patchRequest(RequestBO request){
//        String method = HttpMethod.PATCH.name();
//        String requestUrl =request.getRequestUrl();
//        log.info("请求地址:{}",requestUrl);
//        String params = null;
//        if (request.getParam() != null) {
//            params = request.getParam().toJSONString();
//        }
//        String tenantId = request.getTenantId();
//        log.info("tenantId:{}",tenantId);
//        String timestamp = String.valueOf(System.currentTimeMillis());
//        log.info("请求参数{}",params);
//        String sign = getSign(params, timestamp, method, requestUrl);
//        log.info("sign:{}",sign);
//        HttpHeaders headers = getHttpHeaders(tenantId, timestamp, sign, "tenant-id", "TenantId");
//        HttpEntity<String> entity = new HttpEntity<>(params, headers);
//        return restTemplate.patchForObject(requestUrl, entity, String.class);
//    }
//    /**
//     * get 请求
//     * @author : wei.xiang@einyun.com
//     * @date :  2020/6/29 11:25
//     * @param request 参数封装
//     * @return java.lang.String
//     */
//    public String getRequest(RequestBO request){
//        String method = HttpMethod.GET.name();
//        String requestUrl = request.getRequestUrl();
//        JSONObject param = request.getParam();
//        if (param != null) {
//            List<NameValuePair> nvps = Lists.newLinkedList();
//            Set<String> keys = param.keySet();
//            keys.forEach(key -> nvps.add(new BasicNameValuePair(key,param.getString(key))));
//            try {
//                URI uri = new URIBuilder().setPath(requestUrl).addParameters(nvps).build();
//                requestUrl= uri.toString();
//            }catch (Exception e){
//                log.error("请求路径转换失败:{}",e.getMessage());
//            }
//        }
//        String timestamp = String.valueOf(System.currentTimeMillis());
//        log.info("timestamp:{}",timestamp);
//        String sign = getSign("", timestamp, method, requestUrl);
//        log.info("sign:{}",sign);
//        String tenantId = request.getTenantId();
//        log.info("tenantId:{}",tenantId);
//        HttpHeaders headers = getHttpHeaders(tenantId, timestamp, sign, "tenant-id", "TenantId");
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//        log.info("请求地址:{}",requestUrl);
//        ResponseEntity<String> resEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, String.class);
//        return getResult(requestUrl, resEntity);
//    }
//
//    /**
//     * 请求结果封装
//     * @author : wei.xiang@einyun.com
//     * @date :  2020/6/29 11:24
//     *
//     * @param requestUrl 请求地址
//     * @param stringResponseEntity  请求结果
//     * @return java.lang.String
//     */
//    private String getResult(String requestUrl, ResponseEntity<String> stringResponseEntity) {
//        if (!stringResponseEntity.getStatusCode().isError()){
//            String body = stringResponseEntity.getBody();
//            log.info("结果:{}",body);
//            return body;
//        }else {
//            log.error("请求{}----失败:{}",requestUrl, HttpStatus.INTERNAL_SERVER_ERROR.toString());
//            throw new BaseException(stringResponseEntity.getStatusCode(),stringResponseEntity.getStatusCode().value(),stringResponseEntity.getStatusCode().getReasonPhrase());
//        }
//    }
//
//    /**
//     * 请求头封装
//     * @author : wei.xiang@einyun.com
//     * @date :  2020/6/29 11:22
//     * @param tenantId 租户标识
//     * @param timestamp 请求时间
//     * @param sign 鉴权
//     * @param s 自定义请求头
//     * @param tenantId2 自定义请求头
//     * @return org.springframework.http.HttpHeaders
//     */
//    private HttpHeaders getHttpHeaders(String tenantId, String timestamp, String sign, String s, String tenantId2) {
//        HttpHeaders headers = new HttpHeaders();
//        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//        headers.setContentType(type);
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//        headers.add("Authorization", "Sign " + sign);
//        headers.add("Timestamp", timestamp);
//        headers.add("accessKeys", ACCESS_KEY);
//        // 租户id
//        if(BeanUtils.isNotEmpty(tenantId)){
//            headers.add(s, tenantId);
//            headers.add(tenantId2, tenantId);
//        }
//        log.info("请求头信息:{}", JSON.toJSONString(headers));
//        return headers;
//    }
//
//
//    /**
//     * 数据请求
//     * @author : wei.xiang@einyun.com
//     * @date :  2020/6/11 11:04
//     * @param method 方法类型
//     * @param uri 请求地址
//     * @param params 方法参数
//     * @param tenantId 租户标识
//     * @param timestamp 时间
//     * @param sign 鉴权标识
//     * @return java.lang.String
//     */
//    private String requestBody(String method, String uri, String params, String tenantId, String timestamp, String sign) {
//        //请求头
//        HttpHeaders headers = getHttpHeaders(tenantId, String.valueOf(timestamp), sign, "TenantId", "tenant-id");
//        log.info("请求头参数: tenantId {} ", tenantId);
//        log.info("请求头参数: Sign {}" , sign);
//        log.info("请求头参数: Timestamp {}" , timestamp);
//        log.info("请求主体入参：param {}" , params);
//        HttpEntity<String> httpEntity = new HttpEntity<>(params, headers);
//        log.info("请求地址: uri:{} " ,uri);
//        log.info("请求方式: method {}",HttpMethod.resolve(method));
//        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, Objects.requireNonNull(HttpMethod.resolve(method)), httpEntity, String.class);
//        if (responseEntity.getStatusCode().isError()){
//            log.error(responseEntity.getStatusCode().getReasonPhrase());
//        }
//        log.info("result:{}",responseEntity.getBody());
//        return responseEntity.getBody();
//    }
//
//    /**
//     * 鉴权加密
//     * @author : wei.xiang@einyun.com
//     * @date :  2020/6/29 11:23
//     *
//     * @param params 请求参数
//     * @param timestamp 时间
//     * @param method 请求方法
//     * @param url uri
//     * @return java.lang.String
//     */
//    private String getSign(String params, String timestamp, String method, String url)  {
//        String seed ;
//        //String temp = url + uri;
//        String sign="";
//        String uri = url.substring(StringUtils.ordinalIndexOf(url, "/", 3));
//        log.info("sign-查看日志:method:{},uri:{},timestamp:{}",method,uri,timestamp);
//        if (HttpMethod.GET.matches(method) || StringUtils.isEmpty(params)) {
//            seed = method + "." + uri + "." + ACCESS_SECRET + "." + timestamp;
//        } else {
//            seed = method + "." + uri + "." + DigestUtils.md5DigestAsHex(params.getBytes()) + "." + ACCESS_SECRET + "." + timestamp;
//        }
//        try {
//            log.info("seed:{}",seed);
//            sign = Base64.getBase64(ACCESS_KEY) + "." + DigestUtils.md5DigestAsHex(seed.getBytes());
//        } catch (UnsupportedEncodingException e) {
//            log.error("加密失败e:{}",e.getMessage());
//            e.printStackTrace();
//        }
//        return sign;
//    }
//
//    /**
//     * 数组类型
//     * @author : wei.xiang@einyun.com
//     * @date :  2020/6/30 17:10
//     *
//     * @param request r
//     * @return java.lang.String
//     */
//    public String postArrayRequest(RequestBO request) {
//        String method = HttpMethod.POST.name();
//        String requestUrl =request.getRequestUrl();
//        log.info("requestUrl:{}",requestUrl);
//        String params = null;
//        if (request.getParamArray() != null) {
//            params = request.getParamArray().toJSONString();
//        }
//        String tenantId = request.getTenantId();
//        log.info("tenantId:{}",tenantId);
//        String timestamp = String.valueOf(System.currentTimeMillis());
//        log.info("请求参数:{}",params);
//        String sign = getSign(params, timestamp, method, requestUrl);
//        log.info("sign:{}",sign);
//        HttpHeaders headers = getHttpHeaders(tenantId, timestamp, sign, "tenant-id", "TenantId");
//        HttpEntity<String> entity = new HttpEntity<>(params, headers);
//        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(requestUrl, entity, String.class);
//        return getResult(requestUrl, stringResponseEntity);
//    }
//}
