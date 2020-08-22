package com.miya10kei;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;

public class EventLoop {
  private final ConcurrentLinkedDeque<Event> events = new ConcurrentLinkedDeque<>();
  private final ConcurrentHashMap<String, Consumer<Object>> handlers = new ConcurrentHashMap<>();

  public EventLoop on(String key, Consumer<Object> handler) {
    this.handlers.put(key, handler);
    return this;
  }

  public void dispatch(Event event) {
    events.add(event);
  }

  public void stop() {
    Thread.currentThread().interrupt();
  }

  public void run() {
    while (!(events.isEmpty() && Thread.interrupted())) {
      if (!events.isEmpty()) {
        var event = events.pop();
        if (handlers.containsKey(event.getKey())) {
          handlers.get(event.getKey()).accept(event.getData());
        } else {
          System.err.println("No handler for key " + event.getKey());
        }
      }
    }
  }
}
