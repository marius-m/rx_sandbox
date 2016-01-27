package lt.markmerkk;

import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by mariusmerkevicius on 1/27/16.
 */
public class Main {

  // Returns a List of website URLs based on a text search
  static Observable<List<String>> query(String text) {
    return Observable.create(new Observable.OnSubscribe<List<String>>() {
      @Override
      public void call(Subscriber<? super List<String>> subscriber) {
        subscriber.onNext(new ArrayList<String>(){{
          add("one");
          add("two");
          add("three");
        }});
        subscriber.onCompleted();
      }
    });
  }

  public static void main(String[] args) {
//    query("Hello, world!")
//        .subscribe(urls -> {
//          for (String url : urls)
//            System.out.println(url);
//        });
//    Observable.from(new String[]{"url1", "url2", "url3"})
//        .subscribe(url -> {
//          System.out.println("Output:" + url);
//        });
//    query("Hello")
//        .subscribe(urls -> {
//          Observable.from(urls)
//              .subscribe(url -> System.out.println(url));
//        });
//    query("Hello, world!")
//        .flatMap(new Func1<List<String>, Observable<String>>() {
//          @Override
//          public Observable<String> call(List<String> urls) {
//            return Observable.from(urls);
//          }
//        })
//        .subscribe(url -> System.out.println(url));
    query("Hello, world!")
        .flatMap(urls -> Observable.from(urls))
        .subscribe(url -> System.out.println(url));
  }

}

