package com.yoti.test.util;

@FunctionalInterface
public interface ThrowableConsumer<M> {

	public void accept(M m) throws Exception;
}
