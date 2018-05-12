package hu.ppke.itk.ai.model.search.container;

import hu.ppke.itk.ai.model.Node;

import java.util.PriorityQueue;

public class PriorityQueueContainer implements IContainer<Node> {

    PriorityQueue<Node> queue;

    public PriorityQueueContainer(Node goalNode) {
        this.queue = new PriorityQueue<Node>((a, b) ->
                (int) Math.floor(a.calculateDistanceToNode(goalNode) - b.calculateDistanceToNode(goalNode)));
    }

    @Override
    public Node pop() {
        return queue.remove();
    }

    @Override
    public void push(Node object) {
        queue.add(object);
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public boolean contains(Node object) {
        return queue.contains(object);
    }
}
