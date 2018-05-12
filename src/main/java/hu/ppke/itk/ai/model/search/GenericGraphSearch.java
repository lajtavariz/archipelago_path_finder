package hu.ppke.itk.ai.model.search;

import hu.ppke.itk.ai.enumeration.Algorithm;
import hu.ppke.itk.ai.model.MapModel;
import hu.ppke.itk.ai.model.Node;
import hu.ppke.itk.ai.model.search.container.IContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

            if (!currentNode.equals(agentNode) && !agentNode.isWaterNeighbor(currentNode)) {
                makeSteps(getRouteToCurrentNode(currentNode, agentNode));
            }

            agentNode.setCategory(WATER);
            agentNode = currentNode.setCategory(AGENT).setVisited(true);

            if (agentNode.isOneOfTheNeighborsTheGoal()) {
                System.out.println("Goal has been found!");
                mapModel.setToChangedAndNotifyObservers();
                Thread.sleep(1000);
                mapModel.stopSearch(algorithm);
            }

            addElementsToContainer(nodes, agentNode);
            increaseNrOfSteps();

        } else {
            System.out.println("Data structure is empty. The algorithm is now stopped.");
            mapModel.stopSearch(algorithm);
        }
        mapModel.setToChangedAndNotifyObservers();
    }

    private List<Node> getRouteToCurrentNode(Node nodeToReach, Node agentNode) {
        List<Node> nodeToReachAncestors = nodeToReach.getAncestors();
        List<Node> agentNodeAncestors = agentNode.getAncestors();
        List<Node> wholePath = new ArrayList<>();

        for (Node nodeToReachAncestor : nodeToReachAncestors) {
            if (agentNodeAncestors.contains(nodeToReachAncestor)) {
                List<Node> path1 = agentNodeAncestors.stream()
                        .filter(p -> agentNodeAncestors.indexOf(p) < agentNodeAncestors.indexOf(nodeToReachAncestor))
                        .collect(Collectors.toList());
                List<Node> path2 = nodeToReachAncestors.stream()
                        .filter(p -> nodeToReachAncestors.indexOf(p) <= nodeToReachAncestors.indexOf(nodeToReachAncestor))
                        .collect(Collectors.toList());

                wholePath.addAll(path1);

                Collections.reverse(path2);
                wholePath.addAll(path2);

                return wholePath;
            }
        }

        return wholePath;
    }

    private void makeSteps(List<Node> pathToNode) throws InterruptedException {
        for (Node node : pathToNode) {
            agentNode.setCategory(WATER);
            agentNode = node.setCategory(AGENT);
            mapModel.setToChangedAndNotifyObservers();
            Thread.sleep(sleepTime);
            increaseNrOfSteps();
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
