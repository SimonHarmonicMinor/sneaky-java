package com.kirekov.sneaky;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class SneakyTest {

  @ParameterizedTest
  @ValueSource(strings = {
      "s1", "s2", "s3"
  })
  void shouldThrowExceptionForConsumer(String value) {
    Consumer<String> consumer = Sneaky.consumer(v -> {
      if (v.equals(value)) {
        throw new IOException();
      }
    });

    assertThrows(
        IOException.class,
        () -> consumer.accept(value),
        "Should throw IOException"
    );
  }

  @ParameterizedTest
  @ValueSource(ints = {
      1, 5, 124, -12, 623
  })
  void shouldSucceedForConsumer(int value) {
    Consumer<Integer> consumer = Sneaky.consumer(v -> {
      if (!v.equals(value)) {
        throw new Exception();
      }
    });

    assertDoesNotThrow(
        () -> consumer.accept(value),
        "Should not throw any exceptions"
    );
  }

  @ParameterizedTest
  @MethodSource("biConsumerLongs")
  void shouldThrowExceptionForBiConsumer(long value1, long value2) {
    BiConsumer<Long, Long> consumer = Sneaky.biConsumer((v, u) -> {
      if (v.equals(value1) && u.equals(value2)) {
        throw new Exception();
      }
    });

    assertThrows(
        Exception.class,
        () -> consumer.accept(value1, value2),
        "Should throw Exception"
    );
  }

  @ParameterizedTest
  @MethodSource("biConsumerLongs")
  void shouldSucceedForBiConsumer(long value1, long value2) {
    BiConsumer<Long, Long> consumer = Sneaky.biConsumer((v, u) -> {
      if (!v.equals(value1) || !u.equals(value2)) {
        throw new Exception();
      }
    });

    assertDoesNotThrow(
        () -> consumer.accept(value1, value2),
        "Should not throw Exception"
    );
  }

  @SuppressWarnings("PMD.UnusedPrivateMethod")
  private static Stream<Arguments> biConsumerLongs() {
    return Stream.of(
        arguments(1L, 5L),
        arguments(-61L, -4315L),
        arguments(753L, -48345L),
        arguments(64L, 31L)
    );
  }
}