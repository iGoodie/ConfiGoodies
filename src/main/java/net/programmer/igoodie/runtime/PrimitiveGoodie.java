package net.programmer.igoodie.runtime;

public class PrimitiveGoodie implements ConfigGoodie {

    private Class<?> type;
    private Object value;

    private PrimitiveGoodie(Object value, Class<?> type) {
        checkNull(value);
        this.type = type;
        this.value = value;
    }

    public PrimitiveGoodie(Boolean value) {
        this(value, Boolean.class);
    }

    public PrimitiveGoodie(String value) {
        this(value, String.class);
    }

    public PrimitiveGoodie(Character value) {
        this(value, Character.class);
    }

    public PrimitiveGoodie(Number value) {
        this(value, Number.class);
    }

    private void checkNull(Object value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public PrimitiveGoodie deepCopy() {
        return new PrimitiveGoodie(value, type);
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

    public String getStringValue() {
        if (type == String.class)
            return ((String) value);
        return value.toString();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public static PrimitiveGoodie of(Object value) {
        if (value instanceof String)
            return new PrimitiveGoodie((String) value);

        if (value instanceof Number)
            return new PrimitiveGoodie(((Number) value));

        if (value instanceof Boolean)
            return new PrimitiveGoodie(((Boolean) value));

        if (value instanceof Character)
            return new PrimitiveGoodie(((Character) value));

        throw new IllegalArgumentException();
    }

}
