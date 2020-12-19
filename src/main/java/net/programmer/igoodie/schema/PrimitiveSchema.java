package net.programmer.igoodie.schema;

import net.programmer.igoodie.exception.ValidationException;
import net.programmer.igoodie.runtime.GoodieElement;
import net.programmer.igoodie.runtime.GoodieObject;
import net.programmer.igoodie.runtime.GoodiePrimitive;
import net.programmer.igoodie.sanitizer.GoodieSanitizer;
import net.programmer.igoodie.validator.GoodieValidator;

public class PrimitiveSchema extends GoodieSchema<GoodiePrimitive> {

    protected GoodiePrimitive defaultValue;

    public PrimitiveSchema(String propertyName, Object defaultValue) {
        this(propertyName, GoodiePrimitive.from(defaultValue));
    }

    public PrimitiveSchema(String propertyName, GoodiePrimitive defaultValue) {
        super(propertyName);
        this.defaultValue = defaultValue;
    }

    @SafeVarargs
    public PrimitiveSchema(String propertyName, GoodiePrimitive defaultValue, GoodieValidator<GoodiePrimitive> validator, GoodieSanitizer<GoodiePrimitive>... sanitizers) {
        super(propertyName, validator, sanitizers);
        this.defaultValue = defaultValue;
    }

    @Override
    public GoodiePrimitive getDefaultValue() {
        GoodiePrimitive defaultGoodie = this.defaultValue.deepCopy();
        try {
            return checkAndSanitize(defaultGoodie);

        } catch (ValidationException e) {
            throw new InternalError("Default value does not satisfy validation rules", e);
        }
    }

    @Override
    public SchematicResult<?> check(GoodieElement goodie) {
        return null;
    }

}
