package com.kirekov.sneaky;

import com.kirekov.sneaky.lambda.CheckedConsumer;
import java.util.function.Consumer;

/**
 * Factory for sneaky wrappers.
 */
public final class Sneaky {

  /**
   * Returns {@linkplain Consumer} that throws {@linkplain Exception} as {@linkplain
   * RuntimeException}.
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
   * Throws {@linkplain Exception}. Does not require to add {@code throws Exception} to a method
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
