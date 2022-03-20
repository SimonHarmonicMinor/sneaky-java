package com.kirekov.sneaky;

import com.kirekov.sneaky.lambda.CheckedBiConsumer;
import com.kirekov.sneaky.lambda.CheckedConsumer;
import com.kirekov.sneaky.lambda.CheckedPredicate;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Factory for sneaky wrappers.
 */
public final class Sneaky {

  private Sneaky() {
    // no op
  }


  /**
   * Returns {@linkplain Consumer} that may throw {@linkplain Exception} ignoring {@code throws
   * Exception} clause in the method signature.
   *
   * @param checkedConsumer consumer that throws {@linkplain Exception}
   * @param <T>             the input argument
   * @return wrapped consumer
   */
  public static <T> Consumer<T> consumer(CheckedConsumer<T> checkedConsumer) {
    return value -> {
      try {
        checkedConsumer.accept(value);
      } catch (Exception e) {
        throwUnchecked(e);
      }
    };
  }

  /**
   * Returns {@linkplain BiConsumer} that may throw {@linkplain Exception} ignoring {@code throws
   * Exception} clause in the method signature.
   *
   * @param checkedBiConsumer biConsumer that throws {@linkplain Exception}
   * @param <T>               the first input argument
   * @param <U>               the second input argument
   * @return wrapped biConsumer
   */
  public static <T, U> BiConsumer<T, U> biConsumer(CheckedBiConsumer<T, U> checkedBiConsumer) {
    return (t, u) -> {
      try {
        checkedBiConsumer.accept(t, u);
      } catch (Exception e) {
        throwUnchecked(e);
      }
    };
  }

  /**
   * Returns {@linkplain Predicate} that may throw {@linkplain Exception} ignoring {@code throws
   * Exception} clause in the method signature.
   *
   * @param checkedPredicate predicate that throws {@linkplain Exception}
   * @param <T>              the input argument
   * @return wrapped predicate
   */
  public static <T> Predicate<T> predicate(CheckedPredicate<T> checkedPredicate) {
    return t -> {
      try {
        return checkedPredicate.test(t);
      } catch (Exception e) {
        throwUnchecked(e);
        return false;
      }
    };
  }

  /**
   * Throws {@linkplain Exception}. Does not require adding {@code throws Exception} to a method
   * signature.
   *
   * @param exception exception to throw
   * @param <T>       the type of the exception to throw
   * @throws T exception throw. Due to Java generic erasure the actual type is always {@linkplain
   *           Exception}
   */
  @SuppressWarnings("unchecked")
  public static <T extends Exception> void throwUnchecked(Exception exception) throws T {
    throw (T) exception;
  }
}
