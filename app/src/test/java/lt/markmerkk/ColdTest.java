package lt.markmerkk;

import java.util.concurrent.CountDownLatch;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by mariusmerkevicius on 1/28/16.
 */
public class ColdTest {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  @Before
  public void setUp() {
  }

//  INFO | 2016-01-28 22:19:34,362 | ColdTest.java | 91 | Start: Executing slow task in Service 1
//  INFO | 2016-01-28 22:19:35,364 | ColdTest.java | 43 | From Subscriber 1: data 1
//  INFO | 2016-01-28 22:19:35,365 | ColdTest.java | 96 | End: Executing slow task in Service 1  @Test
  public void cold_runColdOperation_shouldRunOnSubscribers() throws Exception {
    // Arrange
    Observable<String> op1 = operation();
    CountDownLatch latch = new CountDownLatch(1);

    // Act
    op1.subscribe(new Subscriber<String>() {
      @Override
      public void onCompleted() {
        latch.countDown();
      }

      @Override
      public void onError(Throwable e) {
        logger.error(e.getMessage(), e);
      }

      @Override
      public void onNext(String s) {
        logger.info("From Subscriber 1: {}", s);
      }
    });

    latch.await();

    // Assert
  }

//  INFO | 2016-01-28 22:19:33,347 | ColdTest.java | 91 | Start: Executing slow task in Service 1
//  INFO | 2016-01-28 22:19:33,349 | ColdTest.java | 91 | Start: Executing slow task in Service 1
//  INFO | 2016-01-28 22:19:33,351 | ColdTest.java | 91 | Start: Executing slow task in Service 1
//  INFO | 2016-01-28 22:19:34,357 | ColdTest.java | 80 | From Subscriber 3: data 1
//  INFO | 2016-01-28 22:19:34,358 | ColdTest.java | 96 | End: Executing slow task in Service 1
//  INFO | 2016-01-28 22:19:34,357 | ColdTest.java | 72 | From Subscriber 1: data 1
//  INFO | 2016-01-28 22:19:34,359 | ColdTest.java | 96 | End: Executing slow task in Service 1
//  INFO | 2016-01-28 22:19:34,357 | ColdTest.java | 76 | From Subscriber 2: data 1
//  INFO | 2016-01-28 22:19:34,359 | ColdTest.java | 96 | End: Executing slow task in Service 1
  @Test
  public void cold_runMultipleColdOperations_shouldRunOnSubscribers() throws Exception {
    // Arrange
    Observable<String> op1 = operation();
    CountDownLatch latch = new CountDownLatch(3);

    // Act
    op1.subscribe(new Subscriber<String>() {
      @Override
      public void onCompleted() {
        latch.countDown();
      }

      @Override
      public void onError(Throwable e) {
        logger.error(e.getMessage(), e);
      }

      @Override
      public void onNext(String s) {
        logger.info("From Subscriber 1: {}", s);
      }
    });

    op1.subscribe(s -> logger.info("From Subscriber 2: {}", s),
        e -> logger.error(e.getMessage(), e),
        () -> latch.countDown());

    op1.subscribe(s -> logger.info("From Subscriber 3: {}", s),
        e -> logger.error(e.getMessage(), e),
        () -> latch.countDown());

    latch.await();

    // Assert
  }

  public static Observable<String> operation() {
    return Observable.<String>create(s -> {
      logger.info("Start: Executing slow task in Service 1");
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) { }
      s.onNext("data 1");
      logger.info("End: Executing slow task in Service 1");
      s.onCompleted();
    }).subscribeOn(Schedulers.computation());
  }

}
