package net.programmer.igoodie.validator;

import net.programmer.igoodie.exception.ValidationException;
import net.programmer.igoodie.runtime.GoodiePrimitive;

import java.util.regex.Pattern;

public class StringValidator extends GoodieValidator<GoodiePrimitive> {

    protected int minLength;
    protected int maxLength;
    protected Pattern pattern;

    public StringValidator() {
        this(-1, -1, null);
    }

    public StringValidator(int minLength, int maxLength, Pattern pattern) {
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.pattern = pattern;
    }

    public StringValidator withLength(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
        return this;
    }

    public StringValidator withPattern(Pattern pattern) {
        this.pattern = pattern;
        return this;
    }

    @Override
    public void validate(String propertyName, GoodiePrimitive goodie) {
        if (goodie.getType() != String.class)
            throw new ValidationException(propertyName, "Invalid data type. String expected.");

        String stringValue = goodie.getStringValue();

        if (minLength != -1 && stringValue.length() < minLength)
            throw new ValidationException(propertyName, "Expected minimum length of " + minLength);
        if (maxLength != -1 && stringValue.length() > maxLength)
            throw new ValidationException(propertyName, "Expected maximum length of " + maxLength);
        if (pattern != null && !pattern.matcher(stringValue).matches())
            throw new ValidationException(propertyName, "Expected to match regex: " + pattern);
    }

}
