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
    public SchematicResult<GoodieObject> check(GoodieObject goodie) {
        GoodieObject copied = (GoodieObject) goodie.deepCopy();
        SchematicResult<GoodieObject> result = new SchematicResult<>(copied);

        for (String propertyName : schemas.keySet()) {
            // Cursed casting hack, because; Required:capture of <? extends GoodieElement>; Provided:GoodieElement
            GoodieSchema<GoodieElement> propertySchema = (GoodieSchema<GoodieElement>) schemas.get(propertyName);
            GoodieElement propertyGoodie = copied.get(propertyName).deepCopy();

            if (!GoodieSchema.matchesType(propertySchema, propertyGoodie)) {
                copied.put(propertyName, propertySchema.getDefaultValue());
                result.validatedTo(copied);
                continue;
            }

            SchematicResult<GoodieElement> checkResult = propertySchema.check(propertyGoodie);

            if (checkResult.isModified()) {
                copied.put(propertyName, checkResult.getModified());
                if (checkResult.isValidated())
                    result.validatedTo(copied);
                else if (checkResult.isSanitized())
                    result.sanitizedTo(copied);
            }
        }

        return result;
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
