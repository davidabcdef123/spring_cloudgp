package com.david.client.controller;

import com.david.client.annotation.CustomizedLoadBalanced;
import com.david.client.loadbalance.LoadBalancedRequestInterceptor;
import com.david.client.service.feign.clients.SayingService;
import com.david.client.service.rest.clients.SayingRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.bus.ConditionalOnBusEnabled;
import org.springframework.cloud.client.ServiceInstance;
import com.alibaba.fastjson.JSON;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sun.java2d.pipe.SpanIterator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by sc on 2019-03-05.
 */
@RestController
public class ClientController {

/*    @Autowired
    private RestTemplate restTemplate;*/

    //自定义的
    @Autowired
    @CustomizedLoadBalanced
    private RestTemplate restTemplate;

    @Bean
    public ClientHttpRequestInterceptor interceptor(){
        return new LoadBalancedRequestInterceptor();
    }

    @Autowired
    @LoadBalanced // 依赖注入 Ribbon RestTemplate Bean
    private RestTemplate lbRestTemplate;

    @LoadBalanced
    @Bean
    public RestTemplate loadBalancedRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(restTemplate);
        return restTemplate;
    }

    @Bean
    @Autowired
    @CustomizedLoadBalanced
    public  RestTemplate restTemplate(){
        RestTemplate restTemplate=new RestTemplate();
        return restTemplate;
    }

    //加入拦截器的
   /* @Bean
    @Autowired
    public RestTemplate restTemplate( ClientHttpRequestInterceptor interceptor) {
        RestTemplate restTemplate = new RestTemplate();
        //增加拦截器
        //添加Qualifier注解后取消加载拦截器
        //restTemplate.setInterceptors(Arrays.asList(interceptor));
        //
        return restTemplate;
    }*/

    @Bean
    @Autowired
    public Object customizer(@CustomizedLoadBalanced Collection<RestTemplate> restTemplates,
    ClientHttpRequestInterceptor interceptor) {
        restTemplates.forEach(r->{
            r.setInterceptors(Arrays.asList(interceptor));
        });
        return new Object();
    }



    @Value("${spring.application.name}")
    private String currentServiceName;

    @Autowired
    private DiscoveryClient discoveryClient;

/*    @Autowired
    private SayingService sayingService;*/

    //todo
    /*@Autowired
    private SayingRestService sayingRestService;*/

    //    // Map Key service Name , Value URLs
    private volatile Map<String, Set<String>> targetUrlsCache = new HashMap<>();

    //线程安全？ 存储的是自己的地址
    private volatile Set<String> targetUrls = new HashSet<>();

    /**
    * Author: sc
    * Since: 2019-03-18
    * Describe:自己注册到zk，然后获取自己的列表
    * Update: [变更日期YYYY-MM-DD][更改人姓名][变更描述]
    */
    @Scheduled(fixedRate = 10*1000)//10秒更新一次缓存
    public void updateTargetUrlCache(){//更新模板 urls
        // 获取当前应用的机器列表
//        // http://${ip}:${port}
        Set<String> oldTargetUrils=this.targetUrls;
        List<ServiceInstance> serviceInstance = discoveryClient.getInstances(currentServiceName);
        Set<String> newTargetUrls=serviceInstance.stream().map(s->
                        s.isSecure() ?
                                "https://" + s.getHost() + ":" + s.getPort() :
                                "http://" + s.getHost() + ":" + s.getPort()
        ).collect(Collectors.toSet());
        this.targetUrls=newTargetUrls;
        oldTargetUrils.clear();
    }

    @Scheduled(fixedRate = 10 * 1000) // 10 秒钟更新一次缓存
    public void updateTargetUrlsCache() { // 更新目标 URLs
        // 获取当前应用的机器列表
        // http://${ip}:${port}
        Map<String, Set<String>> newTargetUrlsCache = new HashMap<>();
        discoveryClient.getServices().forEach(serviceName -> {
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
            Set<String> newTargetUrls = serviceInstances
                    .stream()
                    .map(s ->
                           s.isSecure() ?
                                    "https://" + s.getHost() + ":" + s.getPort() :
                                    "http://" + s.getHost() + ":" + s.getPort()
                    ).collect(Collectors.toSet());
            newTargetUrlsCache.put(serviceName, newTargetUrls);
        });

        this.targetUrlsCache = newTargetUrlsCache;
    }


    @GetMapping("/invoke/{serviceName}/say") // -> /say
    public String invokeSay(@PathVariable String serviceName, @RequestParam String message){
        // 自定义 RestTemplate 发送请求到服务器
/*        List<String> targetUrls = new LinkedList<>(targetUrlsCache.get(serviceName));
        int size = targetUrls.size();
        int index = new Random().nextInt(size);
        String targetUrl = targetUrls.get(index);
        return restTemplate.getForObject(targetUrl + "/say?message="+message, String.class);
        */
        return restTemplate.getForObject("/"+serviceName + "/say?message="+message, String.class);
    }

    @GetMapping("/lb/invoke/{serviceName}/say") // -> /say
    public String lbInvokeSay(@PathVariable String serviceName,
                              @RequestParam String message) {
        // Ribbon RestTemplate 发送请求到服务器
        // 输出响应
        return lbRestTemplate.getForObject("http://" + serviceName + "/say?message=" + message, String.class);
    }

/*    @GetMapping("/fegin/say")
    public String feignSay(@RequestParam String messgge){
        return sayingService.say(messgge);
    }*/

 /*   @GetMapping("/rest/say")
    public String restSay(@RequestParam String message) {
        return sayingRestService.say(message);
    }*/

 /**
 * Author: sc
 * Since: 2019-03-18
 * Describe:自己调用自己，没什么实际意义
 * Update: [变更日期YYYY-MM-DD][更改人姓名][变更描述]
 */
    @GetMapping("/invoke/say")
    public String invokeSay(@RequestParam String message) {
        //服务器列表快照
        List<String> list = new ArrayList<>(this.targetUrls);
        int size=list.size();
        int index = new Random().nextInt(size);
        String targetUrl = list.get(index);
        return restTemplate.getForObject(targetUrl+"/say?message="+message,String.class);
    }

    @GetMapping("/say")
    public String say(@RequestParam String message) {
        System.out.println("接收到消息 say:" + message);
        return "hello" + message;
    }




    // 自定义 RestTemplate Bean




    @GetMapping("/service/instances/{serviceName}")
    public List<String> getAllServiceInstance(@PathVariable String serviceName){
        return discoveryClient.getInstances(serviceName).stream()
                .map(s->s.getServiceId() + " - " + s.getHost() + ":" + s.getPort()).
                        collect(Collectors.toList());
    }
}
