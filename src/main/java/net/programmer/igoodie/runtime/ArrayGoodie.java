package net.programmer.igoodie.runtime;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayGoodie implements ConfigGoodie,
        Iterable<ConfigGoodie>,
        List<ConfigGoodie> {

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
    public int size() {
        return elements.size();
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return elements.contains(o);
    }

    @Override
    public Iterator<ConfigGoodie> iterator() {
        return elements.iterator();
    }

    @Override
    public Object[] toArray() {
        return elements.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return elements.toArray(a);
    }

    @Override
    public boolean add(ConfigGoodie goodie) {
        return elements.add(goodie);
    }

    @Override
    public boolean remove(Object o) {
        return elements.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return elements.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends ConfigGoodie> c) {
        return elements.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends ConfigGoodie> c) {
        return elements.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return elements.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return elements.retainAll(c);
    }

    @Override
    public void clear() {
        elements.clear();
    }

    @Override
    public ConfigGoodie get(int index) {
        return elements.get(index);
    }

    @Override
    public ConfigGoodie set(int index, ConfigGoodie element) {
        return elements.set(index, element);
    }

    @Override
    public void add(int index, ConfigGoodie element) {
        elements.add(index, element);
    }

    @Override
    public ConfigGoodie remove(int index) {
        return elements.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return elements.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return elements.lastIndexOf(o);
    }

    @Override
    public ListIterator<ConfigGoodie> listIterator() {
        return elements.listIterator();
    }

    @Override
    public ListIterator<ConfigGoodie> listIterator(int index) {
        return elements.listIterator(index);
    }

    @Override
    public List<ConfigGoodie> subList(int fromIndex, int toIndex) {
        return elements.subList(fromIndex, toIndex);
    }

    @Override
    public String toString() {
        return "[" + elements.stream()
                .map(ConfigGoodie::toString)
                .collect(Collectors.joining(",")) + "]";
    }

    public static ArrayGoodie of(Object... elements) {
        return new ArrayGoodie(Stream.of(elements)
                .map(PrimitiveGoodie::from).collect(Collectors.toList()));
    }

    public static ArrayGoodie of(ConfigGoodie... elements) {
        return new ArrayGoodie(Arrays.asList(elements));
    }

}
