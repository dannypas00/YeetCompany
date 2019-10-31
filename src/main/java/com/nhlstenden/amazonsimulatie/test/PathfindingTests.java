package com.nhlstenden.amazonsimulatie.test;

import com.nhlstenden.amazonsimulatie.models.*;
import com.nhlstenden.amazonsimulatie.views.DefaultWebSocketView;

import org.testng.annotations.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PathfindingTests {
    @Test
    public void testPathfinding() throws Exception {
        Pathfinding pathfinder = new Pathfinding();

        pathfinder.getPathToItem("skull");
    }
}