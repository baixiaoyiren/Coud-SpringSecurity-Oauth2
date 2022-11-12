package com.javasm.cloud.entity;

/**
 * @author admin
 */
@FunctionalInterface
public interface HandleBusiness<T> {

    /**
     * handle a result.
     *
     * @return a result
     */

    T handle();


}
