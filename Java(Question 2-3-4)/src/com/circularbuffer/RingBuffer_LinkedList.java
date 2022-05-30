package com.circularbuffer;

public class RingBuffer_LinkedList {
    public static void main(String[] args) {
        RingBuffer buffer = new RingBuffer();
        buffer.put(7);
        buffer.peek();
    }
}

class Node {
    int value;
    Node next;

    public Node(int value) {
        this.value = value;
        this.next = null;
    }
}

class RingBuffer {
    Node head, tail;
    int buffer_size = 16, counter = 0, max_size = 32;

    public void peek() {
        Node e = head;
        while (e != null) {
            System.out.print(e.value + "->");
            e = e.next;
        }
        System.out.println(" ");
    }

    public boolean isEmpty() {
        if (head == null)
            return true;
        else
            return false;
    }

    public void put(int data) {
        Node node = new Node(data);
        if (!isEmpty() && counter != buffer_size) {
            tail.next = node;
            tail = node;
            counter++;
        } else if (!isEmpty() && counter == buffer_size && buffer_size != max_size) {
            System.out.println("Buffer is full! Resizing...");
            //The buffer has been resized to avoid data loss in case of overflow.
            resizeUp();
            tail.next = node;
            tail = node;
            counter++;
        } else if (isEmpty()) {
            head = tail = node;
            counter++;
        } else
            System.out.println("Buffer Overflow!");
    }

    public int remove() {
        Node popData = head;
        if (!isEmpty()) {
            head = head.next;
            counter--;

            if (counter <= 15)
                resizeDown();

            if (head == null) {
                tail = null;
                System.out.println("Buffer is empty: " + isEmpty());
                resizeDown();
            }
        }
        return isEmpty() ? -1 : popData.value;

    }

    public void resizeUp() {
        if (buffer_size != max_size)
            buffer_size *= 2;
    }

    public void resizeDown() {
        if (buffer_size == max_size)
            buffer_size /= 2;
    }
}