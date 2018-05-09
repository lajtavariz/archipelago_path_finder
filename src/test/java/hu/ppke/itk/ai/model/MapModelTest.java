package hu.ppke.itk.ai.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;

public class MapModelTest extends TestCase {

    public MapModelTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(MapModelTest.class);
    }

    public void test2x2MapGraph() {
        evaluateNodeNeighborsForNxNMap(2);
    }

    public void test3x3MapGraph() {
        evaluateNodeNeighborsForNxNMap(3);
    }

    public void test10x10MapGraph() {
        evaluateNodeNeighborsForNxNMap(10);
    }

    public void test299x299MapGraph() {
        evaluateNodeNeighborsForNxNMap(299);
    }

    public void test1000x1000MapGraph() {
        evaluateNodeNeighborsForNxNMap(1000);
    }

    private void evaluateNodeNeighborsForNxNMap(int N) {
        MapModel mapModel = new MapModel(N, 1);
        List<Node> nodes = mapModel.getNodes();

        assertEquals(N * N, nodes.size());

        Node expectedNorthNeighb;
        Node expectedEastNeighb;
        Node expectedSouthNeighb;
        Node expectedWestNeighb;

        for (int i = 0; i < nodes.size(); i++) {
            Node currentNode = nodes.get(i);
            if (currentNode.getyPos() == 0) {
                expectedNorthNeighb = null;
            } else {
                expectedNorthNeighb = nodes.get(i - N);
            }
            if (i % N == N - 1) {
                expectedEastNeighb = null;
            } else {
                expectedEastNeighb = nodes.get(i + 1);
            }
            if (currentNode.getyPos() == N - 1) {
                expectedSouthNeighb = null;
            } else {
                expectedSouthNeighb = nodes.get(i + N);
            }
            if (i % N == 0) {
                expectedWestNeighb = null;
            } else {
                expectedWestNeighb = nodes.get(i - 1);
            }

            assertEquals(expectedNorthNeighb, currentNode.getNorthNeighb());
            assertEquals(expectedEastNeighb, currentNode.getEastNeighb());
            assertEquals(expectedSouthNeighb, currentNode.getSouthNeighb());
            assertEquals(expectedWestNeighb, currentNode.getWestNeighb());
        }
    }
}
