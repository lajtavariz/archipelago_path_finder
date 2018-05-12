package hu.ppke.itk.ai.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import static hu.ppke.itk.ai.enumeration.Category.WATER;

public class NodeTest extends TestCase {

    public NodeTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(NodeTest.class);
    }

    public void testDistance1() {
        evaluateDistance(5, 10, 3, 5, Math.sqrt(29));
    }

    public void testDistance2() {
        evaluateDistance(1200, 800, 1600, 913, 415.6549049391815);
    }

    private void evaluateDistance(int x1, int y1, int x2, int y2, double expectedDistance) {
        Node node1 = new Node(x1, y1, WATER);
        Node node2 = new Node(x2, y2, WATER);

        double distance = node1.calculateDistanceToNode(node2);

        assertEquals(expectedDistance, distance);

        distance = node2.calculateDistanceToNode(node1);

        assertEquals(expectedDistance, distance);
    }
}
