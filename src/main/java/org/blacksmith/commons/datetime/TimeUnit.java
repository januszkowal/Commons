package org.blacksmith.commons.datetime;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public enum TimeUnit implements DateOperation {
  DAY("D", "Day", ChronoUnit.DAYS, 1),
  WEEK("W", "Week", ChronoUnit.WEEKS, 1),
  MONTH("M", "Month", ChronoUnit.MONTHS,1),
  QUARTER("Q", "Quarter", ChronoUnit.MONTHS, 3),
  HALF_YEAR("H", "Half-Year", ChronoUnit.MONTHS, 6),
  YEAR("Y", "Year", ChronoUnit.YEARS,1);

  private final String symbol;
  private final String unitName;
  private final ChronoUnit chronoUnit;
  private final int chronoUnitCount;
  private static Map<String, TimeUnit> unitMap =
      Arrays.stream(TimeUnit.values()).collect(Collectors.toMap(TimeUnit::symbol, e -> e));
  private static Set<ChronoUnit> supportedUnits =
      Collections.unmodifiableSet(Arrays.stream(TimeUnit.values()).map(TimeUnit::chronoUnit).distinct().collect(Collectors.toSet()));

  TimeUnit(String symbol, String unitName, ChronoUnit chronoUnit, int chronoUnitCount) {
    this.symbol = symbol;
    this.unitName = unitName;
    this.chronoUnit = chronoUnit;
    this.chronoUnitCount = chronoUnitCount;
  }

  public String symbol() {
    return this.symbol;
  }

  public String unitName() {
    return this.unitName;
  }

  public TimeUnit of(String symbol) {
    return unitMap.get(symbol);
  }

  public ChronoUnit chronoUnit() {
    return this.chronoUnit;
  }

  public int getChronoUnitCount() {
    return this.chronoUnitCount;
  }

  @Override
  public <T extends Temporal> T addTo(T t, int q) {
    return chronoUnit.addTo(t, q * chronoUnitCount);
  }

  @Override
  public <T extends Temporal> T minusFrom(T t, int q) {
    return chronoUnit.addTo(t, -q * chronoUnitCount);
  }

  public Set<ChronoUnit> supportedUnits() {
    return supportedUnits;
  }
}
