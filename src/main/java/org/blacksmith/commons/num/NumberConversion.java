package org.blacksmith.commons.num;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.blacksmith.commons.arg.Validate;

public class NumberConversion
{
  private static final BigInteger LONG_MIN = BigInteger.valueOf(Long.MIN_VALUE);

  private static final BigInteger LONG_MAX = BigInteger.valueOf(Long.MAX_VALUE);

  private NumberConversion() {}

  public static BigDecimal bigDecimalFromDouble(double value) {
    return BigDecimal.valueOf(value) ;
  }

  public static boolean isNumber(Object value) {
    try {
      Number n = (Number)value;
      return true;
    }
    catch (Exception e) {
      return false;
    }
  }

  public static boolean isNumber(Class<?> clazz) {
    try {
      return Number.class.isAssignableFrom(clazz);
    }
    catch (Exception e) {
      return false;
    }
  }

  private static boolean long2bool(long value)
  {
    return value==1L;
  }

  public static Long createLong(Number value)
  {
    return (value==null) ? null : Long.valueOf(value.longValue());
  }

  public static Long createLong(String value)
  {
    return (StringUtils.isEmpty(value)) ? null : Long.valueOf(value);
  }

  public static Integer createInteger(Number value)
  {
    return (value==null) ? null : Integer.valueOf(value.intValue());
  }

  public static Integer createInteger(String value)
  {
    return (StringUtils.isEmpty(value)) ? null : Integer.valueOf(value);
  }

  public static BigInteger createBigInteger(long value)
  {
    return BigInteger.valueOf(value);
  }

  public static BigInteger createBigInteger(Number value)
  {
    return (value==null) ? null : BigInteger.valueOf(value.longValue());
  }

  public static long toLong(Number value)
  {
    return (value==null) ? 0 : Long.valueOf(value.longValue());
  }

  public static long toLong(String value)
  {
    return (StringUtils.isEmpty(value)) ? null : Long.valueOf(value);
  }

  public static int toInt(Number value)
  {
    return (value==null) ? 0 : Integer.valueOf(value.intValue());
  }

  public static int toInt(String value)
  {
    return (StringUtils.isEmpty(value)) ? null : Integer.valueOf(value);
  }

  public static Boolean createBoolean(Number value)
  {
    return (value==null) ? null : long2bool(value.longValue());
  }

  public static boolean toBoolean(Number value, boolean defaultValue)
  {
    return (value==null) ? defaultValue : long2bool(value.longValue());
  }

  public static Long booleanToLong(Boolean value)
  {
    return (value==null) ? null : booleanToLong(value.booleanValue());
  }

  public static Integer booleanToInteger(Boolean value)
  {
    return (value==null) ? null : booleanToInt(value.booleanValue());
  }

  public static List<Long> toListLong(List<BigInteger> list)
  {
    List<Long> result = new ArrayList<>();
    for (int i=0; i<list.size();i++)
    {
      result.add(Long.valueOf(list.get(i).longValue()));
    }
    return result;
  }
  
  private static int booleanToInt(boolean value) {
    return value ? 1 : 0;
  }

  private static long booleanToLong(boolean value) {
    return value ? 1L : 0L;
  }

  public static Number convertNumberObjectToTargetClass(Object number, Class<?> targetClass)
      throws IllegalArgumentException {

    Validate.notNull(number, "Number must not be null");
    Validate.notNull(targetClass, "Target class must not be null");
    return convertNumberToTargetClassInternal((Number)number, (Class<Number>)targetClass);
  }

  public static <T extends Number> T convertNumberToTargetClass(Number number, Class<T> targetClass)
      throws IllegalArgumentException {

    Validate.notNull(number, "Number must not be null");
    Validate.notNull(targetClass, "Target class must not be null");
    return (T)convertNumberToTargetClassInternal(number, targetClass);
  }

  private static Number convertNumberToTargetClassInternal(Number number, Class<? extends Number> targetClass)
      throws IllegalArgumentException {

    if (targetClass.isInstance(number)) {
      return number;
    }
    else if (Byte.class == targetClass) {
      long value = checkedLongValue(number, targetClass);
      if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
        raiseOverflowException(number, targetClass);
      }
      return Byte.valueOf(number.byteValue());
    }
    else if (Short.class == targetClass) {
      long value = checkedLongValue(number, targetClass);
      if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
        raiseOverflowException(number, targetClass);
      }
      return Short.valueOf(number.shortValue());
    }
    else if (Integer.class == targetClass) {
      long value = checkedLongValue(number, targetClass);
      if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
        raiseOverflowException(number, targetClass);
      }
      return Integer.valueOf(number.intValue());
    }
    else if (Long.class == targetClass) {
      long value = checkedLongValue(number, targetClass);
      return Long.valueOf(value);
    }
    else if (BigInteger.class == targetClass) {
      if (number instanceof BigDecimal) {
        // do not lose precision - use BigDecimal's own conversion
        return ((BigDecimal) number).toBigInteger();
      }
      else {
        // original value is not a Big* number - use standard long conversion
        return BigInteger.valueOf(number.longValue());
      }
    }
    else if (Float.class == targetClass) {
      return Float.valueOf(number.floatValue());
    }
    else if (Double.class == targetClass) {
      return Double.valueOf(number.doubleValue());
    }
    else if (BigDecimal.class == targetClass) {
      // always use BigDecimal(String) here to avoid unpredictability of BigDecimal(double)
      // (see BigDecimal javadoc for details)
      return new BigDecimal(number.toString());
    }
    else {
      throw new IllegalArgumentException("Could not convert number [" + number + "] of type [" +
          number.getClass().getName() + "] to unsupported target class [" + targetClass.getName() + "]");
    }
  }

  private static long checkedLongValue(Number number, Class<? extends Number> targetClass) {
    BigInteger bigInt = null;
    if (number instanceof BigInteger) {
      bigInt = (BigInteger) number;
    }
    else if (number instanceof BigDecimal) {
      bigInt = ((BigDecimal) number).toBigInteger();
    }
    // Effectively analogous to JDK 8's BigInteger.longValueExact()
    if (bigInt != null && (bigInt.compareTo(LONG_MIN) < 0 || bigInt.compareTo(LONG_MAX) > 0)) {
      raiseOverflowException(number, targetClass);
    }
    return number.longValue();
  }

  private static void raiseOverflowException(Number number, Class<?> targetClass) {
    throw new IllegalArgumentException("Could not convert number [" + number + "] of type [" +
        number.getClass().getName() + "] to target class [" + targetClass.getName() + "]: overflow");
  }
}
