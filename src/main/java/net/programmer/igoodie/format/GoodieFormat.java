package net.programmer.igoodie.format;

import net.programmer.igoodie.runtime.GoodieElement;

public abstract class GoodieFormat<E, G extends GoodieElement> {

    public abstract G writeToGoodie(E externalFormat);

    public abstract E readFromGoodie(G goodie);

    public abstract String writeToString(E externalFormat);

    public abstract E readFromString(String text); // <- TODO: Mind Parse exceptions

    public String writeToString(G goodie) {
        E external = readFromGoodie(goodie);
        return writeToString(external);
    }

}
