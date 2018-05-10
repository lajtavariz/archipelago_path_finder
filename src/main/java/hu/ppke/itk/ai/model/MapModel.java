package hu.ppke.itk.ai.model;

import hu.ppke.itk.ai.lib.OpenSimplexNoise;

import java.util.*;
import java.util.concurrent.LinkedTransferQueue;
import java.util.stream.Collectors;

import static hu.ppke.itk.ai.config.Config.*;
import static hu.ppke.itk.ai.model.Category.*;

public class MapModel extends Observable {

    private int canvasSize;
    private int pixelSize;
    private float threshold;
    private double goalNodeThreshold;
    private int nrOfPixelsInARow;

    private OpenSimplexNoise noise;
    private Node agentNode;
    private Node startNode;

    private Thread thread;
    private RandomWalk randomWalk;
    private BFS bfs;

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
                        nodeList.add(new Node(x, y, GOAL));
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

    public void startRandomWalkWithAgent() {
        startComputation(new RandomWalk());
    }

    public void stopRandomWalWithAgent() {
        stopComputation(randomWalk);
    }

    public void startBFS() {
        startComputation(new BFS());
    }

    public void stopBFS() {
        stopComputation(bfs);
    }

    private void startComputation(AbstractSearch abstractSearch) {
        if (abstractSearch instanceof RandomWalk) {
            randomWalk = new RandomWalk();
            thread = new Thread(randomWalk);
        } else if (abstractSearch instanceof BFS) {
            bfs = new BFS();
            thread = new Thread(bfs);
        } else {
            throw new UnsupportedOperationException("Computation is not yet supported!");
        }
        thread.start();
    }

    private void stopComputation(AbstractSearch abstractSearch) {
        try {
            if (thread != null) {
                abstractSearch.terminate();
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

    private void setToChangedAndNotifyObservers() {
        setChanged();
        notifyObservers();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    private void makeRandomStepWithAgent() {
        Random rand = new Random(System.currentTimeMillis());
        int n = rand.nextInt(100000) % 4;

        makeStep(n);
    }

    private abstract class AbstractSearch implements Runnable {
        volatile boolean running = true;

        void terminate() {
            running = false;
        }

        public void run() {
            try {
                while (running) {
                    Thread.sleep(40);
                    makeStep();
                }
            } catch (InterruptedException exc) {
                System.err.println(exc);
                running = false;
            }
        }

        abstract void makeStep();
    }

    private class RandomWalk extends AbstractSearch {
        @Override
        void makeStep() {
            makeRandomStepWithAgent();
        }
    }

    private class BFS extends AbstractSearch {

        Queue<Node> nodesQueue = new LinkedTransferQueue<>();

        BFS() {
            nodesQueue.add(startNode);
            startNode.setVisited(true);
        }

        @Override
        void makeStep() {
            agentNode.setCategory(WATER);
            if (!nodesQueue.isEmpty()) {
                Node currentNode = nodesQueue.remove();
                if (currentNode.getCategory().equals(GOAL)) {
                    stopBFS();
                } else {
                    agentNode = currentNode.setCategory(AGENT).setVisited(true);
                    System.out.println("Expanding node x: " + agentNode.getxPos() + ", y: " + agentNode.getyPos());
                    addElementsToNodesQueue(nodesQueue, agentNode.getAllNeighbors());
                }
            } else {
                stopBFS();
            }
            setToChangedAndNotifyObservers();
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
}
