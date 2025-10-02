package org.mapstruct.ap.spi;

import java.util.Collection;
import javax.lang.model.element.ExecutableElement;

/* loaded from: classes3.dex */
public class BuilderInfo {
    private final Collection<ExecutableElement> buildMethods;
    private final ExecutableElement builderCreationMethod;

    private BuilderInfo(ExecutableElement executableElement, Collection<ExecutableElement> collection) {
        this.builderCreationMethod = executableElement;
        this.buildMethods = collection;
    }

    public ExecutableElement getBuilderCreationMethod() {
        return this.builderCreationMethod;
    }

    public Collection<ExecutableElement> getBuildMethods() {
        return this.buildMethods;
    }

    public static class Builder {
        private Collection<ExecutableElement> buildMethods;
        private ExecutableElement builderCreationMethod;

        public Builder builderCreationMethod(ExecutableElement executableElement) {
            this.builderCreationMethod = executableElement;
            return this;
        }

        public Builder buildMethod(Collection<ExecutableElement> collection) {
            this.buildMethods = collection;
            return this;
        }

        public BuilderInfo build() {
            if (this.builderCreationMethod == null) {
                throw new IllegalArgumentException("Builder creation method is mandatory");
            }
            Collection<ExecutableElement> collection = this.buildMethods;
            if (collection == null) {
                throw new IllegalArgumentException("Build methods are mandatory");
            }
            if (collection.isEmpty()) {
                throw new IllegalArgumentException("Build methods must not be empty");
            }
            return new BuilderInfo(this.builderCreationMethod, this.buildMethods);
        }
    }
}
