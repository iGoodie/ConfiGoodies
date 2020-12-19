package net.programmer.igoodie.sanitizer;

import net.programmer.igoodie.runtime.ConfigGoodie;

public abstract class ConfigSanitizer<T extends ConfigGoodie> {

    public abstract T sanitize(T input);

}
