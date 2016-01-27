package lt.markmerkk;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by mariusmerkevicius on 1/27/16.
 */
public class Main {



  public static void main(String[] args) {
    Observable.just("observable")
        .map(s -> s+s)
        .subscribe(s -> System.out.println("subscriber = "+s));
  }

}

