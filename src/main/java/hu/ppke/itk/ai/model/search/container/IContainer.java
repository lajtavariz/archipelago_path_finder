package hu.ppke.itk.ai.model.search.container;

public interface IContainer<T> {

    T pop();

    void push(T object);

    boolean isEmpty();

    boolean contains(T object);

}
