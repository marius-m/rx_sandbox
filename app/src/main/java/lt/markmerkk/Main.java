package lt.markmerkk;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by mariusmerkevicius on 1/27/16.
 */
public class Main {



  public static void main(String[] args) {
//    Observable.just("Something").subscribe(new Action1<String>() {
//      @Override
//      public void call(String s) {
//        System.out.println("Somehing +" + s);
//      }
//    });
    Observable.just("Something").subscribe(s -> {
      System.out.println("Something +"+s);
    });
  }

}

