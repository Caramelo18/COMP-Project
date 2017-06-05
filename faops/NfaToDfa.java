package faops;

import java.util.Iterator;
import java.util.Vector;
import java.util.HashMap;
import org.graphstream.graph.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.*;

public class NfaToDfa {
private MultiGraph nfa;
private MultiGraph dfa;
private Vector<String> nfaInitNodes;
private Vector<String> nfaFinalNodes;
private Vector<String> alphabet;
private HashMap<String,Vector<String> > newNodes;

public NfaToDfa(MultiGraph nfa){
        this.nfa = nfa;
        this.dfa = new MultiGraph("dfa");
        this.nfaInitNodes = getInitialNodes(nfa);
        this.nfaFinalNodes = getFinalNodes(nfa);
        this.newNodes =  new HashMap<String,Vector<String> >();

        getAlphabet();
        createNodes();
        createEdges();
        for (int i = 0; i < 10 ; i++) {
          cleanGraph();
        }

        DumpDot dump = new DumpDot(dfa);
        dump.dumpFile("exitTest.dot");
}

private void createNodes(){
        Iterator<AbstractNode> nodeIterator = nfa.getNodeIterator();
        Vector<String> tempNodes = new Vector<String>();
        Vector<String> tempNodes2 = new Vector<String>();

        dfa.addNode("Start");
        dfa.getNode("Start").setAttribute("shape", "point");

        while(nodeIterator.hasNext()) {
                tempNodes = new Vector<String>();
                AbstractNode node = nodeIterator.next();

                if(node.getAttribute("shape").equals("point"))
                        continue;

                String nA = node.getId();
                tempNodes = getEmptyNodes(nA);

                String nodeId = nA;
                for (String x : tempNodes) {
                        nodeId += "-" + x;
                }

                addHash(nA,nodeId);

                for (String x : tempNodes) {
                        addHash(x,nodeId);
                }


                MultiNode newNode = new MultiNode(dfa, nodeId);
                dfa.addNode(nodeId);
        }

        for (String x : nfaFinalNodes) {
                for (String y : newNodes.get(x)) {
                        dfa.getNode(y).setAttribute("shape", "doublecircle");
                }
        }
}



private void createEdges(){
        Iterator<AbstractEdge> edgeIt = nfa.getEdgeIterator();
        int id = 221096;

        for (String x : nfaInitNodes) {
                for (String y : newNodes.get(x)) {
                        dfa.addEdge(String.valueOf(id),"Start", y,true);
                        id++;
                }
        }


        while(edgeIt.hasNext()) {
                AbstractEdge edge = edgeIt.next();
                if (edge.hasAttribute("label")) {
                        for (String x : newNodes.get(edge.getNode0().getId())) {
                                for(String y : newNodes.get(edge.getNode1().getId())) {
                                        dfa.addEdge(String.valueOf(id),x,y,true);
                                        dfa.getEdge(String.valueOf(id)).addAttribute("label",edge.getAttribute("label").toString());
                                        id++;
                                }
                        }

                        id++;
                }

        }

}

private Vector<String> getEmptyNodes(String node){
        Iterator<AbstractEdge> iterator = nfa.getEdgeIterator();
        Vector<String> ret = new Vector<String>();

        int id = 1096;

        while(iterator.hasNext()) {
                AbstractEdge edge = iterator.next();

                if (!edge.hasAttribute("label") && edge.getNode0().getId().equals(node)) {
                        String destA = edge.getNode1().getId();
                        ret.add(destA);

                        for (int i = 0; i < ret.size(); i++) {
                                Vector<String> temp = getEmptyNodes(ret.get(i));

                                for (int j = 0; j < temp.size(); j++) {
                                        if (!ret.contains(temp.get(j))) {
                                                ret.add(temp.get(j));
                                        }
                                }

                        }
                }
        }

        return ret;
}

private Vector<String> getInitialNodes(MultiGraph graph){
        Vector<String> temp = new Vector<String>();
        Vector<String> ret = new Vector<String>();

        Iterator<AbstractNode> itNode = graph.getNodeIterator();
        Iterator<AbstractEdge> itEdge = graph.getEdgeIterator();

        while(itNode.hasNext()) {
                AbstractNode node = itNode.next();

                if(node.getAttribute("shape").equals("point"))
                        temp.add(node.getId());
        }

        while(itEdge.hasNext()) {
                AbstractEdge edge = itEdge.next();

                String source = edge.getNode0().getId();
                String dest = edge.getNode1().getId();

                if (temp.contains(source)) {
                        ret.add(dest);
                }
        }

        return ret;
}

private Vector<String> getFinalNodes(MultiGraph graph){
        Vector<String> ret = new Vector<String>();

        Iterator<AbstractNode> itNode = graph.getNodeIterator();

        while(itNode.hasNext()) {
                AbstractNode node = itNode.next();

                if(node.getAttribute("shape").equals("doublecircle"))
                        ret.add(node.getId());
        }
        return ret;
}

private void addHash(String key,String value){
        if (newNodes.containsKey(key)) {
                Vector<String> tVec = newNodes.get(key);
                tVec.add(value);
                newNodes.replace(key,tVec);
        } else {
                Vector<String> tVec = new Vector<String>();
                tVec.add(value);
                newNodes.put(key,tVec);
        }
}

private void getAlphabet(){
        Iterator<AbstractEdge> edgeIterator = nfa.getEdgeIterator();
        alphabet = new Vector<String>();

        while(edgeIterator.hasNext()) {
                AbstractEdge edge = edgeIterator.next();

                String inputA;

                if (edge.hasAttribute("label")) {
                        String tempLab = edge.getAttribute("label").toString();
                        if (!alphabet.contains(tempLab)) {
                                alphabet.add(tempLab);
                        }
                }

        }
}

private void cleanGraph(){
        Iterator<AbstractNode> nodeIterator = dfa.getNodeIterator();

        while(nodeIterator.hasNext()) {
                AbstractNode node = nodeIterator.next();
                Iterator<AbstractEdge> edgeIterator = node.getEnteringEdgeIterator();
                if(!edgeIterator.hasNext()) {
                        if (node.hasAttribute("shape")) {
                                if (!node.getAttribute("shape").toString().equals("point")) {
                                        dfa.removeNode(node.getId());
                                }
                        } else {
                                dfa.removeNode(node.getId());
                        }
                }
        }
}

}
