package lt.markmerkk;

import java.util.Random;
import org.junit.Test;
import rx.Observable;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by mariusmerkevicius on 3/1/16.
 */
public class Adv4Test {
  @Test
  public void test_inputZip_shouldExec() throws Exception {
    // Arrange

    // Act
    Observable run1 = rxJobExecute(new Job()).subscribeOn(Schedulers.io());
    Observable run2 = rxJobExecute(new Job()).subscribeOn(Schedulers.io());
    //Thread.sleep(6000);

    // Assert
    Observable.zip(run1, run2, new Func2() {
      @Override
      public Object call(Object o, Object o2) {
        return null;
      }
    }).toBlocking().single();
  }

  @Test
  public void test_inputMerge_shouldExec() throws Exception {
    // Arrange

    // Act
    Observable run1 = rxJobExecute2(new Job()).subscribeOn(Schedulers.io());
    Observable run2 = rxJobExecute2(new Job()).subscribeOn(Schedulers.io());
    //Thread.sleep(6000);

    // Assert
    Observable.merge(run1, run2).toBlocking().subscribe();
  }

  //region Convenience

  private Observable<Object> rxJobExecute2(Job job) {
    return Observable.empty()
        .doOnCompleted(job::execute);
  }

  private Observable<Void> rxJobExecute(Job job) {
    return Observable.fromCallable(() -> {
      job.execute();
      return null;
    });
  }

  //endregion

  //region Classes

  private class Job {
    void execute() {
      System.out.println("Start");
      try {
        int sleep = new Random().nextInt(5000) + 1;
        System.out.println("Sleep " + sleep);
        Thread.sleep(sleep);
      } catch (InterruptedException e) { }
      System.out.println("End");
    }
  }

  //endregion

}
