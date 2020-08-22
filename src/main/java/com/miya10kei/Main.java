package com.miya10kei;

public class Main {

  public static void main(String[] args) {
    var eventLoop = new EventLoop();

    new Thread(() -> {
      for (int i = 0; i < 6; i++) {
        delay(100);
        eventLoop.dispatch(new Event("tick", i));
      }
      eventLoop.dispatch(new Event("stop", null));
    }).start();

    new Thread(() -> {
      delay(2500);
      eventLoop.dispatch(new Event("hello", "beautiful world"));
      delay(800);
      eventLoop.dispatch(new Event("hello", "beautiful universe"));
    }).start();

    eventLoop.dispatch(new Event("hello", "world!"));
    eventLoop.dispatch(new Event("foo", "bar"));

    eventLoop
        .on("hello", s -> System.out.println("hello " + s))
        .on("tick", n -> System.out.println("tick #" + n))
        .on("stop", v -> eventLoop.stop())
        .run();

    System.out.println("Bye!");
  }

  private static void delay(long mills) {
    try {
      Thread.sleep(mills);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
