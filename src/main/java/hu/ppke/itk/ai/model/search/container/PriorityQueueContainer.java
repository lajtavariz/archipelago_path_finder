package hu.ppke.itk.ai.model.search.container;

import hu.ppke.itk.ai.model.Node;

import java.util.PriorityQueue;

public class PriorityQueueContainer implements IContainer<Node> {

    PriorityQueue<Node> queue;

    public PriorityQueueContainer(Node goalNode) {
        //this.queue = new PriorityQueue<Node>((a,b) -> a.length() - b.length());
    }

    @Override
    public Node pop() {
        return null;
    }

    @Override
    public void push(Node object) {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Node object) {
        return false;
    }
}
