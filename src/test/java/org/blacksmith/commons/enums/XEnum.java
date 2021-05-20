package org.blacksmith.commons.enums;

public enum XEnum implements ValueEnum {

  A("aX"),
  B("By"),
  c("CU");

  private static SingleEnumCache<String, XEnum> cache1 = new SingleEnumCache<>(XEnum.class, XEnum::getValue, false);
  private final String value;

  XEnum(String value) {
    this.value = value;
  }

  public static XEnum fromValue(String value) {
    return cache1.get(value);
  }

  @Override
  public String getValue() {
    return this.value;
  }
}