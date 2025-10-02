package com.google.common.graph;

import com.google.common.base.Preconditions;
import java.util.Iterator;

/* loaded from: classes2.dex */
final class ConfigurableMutableValueGraph<N, V> extends ConfigurableValueGraph<N, V> implements MutableValueGraph<N, V> {
    ConfigurableMutableValueGraph(AbstractGraphBuilder<? super N> abstractGraphBuilder) {
        super(abstractGraphBuilder);
    }

    @Override // com.google.common.graph.MutableValueGraph
    public boolean addNode(N n) {
        Preconditions.checkNotNull(n, "node");
        if (containsNode(n)) {
            return false;
        }
        addNodeInternal(n);
        return true;
    }

    private GraphConnections<N, V> addNodeInternal(N n) {
        GraphConnections<N, V> graphConnectionsNewConnections = newConnections();
        Preconditions.checkState(this.nodeConnections.put(n, graphConnectionsNewConnections) == null);
        return graphConnectionsNewConnections;
    }

    @Override // com.google.common.graph.MutableValueGraph
    public V putEdgeValue(N n, N n2, V v) {
        Preconditions.checkNotNull(n, "nodeU");
        Preconditions.checkNotNull(n2, "nodeV");
        Preconditions.checkNotNull(v, "value");
        if (!allowsSelfLoops()) {
            Preconditions.checkArgument(!n.equals(n2), "Cannot add self-loop edge on node %s, as self-loops are not allowed. To construct a graph that allows self-loops, call allowsSelfLoops(true) on the Builder.", n);
        }
        GraphConnections<N, V> graphConnectionsAddNodeInternal = this.nodeConnections.get(n);
        if (graphConnectionsAddNodeInternal == null) {
            graphConnectionsAddNodeInternal = addNodeInternal(n);
        }
        V vAddSuccessor = graphConnectionsAddNodeInternal.addSuccessor(n2, v);
        GraphConnections<N, V> graphConnectionsAddNodeInternal2 = this.nodeConnections.get(n2);
        if (graphConnectionsAddNodeInternal2 == null) {
            graphConnectionsAddNodeInternal2 = addNodeInternal(n2);
        }
        graphConnectionsAddNodeInternal2.addPredecessor(n, v);
        if (vAddSuccessor == null) {
            long j = this.edgeCount + 1;
            this.edgeCount = j;
            Graphs.checkPositive(j);
        }
        return vAddSuccessor;
    }

    @Override // com.google.common.graph.MutableValueGraph
    public V putEdgeValue(EndpointPair<N> endpointPair, V v) {
        validateEndpoints(endpointPair);
        return putEdgeValue(endpointPair.nodeU(), endpointPair.nodeV(), v);
    }

    @Override // com.google.common.graph.MutableValueGraph
    public boolean removeNode(N n) {
        Preconditions.checkNotNull(n, "node");
        GraphConnections<N, V> graphConnections = this.nodeConnections.get(n);
        if (graphConnections == null) {
            return false;
        }
        if (allowsSelfLoops() && graphConnections.removeSuccessor(n) != null) {
            graphConnections.removePredecessor(n);
            this.edgeCount--;
        }
        Iterator<N> it = graphConnections.successors().iterator();
        while (it.hasNext()) {
            this.nodeConnections.getWithoutCaching(it.next()).removePredecessor(n);
            this.edgeCount--;
        }
        if (isDirected()) {
            Iterator<N> it2 = graphConnections.predecessors().iterator();
            while (it2.hasNext()) {
                Preconditions.checkState(this.nodeConnections.getWithoutCaching(it2.next()).removeSuccessor(n) != null);
                this.edgeCount--;
            }
        }
        this.nodeConnections.remove(n);
        Graphs.checkNonNegative(this.edgeCount);
        return true;
    }

    @Override // com.google.common.graph.MutableValueGraph
    public V removeEdge(N n, N n2) {
        Preconditions.checkNotNull(n, "nodeU");
        Preconditions.checkNotNull(n2, "nodeV");
        GraphConnections<N, V> graphConnections = this.nodeConnections.get(n);
        GraphConnections<N, V> graphConnections2 = this.nodeConnections.get(n2);
        if (graphConnections == null || graphConnections2 == null) {
            return null;
        }
        V vRemoveSuccessor = graphConnections.removeSuccessor(n2);
        if (vRemoveSuccessor != null) {
            graphConnections2.removePredecessor(n);
            long j = this.edgeCount - 1;
            this.edgeCount = j;
            Graphs.checkNonNegative(j);
        }
        return vRemoveSuccessor;
    }

    @Override // com.google.common.graph.MutableValueGraph
    public V removeEdge(EndpointPair<N> endpointPair) {
        validateEndpoints(endpointPair);
        return removeEdge(endpointPair.nodeU(), endpointPair.nodeV());
    }

    private GraphConnections<N, V> newConnections() {
        if (isDirected()) {
            return DirectedGraphConnections.of();
        }
        return UndirectedGraphConnections.of();
    }
}
