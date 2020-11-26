package com.teamteam.sirendog.list;


import java.io.Serializable;
import java.util.Arrays;

public class PhoneArrayList<E> implements List<E>, Serializable {

    private static final int defaultSize = 100;
    private int listSize;
    private E[] data;

    public PhoneArrayList() {
        this(defaultSize);
    }

    public PhoneArrayList(int size) {
        listSize = 0;
        data = (E[]) new Object[size];
    }

    @Override
    public void clear() {
        listSize = 0;
    }

    @Override
    public void insert(int pos, E item) {
        for(int i=listSize; i>pos; i--)
            data[i] = data[i-1];

        data[pos] = item;
        listSize++;
    }

    @Override
    public void append(E item) {
        data[listSize++] = item;
    }

    public void append(PhoneArrayList<E> items) {
        for(int i=listSize ; i<items.length()+listSize ; i++)
        {
            data[i] = items.getValue(i);
        }
        listSize+=items.length();
    }

    @Override
    public void update(int pos, E item) {
        data[pos] = item;
    }

    @Override
    public E getValue(int pos) {
        return data[pos];
    }

    @Override
    public E remove(int pos) {
        E ret = data[pos];

        for(int i=pos; i<listSize-1; i++)
            data[i] = data[i+1];

        listSize--;

        return ret;
    }

    @Override
    public int length() {
        return listSize;
    }

    public String toString() {
        return Arrays.toString(data);
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListIterator<E>() {
            int pos = 0;
            @Override
            public boolean hasNext() {
                return pos < listSize;
            }

            @Override
            public E next() {
                return data[pos++];
            }

            @Override
            public boolean hasPrevious() {
                return pos > 0;
            }

            @Override
            public E previous() {
                return data[--pos];
            }
        };
    }

}