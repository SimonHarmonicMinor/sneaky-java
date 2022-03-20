package com.kirekov.sneaky.lambda;

import java.util.function.Function;

/**
 * Function that may throw {@linkplain Exception}.
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 * @see Function
 */
public interface CheckedFunction<T, R> {

  /**
   * Applies this function to the given argument.
   *
   * @param t the function argument
   * @return the function result
   * @throws Exception if any error occurs
   */
  R apply(T t) throws Exception;
}
