package com.kirekov.sneaky;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("PMD.UnusedPrivateMethod")
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

  private static Stream<Arguments> biConsumerLongs() {
    return Stream.of(
        arguments(1L, 5L),
        arguments(-61L, -4315L),
        arguments(753L, -48345L),
        arguments(64L, 31L)
    );
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 4, 5})
  void shouldThrowExceptionForPredicate(int value) {
    Predicate<Integer> predicate = Sneaky.predicate(v -> {
      if (v.equals(value)) {
        throw new Exception();
      }
      return true;
    });

    assertThrows(
        Exception.class,
        () -> predicate.test(value),
        "Should throw exception"
    );
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 4, 5})
  void shouldSucceedForPredicate(int value) {
    Predicate<Integer> predicate = Sneaky.predicate(v -> {
      if (!v.equals(value)) {
        throw new Exception();
      }
      return true;
    });

    boolean result = assertDoesNotThrow(
        () -> predicate.test(value),
        "Should succeed"
    );
    assertTrue(result, "Unexpected predicate result");
  }

  @ParameterizedTest
  @MethodSource("biPredicateIntegers")
  void shouldThrowExceptionForBiPredicate(int value1, int value2) {
    BiPredicate<Integer, Integer> biPredicate = Sneaky.biPredicate((v1, v2) -> {
      if (v1.equals(value1) && v2.equals(value2)) {
        throw new Exception();
      }
      return false;
    });

    assertThrows(
        Exception.class,
        () -> biPredicate.test(value1, value2),
        "Should throw exception"
    );
  }

  @ParameterizedTest
  @MethodSource("biPredicateIntegers")
  void shouldSucceedForBiPredicate(int value1, int value2) {
    BiPredicate<Integer, Integer> biPredicate = Sneaky.biPredicate((v1, v2) -> {
      if (!v1.equals(value1) || !v2.equals(value2)) {
        throw new Exception();
      }
      return false;
    });

    boolean result = assertDoesNotThrow(
        () -> biPredicate.test(value1, value2),
        "Should succeed"
    );
    assertFalse(result, "Unexpected biPredicate result");
  }

  private static Stream<Arguments> biPredicateIntegers() {
    return Stream.of(
        arguments(12, 41),
        arguments(-41, 9712),
        arguments(5641, 752),
        arguments(-5131, 8254)
    );
  }

  @Test
  void shouldThrowExceptionForSupplier() {
    Supplier<String> supplier = Sneaky.supplier(() -> {
      throw new Exception();
    });

    assertThrows(
        Exception.class,
        supplier::get,
        "Should throw exception"
    );
  }

  @Test
  void shouldSucceedForSupplier() {
    Supplier<String> supplier = Sneaky.supplier(() -> "some_string");

    String result = assertDoesNotThrow(supplier::get, "Should not throw exception");
    assertEquals("some_string", result, "Unexpected supplier result");
  }

  @ParameterizedTest
  @MethodSource("functionIntegers")
  void shouldThrowExceptionForFunction(int argument, int result) {
    Function<Integer, Integer> function = Sneaky.function(t -> {
      if (t.equals(argument)) {
        throw new Exception();
      }
      return result;
    });

    assertThrows(
        Exception.class,
        () -> function.apply(argument),
        "Should throw exception"
    );
  }

  @ParameterizedTest
  @MethodSource("functionIntegers")
  void shouldSucceedForFunction(int argument, int result) {
    Function<Integer, Integer> function = Sneaky.function(t -> {
      if (!t.equals(argument)) {
        throw new Exception();
      }
      return result;
    });

    int res = assertDoesNotThrow(
        () -> function.apply(argument),
        "Should not throw exception"
    );
    assertEquals(result, res, "Unexpected function result");
  }

  private static Stream<Arguments> functionIntegers() {
    return Stream.of(
        arguments(1, 2),
        arguments(4, -123),
        arguments(81, 4124)
    );
  }

  @ParameterizedTest
  @MethodSource("biFunctionIntegers")
  void shouldThrowExceptionForBiFunction(int arg1, int arg2, int result) {
    BiFunction<Integer, Integer, Integer> biFunction = Sneaky.biFunction(
        (t, u) -> {
          if (t.equals(arg1) && u.equals(arg2)) {
            throw new Exception();
          }
          return result;
        }
    );

    assertThrows(
        Exception.class,
        () -> biFunction.apply(arg1, arg2),
        "Should throw exception"
    );
  }

  @ParameterizedTest
  @MethodSource("biFunctionIntegers")
  void shouldSucceedForBiFunction(int arg1, int arg2, int result) {
    BiFunction<Integer, Integer, Integer> biFunction = Sneaky.biFunction(
        (t, u) -> {
          if (!t.equals(arg1) || !u.equals(arg2)) {
            throw new Exception();
          }
          return result;
        }
    );

    int res = assertDoesNotThrow(
        () -> biFunction.apply(arg1, arg2),
        "Should not throw exception"
    );

    assertEquals(result, res, "Unexpected function result");
  }

  private static Stream<Arguments> biFunctionIntegers() {
    return Stream.of(
        arguments(1, 2, 3),
        arguments(-51, 982, 0),
        arguments(987, 713, -16851)
    );
  }
}