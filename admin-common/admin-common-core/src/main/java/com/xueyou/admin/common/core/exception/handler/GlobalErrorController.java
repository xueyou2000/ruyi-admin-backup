//package com.xueyou.admin.common.core.exception.handler;
//
//import com.xueyou.admin.common.core.vo.Response;
//import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
//import org.springframework.boot.web.servlet.error.ErrorAttributes;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 全局错误控制器
// *
// * 在springboot 2.3.0版本之前，可以使用实现ErrorController, springboot 2.3.0版本以后getErrorPath已经被标记为@Deprecated @see https://blog.csdn.net/bufegar0/article/details/108447557
// *
// * @author chendong
// * @version V1.0.0
// * @since 2020/9/30 9:43 上午
// */
//@RestController
//public class GlobalErrorController extends AbstractErrorController {
//
//    private static final String ERROR_PATH = "/error";
//
//    public GlobalErrorController(ErrorAttributes errorAttributes) {
//        super(errorAttributes);
//    }
//
//    @RequestMapping(value = ERROR_PATH)
//    public Response error(HttpServletRequest request) {
//        HttpStatus status = getStatus(request);
//        return Response.error(status.value(), status.getReasonPhrase());
//    }
//
//    @Override
//    public String getErrorPath() {
//        return ERROR_PATH;
//    }
//}
