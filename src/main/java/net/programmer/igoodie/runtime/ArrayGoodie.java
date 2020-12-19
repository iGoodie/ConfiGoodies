package net.programmer.igoodie.runtime;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayGoodie implements ConfigGoodie, Iterable<ConfigGoodie> {

    protected List<ConfigGoodie> elements;

    public ArrayGoodie() {
        this(new LinkedList<>());
    }

    public ArrayGoodie(List<ConfigGoodie> elements) {
        this.elements = elements;
    }

    @Override
    public ConfigGoodie deepCopy() {
        ArrayGoodie arrayGoodie = new ArrayGoodie();
        for (ConfigGoodie element : this.elements) {
            arrayGoodie.elements.add(element.deepCopy());
        }
        return arrayGoodie;
    }

    @Override
    public Iterator<ConfigGoodie> iterator() {
        return elements.iterator();
    }

    @Override
    public String toString() {
        return "[" + elements.stream()
                .map(ConfigGoodie::toString)
                .collect(Collectors.joining(",")) + "]";
    }

    public static ArrayGoodie of(Object... elements) {
        return new ArrayGoodie(Stream.of(elements)
                .map(PrimitiveGoodie::of).collect(Collectors.toList()));
    }

    public static ArrayGoodie of(ConfigGoodie... elements) {
        return new ArrayGoodie(Arrays.asList(elements));
    }

}
