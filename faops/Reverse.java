package faops;

import java.util.Iterator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.AbstractEdge;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;


public class Reverse {

    private MultiGraph reversedGraph;

    public Reverse(MultiGraph graph) {
        this.reversedGraph = new MultiGraph(graph.getId() + "r");

        Iterator<AbstractNode> nodeIterator = graph.getNodeIterator();
        while(nodeIterator.hasNext())
            this.reversedGraph.addNode(nodeIterator.next().getId());

        Iterator<AbstractEdge> iterator = graph.getEdgeIterator();
        int i = 5656;
        while(iterator.hasNext()){
            AbstractEdge e = iterator.next();
            System.out.println("From " + e.getNode0() + " to " + e.getNode1());
            String source = e.getNode0().getId();
            String dest = e.getNode1().getId();
            this.reversedGraph.addEdge(String.valueOf(i), dest, source);
            i++;
        }


        Iterator<AbstractEdge> iterator2 = reversedGraph.getEdgeIterator();
        while(iterator2.hasNext()){
            AbstractEdge e = iterator2.next();
            System.out.println("New From " + e.getNode0() + " to " + e.getNode1());
        }

        DumpDot dump = new DumpDot(this.reversedGraph);
        dump.dumpFile("exitTest.dot");
    }

    public MultiGraph getReversed() {
        return reversedGraph;
    }

}
