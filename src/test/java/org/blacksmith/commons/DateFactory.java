package org.blacksmith.commons;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateFactory {

  public static java.sql.Timestamp createSqlTimestamp(LocalDateTime dt) {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(Calendar.YEAR, dt.getYear());
    cal.set(Calendar.MONTH, dt.getMonthValue() - 1);
    cal.set(Calendar.DAY_OF_MONTH, dt.getDayOfMonth());
    cal.set(Calendar.HOUR_OF_DAY, dt.getHour());
    cal.set(Calendar.MINUTE, dt.getMinute());
    cal.set(Calendar.SECOND, dt.getSecond());
    Timestamp result = new Timestamp(cal.toInstant().toEpochMilli());
    result.setNanos(dt.getNano());
    return result;
  }

  public static java.util.Date createDate(LocalDate d) {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(Calendar.YEAR, d.getYear());
    cal.set(Calendar.MONTH, d.getMonthValue() - 1);
    cal.set(Calendar.DAY_OF_MONTH, d.getDayOfMonth());
    return new Date(cal.toInstant().toEpochMilli());
  }

  public static Date createDate(LocalDateTime dt) {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(Calendar.YEAR, dt.getYear());
    cal.set(Calendar.MONTH, dt.getMonthValue() - 1);
    cal.set(Calendar.DAY_OF_MONTH, dt.getDayOfMonth());
    cal.set(Calendar.HOUR_OF_DAY, dt.getHour());
    cal.set(Calendar.MINUTE, dt.getMinute());
    cal.set(Calendar.SECOND, dt.getSecond());
    return new Date(cal.toInstant().toEpochMilli());
  }

  public static java.sql.Date createSqlDate(LocalDate d) {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(Calendar.YEAR, d.getYear());
    cal.set(Calendar.MONTH, d.getMonthValue() - 1);
    cal.set(Calendar.DAY_OF_MONTH, d.getDayOfMonth());
    return new java.sql.Date(cal.toInstant().toEpochMilli());
  }

  public static java.sql.Date createSqlDate(LocalDateTime dt) {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(Calendar.YEAR, dt.getYear());
    cal.set(Calendar.MONTH, dt.getMonthValue() - 1);
    cal.set(Calendar.DAY_OF_MONTH, dt.getDayOfMonth());
    cal.set(Calendar.HOUR_OF_DAY, dt.getHour());
    cal.set(Calendar.MINUTE, dt.getMinute());
    cal.set(Calendar.SECOND, dt.getSecond());
    return new java.sql.Date(cal.toInstant().toEpochMilli());
  }

  public static Timestamp createSqlTimestampWithoutNanos(LocalDateTime dt) {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(Calendar.YEAR, dt.getYear());
    cal.set(Calendar.MONTH, dt.getMonthValue() - 1);
    cal.set(Calendar.DAY_OF_MONTH, dt.getDayOfMonth());
    cal.set(Calendar.HOUR_OF_DAY, dt.getHour());
    cal.set(Calendar.MINUTE, dt.getMinute());
    cal.set(Calendar.SECOND, dt.getSecond());
    return new Timestamp(cal.toInstant().toEpochMilli());
  }
}
