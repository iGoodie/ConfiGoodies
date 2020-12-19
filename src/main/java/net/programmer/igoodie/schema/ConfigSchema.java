package net.programmer.igoodie.schema;

import net.programmer.igoodie.exception.ValidationException;
import net.programmer.igoodie.runtime.ConfigGoodie;
import net.programmer.igoodie.sanitizer.ConfigSanitizer;
import net.programmer.igoodie.validator.ConfigValidator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class ConfigSchema<T extends ConfigGoodie> {

    protected String propertyName;
    protected ConfigValidator validator;
    protected List<ConfigSanitizer<T>> sanitizers;

    public ConfigSchema(String propertyName) {
        this(propertyName, null);
    }

    @SafeVarargs
    public ConfigSchema(String propertyName, ConfigValidator validator, ConfigSanitizer<T>... sanitizers) {
        this.propertyName = propertyName;
        this.validator = validator;
        this.sanitizers = new LinkedList<>();
        this.sanitizers.addAll(Arrays.asList(sanitizers));
    }

    public String getPropertyName() {
        return propertyName;
    }

    public ConfigValidator getValidator() {
        return validator;
    }

    public List<ConfigSanitizer<T>> getSanitizers() {
        return sanitizers;
    }

    public ConfigSchema<T> withValidator(ConfigValidator validator) {
        this.validator = validator;
        return this;
    }

    @SafeVarargs
    public final ConfigSchema<T> withSanitizers(ConfigSanitizer<T>... sanitizers) {
        if (this.sanitizers == null)
            this.sanitizers = new LinkedList<>();
        this.sanitizers.addAll(Arrays.asList(sanitizers));
        return this;
    }

    @SuppressWarnings("unchecked")
    public T checkAndSanitize(T value) {
        if (validator != null) validator.validate(propertyName, value);

        if (sanitizers.size() == 0) return value;

        T sanitizedValue = (T) value.deepCopy();
        for (ConfigSanitizer<T> sanitizer : sanitizers) {
            sanitizedValue = sanitizer.sanitize(sanitizedValue);
        }
        return sanitizedValue;
    }

    public abstract T getDefaultValue();

    public abstract SchematicResult<?> check(ConfigGoodie goodie);

}
