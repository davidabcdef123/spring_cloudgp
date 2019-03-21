package com.david.cloud.server.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by sc on 2019-03-11.
 */
public interface SimpleMessageReceiver {

    @Input("zl2019")
    SubscribableChannel zl();

/*    @Input("test007")
    SubscribableChannel testChannel();

    @Input("test-http")
    SubscribableChannel httpChannel();*/
}
