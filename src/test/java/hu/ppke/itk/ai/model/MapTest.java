package hu.ppke.itk.ai.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;

public class MapTest extends TestCase{

    public MapTest(String testName){
        super(testName);
    }

    public static Test suite()
    {
        return new TestSuite(MapTest.class);
    }

    public void testMap() {
        Map map = new Map();
        map.changePixelSize(300);
        List<Node> nodes = map.getNodes();

        assertEquals(4, nodes.size());

        assertEquals(null, nodes.get(0).getNorthNeighb());
        assertEquals(nodes.get(1), nodes.get(0).getEastNeighb());
        assertEquals(nodes.get(2), nodes.get(0).getSouthNeighb());
        assertEquals(null, nodes.get(0).getWestNeighb());

        assertEquals(null, nodes.get(1).getNorthNeighb());
        assertEquals(null, nodes.get(1).getEastNeighb());
        assertEquals(nodes.get(3), nodes.get(1).getSouthNeighb());
        assertEquals(nodes.get(0), nodes.get(1).getWestNeighb());

        assertEquals(nodes.get(0), nodes.get(2).getNorthNeighb());
        assertEquals(nodes.get(3), nodes.get(2).getEastNeighb());
        assertEquals(null, nodes.get(2).getSouthNeighb());
        assertEquals(null, nodes.get(2).getWestNeighb());

        assertEquals(nodes.get(1), nodes.get(3).getNorthNeighb());
        assertEquals(null, nodes.get(3).getEastNeighb());
        assertEquals(null, nodes.get(3).getSouthNeighb());
        assertEquals(nodes.get(2), nodes.get(3).getWestNeighb());

    }
}
