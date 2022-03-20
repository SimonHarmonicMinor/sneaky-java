package com.kirekov.sneaky.lambda;

import java.util.function.BiConsumer;

/**
 * BiConsumer that may throw {@linkplain Exception}.
 *
 * @param <T> the first input argument
 * @param <U> the second input argument
 * @see BiConsumer
 */
@FunctionalInterface
public interface CheckedBiConsumer<T, U> {

  /**
   * Performs the operation on the given arguments.
   *
   * @param t the first input argument
   * @param u the second input argument
   * @throws Exception if any error occurs
   */
  void accept(T t, U u) throws Exception;
}
