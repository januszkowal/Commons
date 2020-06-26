package org.blacksmith.commons.datetime;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Map;
import org.blacksmith.commons.enums.EnumUtils;

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
  private static final Map<String, TimeUnit> unitMap = EnumUtils.getAttrEnumMap(TimeUnit.class,TimeUnit::symbol);

  TimeUnit(String symbol, String unitName, ChronoUnit chronoUnit, int chronoUnitCount) {
    this.symbol = symbol;
    this.unitName = unitName;
    this.chronoUnit = chronoUnit;
    this.chronoUnitCount = chronoUnitCount;
  }

  public static TimeUnit ofSymbol(String symbol) {
    return unitMap.get(symbol);
  }

  @Override
  public <T extends Temporal> T addTo(T t, int q) {
    return chronoUnit.addTo(t, q * chronoUnitCount);
  }

  @Override
  public <T extends Temporal> T minusFrom(T t, int q) {
    return chronoUnit.addTo(t, -q * chronoUnitCount);
  }

  public String symbol() {
    return this.symbol;
  }

  public String unitName() {return this.unitName; }

  public ChronoUnit chronoUnit() {
    return this.chronoUnit;
  }

  public int chronoUnitCount() {
    return this.chronoUnitCount;
  }
}
