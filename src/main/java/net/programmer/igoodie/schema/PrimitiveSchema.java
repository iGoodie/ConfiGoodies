package net.programmer.igoodie.schema;

import net.programmer.igoodie.exception.ValidationException;
import net.programmer.igoodie.runtime.ConfigGoodie;
import net.programmer.igoodie.runtime.PrimitiveGoodie;
import net.programmer.igoodie.sanitizer.ConfigSanitizer;
import net.programmer.igoodie.validator.ConfigValidator;

public class PrimitiveSchema extends ConfigSchema<PrimitiveGoodie> {

    protected PrimitiveGoodie defaultValue;

    public PrimitiveSchema(String propertyName, Object defaultValue) {
        this(propertyName, PrimitiveGoodie.from(defaultValue));
    }

    public PrimitiveSchema(String propertyName, PrimitiveGoodie defaultValue) {
        super(propertyName);
        this.defaultValue = defaultValue;
    }

    @SafeVarargs
    public PrimitiveSchema(String propertyName, PrimitiveGoodie defaultValue, ConfigValidator validator, ConfigSanitizer<PrimitiveGoodie>... sanitizers) {
        super(propertyName, validator, sanitizers);
        this.defaultValue = defaultValue;
    }

    @Override
    public PrimitiveGoodie getDefaultValue() {
        PrimitiveGoodie defaultGoodie = this.defaultValue.deepCopy();
        try {
            return checkAndSanitize(defaultGoodie);

        } catch (ValidationException e) {
            throw new InternalError("Default value does not satisfy validation rules", e);
        }
    }

    @Override
    public SchematicResult<?> check(ConfigGoodie goodie) {
        return null;
    }

}
