package org.blacksmith.commons.datetime;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SuppressWarnings({"SameParameterValue", "unused"})
public class LocalDateUtilsRef {

  // First day-of-month minus one for a standard year
  // array length 13 with element zero ignored, so month 1 to 12 can be queried directly
  private static final int[] STANDARD = {0, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
  // First day-of-month minus one for a leap year
  // array length 13 with element zero ignored, so month 1 to 12 can be queried directly
  private static final int[] LEAP = {0, 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335};

  /**
   * Restricted constructor.
   */
  private LocalDateUtilsRef() {
  }

  //-------------------------------------------------------------------------

  /**
   * Finds the day-of-year of the datetime.
   * <p>
   * Faster than the JDK method.
   *
   * @param date the datetime to query
   * @return the day-of-year
   */
  static int doy(LocalDate date) {
    int[] lookup = (date.isLeapYear() ? LEAP : STANDARD);
    return lookup[date.getMonthValue()] + date.getDayOfMonth();
  }

  /**
   * Adds a number of days to the datetime.
   * <p>
   * Faster than the JDK method.
   *
   * @param date      the datetime to add to
   * @param daysToAdd the days to add
   * @return the new datetime
   */
  static LocalDate plusDays(LocalDate date, int daysToAdd) {
    if (daysToAdd == 0) {
      return date;
    }
    // add the days to the current day-of-month
    // if it is guaranteed to be in this month or the next month then fast path it
    // (59th Jan is 28th Feb, 59th Feb is 31st Mar)
    int dom = date.getDayOfMonth() + daysToAdd;
    if (dom > 0 && dom <= 59) {
      int monthLen = date.lengthOfMonth();
      int month = date.getMonthValue();
      int year = date.getYear();
      if (dom <= monthLen) {
        return LocalDate.of(year, month, dom);
      } else if (month < 12) {
        return LocalDate.of(year, month + 1, dom - monthLen);
      } else {
        return LocalDate.of(year + 1, 1, dom - monthLen);
      }
    }
    long mjDay = Math.addExact(date.toEpochDay(), daysToAdd);
    return LocalDate.ofEpochDay(mjDay);
  }

  /**
   * Returns the number of days between two dates.
   * <p>
   * Faster than the JDK method.
   *
   * @param firstDate  the first datetime
   * @param secondDate the second datetime, after the first
   * @return the new datetime
   */
  static long daysBetween(LocalDate firstDate, LocalDate secondDate) {
    int firstYear = firstDate.getYear();
    int secondYear = secondDate.getYear();
    if (firstYear == secondYear) {
      return doy(secondDate) - doy(firstDate);
    }
    if ((firstYear + 1) == secondYear) {
      return (firstDate.lengthOfYear() - doy(firstDate)) + doy(secondDate);
    }
    return secondDate.toEpochDay() - firstDate.toEpochDay();
  }

  //-------------------------------------------------------------------------

  /**
   * Streams the set of dates included in the range.
   * <p>
   * This returns a stream consisting of each datetime in the range. The stream is ordered.
   *
   * @param startInclusive the start datetime
   * @param endExclusive   the end datetime
   * @return the stream of dates from the start to the end
   */
  static Stream<LocalDate> stream(LocalDate startInclusive, LocalDate endExclusive) {
    Iterator<LocalDate> it = new Iterator<>() {
      private LocalDate current = startInclusive;

      @Override
      public LocalDate next() {
        LocalDate result = current;
        current = plusDays(current, 1);
        return result;
      }

      @Override
      public boolean hasNext() {
        return current.isBefore(endExclusive);
      }
    };
    long count = endExclusive.toEpochDay() - startInclusive.toEpochDay() + 1;
    Spliterator<LocalDate> spliterator = Spliterators.spliterator(it, count,
        Spliterator.IMMUTABLE | Spliterator.NONNULL |
            Spliterator.DISTINCT | Spliterator.ORDERED | Spliterator.SORTED |
            Spliterator.SIZED | Spliterator.SUBSIZED);
    return StreamSupport.stream(spliterator, false);
  }
}
