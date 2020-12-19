package net.programmer.igoodie.schema;

import net.programmer.igoodie.runtime.ConfigGoodie;
import net.programmer.igoodie.runtime.ObjectGoodie;
import net.programmer.igoodie.sanitizer.ConfigSanitizer;
import net.programmer.igoodie.validator.ConfigValidator;

import java.util.HashMap;
import java.util.Map;

public class ObjectSchema extends ConfigSchema<ObjectGoodie> {

    protected Map<String, ConfigSchema<? extends ConfigGoodie>> schemas;

    private ObjectSchema() {
        this("$root");
    }

    private ObjectSchema(String propertyName) {
        super(propertyName);
        this.schemas = new HashMap<>();
    }

    @SafeVarargs
    public ObjectSchema(String propertyName, ConfigValidator validator, ConfigSanitizer<ObjectGoodie>... sanitizers) {
        super(propertyName, validator, sanitizers);
        this.schemas = new HashMap<>();
    }

    @Override
    public ObjectGoodie getDefaultValue() {
        ObjectGoodie goodie = new ObjectGoodie();
        schemas.forEach((key, schema) -> {
            ConfigGoodie defaultValue = schema.getDefaultValue();
            goodie.with(key, defaultValue);
        });
        return goodie;
    }

    @Override
    public SchematicResult<?> check(ConfigGoodie goodie) {
        if (!(goodie instanceof ObjectGoodie))
            return SchematicResult.invalidType(new ObjectGoodie());

        // TODO:
        return null;
    }

    @SafeVarargs
    public static ObjectSchema of(ConfigSchema<? extends ConfigGoodie>... schemas) {
        ObjectSchema root = new ObjectSchema();
        for (ConfigSchema<? extends ConfigGoodie> schema : schemas) {
            root.schemas.put(schema.getPropertyName(), schema);
        }
        return root;
    }

    @SafeVarargs
    public static ObjectSchema of(String propertyName, ConfigSchema<? extends ConfigGoodie>... schemas) {
        ObjectSchema root = new ObjectSchema(propertyName);
        for (ConfigSchema<? extends ConfigGoodie> schema : schemas) {
            root.schemas.put(schema.getPropertyName(), schema);
        }
        return root;
    }

}
