package com.miya10kei;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Event {
  private final String key;
  private final Object data;
}
