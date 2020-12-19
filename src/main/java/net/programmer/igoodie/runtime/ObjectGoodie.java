package net.programmer.igoodie.runtime;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class ObjectGoodie implements ConfigGoodie, Iterable<Map.Entry<String, ConfigGoodie>> {

    protected Map<String, ConfigGoodie> properties;

    public ObjectGoodie() {
        this(new HashMap<>());
    }

    public ObjectGoodie(Map<String, ConfigGoodie> properties) {
        this.properties = properties;
    }

    public ObjectGoodie with(String key, Object value) {
        if (!(value instanceof ConfigGoodie)) {
            this.properties.put(key, PrimitiveGoodie.of(value));
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

    public Map<String, ConfigGoodie> getProperties() {
        return properties;
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
