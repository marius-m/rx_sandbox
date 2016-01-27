package lt.markmerkk;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by mariusmerkevicius on 1/27/16.
 */
public class Main {



  public static void main(String[] args) {
    myObservable.subscribe(myAction);
  }

  static Observable<String> myObservable = Observable.just("Something");

  static Action1<String> myAction = new Action1<String>() {

    @Override
    public void call(String s) {
      System.out.println("Some action "+s);
    }
  };

}

