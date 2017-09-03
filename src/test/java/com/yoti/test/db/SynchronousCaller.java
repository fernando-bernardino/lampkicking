package com.yoti.test.db;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.springframework.boot.test.context.TestComponent;

import com.mongodb.async.SingleResultCallback;
import com.yoti.test.util.ThrowableConsumer;

@TestComponent
public class SynchronousCaller {

	public void execute(ThrowableConsumer<SingleResultCallback<Void>> consumer) throws Exception {

		CountDownLatch lock = new CountDownLatch(1);
		
		consumer.accept((result, throwable) -> {
			
			lock.countDown();
			
		});
		
		lock.await(10000, TimeUnit.MILLISECONDS);
	}


	public <M> void execute(ThrowableConsumer<SingleResultCallback<M>> consumer, Consumer<M> callback) throws Exception {

		CountDownLatch lock = new CountDownLatch(1);
		
		consumer.accept((result, throwable) -> {
			
			callback.accept(result);
			
			lock.countDown();
			
		});
		
		lock.await(10000, TimeUnit.MILLISECONDS);
	}
}
