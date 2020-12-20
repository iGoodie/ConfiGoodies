package net.programmer.igoodie.schema;

import net.programmer.igoodie.exception.ValidationException;
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

        try {
            validate(defaultValue, false);

        } catch (ValidationException e) {
            throw new InternalError("Default value does not satisfy validation rules", e);
        }
    }

    @SafeVarargs
    public PrimitiveSchema(String propertyName, GoodiePrimitive defaultValue, GoodieValidator<GoodiePrimitive> validator, GoodieSanitizer<GoodiePrimitive>... sanitizers) {
        super(propertyName, validator, sanitizers);
        this.defaultValue = defaultValue;
    }

    @Override
    public GoodiePrimitive getDefaultValue() {
        return sanitize(this.defaultValue.deepCopy());
    }

    @Override
    public SchematicResult<GoodiePrimitive> check(GoodiePrimitive goodie) {
        SchematicResult<GoodiePrimitive> result = new SchematicResult<>(goodie);

        GoodiePrimitive validated = validate(goodie);
        if (!validated.equals(goodie)) {
            result.validatedTo(validated.deepCopy());
            return result;
        }

        GoodiePrimitive sanitized = sanitize(goodie);
        if (!sanitized.equals(goodie)) {
            result.sanitizedTo(sanitized.deepCopy());
            return result;
        }

        return result;
    }

}
