package faops;

import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import org.graphstream.graph.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.*;
import org.graphstream.graph.implementations.AbstractEdge;
import org.graphstream.graph.implementations.DefaultGraph;

public class Reverse {

    private MultiGraph reversedGraph;
    private MultiGraph graph;

    private String startPoint;
    private ArrayList<String> acceptState;

    //souce: https://cs.stackexchange.com/questions/39622/designing-a-dfa-and-the-reverse-of-it
    public Reverse(MultiGraph graph) {
        this.graph = graph;
        this.reversedGraph = new MultiGraph(graph.getId() + "r");
        acceptState = new ArrayList<String>();

        nodes();
        edges();
    }

    private void nodes(){
        Iterator<AbstractNode> nodeIterator = graph.getNodeIterator();
        while(nodeIterator.hasNext()){
            AbstractNode node = nodeIterator.next();

            reversedGraph.addNode(node.getId());

            if(node.hasAttribute("shape")){
                String type = node.getAttribute("shape").toString();
                String newType = getNewNodeType(type);
                reversedGraph.getNode(node.getId()).setAttribute("shape", newType);

                if(type.equals("point"))
                    startPoint = node.getId();
                else if(type.equals("doublecircle")){
                    acceptState.add(node.getId());
                }
            }
        }
    }

    private void edges(){
        Iterator<AbstractEdge> iterator = graph.getEdgeIterator();
        int i = 5656;
        while(iterator.hasNext()){
            AbstractEdge e = iterator.next();
            String source = e.getNode0().getId();
            String dest = e.getNode1().getId();

            if(source.equals(startPoint)){
                for(int j = 0; j < acceptState.size(); j++){
                    reversedGraph.addEdge(String.valueOf(i), source, acceptState.get(j), true);
                    i++;
                }
                continue;
            }
            reversedGraph.addEdge(String.valueOf(i), dest, source, true);

            if(e.hasAttribute("label"))
                reversedGraph.getEdge(String.valueOf(i)).addAttribute("label", e.getAttribute("label").toString());
            i++;
        }
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
