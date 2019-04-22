package org.apache.ibatis.type;

public interface BaseEnum<E extends Enum<?>,T> {

    public T getValue();

    public String getName();
}
