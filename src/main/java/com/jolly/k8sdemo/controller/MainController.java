package com.jolly.k8sdemo.controller;

import com.jolly.k8sdemo.controller.component.RequestCounter;
import com.jolly.k8sdemo.model.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MainController {
    @Autowired
    private RequestCounter requestCounter;

    private static final String VERSION_0 = "0.0";
    private static final String DESCRIPTION_0 = "1 pod and no graceful shutdown";

    @GetMapping("/status")
    public Mono<Response> status() {
        log.info("k8s demo-test");
        return Mono.just(Response.builder()
                        .text("k8s demo-test")
                        .version(VERSION_0)
                        .description(DESCRIPTION_0)
                        .build()
        );
    }

    @GetMapping("/delay/{duration}")
    public Mono<Response> duration(@PathVariable("duration") Integer duration) {
        String context = "delay for " + duration + " s";
        requestCounter.increment();

        log.info("request #{} : {}", requestCounter.getCount(), context);
        return Mono.fromFuture(() -> Mono.just(Response.builder()
                                .text(context)
                                .version(VERSION_0)
                                .description(DESCRIPTION_0)
                                .build())
                        .toFuture())
                .delayElement(Duration.ofSeconds(duration));
    }
}
