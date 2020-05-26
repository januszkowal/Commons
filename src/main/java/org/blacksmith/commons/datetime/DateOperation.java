package org.blacksmith.commons.datetime;

import java.time.temporal.Temporal;

public interface DateOperation {
  <T extends Temporal> T addTo(T t, int q);
  default <T extends Temporal> T addTo(T t) {
    return addTo(t,1);
  }
  <T extends Temporal> T minusFrom(T t, int q);
  default <T extends Temporal> T minusFrom(T t) {
    return minusFrom(t,1);
  }
}
