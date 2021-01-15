package net.programmer.igoodie.validator;

import net.programmer.igoodie.exception.ValidationException;
import net.programmer.igoodie.runtime.GoodiePrimitive;

public class IntegerValidator extends GoodieValidator<GoodiePrimitive> {

    protected int minValue;
    protected int maxValue;

    public IntegerValidator() {
        this(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public IntegerValidator(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public void validate(String propertyName, GoodiePrimitive goodie) throws ValidationException {
        if (goodie.getType() != int.class)
            throw new ValidationException(propertyName, "Invalid data type. Integer expected");

        int numberValue = goodie.getNumberValue().intValue();

        if (numberValue < minValue)
            throw new ValidationException(propertyName, "Expected minimum value of " + minValue);
        if (numberValue > maxValue)
            throw new ValidationException(propertyName, "Expected maximum value of " + maxValue);
    }

}
