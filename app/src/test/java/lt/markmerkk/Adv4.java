package lt.markmerkk;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func3;

/**
 * Created by mariusmerkevicius on 1/30/16.
 */
public class Adv4 {

  @Test
  public void test_input_should() throws Exception {
    // Arrange
    // Act
    long start = System.currentTimeMillis();
    run();
    System.out.println("Finished in: " + (System.currentTimeMillis() - start) + "ms");

    // Assert
  }

  public static void run() throws Exception {
    final ExecutorService executor = new ThreadPoolExecutor(4, 4, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
    try {

      Future<String> f1 = executor.submit(new CallToRemoteServiceA());
      Observable<String> f1Observable = Observable.from(f1);
      Observable<String> f3Observable = f1Observable
          .flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
              System.out.println("Observed from f1: " + s);
              Future<String> f3 = executor.submit(new CallToRemoteServiceC(s));
              return Observable.from(f3);
            }
          });

      Future<Integer> f2 = executor.submit(new CallToRemoteServiceB());
      Observable<Integer> f2Observable = Observable.from(f2);
      Observable<Integer> f4Observable = f2Observable
          .flatMap(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
              System.out.println("Observed from f2: " + integer);
              Future<Integer> f4 = executor.submit(new CallToRemoteServiceD(integer));
              return Observable.from(f4);
            }
          });

      Observable<Integer> f5Observable = f2Observable
          .flatMap(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
              System.out.println("Observed from f2: " + integer);
              Future<Integer> f5 = executor.submit(new CallToRemoteServiceE(integer));
              return Observable.from(f5);
            }
          });

      Observable.zip(f3Observable, f4Observable, f5Observable, new Func3<String, Integer, Integer, Map<String, String>>() {
        @Override
        public Map<String, String> call(String s, Integer integer, Integer integer2) {
          Map<String, String> map = new HashMap<String, String>();
          map.put("f3", s);
          map.put("f4", String.valueOf(integer));
          map.put("f5", String.valueOf(integer2));
          return map;
        }
      }).subscribe(new Action1<Map<String, String>>() {
        @Override
        public void call(Map<String, String> map) {
          System.out.println(map.get("f3") + " => " + (Integer.valueOf(map.get("f4")) * Integer.valueOf(map.get("f5"))));
        }
      });

    } finally {
      executor.shutdownNow();
    }
  }

  //region Dependencies

  private static final class CallToRemoteServiceA implements Callable<String> {
    @Override
    public String call() throws Exception {
      System.out.println("A called");
      // simulate fetching data from remote service
      Thread.sleep(1000);
      return "responseA";
    }
  }

  private static final class CallToRemoteServiceB implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
      System.out.println("B called");
      // simulate fetching data from remote service
      Thread.sleep(4000);
      return 100;
    }
  }

  private static final class CallToRemoteServiceC implements Callable<String> {

    private final String dependencyFromA;

    public CallToRemoteServiceC(String dependencyFromA) {
      this.dependencyFromA = dependencyFromA;
    }

    @Override
    public String call() throws Exception {
      System.out.println("C called");
      // simulate fetching data from remote service
      Thread.sleep(6000);
      return "responseB_" + dependencyFromA;
    }
  }

  private static final class CallToRemoteServiceD implements Callable<Integer> {

    private final Integer dependencyFromB;

    public CallToRemoteServiceD(Integer dependencyFromB) {
      this.dependencyFromB = dependencyFromB;
    }

    @Override
    public Integer call() throws Exception {
      System.out.println("D called");
      // simulate fetching data from remote service
      Thread.sleep(1400);
      return 40 + dependencyFromB;
    }
  }

  private static final class CallToRemoteServiceE implements Callable<Integer> {

    private final Integer dependencyFromB;

    public CallToRemoteServiceE(Integer dependencyFromB) {
      this.dependencyFromB = dependencyFromB;
    }

    @Override
    public Integer call() throws Exception {
      System.out.println("E called");
      // simulate fetching data from remote service
      Thread.sleep(5500);
      return 5000 + dependencyFromB;
    }
  }

  //endregion

}
