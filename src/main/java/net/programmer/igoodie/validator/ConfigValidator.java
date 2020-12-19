package net.programmer.igoodie.validator;

import net.programmer.igoodie.exception.ValidationException;
import net.programmer.igoodie.runtime.ConfigGoodie;

public abstract class ConfigValidator<T extends ConfigGoodie> {

    public abstract void validate(String propertyName, T goodie) throws ValidationException;

}
