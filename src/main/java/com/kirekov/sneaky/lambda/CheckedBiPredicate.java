package com.kirekov.sneaky.lambda;

/**
 * BiPredicate that may throw {@linkplain Exception}.
 *
 * @param <T> the first input argument
 * @param <U> the second input argument
 */
@FunctionalInterface
public interface CheckedBiPredicate<T, U> {

  /**
   * Evaluates this predicate on the given arguments.
   *
   * @param t the first input argument
   * @param u the second input argument
   * @return true if the input arguments match the predicate, otherwise false
   * @throws Exception if any error occurs
   */
  boolean test(T t, U u) throws Exception;
}
