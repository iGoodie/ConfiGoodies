package net.programmer.igoodie.sanitizer;

import net.programmer.igoodie.runtime.GoodieElement;

public abstract class GoodieSanitizer<T extends GoodieElement> {

    public abstract T sanitize(T goodie);

}
