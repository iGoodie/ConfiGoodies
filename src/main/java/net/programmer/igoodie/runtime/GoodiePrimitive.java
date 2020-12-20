package net.programmer.igoodie.runtime;

import java.util.Objects;

public class GoodiePrimitive implements GoodieElement {

    private Class<?> type;
    private Object value;

    private GoodiePrimitive(Object value, Class<?> type) {
        checkNull(value);
        this.type = type;
        this.value = value;
    }

    public GoodiePrimitive(Boolean value) {
        this(value, Boolean.class);
    }

    public GoodiePrimitive(String value) {
        this(value, String.class);
    }

    public GoodiePrimitive(Character value) {
        this(value, Character.class);
    }

    public GoodiePrimitive(Number value) {
        this(value, Number.class);
    }

    private void checkNull(Object value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public GoodiePrimitive deepCopy() {
        return new GoodiePrimitive(value, type);
    }

    public Class<?> getType() {
        return type;
    }

    public Object getRawValue() {
        return value;
    }

    public boolean getBooleanValue() {
        if (type == Boolean.class)
            return ((Boolean) value);
        throw new IllegalStateException();
    }

    public Number getNumberValue() {
        if (Number.class.isAssignableFrom(type))
            return ((Number) value);
        throw new IllegalStateException();
    }

    public String getStringValue() {
        if (type == String.class)
            return ((String) value);
        return value.toString();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodiePrimitive that = (GoodiePrimitive) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    public static GoodiePrimitive from(Object value) {
        if (value instanceof String)
            return new GoodiePrimitive((String) value);
        if (value instanceof Number)
            return new GoodiePrimitive(((Number) value));
        if (value instanceof Boolean)
            return new GoodiePrimitive(((Boolean) value));
        if (value instanceof Character)
            return new GoodiePrimitive(((Character) value));

        throw new IllegalArgumentException();
    }

}
