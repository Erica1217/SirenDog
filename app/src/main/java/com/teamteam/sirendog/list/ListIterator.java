package com.teamteam.sirendog.list;

public interface ListIterator<E> {
    boolean hasNext();
    E next();
    boolean hasPrevious();
    E previous();
}