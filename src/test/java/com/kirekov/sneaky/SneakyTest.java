package com.kirekov.sneaky;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.function.Consumer;
import org.junit.jupiter.params.ParameterizedTest;
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
}