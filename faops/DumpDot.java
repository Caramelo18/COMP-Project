package faops;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.*;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSinkDOT;

import java.io.IOException;

public class DumpDot {
    MultiGraph graph;

    public DumpDot(MultiGraph graph) {
        this.graph = graph;
        this.graph.addAttribute("rankdir", "LR");
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
