package lt.markmerkk;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by mariusmerkevicius on 1/27/16.
 */
public class Main {



  public static void main(String[] args) {
    Observable.just("123")
        .map(new Func1<String, Integer>() {
          @Override
          public Integer call(String s) {
            return Integer.parseInt(s)+1;
          }
        })
        .subscribe(i -> System.out.println("subscriber = "+i));
  }

}

