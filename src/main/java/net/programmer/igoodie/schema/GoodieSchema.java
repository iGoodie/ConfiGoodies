package net.programmer.igoodie.schema;

import net.programmer.igoodie.exception.ValidationException;
import net.programmer.igoodie.runtime.GoodieElement;
import net.programmer.igoodie.sanitizer.GoodieSanitizer;
import net.programmer.igoodie.validator.GoodieValidator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class GoodieSchema<G extends GoodieElement> {

    protected String propertyName;
    protected GoodieValidator<G> validator;
    protected List<GoodieSanitizer<G>> sanitizers;

    public GoodieSchema(String propertyName) {
        this(propertyName, null);
    }

    @SafeVarargs
    public GoodieSchema(String propertyName, GoodieValidator<G> validator, GoodieSanitizer<G>... sanitizers) {
        this.propertyName = propertyName;
        this.validator = validator;
        this.sanitizers = new LinkedList<>();
        this.sanitizers.addAll(Arrays.asList(sanitizers));
    }

    public String getPropertyName() {
        return propertyName;
    }

    public boolean hasValidator() {
        return validator != null;
    }

    public int sanitizerLength() {
        if (sanitizers == null) return 0;
        return sanitizers.size();
    }

    public GoodieValidator<G> getValidator() {
        return validator;
    }

    public List<GoodieSanitizer<G>> getSanitizers() {
        return sanitizers;
    }

    public GoodieSchema<G> withValidator(GoodieValidator<G> validator) {
        this.validator = validator;
        return this;
    }

    @SafeVarargs
    public final GoodieSchema<G> withSanitizers(GoodieSanitizer<G>... sanitizers) {
        if (this.sanitizers == null)
            this.sanitizers = new LinkedList<>();
        this.sanitizers.addAll(Arrays.asList(sanitizers));
        return this;
    }

    public G validate(G goodie) {
        return validate(goodie, true);
    }

    public G validate(G goodie, boolean safe) {
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

    public G sanitize(G goodie) {
        if (sanitizers.size() != 0) {
            G sanitizedValue = goodie;
            for (GoodieSanitizer<G> sanitizer : sanitizers) {
                sanitizedValue = sanitizer.sanitize(sanitizedValue);
            }
            return sanitizedValue;
        }
        return goodie;
    }

    public abstract G getDefaultValue();

    public abstract SchematicResult<G> check(G goodie);

}
