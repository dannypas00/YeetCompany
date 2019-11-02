package com.nhlstenden.amazonsimulatie.test;

import com.nhlstenden.amazonsimulatie.models.*;

import org.testng.annotations.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RobotMovementTest {
    /**
     * TestRobotMovement tests the robot's ability to move to a specified node's X and Z coordinates.
     */
    @Test
    public void TestRobotMovement() throws Exception {
        Robot robot = new Robot();
        Node target = new Node("Test");
        double targetX = 10;
        double targetZ = 10;
        target.setX((int) Math.ceil(targetX));
        target.setZ((int) Math.ceil(targetZ));
        Stack<Node> route  = new Stack<>();
        route.push(target);
        robot.goRoute(route, "put");
        while(robot.update()) {}
        assertThat(robot.getX(), is((Double) targetX));
        assertThat(robot.getZ(), is ((Double) targetZ));
    }
}
