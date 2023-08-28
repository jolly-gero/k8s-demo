package com.jolly.k8sdemo.controller;

import com.jolly.k8sdemo.component.RequestCounter;
import com.jolly.k8sdemo.model.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MainController {
    @Autowired
    private RequestCounter requestCounter;

    private static final String VERSION_2 = "2.0";
    private static final String DESCRIPTION_2 = "1 pod and set graceful shutdown, preStopHook and terminationGracePeriodSeconds";

    @GetMapping("/status")
    public Mono<Response> status() {
        log.info("[V2]: k8s demo-test");
        return Mono.just(Response.builder()
                .text("k8s demo-test")
                .pod(Objects.requireNonNullElse(System.getenv("HOSTNAME"), "can't find pod name"))
                .version(VERSION_2)
                .description(DESCRIPTION_2)
                .build()
        );
    }

    @GetMapping("/delay/{duration}")
    public Mono<Response> duration(@PathVariable("duration") Integer duration) {
        String context = "delay for " + duration + " s";
        requestCounter.increment();

        log.info("[V2]: request #{} : {}", requestCounter.getCount(), context);
        return Mono.fromFuture(() -> Mono.just(Response.builder()
                                .text(context)
                                .pod(Objects.requireNonNullElse(System.getenv("HOSTNAME"), "can't find pod name"))
                                .version(VERSION_2)
                                .sequence(requestCounter.getCount())
                                .description(DESCRIPTION_2)
                                .build())
                        .toFuture())
                .delayElement(Duration.ofSeconds(duration));
    }
}
