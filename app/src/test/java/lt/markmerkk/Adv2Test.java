package lt.markmerkk;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by mariusmerkevicius on 1/28/16.
 * Source: http://blog.danlew.net/2014/09/30/grokking-rxjava-part-3/
 */
public class Adv2Test {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

//  INFO | 2016-01-28 23:58:47,157 | Adv2Test.java | 36 | onError
  @Test
  public void adv2_inputErrorHandling_shouldThrow() throws Exception {
    // Arrange
    // Act
    // Assert
    Observable.just("Hello, world!")
        .map(new Func1<String, String>() {
          @Override
          public String call(String s) {
            throw new IllegalArgumentException("Some error!");
          }
        })
        .subscribe(new Subscriber<String>() {
          @Override
          public void onCompleted() {
            logger.info("onCompleted");
          }

          @Override
          public void onError(Throwable e) {
            logger.info("onError");
          }

          @Override
          public void onNext(String s) {
            logger.info("onNext: "+s);
          }
        });
  }

//  INFO | 2016-01-28 23:59:23,261 | Adv2Test.java | 72 | onNext: Mod:Hello, world!
//  INFO | 2016-01-28 23:59:23,263 | Adv2Test.java | 62 | onCompleted
  @Test
  public void adv2_inputErrorHandling_shouldProceed() throws Exception {
    // Arrange
    // Act
    // Assert
    Observable.just("Hello, world!")
        .map(new Func1<String, String>() {
          @Override
          public String call(String s) {
            return "Mod:"+s;
          }
        })
        .subscribe(new Subscriber<String>() {
          @Override
          public void onCompleted() {
            logger.info("onCompleted");
          }

          @Override
          public void onError(Throwable e) {
            logger.info("onError");
          }

          @Override
          public void onNext(String s) {
            logger.info("onNext: "+s);
          }
        });
  }

}
