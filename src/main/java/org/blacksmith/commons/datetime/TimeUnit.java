package org.blacksmith.commons.datetime;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TimeUnit implements DateOperation {
  DAY("D","Day"){
    @SuppressWarnings("unchecked")
    @Override public Temporal plus(Temporal t, int amount) {
      return ChronoUnit.DAYS.addTo(t,amount);
    }
    @SuppressWarnings("unchecked")
    @Override public Temporal minus(Temporal t, int amount) {
      return ChronoUnit.DAYS.addTo(t,-amount);
    }
  },
  WEEK("W","Week"){
    @SuppressWarnings("unchecked")
    @Override public Temporal plus(Temporal t, int amount) {
      return ChronoUnit.WEEKS.addTo(t,amount);
    }
    @SuppressWarnings("unchecked")
    @Override public Temporal minus(Temporal t, int amount) {
      return ChronoUnit.WEEKS.addTo(t,-amount);
    }
  },
  MONTH("M","Month"){
    @SuppressWarnings("unchecked")
    @Override public Temporal plus(Temporal t, int amount) {
      return ChronoUnit.MONTHS.addTo(t,amount);
    }
    @SuppressWarnings("unchecked")
    @Override public Temporal minus(Temporal t, int amount) {
      return ChronoUnit.MONTHS.addTo(t,-amount);
    }
  },
  QUARTER("Q","Quarter"){
    @SuppressWarnings("unchecked")
    @Override public Temporal plus(Temporal t, int amount) {
      return ChronoUnit.MONTHS.addTo(t,3L*amount);
    }
    @SuppressWarnings("unchecked")
    @Override public Temporal minus(Temporal t, int amount) {
      return ChronoUnit.MONTHS.addTo(t,-3L*amount);
    }
  },
  HALF_YEAR("H","Half-Year"){
    @SuppressWarnings("unchecked")
    @Override public Temporal plus(Temporal t, int amount) {
      return ChronoUnit.MONTHS.addTo(t,6L*amount);
    }
    @SuppressWarnings("unchecked")
    @Override public Temporal minus(Temporal t, int amount) {
      return ChronoUnit.MONTHS.addTo(t,-6L*amount);
    }
  },
  YEAR("Y","Year"){
    @SuppressWarnings("unchecked")
    @Override public Temporal plus(Temporal t, int amount) {
      return ChronoUnit.YEARS.addTo(t,amount);
    }
    @SuppressWarnings("unchecked")
    @Override public Temporal minus(Temporal t, int amount) {
      return ChronoUnit.YEARS.addTo(t,-amount);
    }
  };
  private String symbol;
  private String symbolName;
  private static Map<String,TimeUnit> unitMap =
      Arrays.stream(TimeUnit.values()).collect(Collectors.toMap(TimeUnit::symbol, e -> e));

  TimeUnit(String symbol, String symbolName) {
    this.symbol = symbol;
    this.symbolName = symbolName;
  }
  public String symbol() {
    return this.symbol;
  }
  public String symbolName() {
    return this.symbolName;
  }
  public TimeUnit of(String symbol) {
    return unitMap.get(symbol);
  }
}
