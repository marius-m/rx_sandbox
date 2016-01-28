package lt.markmerkk;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by mariusmerkevicius on 1/28/16.
 */
public class AdvTest {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  // Setup
  Observable<List<String>> query(String text) {
    return Observable.create(new Observable.OnSubscribe<List<String>>() {
      @Override
      public void call(Subscriber<? super List<String>> subscriber) {
        subscriber.onNext(new ArrayList<String>() {{
          add("one");
          add("two");
          add("three");
        }});
      }
    });
  }

//  INFO | 2016-01-28 23:35:58,217 | AdvTest.java | 37 | url1
//  INFO | 2016-01-28 23:35:58,220 | AdvTest.java | 37 | url2
//  INFO | 2016-01-28 23:35:58,220 | AdvTest.java | 37 | url3
  @Test
  public void from_traverseCollection_shouldExecute() throws Exception {
    // Arrange
    // Act
    // Assert
    Observable.from(new String[]{"url1", "url2", "url3"})
        .subscribe(url -> logger.info(url));
  }

//  INFO | 2016-01-28 23:37:42,640 | AdvTest.java | 56 | one
//  INFO | 2016-01-28 23:37:42,642 | AdvTest.java | 56 | two
//  INFO | 2016-01-28 23:37:42,642 | AdvTest.java | 56 | three
  @Test
  public void from_flatMap_shouldExecute() throws Exception {
    // Arrange
    // Act
    // Assert
    query("Hello, world!")
        .flatMap(new Func1<List<String>, Observable<String>>() {
          @Override
          public Observable<String> call(List<String> urls) {
            return Observable.from(urls);
          }
        })
        .subscribe(url -> logger.info(url));
  }

//  INFO | 2016-01-28 23:39:04,112 | AdvTest.java | 72 | one
//  INFO | 2016-01-28 23:39:04,115 | AdvTest.java | 72 | two
//  INFO | 2016-01-28 23:39:04,115 | AdvTest.java | 72 | three
  @Test
  public void from_flatMapLambda_shouldExecute() throws Exception {
    // Arrange
    // Act
    // Assert
    query("Hello, world!")
        .flatMap(urls -> Observable.from(urls))
        .subscribe(url -> logger.info(url));
  }

//  INFO | 2016-01-28 23:43:17,413 | AdvTest.java | 85 | mod:one
//  INFO | 2016-01-28 23:43:17,415 | AdvTest.java | 85 | mod:two
//  INFO | 2016-01-28 23:43:17,416 | AdvTest.java | 85 | mod:three
  @Test
  public void from_flatModification_shouldExecute() throws Exception {
    query("Hello, world!")
        .flatMap(urls -> Observable.from(urls))
        .flatMap(new Func1<String, Observable<String>>() {
          @Override
          public Observable<String> call(String url) {
            return Observable.just("mod:" + url);
          }
        })
        .subscribe(title -> logger.info(title));
  }

//  INFO | 2016-01-28 23:44:31,034 | AdvTest.java | 99 | modone
//  INFO | 2016-01-28 23:44:31,038 | AdvTest.java | 99 | modtwo
//  INFO | 2016-01-28 23:44:31,038 | AdvTest.java | 99 | modthree
  @Test
  public void from_flatModificationLambda_shouldExecute() throws Exception {
    query("Hello, world!")
        .flatMap(urls -> Observable.from(urls))
        .flatMap(url -> Observable.just("mod" + url))
        .subscribe(title -> logger.info(title));
  }

//  INFO | 2016-01-28 23:46:12,975 | AdvTest.java | 108 | two
  @Test
  public void from_flatFilterLambda_shouldExecute() throws Exception {
    query("Hello, world!")
        .flatMap(urls -> Observable.from(urls))
        .flatMap(url -> Observable.just(("two".equals(url)) ? "two" : null))
        .filter(title -> title != null)
        .subscribe(title -> logger.info(title));
  }

//  INFO | 2016-01-28 23:47:26,664 | AdvTest.java | 118 | mod:one
//  INFO | 2016-01-28 23:47:26,667 | AdvTest.java | 118 | mod:two
  @Test
  public void from_flatTake2_shouldExecute() throws Exception {
    query("Hello, world!")
        .flatMap(urls -> Observable.from(urls))
        .flatMap(url -> Observable.just("mod:" + url))
        .take(2)
        .subscribe(title -> logger.info(title));
  }

//  INFO | 2016-01-28 23:48:49,742 | AdvTest.java | 129 | Doing something with mod:one
//  INFO | 2016-01-28 23:48:49,745 | AdvTest.java | 130 | mod:one
//  INFO | 2016-01-28 23:48:49,745 | AdvTest.java | 129 | Doing something with mod:two
//  INFO | 2016-01-28 23:48:49,746 | AdvTest.java | 130 | mod:two
  @Test
  public void from_flatDoSomething_shouldExecute() throws Exception {
    query("Hello, world!")
        .flatMap(urls -> Observable.from(urls))
        .flatMap(url -> Observable.just("mod:" + url))
        .take(2)
        .doOnNext(title -> logger.info("Doing something with "+title))
        .subscribe(title -> logger.info(title));
  }

}
