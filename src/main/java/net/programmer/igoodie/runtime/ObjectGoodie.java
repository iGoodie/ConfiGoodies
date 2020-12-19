package net.programmer.igoodie.runtime;

import java.util.*;
import java.util.stream.Collectors;

public class ObjectGoodie implements ConfigGoodie,
        Iterable<Map.Entry<String, ConfigGoodie>>,
        Map<String, ConfigGoodie> {

    protected Map<String, ConfigGoodie> properties;

    public ObjectGoodie() {
        this(new HashMap<>());
    }

    public ObjectGoodie(Map<String, ConfigGoodie> properties) {
        this.properties = properties;
    }

    public ObjectGoodie with(String key, Object value) {
        if (!(value instanceof ConfigGoodie)) {
            this.properties.put(key, PrimitiveGoodie.from(value));
            return this;
        }

        return with(key, ((ConfigGoodie) value));
    }

    public ObjectGoodie with(String key, ConfigGoodie value) {
        this.properties.put(key, value);
        return this;
    }

    @Override
    public ConfigGoodie deepCopy() {
        ObjectGoodie objectGoodie = new ObjectGoodie();
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
    public ConfigGoodie get(Object key) {
        return properties.get(key);
    }

    @Override
    public ConfigGoodie put(String key, ConfigGoodie value) {
        return properties.put(key, value);
    }

    @Override
    public ConfigGoodie remove(Object key) {
        return properties.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends ConfigGoodie> m) {
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
    public Collection<ConfigGoodie> values() {
        return properties.values();
    }

    @Override
    public Set<Entry<String, ConfigGoodie>> entrySet() {
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
    public Iterator<Map.Entry<String, ConfigGoodie>> iterator() {
        return properties.entrySet().iterator();
    }

}
