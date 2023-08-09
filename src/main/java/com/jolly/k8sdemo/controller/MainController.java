package com.jolly.k8sdemo.controller;

import com.jolly.k8sdemo.model.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private static final String DESCRIPTION_1 = "version : 1, 1 pod and no others config";
    private static final String DESCRIPTION_2 = "version : 2, 1 pod and set graceful shutdown config";

    @GetMapping("/status")
    public Mono<Response> status() {
        log.info("k8s demo-test");
        return Mono.just(Response.builder()
                .text("k8s demo-test")
                .description(DESCRIPTION_1)
//                .description(DESCRIPTION_2)
                .build()
        );
    }

    @GetMapping("/delay/{duration}")
    public Mono<Response> duration(@PathVariable("duration") Integer duration) {
        String context = "delay for " + duration + " s";
        log.info(context);
        return Mono.fromFuture(() -> Mono.just(Response.builder()
                                .text(context)
                                .description(DESCRIPTION_1)
//                                .description(DESCRIPTION_2)
                                .build())
                        .toFuture())
                .delayElement(Duration.ofSeconds(duration));
    }
}
