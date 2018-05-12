package hu.ppke.itk.ai.model.search;

import hu.ppke.itk.ai.enumeration.Algorithm;
import hu.ppke.itk.ai.model.MapModel;
import hu.ppke.itk.ai.model.Node;
import hu.ppke.itk.ai.model.search.container.IContainer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static hu.ppke.itk.ai.config.Config.DEFAULT_SLEEP_TIME;
import static hu.ppke.itk.ai.enumeration.Category.*;

public abstract class GenericGraphSearch<T extends IContainer<Node>> extends AbstractSearch {

    private T nodes;
    private Algorithm algorithm;

    private Node agentNode;

    protected GenericGraphSearch(MapModel mapModel, T nodes, Algorithm algorithm) {
        super(mapModel);
        agentNode = mapModel.getAgentNode();
        this.nodes = nodes;
        this.algorithm = algorithm;

        Node startNode = mapModel.getStartNode();
        nodes.push(startNode);
        startNode.setVisited(true);
    }

    @Override
    protected void makeStep() throws InterruptedException {
        if (!nodes.isEmpty()) {
            Node currentNode = nodes.pop();
            if (currentNode.getCategory().equals(GOAL)) {
                mapModel.stopSearch(algorithm);
            } else {
                if (!currentNode.equals(agentNode) && !agentNode.isWaterNeighbor(currentNode)) {
                    makeSteps(getRouteToCurrentNode(currentNode, agentNode));
                }

                agentNode.setCategory(WATER);
                agentNode = currentNode.setCategory(AGENT).setVisited(true);
                addElementsToContainer(nodes, agentNode);

            }
        } else {
            mapModel.stopSearch(algorithm);
        }
        mapModel.setToChangedAndNotifyObservers();
    }

    private List<Node> getRouteToCurrentNode(Node nodeToReach, Node agentNode) throws InterruptedException {
        List<Node> nodeToReachAncestors = new LinkedList<>();
        List<Node> agentNodeAncestors = new LinkedList<>();
        List<Node> pathToNodeToReach = new LinkedList<>();

        boolean hasCommonAncestorBeenFound = false;

        Node nodeToReachNextAncestor = nodeToReach.getAncestorNode();
        Node agentNodeNextAncestor = agentNode.getAncestorNode();

        if (nodeToReachNextAncestor.equals(agentNodeNextAncestor)) {
            hasCommonAncestorBeenFound = true;
            pathToNodeToReach.add(agentNodeNextAncestor);
        } else {
            nodeToReachAncestors.add(nodeToReachNextAncestor);
            agentNodeAncestors.add(agentNodeNextAncestor);
        }

        while (!hasCommonAncestorBeenFound) {

            if (nodeToReachNextAncestor.equals(agentNodeNextAncestor)) {
                hasCommonAncestorBeenFound = true;

                ((LinkedList<Node>) agentNodeAncestors).removeLast();
                pathToNodeToReach.addAll(agentNodeAncestors);

                Collections.reverse(nodeToReachAncestors);
                pathToNodeToReach.addAll(nodeToReachAncestors);
            } else {
                if (nodeToReachNextAncestor.getAncestorNode() != null) {
                    nodeToReachNextAncestor = nodeToReachNextAncestor.getAncestorNode();
                    nodeToReachAncestors.add(nodeToReachNextAncestor);
                }
                if (agentNodeNextAncestor.getAncestorNode() != null) {
                    agentNodeNextAncestor = agentNodeNextAncestor.getAncestorNode();
                    agentNodeAncestors.add(agentNodeNextAncestor);
                }
            }
        }

        return pathToNodeToReach;
    }

    private void makeSteps(List<Node> pathToNode) throws InterruptedException {
        for (Node node : pathToNode) {
            agentNode.setCategory(WATER);
            agentNode = node.setCategory(AGENT);
            mapModel.setToChangedAndNotifyObservers();
            Thread.sleep(DEFAULT_SLEEP_TIME);
        }
    }

    private void addElementsToContainer(IContainer<Node> nodes, Node agentNode) {
        List<Node> filteredNeighbors = agentNode.getAllNeighbors().stream()
                .filter(p -> p != null && !p.isVisited() && !p.getCategory().equals(LAND)).collect(Collectors.toList());

        for (Node node : filteredNeighbors) {
            if (!nodes.contains(node)) {
                node.setAncestorNode(agentNode);
                nodes.push(node);
            }
        }
    }
}
