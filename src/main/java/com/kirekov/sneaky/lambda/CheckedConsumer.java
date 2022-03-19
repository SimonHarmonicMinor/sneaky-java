package com.kirekov.sneaky.lambda;

import java.util.function.Consumer;

/**
 * Consumer that may throw {@linkplain Exception}.
 *
 * @param <T> the type of the input parameter
 * @see Consumer
 */
@FunctionalInterface
public interface CheckedConsumer<T> {

  /**
   * Performs this operation on the given argument.
   *
   * @param t the input argument
   * @throws Exception if any error occurs
   */
  void accept(T t) throws Exception;
}
