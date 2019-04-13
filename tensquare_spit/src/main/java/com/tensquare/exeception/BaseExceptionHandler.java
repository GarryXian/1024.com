package com.tensquare.exeception;

import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.NoSuchElementException;

@ControllerAdvice //
public class BaseExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception01(Exception e){
        return new Result(false, StatusCode.ERROR,"发生异常, 异常信息为: "+e.getMessage());
    }

    /**
     * 无法查到数据时, 返回空数据集
     * @param e
     * @return
     */
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    public Result exception02(NoSuchElementException e){
        return new Result(true, StatusCode.OK,"查无此id");
    }
}
