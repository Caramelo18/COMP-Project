package faops;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSinkDOT;

import java.io.IOException;

public class DumpDot {
    Graph graph;

    public DumpDot(Graph graph) {
        this.graph = graph;
    }

    public void dumpFile(String path) {
        FileSinkDOT fs = new FileSinkDOT(true);

        try {
            fs.writeAll(graph, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
