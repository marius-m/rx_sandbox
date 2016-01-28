package lt.markmerkk;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by mariusmerkevicius on 1/28/16.
 */
public class Transformation {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

//  INFO | 2016-01-28 23:20:47,370 | Transformation.java | 24 | Hello, world! -modify
  @Test
  public void test_inputTransformationBasic_shouldTransform() throws Exception {
    Observable.just("Hello, world!")
        .map(new Func1<String, String>() {
          @Override
          public String call(String s) {
            return s + " -modify";
          }
        })
        .subscribe(s -> logger.info(s));
  }

//  INFO | 2016-01-28 23:21:46,596 | Transformation.java | 32 | Hello, world! -modify
  @Test
  public void test_inputTransformationBasicLambda_shouldTransform() throws Exception {
    Observable.just("Hello, world!")
        .map(s -> s + " -modify")
        .subscribe(s -> logger.info(s));
  }

//  INFO | 2016-01-28 23:22:53,378 | Transformation.java | 45 | -1880044555
  @Test
  public void test_inputTransformationOtherType_shouldTransform() throws Exception {
    Observable.just("Hello, world!")
        .map(new Func1<String, Integer>() {
          @Override
          public Integer call(String s) {
            return s.hashCode();
          }
        })
        .subscribe(i -> logger.info(Integer.toString(i)));
  }

//  INFO | 2016-01-28 23:23:36,910 | Transformation.java | 53 | -1880044555
  @Test
  public void test_inputTransformationOtherTypeLambda_shouldTransform() throws Exception {
    Observable.just("Hello, world!")
        .map(s -> s.hashCode())
        .subscribe(i -> logger.info(Integer.toString(i)));
  }
}
