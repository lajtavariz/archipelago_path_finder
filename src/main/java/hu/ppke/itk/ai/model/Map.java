package hu.ppke.itk.ai.model;

import hu.ppke.itk.ai.lib.OpenSimplexNoise;

import java.util.ArrayList;
import java.util.List;

import static hu.ppke.itk.ai.config.Config.*;
import static hu.ppke.itk.ai.model.Category.LAND;
import static hu.ppke.itk.ai.model.Category.WATER;

public class Map {

    private byte featureSize;
    private float threshold;

    private OpenSimplexNoise noise;

    private List<Node> nodes;

    public Map() {
        featureSize = DEFAULT_FEATURE_SIZE;
        threshold = DEFAULT_THRESHOLD;
        noise = getOpenSimplexNoiseWithNewSeed();
        nodes = generateNodes();
    }

    private OpenSimplexNoise getOpenSimplexNoiseWithNewSeed(){
        return new OpenSimplexNoise(System.currentTimeMillis());
    }

    public void changeFeatureSize(byte featureSize){
        this.featureSize = featureSize;
        reGenerateNodes();
    }

    public void changeThreshold(float threshold){
        this.threshold = threshold;
        reGenerateNodes();
    }

    public void reGenerateNodes() {
        noise = getOpenSimplexNoiseWithNewSeed();
        nodes = generateNodes();
    }

    private List<Node> generateNodes() {
        List<Node> nodeList = new ArrayList<Node>();
        for (int y = 0; y < SIZE_OF_CANVAS; y++){
            for (int x = 0; x < SIZE_OF_CANVAS; x++) {
                double noiseValue = noise.eval(x / featureSize, y / featureSize);

                if (noiseValue > threshold){
                    nodeList.add(new Node(x, y, LAND));
                }
                else {
                    nodeList.add(new Node(x, y, WATER));
                }
            }
        }

        return nodeList;
    }

    public List<Node> getNodes() {
        return nodes;
    }
}
