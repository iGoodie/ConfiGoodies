package net.programmer.igoodie.validator;

import net.programmer.igoodie.exception.ValidationException;
import net.programmer.igoodie.runtime.GoodieElement;

public abstract class GoodieValidator<T extends GoodieElement> {

    public abstract void validate(String propertyName, T goodie) throws ValidationException;

}
