package com.david.cloud.binderhttp;

import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * Created by sc on 2019-03-13.
 */
@RestController
public class MessageReceiverController implements Controller {

    public static final String ENDPOINT_URI = "/message/receive";

    private MessageChannel inputChannel;

    @PostMapping(ENDPOINT_URI)
    public String receive(HttpServletRequest request)throws Exception{
        // 请求内容
        InputStream inputStream=request.getInputStream();
        // 接收到客户端发送的 HTTP 实体，需要 MessageChannel 回写
        byte[] requestBody = StreamUtils.copyToByteArray(inputStream);
        inputChannel.send(new GenericMessage<>(requestBody));
        return "ok";
    }
    public MessageChannel getInputChannel() {
        return inputChannel;
    }

    public void setInputChannel(MessageChannel inputChannel) {
        this.inputChannel = inputChannel;
    }

    @Nullable
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return null;
    }
}
