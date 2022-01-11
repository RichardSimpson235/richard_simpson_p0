package main.test.java.structures;

import main.java.structures.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ListTest {

    @Test
    public void addTest() {
        List<Integer> list = new List<>();
        list.add(1);

        assertEquals(list.size(), 1);
    }

    @Test
    public void addMultipleTest() {
        List<Integer> list = new List<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        assertEquals(list.size(), 4);
    }

    @Test
    public void addManyTest() {
        List<Integer> list = new List<>();
        for(int i = 0; i < 10000; i++) {
            list.add(i);
        }

        assertEquals(list.size(), 10000);
    }

    @Test
    public void getTest() {
        int i = 1;
        List<Integer> list = new List<>();
        list.add(i);

        assertEquals(list.get(0), i);
    }

    @Test
    public void getArbitraryTest() {
        int j = 100;
        List<Integer> list = new List<>();
        for (int i = 0; i < 6; i++) {
            if(i == 4) {
                list.add(j);
            } else {
                list.add(i);
            }
        }

        assertEquals(list.get(4), j);
    }

    @Test
    public void removeTest() {
        List<Integer> list = new List<>();
        for(int i = 0; i < 5; i++) {
            list.add(i);
        }

        list.remove(3);

        assertEquals(list.size(), 4);

        for (int i = 0; i < list.size(); i++) {

            if(i <= 2) {
                assertEquals(list.get(i), i);
            } else if (i == 3) {
                assertNotEquals(list.get(i), i);
            } else {
                assertEquals(list.get(i), i + 1);
            }
        }
    }

    @Test
    public void testContains() {
        List<Integer> list = new List<>();
        for(int i = 0; i < 5; i++) {
            list.add(i);
        }

        assert list.contains(3);
    }

    @Test
    public void testIsEmpty() {
        List<Integer> list = new List<>();

        assert list.isEmpty();
    }

    @Test
    public void testIsNotEmpty() {
        List<Integer> list = new List<>();
        for(int i = 0; i < 5; i++) {
            list.add(i);
        }

        assert !list.isEmpty();
    }
}
