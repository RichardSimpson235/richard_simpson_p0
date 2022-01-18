package main.java.structures;

public class List<E> {

    private Object[] array;
    private int size;

    public List() {
        this(2);
    }

    public List(int size) {
        this.size = 0;
        this.array = new Object[size];
    }

    @SuppressWarnings("unchecked")
    public E get(int index) throws IndexOutOfBoundsException {
        if (index >= size) {
            throw new IndexOutOfBoundsException("The index provided was too large, got: " + index +
                    ", but the largest allowed value is " + (this.size - 1));
        }

        return (E) this.array[index];
    }

    // resize list if we hit the maximum size
    public void add(E item) {
        if(this.size >= this.array.length) {
            resize(this.array.length * 2);
        }

        this.array[this.size] = item;
        this.size++;
    }

    public void remove(E item) {
        for (int i = this.size - 1; i >= 0; i--) {
            if (item.equals(this.array[i])) {
                if(i == this.size - 1) {
                    this.array[i] = null;
                } else {
                    System.arraycopy(this.array, i + 1, this.array, i, this.size - i);
                }
                this.size--;

                break;
            }
        }
    }

    public boolean contains(E item) {
        for (Object containedItem: this.array) {
            if (item.equals(containedItem)) {
                return true;
            }
        }

        return false;
    }

    public void resize(int newSize) {
        Object[] temp = new Object[newSize];
        System.arraycopy(this.array, 0, temp, 0, this.size);

        this.array = temp;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("List{");
        for (Object item: this.array) {
            if(item != null) {
                output.append(", ");
                output.append(item);
            }
        }

        output.append("}");

        return output.toString();
    }
}
