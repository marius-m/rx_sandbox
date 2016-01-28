package lt.markmerkk;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by mariusmerkevicius on 1/28/16.
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
}
