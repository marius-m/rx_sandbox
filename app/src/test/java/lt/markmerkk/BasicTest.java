package lt.markmerkk;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by mariusmerkevicius on 1/28/16.
 * Source: http://blog.danlew.net/2014/09/15/grokking-rxjava-part-1/
 */
public class BasicTest {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

//  INFO | 2016-01-28 23:01:04,434 | BasicTest.java | 30 | Hello, world!
  @Test
  public void basic_basicObservableUsingVariables_shouldExecute() throws Exception {
    Observable<String> myObservable = Observable.create(
        new Observable.OnSubscribe<String>() {
          @Override
          public void call(Subscriber<? super String> sub) {
            sub.onNext("Hello, world!");
            sub.onCompleted();
          }
        }
    );
    Subscriber<String> mySubscriber = new Subscriber<String>() {
      @Override
      public void onNext(String s) {
        logger.info(s);
      }

      @Override
      public void onCompleted() { }

      @Override
      public void onError(Throwable e) { }
    };
    myObservable.subscribe(mySubscriber);
  }

//  INFO | 2016-01-28 23:06:07,366 | BasicTest.java | 51 | Hello world!
  @Test
  public void basic_basicObservableWrap1_shouldExecute() throws Exception {
    Observable<String> myObservable = Observable.just("Hello world!");
    myObservable.subscribe(new Action1<String>() {
      @Override
      public void call(String s) {
        logger.info(s);
      }
    });
  }

//  INFO | 2016-01-28 23:07:14,178 | BasicTest.java | 60 | Hello world!
  @Test
  public void basic_basicObservableLambda_shouldExecute() throws Exception {
    Observable<String> myObservable = Observable.just("Hello world!");
    myObservable.subscribe(s -> logger.info(s));
  }
}
