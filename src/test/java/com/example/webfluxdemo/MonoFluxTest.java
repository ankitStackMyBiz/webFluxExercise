package com.example.webfluxdemo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoFluxTest {

    @Test
    @DisplayName("Should pass when passing string")
    public void monoTest() {
        Mono<String> monoString = Mono.just("AnkitTest").log();
        monoString.subscribe(System.out::println);
    }

    @Test
    @DisplayName("Should throw OnError")
    public void monoTestOnError() {
        Mono<?> monoString = Mono.just("AnkitTest")
                .then(Mono.error(new RuntimeException("Error occured"))).log();
        monoString.subscribe(System.out::println, (e) -> System.out.println(e.getMessage()));
    }

    @Test
    public void monoInteger() {
        Mono<Integer> monoInteger = Mono.just(10).log();
        monoInteger.subscribe();
    }

    @Test
    public void fluxString() {
        Flux<String> fluxString = Flux.just("Apple", "Mango", "Grapes", "Orange")
                .concatWithValues("Kiwi");
        fluxString.subscribe(System.out::println);
    }

    @Test
    public void fluxStringOnError() {
        Flux<String> stringFlux = Flux.just("Maruti", "Honda", "Chevi")
                .concatWith(Flux.error(new RuntimeException("Exception occured")));
        stringFlux.subscribe(System.out::println, (e) -> System.out.println(e.getMessage()));
    }

    @Test
    public void fluxInteger() {
        Flux<Integer> integerFlux = Flux.just(1, 2, 3, 4, 5, 4, 3, 2, 1).log();
        integerFlux.subscribe(System.out::println);
    }

    @Test
    public void monoValidation() {
        Mono<String> stringMono = Mono.just("WebFlux").log();
        StepVerifier.create(stringMono)
                .expectNext("WebFlux")
                .verifyComplete();
    }

    @Test
    public void monoValidationError() {
        Mono<String> stringMono = Mono.just("WebFlux").log();
        StepVerifier.create(stringMono)
                .expectNext("WebFluxxxxx")
                .verifyComplete();
    }
    @Test
    public void monoExpectError() {
        Mono<String> stringMono = Mono.error(new RuntimeException("Exception mono"));
        StepVerifier.create(stringMono)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    @DisplayName("Should pass if expectNext is passed in order of insertion")
    public void fluxVerifier() {
        Flux<String> stringFlux = Flux.just("Apple", "Ball", "Cat", "Dog").log();
        StepVerifier.create(stringFlux)
                .expectNext("Apple")
                .expectNext("Ball")
                .expectNext("Cat")
                .expectNext("Dog")
                .verifyComplete();
    }

    @Test
    @DisplayName("Should pass if expectNext is passed in order of insertion")
    public void fluxVerifierError() {
        Flux<String> stringFlux = Flux.just("Apple", "Ball", "Cat", "Dog").log();
        StepVerifier.create(stringFlux)
                .expectNext("Apple")
                .expectNext("Ball")
                .expectNext("Elephant")
                .expectNext("Dog")
                .verifyComplete();
    }

    @Test
    @DisplayName("Should pass and error will be caught on onError()")
    public void fluxVerifierExpectError() {
        Flux<String> stringFlux = Flux.just("Apple", "Ball", "Cat", "Dog")
                .concatWith(Flux.error(new RuntimeException("Exception occured")))
                .concatWithValues("Horse").log();
        StepVerifier.create(stringFlux)
                .expectNext("Apple")
                .expectNext("Ball")
                .expectNext("Cat")
                .expectNext("Dog")
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    @DisplayName("Should pass and error will be caught on onError() with error message")
    public void fluxVerifierExpectErrorMessage() {
        Flux<String> stringFlux = Flux.just("Apple", "Ball", "Cat", "Dog")
                .concatWith(Flux.error(new RuntimeException("Exception occured"))).log();
        StepVerifier.create(stringFlux)
                .expectNext("Apple", "Ball", "Cat", "Dog")
                .expectErrorMessage("Exception occured")
                .verify();
    }

    @Test
    @DisplayName("Should fail if different error message is expected")
    public void fluxVerifierExpectErrorMessageWithDifferentMessage() {
        Flux<String> stringFlux = Flux.just("Apple", "Ball", "Cat", "Dog")
                .concatWith(Flux.error(new RuntimeException("Exception occured"))).log();
        StepVerifier.create(stringFlux)
                .expectNext("Apple", "Ball", "Cat", "Dog")
                .expectErrorMessage("No exception")
                .verify();
    }
}
