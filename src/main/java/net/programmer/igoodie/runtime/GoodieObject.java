package net.programmer.igoodie.runtime;

import java.util.*;
import java.util.stream.Collectors;

public class GoodieObject implements GoodieElement,
        Iterable<Map.Entry<String, GoodieElement>>,
        Map<String, GoodieElement> {

    protected Map<String, GoodieElement> properties;

    public GoodieObject() {
        this(new HashMap<>());
    }

    public GoodieObject(Map<String, GoodieElement> properties) {
        this.properties = properties;
    }

    public GoodieObject with(String key, Object value) {
        if (!(value instanceof GoodieElement)) {
            this.properties.put(key, GoodiePrimitive.from(value));
            return this;
        }

        return with(key, ((GoodieElement) value));
    }

    public GoodieObject with(String key, GoodieElement value) {
        this.properties.put(key, value);
        return this;
    }

    @Override
    public GoodieElement deepCopy() {
        GoodieObject objectGoodie = new GoodieObject();
        this.properties.forEach((name, value) -> {
            objectGoodie.properties.put(name, value.deepCopy());
        });
        return objectGoodie;
    }

    @Override
    public int size() {
        return properties.size();
    }

    @Override
    public boolean isEmpty() {
        return properties.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return properties.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return properties.containsValue(value);
    }

    @Override
    public GoodieElement get(Object key) {
        return properties.get(key);
    }

    @Override
    public GoodieElement put(String key, GoodieElement value) {
        return properties.put(key, value);
    }

    @Override
    public GoodieElement remove(Object key) {
        return properties.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends GoodieElement> m) {
        properties.putAll(m);
    }

    @Override
    public void clear() {
        properties.clear();
    }

    @Override
    public Set<String> keySet() {
        return properties.keySet();
    }

    @Override
    public Collection<GoodieElement> values() {
        return properties.values();
    }

    @Override
    public Set<Entry<String, GoodieElement>> entrySet() {
        return properties.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        // TODO: Check types too (?)
        return properties.equals(o);
    }

    @Override
    public int hashCode() {
        return properties.hashCode();
    }

    @Override
    public String toString() {
        return "{" + properties.entrySet().stream()
                .map(entry -> entry.getKey() + ":" + entry.getValue())
                .collect(Collectors.joining(", ")) + "}";
    }

    @Override
    public Iterator<Map.Entry<String, GoodieElement>> iterator() {
        return properties.entrySet().iterator();
    }

}
