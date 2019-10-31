package com.nhlstenden.amazonsimulatie.test;

import com.nhlstenden.amazonsimulatie.models.*;

import org.testng.annotations.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PathfindingTests {
    @Test
    public void PathfindingTests() throws Exception {
        Pathfinding pathfinder = new Pathfinding();
        Node[][] nodes = pathfinder.getNodes();
        List<Node> expectedPath = new ArrayList<>();
        expectedPath.addAll(Arrays.asList(new Node[]{nodes[4][6], nodes[3][6], nodes[2][6], nodes[1][6], nodes[0][6], nodes[0][5], nodes[0][4], nodes[0][3], nodes[0][2], nodes[0][1], nodes[0][0]}));

        assertThat((List<Node>) pathfinder.getPath(nodes[4][6]), is(expectedPath));
    }
}