//package com.muyi.servicestudy.beanconfig;
//
//import com.alibaba.druid.util.StringUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.TransportAddress;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
//import java.net.InetAddress;
//
///**
///**
// * @author Muyi,  dcmuyi@qq.com
// * @date 2019-04-17.
// */
//@Configuration
//@Slf4j
//public class ESBeanConfig {
//    private static final String CLUSTER_NODES_SPLIT_SYMBOL = ",";
//    private static final String HOST_PORT_SPLIT_SYMBOL = ":";
//
//    @Autowired
//    private Environment environment;
//
//    @Bean
//    public TransportClient getTransportClient() {
//        log.info("elasticsearch init.");
//        String clusterName = environment.getRequiredProperty("elasticsearch.cluster-name");
//        if (StringUtils.isEmpty(clusterName)) {
//            throw new RuntimeException("elasticsearch.cluster-name is empty.");
//        }
//        String clusterNodes = environment.getRequiredProperty("elasticsearch.cluster-nodes");
//        if (StringUtils.isEmpty(clusterNodes)) {
//            throw new RuntimeException("elasticsearch.cluster-nodes is empty.");
//        }
//
//        try {
//            Settings settings = Settings.builder().put("cluster.name", clusterName.trim())
//                    //客户端去嗅探整个cluster的状态，把集群中其它机器的ip地址加到客户端中-已配置 不需要
////                    .put("client.transport.sniff", true)
//                    .build();
//            TransportClient transportClient = new PreBuiltTransportClient(settings);
//            String[] clusterNodeArray = clusterNodes.trim().split(CLUSTER_NODES_SPLIT_SYMBOL);
//            for (String clusterNode : clusterNodeArray) {
//                String[] clusterNodeInfoArray = clusterNode.trim().split(HOST_PORT_SPLIT_SYMBOL);
//                TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(clusterNodeInfoArray[0]),
//                        Integer.parseInt(clusterNodeInfoArray[1]));
//                transportClient.addTransportAddress(transportAddress);
//            }
//            log.info("elasticsearch init success.");
//            return transportClient;
//        } catch (Exception e) {
//            throw new RuntimeException("elasticsearch init fail.");
//        }
//    }
//}
