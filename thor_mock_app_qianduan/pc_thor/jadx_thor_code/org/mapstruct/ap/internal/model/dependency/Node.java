package org.mapstruct.ap.internal.model.dependency;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
class Node {
    private final String name;
    private boolean visited;
    private int traversalSequence = -1;
    private final List<Node> descendants = new ArrayList();

    Node(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void setVisited(boolean z) {
        this.visited = z;
    }

    public boolean isProcessed() {
        return this.traversalSequence >= 0;
    }

    public int getTraversalSequence() {
        return this.traversalSequence;
    }

    public void setTraversalSequence(int i) {
        this.traversalSequence = i;
    }

    public void addDescendant(Node node) {
        this.descendants.add(node);
    }

    public List<Node> getDescendants() {
        return this.descendants;
    }

    public String toString() {
        return this.name;
    }

    public int hashCode() {
        String str = this.name;
        return 31 + (str == null ? 0 : str.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && Objects.equals(this.name, ((Node) obj).name);
    }
}
