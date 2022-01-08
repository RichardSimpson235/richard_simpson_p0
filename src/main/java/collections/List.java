package main.java.collections;

public class List<E> {

    private Object[] array;
    private int size;

    public List() {
        this.size = 2;
        this.array = new Object[size];
    }

    public List(int size) {
        this.size = size;
        this.array = new Object[size];
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        return (E) array[index];
    }

    public void add(E item) {}

    public void remove(E item) {}

    public boolean contains(E item) {
        for (Object containedItem: this.array) {
            if (item.equals(containedItem)) {
                return true;
            }
        }

        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("List{");
        for (Object item: this.array) {
            output.append(" " + item.toString());
        }

        output.append("}");

        return output.toString();
    }
}
