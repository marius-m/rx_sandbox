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
        .map(s -> Integer.parseInt(s)+1)
        .subscribe(i -> System.out.println("subscriber = "+i));
  }

}

