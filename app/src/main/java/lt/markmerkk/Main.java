package lt.markmerkk;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by mariusmerkevicius on 1/27/16.
 */
public class Main {



  public static void main(String[] args) {
    myObservable.subscribe(mySubscriber);
  }

  static Observable<String> myObservable = Observable.create(
      new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> sub) {
          sub.onNext("Hello, world!");
          sub.onCompleted();
        }
      }
  );

  static Subscriber<String> mySubscriber = new Subscriber<String>() {
    @Override
    public void onNext(String s) { System.out.println(s); }

    @Override
    public void onCompleted() { }

    @Override
    public void onError(Throwable e) { }
  };

}

