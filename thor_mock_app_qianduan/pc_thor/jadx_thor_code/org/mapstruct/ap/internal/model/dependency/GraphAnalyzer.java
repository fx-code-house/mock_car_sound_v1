package org.mapstruct.ap.internal.model.dependency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class GraphAnalyzer {
    private final Stack<Node> currentPath;
    private final Set<List<String>> cycles;
    private int nextTraversalSequence;
    private final Map<String, Node> nodes;

    private GraphAnalyzer(Map<String, Node> map) {
        this.nextTraversalSequence = 0;
        this.nodes = map;
        this.cycles = new HashSet();
        this.currentPath = new Stack<>();
    }

    public static GraphAnalyzerBuilder builder() {
        return new GraphAnalyzerBuilder();
    }

    public static GraphAnalyzerBuilder withNode(String str, String... strArr) {
        return builder().withNode(str, strArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void analyze() {
        Iterator<Node> it = this.nodes.values().iterator();
        while (it.hasNext()) {
            depthFirstSearch(it.next());
        }
    }

    public int getTraversalSequence(String str) {
        Node node = this.nodes.get(str);
        if (node != null) {
            return node.getTraversalSequence();
        }
        return -1;
    }

    public Set<List<String>> getCycles() {
        return this.cycles;
    }

    private void depthFirstSearch(Node node) {
        if (node.isProcessed()) {
            return;
        }
        this.currentPath.push(node);
        if (node.isVisited()) {
            this.cycles.add(getCurrentCycle(node));
            this.currentPath.pop();
            return;
        }
        node.setVisited(true);
        Iterator<Node> it = node.getDescendants().iterator();
        while (it.hasNext()) {
            depthFirstSearch(it.next());
        }
        int i = this.nextTraversalSequence;
        this.nextTraversalSequence = i + 1;
        node.setTraversalSequence(i);
        this.currentPath.pop();
    }

    private List<String> getCurrentCycle(Node node) {
        ArrayList arrayList = new ArrayList();
        Iterator<Node> it = this.currentPath.iterator();
        boolean z = false;
        while (it.hasNext()) {
            Node next = it.next();
            if (!z && next.equals(node)) {
                z = true;
            }
            if (z) {
                arrayList.add(next.getName());
            }
        }
        return arrayList;
    }

    public static class GraphAnalyzerBuilder {
        private final Map<String, Node> nodes = new LinkedHashMap();

        public GraphAnalyzerBuilder withNode(String str, Set<String> set) {
            Node nodeComputeIfAbsent = this.nodes.computeIfAbsent(str, new Function() { // from class: org.mapstruct.ap.internal.model.dependency.GraphAnalyzer$GraphAnalyzerBuilder$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return new Node((String) obj);
                }
            });
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                nodeComputeIfAbsent.addDescendant(this.nodes.computeIfAbsent(it.next(), new Function() { // from class: org.mapstruct.ap.internal.model.dependency.GraphAnalyzer$GraphAnalyzerBuilder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return new Node((String) obj);
                    }
                }));
            }
            return this;
        }

        public GraphAnalyzerBuilder withNode(String str, String... strArr) {
            return withNode(str, new LinkedHashSet(Arrays.asList(strArr)));
        }

        public GraphAnalyzer build() {
            GraphAnalyzer graphAnalyzer = new GraphAnalyzer(this.nodes);
            graphAnalyzer.analyze();
            return graphAnalyzer;
        }
    }
}
