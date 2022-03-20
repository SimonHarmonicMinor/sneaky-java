package com.kirekov.sneaky.lambda;

import java.util.function.BiFunction;

/**
 * BiFunction that may throw {@linkplain Exception}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <R> the type of the result of the function
 * @see BiFunction
 */
public interface CheckedBiFunction<T, U, R> {

  /**
   * Applies this function to the given arguments.
   *
   * @param t the first function argument
   * @param u the second function argument
   * @return the function result
   * @throws Exception if any error occurs
   */
  R apply(T t, U u) throws Exception;
}
