package faops;

import java.util.Iterator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;

public class Reverse {

    private DefaultGraph reversedGraph;

    public Reverse(DefaultGraph graph) {
        this.reversedGraph = graph;
        Iterator iterator = graph.getEdgeIterator();

        while(iterator.hasNext()){
            System.out.println("From " + iterator.next().getNode0() + " to " + iterator.next().getNode1());
        }
    }

    public DefaultGraph getReversed() {
        return reversedGraph;
    }

}
