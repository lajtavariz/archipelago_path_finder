package hu.ppke.itk.ai.model;

import hu.ppke.itk.ai.lib.OpenSimplexNoise;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static hu.ppke.itk.ai.config.Config.*;
import static hu.ppke.itk.ai.model.Category.*;

public class Map {

    private int canvasSize;
    private int pixelSize;
    private float threshold;
    private int nrOfPixelsInARow;

    private OpenSimplexNoise noise;
    private Node agentNode;

    private List<Node> nodes;

    public Map() {
        canvasSize = DEFAULT_CANVAS_SIZE;
        pixelSize = DEFAULT_PIXEL_SIZE;
        threshold = DEFAULT_THRESHOLD;
        init();
    }

    public Map(int canvasSize, int pixelSize) {
        this.canvasSize = canvasSize;
        this.pixelSize = pixelSize;
        threshold = DEFAULT_THRESHOLD;
        init();
    }

    private void init() {
        nrOfPixelsInARow = calculateNrOfPixelsInARow();
        noise = getNoiseWithNewSeed();
        nodes = generateNodes();
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
        nodes = reGenerateNodes();
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
        nodes = reGenerateNodes();
    }

    public List<Node> reGenerateNodes() {
        noise = getNoiseWithNewSeed();
        return generateNodes();
    }

    private List<Node> generateNodes() {
        agentNode = null;
        List<Node> nodeList = new ArrayList<Node>();
        for (int y = 0; y < nrOfPixelsInARow; y++) {
            for (int x = 0; x < nrOfPixelsInARow; x++) {
                double noiseValue = noise.eval(x, y);

                if (noiseValue > threshold) {
                    nodeList.add(new Node(x, y, LAND));
                } else {
                    // we place the agent in the first generated water cell
                    if (agentNode == null) {
                        agentNode = new Node(x, y, AGENT);
                        nodeList.add(agentNode);
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

    public void makeRandomStepWithAgent() {
        Random rand = new Random(System.currentTimeMillis());
        int n = rand.nextInt(100000) % 4;

        makeStep(n);
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
    }

    private void evaluateChangesForStep(Node neighb) {
        if (neighb != null && !LAND.equals(neighb.getCategory())) {
            agentNode.setCategory(WATER);
            agentNode = neighb.setCategory(AGENT);
        }
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public int getPixelSize() {
        return pixelSize;
    }
}
