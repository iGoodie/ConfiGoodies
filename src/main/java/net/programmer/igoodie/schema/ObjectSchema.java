package net.programmer.igoodie.schema;

import net.programmer.igoodie.runtime.GoodieElement;
import net.programmer.igoodie.runtime.GoodieObject;
import net.programmer.igoodie.sanitizer.GoodieSanitizer;
import net.programmer.igoodie.validator.GoodieValidator;

import java.util.HashMap;
import java.util.Map;

public class ObjectSchema extends GoodieSchema<GoodieObject> {

    protected Map<String, GoodieSchema<? extends GoodieElement>> schemas;

    private ObjectSchema() {
        this("$root");
    }

    private ObjectSchema(String propertyName) {
        super(propertyName);
        this.schemas = new HashMap<>();
    }

    @SafeVarargs
    public ObjectSchema(String propertyName, GoodieValidator<GoodieObject> validator, GoodieSanitizer<GoodieObject>... sanitizers) {
        super(propertyName, validator, sanitizers);
        this.schemas = new HashMap<>();
    }

    @Override
    public GoodieObject getDefaultValue() {
        GoodieObject goodie = new GoodieObject();
        schemas.forEach((key, schema) -> {
            GoodieElement defaultValue = schema.getDefaultValue();
            goodie.with(key, defaultValue);
        });
        return goodie;
    }

    @Override
    public SchematicResult<?> check(GoodieElement goodie) {
        if (!(goodie instanceof GoodieObject))
            return SchematicResult.invalidType(new GoodieObject());

        // TODO:
        return null;
    }

    @SafeVarargs
    public static ObjectSchema of(GoodieSchema<? extends GoodieElement>... schemas) {
        ObjectSchema root = new ObjectSchema();
        for (GoodieSchema<? extends GoodieElement> schema : schemas) {
            root.schemas.put(schema.getPropertyName(), schema);
        }
        return root;
    }

    @SafeVarargs
    public static ObjectSchema of(String propertyName, GoodieSchema<? extends GoodieElement>... schemas) {
        ObjectSchema root = new ObjectSchema(propertyName);
        for (GoodieSchema<? extends GoodieElement> schema : schemas) {
            root.schemas.put(schema.getPropertyName(), schema);
        }
        return root;
    }

}
