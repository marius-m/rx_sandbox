package lt.markmerkk;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mariusmerkevicius on 1/27/16.
 */
public class Main {

//  // Returns a List of website URLs based on a text search
//  static Observable<List<String>> query(String text) {
//    return Observable.create(new Observable.OnSubscribe<List<String>>() {
//      @Override
//      public void call(Subscriber<? super List<String>> subscriber) {
//        subscriber.onNext(new ArrayList<String>(){{
//          add("one");
//          add("two");
//          add("three");
//        }});
//        subscriber.onCompleted();
//      }
//    });
//  }

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
//    query("Hello, world!")
//        .flatMap(urls -> Observable.from(urls))
//        .subscribe(url -> System.out.println(url));
//    query("Hello, world!")
//        .flatMap(urls -> Observable.from(urls))
//        .flatMap(new Func1<String, Observable<String>>() {
//          @Override
//          public Observable<String> call(String url) {
//            return getTitle(url);
//          }
//        })
//        .subscribe(title -> System.out.println(title));
//    query("Hello, world!")
//        .flatMap(urls -> Observable.from(urls))
//        .flatMap(url -> getTitle(url))
//        .filter(title -> title != null)
//        .take(5)
//        .doOnNext(title -> System.out.println("Doing something more with a "+title))
//        .subscribe(title -> System.out.println(title));
//    query("Hello, world!")
//        .flatMap(urls -> Observable.from(urls))
//        .map(new Func1<String, String>() {
//          @Override
//          public String call(String s) {
//            if ("two".equals(s))
//              throw new IllegalArgumentException("s is two!");
//            return s;
//          }
//        })
//        .subscribe(new Subscriber<String>() {
//          @Override
//          public void onCompleted() {
//            System.out.println("Complete");
//          }
//
//          @Override
//          public void onError(Throwable e) {
//            System.out.println("Some error: "+e.getMessage());
//          }
//
//          @Override
//          public void onNext(String s) {
//            System.out.println("Output: "+s);
//          }
//        });

    // Cold observable

  }


//  private static Observable<String> getTitle(String url) {
//    return Observable.just(("two".equals(url) ? url : null));
//  }

}

