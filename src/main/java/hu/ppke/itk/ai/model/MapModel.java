package hu.ppke.itk.ai.model;

import hu.ppke.itk.ai.enumeration.Algorithm;
import hu.ppke.itk.ai.model.search.AbstractSearch;
import hu.ppke.itk.ai.model.search.impl.BFS;
import hu.ppke.itk.ai.model.search.impl.DFS;
import hu.ppke.itk.ai.model.search.impl.GreedySearch;
import hu.ppke.itk.ai.model.search.impl.RandomSearch;
import hu.ppke.itk.ai.noise.OpenSimplexNoise;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static hu.ppke.itk.ai.config.Config.*;
import static hu.ppke.itk.ai.enumeration.Category.*;

public class MapModel extends Observable {

    private int canvasSize;
    private int pixelSize;
    private float threshold;
    private double goalNodeThreshold;
    private int nrOfPixelsInARow;

    private OpenSimplexNoise noise;
    private Node agentNode;
    private Node startNode;
    private Node goalNode;

    private Thread thread;
    private RandomSearch randomSearch;
    private BFS bfs;
    private DFS dfs;
    private GreedySearch greedy;

    private List<Node> nodes;

    public MapModel() {
        canvasSize = DEFAULT_CANVAS_SIZE;
        pixelSize = DEFAULT_PIXEL_SIZE;
        threshold = DEFAULT_THRESHOLD;
        goalNodeThreshold = DEFAULT_GOAL_NODE_THRESHOLD;
        init();
    }

    public MapModel(int canvasSize, int pixelSize) {
        this.canvasSize = canvasSize;
        this.pixelSize = pixelSize;
        threshold = DEFAULT_THRESHOLD;
        goalNodeThreshold = DEFAULT_GOAL_NODE_THRESHOLD;
        init();
    }

    private void init() {
        nrOfPixelsInARow = calculateNrOfPixelsInARow();
        noise = getNoiseWithNewSeed();
        nodes = generateNodes();

        setToChangedAndNotifyObservers();
    }

    private int calculateNrOfPixelsInARow() {
        return canvasSize / pixelSize;
    }

    private OpenSimplexNoise getNoiseWithNewSeed() {
        return new OpenSimplexNoise(System.currentTimeMillis());
    }

    public void setPixelSize(int pixelSize) {
        this.pixelSize = pixelSize;
        nrOfPixelsInARow = calculateNrOfPixelsInARow();
        reGenerateNodes();

        setToChangedAndNotifyObservers();
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
        reGenerateNodes();

        setToChangedAndNotifyObservers();
    }

    public void reGenerateNodes() {
        noise = getNoiseWithNewSeed();
        nodes = generateNodes();

        setToChangedAndNotifyObservers();
    }

    private List<Node> generateNodes() {
        agentNode = null;
        boolean hasTheGoalNodeBeenPlaced = false;
        List<Node> nodeList = new ArrayList<>();
        for (int y = 0; y < nrOfPixelsInARow; y++) {
            for (int x = 0; x < nrOfPixelsInARow; x++) {
                double noiseValue = noise.eval(x, y);

                if (noiseValue > threshold) {
                    nodeList.add(new Node(x, y, LAND));
                } else {
                    // we place the agent in the first generated water cell
                    if (agentNode == null) {
                        agentNode = new Node(x, y, AGENT);
                        startNode = agentNode;
                        nodeList.add(agentNode);

                        // if we passed the threshold we place the goal node
                    } else if ((y > nrOfPixelsInARow * goalNodeThreshold)
                            && (x > nrOfPixelsInARow * goalNodeThreshold) && !hasTheGoalNodeBeenPlaced) {
                        goalNode = new Node(x, y, GOAL);
                        nodeList.add(goalNode);
                        hasTheGoalNodeBeenPlaced = true;
                    } else {
                        nodeList.add(new Node(x, y, WATER));
                    }
                }
            }
        }

        return findNeighbors(nodeList);
    }

    private List<Node> findNeighbors(List<Node> nodeList) {
        for (int i = 0; i < nodeList.size(); i++) {
            Node currentNode = nodeList.get(i);

            int N = nrOfPixelsInARow;

            if (i < N) {
                currentNode.setNorthNeighb(null);
            } else {
                currentNode.setNorthNeighb(nodeList.get(i - N));
            }
            if (i % N == N - 1) {
                currentNode.setEastNeighb(null);
            } else {
                currentNode.setEastNeighb(nodeList.get(i + 1));
            }
            if (N * N - N <= i) {
                currentNode.setSouthNeighb(null);
            } else {
                currentNode.setSouthNeighb(nodeList.get(i + N));
            }
            if (i % N == 0) {
                currentNode.setWestNeighb(null);
            } else {
                currentNode.setWestNeighb(nodeList.get(i - 1));
            }
        }

        return nodeList;
    }

    public void startSearch(Algorithm algorithm) {
        switch (algorithm) {
            case RANDOM:
                randomSearch = new RandomSearch(this);
                startComputation(randomSearch);
                break;
            case BFS:
                bfs = new BFS(this);
                startComputation(bfs);
                break;
            case DFS:
                dfs = new DFS(this);
                startComputation(dfs);
                break;
            case GREEDY:
                greedy = new GreedySearch(this);
                startComputation(greedy);
                break;
            default:
                throw new UnsupportedOperationException("Algorithm " + algorithm + " is not yet supported.");
        }
    }

    public void stopSearch(Algorithm algorithm) {
        switch (algorithm) {
            case RANDOM:
                stopComputation(randomSearch);
                break;
            case BFS:
                stopComputation(bfs);
                break;
            case DFS:
                stopComputation(dfs);
                break;
            case GREEDY:
                stopComputation(greedy);
                break;
            default:
                throw new UnsupportedOperationException("Algorithm " + algorithm + " is not yet supported.");
        }
    }

    public void evaluateAlgorithms() throws InterruptedException {
        float new_threshold = 0.0f;
        for (int i = 0; i <= 10; i++) {
            threshold = new_threshold;
            reGenerateNodes();
            new_threshold = new_threshold + 0.1f;

            for (int j = 1; j <= 10; j++) {
                bfs = (BFS) new BFS(this).setSleepTime(1);
                startComputation(bfs);
                thread.join();

            }


        }
    }

    private <T extends AbstractSearch> void startComputation(T search) {
        thread = new Thread(search);
        thread.start();
    }

    private <T extends AbstractSearch> void stopComputation(T search) {
        try {
            if (thread != null) {
                search.terminate();
                thread.join();
            }
        } catch (Exception exc) {
            System.err.println(exc);
        }
    }

    public void makeStep(int direction) {
        switch (direction) {
            case 0:
                evaluateChangesForStep(agentNode.getNorthNeighb());
                break;
            case 1:
                evaluateChangesForStep(agentNode.getEastNeighb());
                break;
            case 2:
                evaluateChangesForStep(agentNode.getSouthNeighb());
                break;
            case 3:
                evaluateChangesForStep(agentNode.getWestNeighb());
                break;
        }

        setToChangedAndNotifyObservers();
    }

    private void evaluateChangesForStep(Node neighb) {
        if (neighb != null && !LAND.equals(neighb.getCategory())) {
            agentNode.setCategory(WATER);
            agentNode = neighb.setCategory(AGENT);
        }
    }

    public void setToChangedAndNotifyObservers() {
        setChanged();
        notifyObservers();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getAgentNode() {
        return agentNode;
    }

    public Node getGoalNode() {
        return goalNode;
    }
}
