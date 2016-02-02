package lt.markmerkk;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by mariusmerkevicius on 1/28/16.
 * Source: http://blog.danlew.net/2014/09/30/grokking-rxjava-part-3/
 */
public class Adv3Test {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

//  INFO | 2016-01-30 17:24:41,828 | Adv3Test.java | 29 | one/four
//  INFO | 2016-01-30 17:24:41,831 | Adv3Test.java | 29 | two/five
//  INFO | 2016-01-30 17:24:41,831 | Adv3Test.java | 29 | three/six
  @Test
  public void adv3_zip_shouldProceed() throws Exception {
    // Arrange
    // Act
    // Assert
    Observable<String> source1 = Observable.from(new String[]{"one", "two", "three", "four"});
    Observable<String> source2 = Observable.from(new String[]{"four", "five", "six"});

    Observable.zip(source1, source2, new Func2<String, String, String>() {
      @Override
      public String call(String s1, String s2) {
        return s1 + "/" + s2;
      }
    }).subscribe(s -> logger.info(s));
  }

//  INFO | 2016-01-30 17:28:33,392 | Adv3Test.java | 53 | one/aaa
//  INFO | 2016-01-30 17:28:33,394 | Adv3Test.java | 53 | two/bbb
//  INFO | 2016-01-30 17:28:33,394 | Adv3Test.java | 53 | three/ccc
  @Test
  public void adv3_zip2_shouldProceed() throws Exception {
    // Arrange
    // Act
    // Assert
    Observable<String> source1 = Observable.from(new String[]{"one", "two", "three", "four"});
    Observable<String> source2 = Observable.from(new ArrayList<String>() {{
      add("aaa");
      add("bbb");
      add("ccc");
    }});

    Observable.zip(source1, source2, new Func2<String, String, String>() {
      @Override
      public String call(String s1, String s2) {
        return s1 + "/" + s2;
      }
    }).subscribe(s -> logger.info(s));
  }

  @Test
  public void adv3_zip3_shouldProceed() throws Exception {
    // Arrange
    // Act
    // Assert
    Observable<String> source1 = Observable.from(new String[]{"one", "two", "three", "four"});
    Observable<String> source2 = Observable.from(new ArrayList<String>() {{
      add("aaa");
      add("bbb");
      add("ccc");
    }});

    source1
        .flatMap(new Func1<String, Observable<String>>() {
          @Override
          public Observable<String> call(String s) {
            return Observable.just(s);
          }
        }, new Func2<String, String, String>() {
          @Override
          public String call(String s, String s2) {
            return s + "/" + s2;
          }
        })
        .subscribe(s -> logger.info(s));
  }

//  INFO | 2016-01-30 18:16:33,619 | Adv3Test.java | 106 | one
//  INFO | 2016-01-30 18:16:33,622 | Adv3Test.java | 106 | one/two
//  INFO | 2016-01-30 18:16:33,622 | Adv3Test.java | 106 | one/two/three
//  INFO | 2016-01-30 18:16:33,622 | Adv3Test.java | 106 | one/two/three/four
  @Test
  public void adv3_scan_shouldĘProceed() throws Exception {
    // Arrange
    // Act
    // Assert
    Observable<String> source1 = Observable.from(new String[]{"one", "two", "three", "four"});
    Observable<String> source2 = Observable.from(new ArrayList<String>() {{
      add("aaa");
      add("bbb");
      add("ccc");
    }});

    source1
        .scan(new Func2<String, String, String>() {
          @Override
          public String call(String s, String s2) {
            return s + "/" + s2;
          }
        })
        .subscribe(s -> logger.info(s));
  }

//  INFO | 2016-01-30 18:02:20,405 | Adv3Test.java | 73 | one
//  INFO | 2016-01-30 18:02:20,407 | Adv3Test.java | 73 | two
//  INFO | 2016-01-30 18:02:20,408 | Adv3Test.java | 73 | three
//  INFO | 2016-01-30 18:02:20,408 | Adv3Test.java | 73 | four
//  INFO | 2016-01-30 18:02:20,409 | Adv3Test.java | 73 | aaa
//  INFO | 2016-01-30 18:02:20,409 | Adv3Test.java | 73 | bbb
//  INFO | 2016-01-30 18:02:20,409 | Adv3Test.java | 73 | ccc
  @Test
  public void adv3_merge_shouldProceed() throws Exception {
    // Arrange
    // Act
    // Assert
    Observable<String> source1 = Observable.from(new String[]{"one", "two", "three", "four"});
    Observable<String> source2 = Observable.from(new ArrayList<String>() {{
      add("aaa");
      add("bbb");
      add("ccc");
    }});

    Observable.merge(source1, source2).subscribe(s -> logger.info(s));
  }

//  INFO | 2016-01-30 18:02:38,942 | Adv3Test.java | 100 | four / aaa
//  INFO | 2016-01-30 18:02:38,945 | Adv3Test.java | 100 | four / bbb
//  INFO | 2016-01-30 18:02:38,945 | Adv3Test.java | 100 | four / ccc
  @Test
  public void adv3_combineLatest_shouldProceed() throws Exception {
    // Arrange
    // Act
    // Assert
    Observable<String> source1 = Observable.from(new String[]{"one", "two", "three", "four"});
    Observable<String> source2 = Observable.from(new ArrayList<String>() {{
      add("aaa");
      add("bbb");
      add("ccc");
    }});

    Observable.combineLatest(source1, source2, new Func2<String, String, String>() {
      @Override
      public String call(String s, String s2) {
        return s + " / " + s2;
      }
    }).subscribe(s -> logger.info(s));
  }

}
