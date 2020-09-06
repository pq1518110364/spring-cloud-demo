package configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiangwei
 * @date 2020-08-16 10:28 上午
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {
        log.error("ExceptionHandler ===>" + e.getMessage());
        e.printStackTrace();
        // 这里可根据不同异常引起的类做不同处理方式
        String exceptionName = ClassUtils.getShortName(e.getClass());
        log.error("ExceptionHandler ===>" + exceptionName);
        ModelAndView mav = new ModelAndView();
        mav.addObject("stackTrace", e.getStackTrace());
        mav.addObject("errorMessage", e.getMessage());
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("forward:/error/500");
        return mav;
    }
}
