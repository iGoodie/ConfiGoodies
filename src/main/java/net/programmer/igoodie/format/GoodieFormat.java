package net.programmer.igoodie.format;

import net.programmer.igoodie.runtime.ConfigGoodie;

public abstract class GoodieFormat<E, G extends ConfigGoodie> {

    public abstract G serializeGoodie(E externalFormat);

    public abstract E deserializeGoodie(G goodie);

    public abstract E readFromString(String text);

    public abstract String writeToString(E externalFormat);

}
