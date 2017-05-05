package faops;

import java.util.Collection;
import java.util.Iterator;
import org.graphstream.graph.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.*;
import org.graphstream.graph.implementations.AbstractEdge;
import org.graphstream.graph.implementations.DefaultGraph;

public class Reverse {

    private MultiGraph reversedGraph;

    public Reverse(MultiGraph graph) {
        this.reversedGraph = new MultiGraph(graph.getId() + "r");

        Iterator<AbstractNode> nodeIterator = graph.getNodeIterator();
        while(nodeIterator.hasNext()){
            AbstractNode node = nodeIterator.next();

            reversedGraph.addNode(node.getId());

            if(node.hasAttribute("shape")){
                String type = node.getAttribute("shape").toString();
                String newType = getNewNodeType(type);
                reversedGraph.getNode(node.getId()).setAttribute("shape", newType);
                //node.setAttribute("shape", newType);
            //    System.out.println("Previous type: " +  type);
            //    System.out.println("New type: " +  node.getAttribute("shape").toString( ));
            }
        }

        Iterator<AbstractEdge> iterator = graph.getEdgeIterator();
        int i = 5656;
        while(iterator.hasNext()){
            AbstractEdge e = iterator.next();
        //    System.out.println("From " + e.getNode0() + " to " + e.getNode1());
            String source = e.getNode0().getId();
            String dest = e.getNode1().getId();
            reversedGraph.addEdge(String.valueOf(i), dest, source, true);

            if(e.hasAttribute("label"))
                reversedGraph.getEdge(String.valueOf(i)).addAttribute("label", e.getAttribute("label").toString());
            i++;
        }


    /*    Iterator<AbstractEdge> iterator2 = reversedGraph.getEdgeIterator();
        while(iterator2.hasNext()){
            AbstractEdge e = iterator2.next();
            System.out.println("New From " + e.getNode0() + " to " + e.getNode1());
        }*/

        DumpDot dump = new DumpDot(reversedGraph);
        dump.dumpFile("exitTest.dot");
    }


    private String getNewNodeType(String currentType){
        String ret = null;
        switch(currentType){
            case "doublecircle":
                ret = "circle";
                break;
            case "circle":
                ret = "doublecircle";
                break;
            default:
                ret = "point"; //TODO: CHANGE
                break;
        }
        return ret;
    }


    public MultiGraph getReversed() {
        return reversedGraph;
    }

}
