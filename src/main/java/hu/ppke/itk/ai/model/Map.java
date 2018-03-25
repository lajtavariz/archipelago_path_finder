package hu.ppke.itk.ai.model;

import hu.ppke.itk.ai.lib.OpenSimplexNoise;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static hu.ppke.itk.ai.config.Config.*;
import static hu.ppke.itk.ai.model.Category.*;

public class Map {

    private int pixelSize;
    private float threshold;
    private int nrOfPixelsInARow;

    private OpenSimplexNoise noise;
    private Node agentNode;

    private List<Node> nodes;

    public Map() {
        pixelSize = DEFAULT_PIXEL_SIZE;
        threshold = DEFAULT_THRESHOLD;
        noise = getNoiseWithNewSeed();
        nodes = generateNodes();
        calculateNrOfPixelsInARow();
    }

    private void calculateNrOfPixelsInARow (){
        nrOfPixelsInARow = SIZE_OF_CANVAS / pixelSize;
    }

    private OpenSimplexNoise getNoiseWithNewSeed(){
        return new OpenSimplexNoise(System.currentTimeMillis());
    }

    public void changePixelSize(int pixelSize){
        this.pixelSize = pixelSize;
        calculateNrOfPixelsInARow();
        reGenerateNodes();
    }

    public void changeThreshold(float threshold){
        this.threshold = threshold;
        reGenerateNodes();
    }

    public void reGenerateNodes() {
        noise = getNoiseWithNewSeed();
        nodes = generateNodes();
    }

    private List<Node> generateNodes() {
        agentNode = null;
        List<Node> nodeList = new ArrayList<Node>();
        for (int y = 0; y < nrOfPixelsInARow; y++){
            for (int x = 0; x < nrOfPixelsInARow; x++) {
                double noiseValue = noise.eval(x, y);

                if (noiseValue > threshold){
                    nodeList.add(new Node(x, y, LAND));
                }
                else {
                    // we place the agent in the first generated water cell
                    if (agentNode == null){
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
            Node node = nodeList.get(i);

            if (node.getxPos() == 0){
                node.setWestNeighb(null);
                node.setEastNeighb(nodeList.get(i + 1));
            }
            else if (node.getxPos() > 0 && node.getxPos() < nrOfPixelsInARow - 1){
                node.setWestNeighb(nodeList.get(i - 1));
                node.setEastNeighb(nodeList.get(i + 1));
            }
            else{
                node.setWestNeighb(nodeList.get(i - 1));
                node.setEastNeighb(null);
            }

            if (node.getyPos() == 0){
                node.setNorthNeighb(null);
                node.setSouthNeighb(nodeList.get(i + nrOfPixelsInARow));
            }
            else if (node.getyPos() > 0 && node.getyPos() < nrOfPixelsInARow - 1){
                node.setNorthNeighb(nodeList.get(i - nrOfPixelsInARow));
                node.setSouthNeighb(nodeList.get(i + nrOfPixelsInARow));
            }
            else{
                node.setNorthNeighb(nodeList.get(i - nrOfPixelsInARow));
                node.setSouthNeighb(null);
            }
        }

        return nodeList;
    }

    public void makeRandomStepWithAgent() {
        Random rand = new Random(System.currentTimeMillis());
        int n = rand.nextInt(100000) % 4;

        switch (n){
            case 0:
                evaluateChangesForStep(agentNode.getNorthNeighb());
            case 1:
                evaluateChangesForStep(agentNode.getEastNeighb());
            case 2:
                evaluateChangesForStep(agentNode.getSouthNeighb());
            case 3:
                evaluateChangesForStep(agentNode.getWestNeighb());
        }
    }

    private void evaluateChangesForStep(Node neighb){
        if (neighb == null || neighb.getCategory().equals(LAND)){
            //makeRandomStepWithAgent();
        } else{
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
