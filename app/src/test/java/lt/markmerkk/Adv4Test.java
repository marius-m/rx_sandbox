package lt.markmerkk;

import java.util.Random;
import org.junit.Test;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by mariusmerkevicius on 3/1/16.
 */
public class Adv4Test {
  @Test
  public void test_inputValid_shouldExec() throws Exception {
    // Arrange

    // Act
    rxJobExecute(new Job()).subscribeOn(Schedulers.io()).subscribe();
    rxJobExecute(new Job()).subscribeOn(Schedulers.io()).subscribe();
    Thread.sleep(6000);

    // Assert

  }

  private Observable<Void> rxJobExecute(Job job) {
    return Observable.fromCallable(() -> {
      job.execute();
      return null;
    });
  }

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

}
