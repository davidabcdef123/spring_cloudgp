package com.david.client.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Created by sc on 2019-03-11.
 */

public interface SimpleMessageService {

    @Output("gupao2018") // Channel name
    MessageChannel gupao();//destination = test2018

    @Output("test007")
    MessageChannel testChannel();//  destination = test007

    @Output("test-http")
    MessageChannel http();//  destination = http


}
