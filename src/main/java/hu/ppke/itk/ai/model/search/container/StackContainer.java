package hu.ppke.itk.ai.model.search.container;

import hu.ppke.itk.ai.model.Node;

import java.util.Stack;

public class StackContainer implements IContainer<Node> {

    private Stack<Node> stack;

    public StackContainer() {
        stack = new Stack<>();
    }

    @Override
    public Node pop() {
        return stack.pop();
    }

    @Override
    public void push(Node node) {
        stack.push(node);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public boolean contains(Node object) {
        return stack.contains(object);
    }
}
