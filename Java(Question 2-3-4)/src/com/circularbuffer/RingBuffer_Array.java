package com.circularbuffer;

class mRingBuffer_Array {
    public static void main(String[] args) {
        RingBuffer_Array ringBuffer_array = new RingBuffer_Array(2);
        ringBuffer_array.push(7);
    }
}

public class RingBuffer_Array {
    private int head, tail; //read, write
    private int[] datas;

    public RingBuffer_Array(int data) {
        this.datas = new int[data];
        this.head = this.tail = -1;
    }

    public void push(int data) {
        if (isFull())
            resize();
        else if (isEmpty())
            head++;
        tail = (tail + 1) % datas.length;
        datas[tail] = data;
    }

    public int remove() {
        if (isEmpty())
            System.out.println("Empty");
        int temp = datas[head];
        if (head == tail)
            head = tail = -1;
        else
            head = (head + 1) % datas.length;
        return temp;

    }

    public boolean isEmpty() {
        return head == -1;
    }

    public boolean isFull() {
        return (tail + 1) % datas.length == head;
    }

    public void resize() {
        int[] tempArr = new int[datas.length * 2];
        int i = 0, j = head;

        do {
            tempArr[i++] = datas[j];
            j = (j + 1) % datas.length;
        } while (j != head);
        head = 0;
        tail = datas.length - 1;
        datas = tempArr;
    }


}
