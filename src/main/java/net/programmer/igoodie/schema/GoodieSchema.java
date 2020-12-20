package net.programmer.igoodie.schema;

import net.programmer.igoodie.exception.ValidationException;
import net.programmer.igoodie.runtime.GoodieElement;
import net.programmer.igoodie.sanitizer.GoodieSanitizer;
import net.programmer.igoodie.validator.GoodieValidator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class GoodieSchema<T extends GoodieElement> {

    protected String propertyName;
    protected GoodieValidator<T> validator;
    protected List<GoodieSanitizer<T>> sanitizers;

    public GoodieSchema(String propertyName) {
        this(propertyName, null);
    }

    @SafeVarargs
    public GoodieSchema(String propertyName, GoodieValidator<T> validator, GoodieSanitizer<T>... sanitizers) {
        this.propertyName = propertyName;
        this.validator = validator;
        this.sanitizers = new LinkedList<>();
        this.sanitizers.addAll(Arrays.asList(sanitizers));
    }

    public String getPropertyName() {
        return propertyName;
    }

    public GoodieValidator<T> getValidator() {
        return validator;
    }

    public List<GoodieSanitizer<T>> getSanitizers() {
        return sanitizers;
    }

    public GoodieSchema<T> withValidator(GoodieValidator<T> validator) {
        this.validator = validator;
        return this;
    }

    @SafeVarargs
    public final GoodieSchema<T> withSanitizers(GoodieSanitizer<T>... sanitizers) {
        if (this.sanitizers == null)
            this.sanitizers = new LinkedList<>();
        this.sanitizers.addAll(Arrays.asList(sanitizers));
        return this;
    }

    public T validate(T goodie) {
        return validate(goodie, true);
    }

    public T validate(T goodie, boolean safe) {
        if (validator != null) {
            try {
                validator.validate(propertyName, goodie);
            } catch (ValidationException e) {
                if (!safe) throw e;
                return getDefaultValue();
            }
        }
        return goodie;
    }

    public T sanitize(T goodie) {
        if (sanitizers.size() != 0) {
            T sanitizedValue = goodie;
            for (GoodieSanitizer<T> sanitizer : sanitizers) {
                sanitizedValue = sanitizer.sanitize(sanitizedValue);
            }
            return sanitizedValue;
        }
        return goodie;
    }

    public abstract T getDefaultValue();

    public abstract SchematicResult<T> check(T goodie);

}
