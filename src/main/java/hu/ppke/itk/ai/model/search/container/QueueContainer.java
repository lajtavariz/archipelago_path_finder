package hu.ppke.itk.ai.model.search.container;

import hu.ppke.itk.ai.model.Node;

import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;

public class QueueContainer implements IContainer<Node> {

    private Queue<Node> queue;

    public QueueContainer() {
        this.queue = new LinkedTransferQueue<>();
    }

    @Override
    public Node pop() {
        return queue.remove();
    }

    @Override
    public void push(Node node) {
        queue.add(node);
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
