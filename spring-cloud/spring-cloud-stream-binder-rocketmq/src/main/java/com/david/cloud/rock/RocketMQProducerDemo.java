package com.david.cloud.rock;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * Created by sc on 2019-03-13.
 */
public class RocketMQProducerDemo {

    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("test-group");
        producer.setNamesrvAddr("localhost:9876");
       // producer.setNamesrvAddr("102.4.6:10911");
        //Launch the instance.
        producer.start();
        for (int i=0;i<100;i++){
            //Create a message instance, specifying topic, tag and message body.
            Message msg=new Message("TopicTest-1" /* Topic */
                    ,"TopicTest-1" /* Topic */
            ,("hello world"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
