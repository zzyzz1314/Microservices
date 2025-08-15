package cn.zwh.ymcc.exception;

import cn.zwh.ymcc.constants.ErrorCode;
import cn.zwh.ymcc.result.JSONResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.BindingResult;

import java.net.BindException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobleExceptionHandler {

    //拦截异常 : 这个注解就可以拦截器 GlobleException 异常
    @ExceptionHandler(GlobleBusinessException.class)
    public JSONResult globleException(GlobleBusinessException e){
        e.printStackTrace();
        return JSONResult.error(e.getMessage());
        
    }


    //拦截器其他异常
    @ExceptionHandler(Exception.class)
    public JSONResult exception(Exception e){
        e.printStackTrace();
        //获取e中的信息，要求输出为[用户名必填][邮箱格式不正确] 利用debug模式查看
        //@TODO
        List<String> errorList = new ArrayList<>();
        if (e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
                errorList.add(fieldError.getDefaultMessage());
            });
            return JSONResult.error(errorList.toString());
        }

        return JSONResult.error(ErrorCode.NETWORK_ERROR);
    }

}