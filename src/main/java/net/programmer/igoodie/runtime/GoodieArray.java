package net.programmer.igoodie.runtime;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GoodieArray implements GoodieElement,
        Iterable<GoodieElement>,
        List<GoodieElement> {

    protected List<GoodieElement> elements;

    public GoodieArray() {
        this(new LinkedList<>());
    }

    public GoodieArray(List<GoodieElement> elements) {
        this.elements = elements;
    }

    @Override
    public GoodieArray deepCopy() {
        GoodieArray arrayGoodie = new GoodieArray();
        for (GoodieElement element : this.elements) {
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
    public Iterator<GoodieElement> iterator() {
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
    public boolean add(GoodieElement goodie) {
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
    public boolean addAll(Collection<? extends GoodieElement> c) {
        return elements.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends GoodieElement> c) {
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
    public GoodieElement get(int index) {
        return elements.get(index);
    }

    @Override
    public GoodieElement set(int index, GoodieElement element) {
        return elements.set(index, element);
    }

    @Override
    public void add(int index, GoodieElement element) {
        elements.add(index, element);
    }

    @Override
    public GoodieElement remove(int index) {
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
    public ListIterator<GoodieElement> listIterator() {
        return elements.listIterator();
    }

    @Override
    public ListIterator<GoodieElement> listIterator(int index) {
        return elements.listIterator(index);
    }

    @Override
    public List<GoodieElement> subList(int fromIndex, int toIndex) {
        return elements.subList(fromIndex, toIndex);
    }

    @Override
    public String toString() {
        return "[" + elements.stream()
                .map(GoodieElement::toString)
                .collect(Collectors.joining(",")) + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodieArray that = (GoodieArray) o;
        return Objects.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }

    public static GoodieArray of(Object... elements) {
        return new GoodieArray(Stream.of(elements)
                .map(GoodiePrimitive::from).collect(Collectors.toList()));
    }

    public static GoodieArray of(GoodieElement... elements) {
        return new GoodieArray(Arrays.asList(elements));
    }

}
