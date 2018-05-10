package hu.ppke.itk.ai.model.search;

import hu.ppke.itk.ai.model.MapModel;
import hu.ppke.itk.ai.model.Node;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.stream.Collectors;

import static hu.ppke.itk.ai.model.Category.*;

public class BFS extends AbstractSearch {

    Queue<Node> nodesQueue = new LinkedTransferQueue<>();

    private Node agentNode;

    public BFS(MapModel mapModel) {
        super(mapModel);
        agentNode = mapModel.getAgentNode();

        Node startNode = mapModel.getStartNode();
        nodesQueue.add(startNode);
        startNode.setVisited(true);
    }

    @Override
    protected void makeStep() {
        agentNode.setCategory(WATER);
        if (!nodesQueue.isEmpty()) {
            Node currentNode = nodesQueue.remove();
            if (currentNode.getCategory().equals(GOAL)) {
                mapModel.stopBFS();
            } else {
                agentNode = currentNode.setCategory(AGENT).setVisited(true);
                //System.out.println("Expanding node x: " + agentNode.getxPos() + ", y: " + agentNode.getyPos());
                addElementsToNodesQueue(nodesQueue, agentNode.getAllNeighbors());
            }
        } else {
            mapModel.stopBFS();
        }
        mapModel.setToChangedAndNotifyObservers();
    }

    private void addElementsToNodesQueue(Queue<Node> nodesQueue, List<Node> neighbors) {
        List<Node> filteredNeighbors = neighbors.stream()
                .filter(p -> p != null && !p.isVisited() && !p.getCategory().equals(LAND)).collect(Collectors.toList());

        for (Node node : filteredNeighbors) {
            if (!nodesQueue.contains(node)) {
                nodesQueue.add(node);
            }
        }
    }
}
