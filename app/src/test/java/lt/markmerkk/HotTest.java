package lt.markmerkk;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by mariusmerkevicius on 1/28/16.
 * Source: http://www.javacodegeeks.com/2015/03/hot-and-cold-rx-java-observable.html
 */
public class HotTest {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);
  private Random rand;

  @Before
  public void setUp() {
    rand = new Random();
  }

//  INFO | 2016-01-28 22:47:42,407 | HotTest.java | 50 | Start: Executing slow task in Service 1
//  INFO | 2016-01-28 22:47:43,416 | HotTest.java | 27 | From Subscriber 1: data 1
//  INFO | 2016-01-28 22:47:43,417 | HotTest.java | 31 | From Subscriber 2: data 1
//  INFO | 2016-01-28 22:47:43,417 | HotTest.java | 35 | From Subscriber 3: data 1
//  INFO | 2016-01-28 22:47:43,417 | HotTest.java | 55 | End: Executing slow task in Service 1
  @Test
  public void hot_runHotOperation_shouldRunOnAllSubscribers() throws Exception {
    // Arrange
    Observable<String> op1 = longOperation();

    ConnectableObservable<String> connectableObservable =  op1.publish();

    CountDownLatch latch = new CountDownLatch(3);

    connectableObservable.subscribe(s -> logger.info("From Subscriber 1: {}", s),
        e -> logger.error(e.getMessage(), e),
        () -> latch.countDown());

    connectableObservable.subscribe(s -> logger.info("From Subscriber 2: {}", s),
        e -> logger.error(e.getMessage(), e),
        () -> latch.countDown());

    connectableObservable.subscribe(s -> logger.info("From Subscriber 3: {}", s),
        e -> logger.error(e.getMessage(), e),
        () -> latch.countDown());

    connectableObservable.connect();

    latch.await();

    // Act
    // Assert

  }

//  INFO | 2016-01-28 22:49:23,427 | HotTest.java | 82 | Start: Executing slow task in Service 1
//  INFO | 2016-01-28 22:49:24,440 | HotTest.java | 64 | From Subscriber 1: data 1
//  INFO | 2016-01-28 22:49:24,441 | HotTest.java | 68 | From Subscriber 2: data 1
//  INFO | 2016-01-28 22:49:24,442 | HotTest.java | 72 | From Subscriber 3: data 1
//  INFO | 2016-01-28 22:49:24,442 | HotTest.java | 87 | End: Executing slow task in Service 1
  @Test
  public void hot_runHotUsingSubject_shouldRunOnAllSubscribers() throws Exception {
    Observable<String> op1 = longOperation();

    PublishSubject<String> publishSubject = PublishSubject.create();

    op1.subscribe(publishSubject);

    CountDownLatch latch = new CountDownLatch(3);

    publishSubject.subscribe(s -> logger.info("From Subscriber 1: {}", s),
        e -> logger.error(e.getMessage(), e),
        () -> latch.countDown());

    publishSubject.subscribe(s -> logger.info("From Subscriber 2: {}", s),
        e -> logger.error(e.getMessage(), e),
        () -> latch.countDown());

    publishSubject.subscribe(s -> logger.info("From Subscriber 3: {}", s),
        e -> logger.error(e.getMessage(), e),
        () -> latch.countDown());


    latch.await();
  }

  @Test
  public void hot_runHotWithLoadIndicator_shouldIndicateRunner() throws Exception {
    Observable<String> op1 = longOperation();
    PublishSubject<String> publishSubject = PublishSubject.create();

    op1.subscribe(publishSubject);

    publishSubject.subscribe(s -> {
      System.out.println("Loading...");
    }, error -> {
      System.out.println("... error.");
    }, () -> {
      System.out.println("... complete.");
    });

    CountDownLatch latch = new CountDownLatch(3);
    publishSubject.subscribe(s -> logger.info("From Subscriber 1: {}", s),
        e -> logger.error(e.getMessage(), e),
        () -> latch.countDown());
    publishSubject.subscribe(s -> logger.info("From Subscriber 2: {}", s),
        e -> logger.error(e.getMessage(), e),
        () -> latch.countDown());
    publishSubject.subscribe(s -> logger.info("From Subscriber 3: {}", s),
        e -> logger.error(e.getMessage(), e),
        () -> latch.countDown());

    latch.await();
  }

  //region Observables

  public Observable<String> longOperation() {
    return Observable.<String>create(s -> {
      logger.info("Start: Executing slow task in Service 1");
      try {
        int minDelay = 100;
        int maxDelay = 2000;
        Thread.sleep((minDelay + rand.nextInt((maxDelay - minDelay) + 1)));
      } catch (InterruptedException e) { }
      s.onNext("data 1");
      logger.info("End: Executing slow task in Service 1");
      s.onCompleted();
    }).subscribeOn(Schedulers.computation());
  }

  //endregion

}
