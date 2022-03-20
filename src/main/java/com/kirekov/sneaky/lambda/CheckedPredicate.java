package com.kirekov.sneaky.lambda;

/**
 * Predicate that may throw {@linkplain Exception}.
 *
 * @param <T> the input parameter
 * @see CheckedPredicate
 */
@FunctionalInterface
public interface CheckedPredicate<T> {

  /**
   * Evaluates this predicate on the given argument.
   *
   * @param t the input parameter
   * @return true if the input argument matches the predicate, otherwise false
   * @throws Exception if any error occurs
   */
  boolean test(T t) throws Exception;
}
