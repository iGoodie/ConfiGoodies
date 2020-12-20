package net.programmer.igoodie.sanitizer;

import net.programmer.igoodie.runtime.GoodieElement;

@FunctionalInterface
public interface GoodieSanitizer<T extends GoodieElement> {

    T sanitize(T goodie);

}
