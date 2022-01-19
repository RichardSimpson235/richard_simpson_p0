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

    /**
     * This method is used to retrieve data from the list.
     *
     * @param index                      The index to get the item from
     * @return                           The object stored at index
     * @throws IndexOutOfBoundsException Thrown when the index is too small or too large
     */
    @SuppressWarnings("unchecked")
    public E get(int index) throws IndexOutOfBoundsException {
        if (index >= size) {
            throw new IndexOutOfBoundsException("The index provided was too large, got: " + index +
                    ", but the largest allowed value is " + (this.size - 1));
        }

        return (E) this.array[index];
    }

    /**
     * This method adds elements to the list. If the array is full, we
     * resize the array and then add the element.
     *
     * @param item The item to add to the list
     */
    public void add(E item) {
        if(this.size >= this.array.length) {
            resize(this.array.length * 2);
        }

        this.array[this.size] = item;
        this.size++;
    }

    /**
     * This method removes items from the list.
     *
     * @param item The item to be removed
     */
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

    /**
     * Iterates through the array and checks equality with the item.
     *
     * @param item The item to check
     * @return     Whether the item is in the list or not
     */
    public boolean contains(E item) {
        for (Object containedItem: this.array) {
            if (item.equals(containedItem)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Used to resize the underlying array.
     *
     * @param newSize new size of the underlying array
     */
    public void resize(int newSize) {
        Object[] temp = new Object[newSize];
        System.arraycopy(this.array, 0, temp, 0, this.size);

        this.array = temp;
    }

    /**
     * Returns the number of elements in then list
     *
     * @return the size of the list
     */
    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Simply iterates through the list and appends the item's toString value.
     *
     * @return a string representation of the list
     */
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
