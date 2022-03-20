package com.kirekov.sneaky.lambda;

import java.util.function.Supplier;

/**
 * Supplier that may throw {@linkplain Exception}.
 *
 * @param <T> the type of results supplied by this supplier
 * @see Supplier
 */
public interface CheckedSupplier<T> {

  /**
   * Gets a result.
   *
   * @return a result
   * @throws Exception if any exception occurs
   */
  T get() throws Exception;
}
