package com.tigerpay.payment.config.properties;

import com.tigerbeetle.Client;
import com.tigerbeetle.UInt128;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TigerBeetleProperties {

    @Value("${tigerbeetle.replica-addresses}")
    private String[] replicaAddresses;

    @Value("${tigerbeetle.cluster-id}")
    private Integer clusterId;

    @Bean
    public Client tigerBeetleClient() {
        return new Client(UInt128.asBytes(clusterId), replicaAddresses);
    }
}
