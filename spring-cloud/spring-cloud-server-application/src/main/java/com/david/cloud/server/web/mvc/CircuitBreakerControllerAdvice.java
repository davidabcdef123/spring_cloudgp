package com.david.cloud.server.web.mvc;

import com.david.cloud.server.controller.ServerController;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.TimeoutException;

/**
 * Created by sc on 2019-03-11.
 */
@RestControllerAdvice(assignableTypes = ServerController.class)
public class CircuitBreakerControllerAdvice {

    @ExceptionHandler
    public void onTimeoutException(TimeoutException timeoutException,
                                   Writer writer) throws IOException {
        System.out.println("CircuitBreakerControllerAdvice");
        writer.write(errorContent(""));
        writer.flush();
        writer.close();
    }

    /**
     * 1.追加转换器
     * 2.追加校验器
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        System.out.println("initBinder");
        //统一日期处理
        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
        //追加校验器
        //binder.addValidators(personValidator);
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "Magical Sam");
    }


    public String errorContent(String message) {
        return "CircuitBreakerControllerAdvice Fault";
    }
}
