package com.example.webfluxdemo.basic.dao;

import com.example.webfluxdemo.basic.dto.CustomerDto;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CustomerDao {

    public List<CustomerDto> getCustomerList() {
        return IntStream.rangeClosed(1, 10)
                .peek(CustomerDao::sleepExecution)
                .peek(i -> System.out.println("current count : " + i))
                .mapToObj(i -> new CustomerDto(i, "customer " + i))
                .collect(Collectors.toList());
    }

    public Flux<CustomerDto> getCustomerListStream() {
        return Flux.range(1,10)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println("current count in stream : "+ i))
                .map(i -> new CustomerDto(i, "customer "+i));
    }

    private static void sleepExecution(int i) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
