package org.mapstruct.ap.internal.writer;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

/* loaded from: classes3.dex */
class IndentationCorrectingWriter extends Writer {
    private static final boolean DEBUG = false;
    private final StateContext context;
    private State currentState;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final boolean IS_WINDOWS = System.getProperty("os.name").startsWith("Windows");

    IndentationCorrectingWriter(Writer writer) {
        super(writer);
        this.currentState = State.START_OF_LINE;
        this.context = new StateContext(writer);
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) throws IOException {
        this.context.reset(cArr, i);
        while (i < i2) {
            State stateHandleCharacter = this.currentState.handleCharacter(cArr[i], this.context);
            State state = this.currentState;
            if (stateHandleCharacter != state) {
                state.onExit(this.context, stateHandleCharacter);
                stateHandleCharacter.onEntry(this.context);
                this.currentState = stateHandleCharacter;
            }
            this.context.currentIndex++;
            i++;
        }
        this.currentState.onBufferFinished(this.context);
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() throws IOException {
        this.context.writer.flush();
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.currentState.onExit(this.context, null);
        this.context.writer.close();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isWindows() {
        return IS_WINDOWS;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static char[] getIndentation(int i) {
        char[] cArr = new char[i * 4];
        Arrays.fill(cArr, ' ');
        return cArr;
    }

    private enum State {
        START_OF_LINE { // from class: org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State.1
            @Override // org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State
            State doHandleCharacter(char c, StateContext stateContext) {
                if (c == '\n') {
                    return AFTER_LINE_BREAK;
                }
                if (c == '\r') {
                    return IndentationCorrectingWriter.isWindows() ? IN_LINE_BREAK : AFTER_LINE_BREAK;
                }
                if (c != '\"') {
                    if (c != '{') {
                        if (c != '}') {
                            if (c != '(') {
                                if (c != ')') {
                                    return IN_TEXT;
                                }
                            }
                        }
                        stateContext.decrementIndentationLevel();
                        return IN_TEXT;
                    }
                    stateContext.incrementIndentationLevel();
                    return IN_TEXT;
                }
                return IN_STRING;
            }

            @Override // org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State
            void doOnEntry(StateContext stateContext) throws IOException {
                stateContext.writer.write(IndentationCorrectingWriter.getIndentation(stateContext.getIndentationLevel()));
            }

            @Override // org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State
            void onExit(StateContext stateContext, State state) throws IOException {
                flush(stateContext);
            }

            @Override // org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State
            void onBufferFinished(StateContext stateContext) throws IOException {
                flush(stateContext);
            }
        },
        IN_TEXT { // from class: org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State.2
            @Override // org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State
            State doHandleCharacter(char c, StateContext stateContext) {
                if (c == '\n') {
                    return AFTER_LINE_BREAK;
                }
                if (c == '\r') {
                    return IndentationCorrectingWriter.isWindows() ? IN_LINE_BREAK : AFTER_LINE_BREAK;
                }
                if (c != '\"') {
                    if (c != '{') {
                        if (c != '}') {
                            if (c != '(') {
                                if (c != ')') {
                                    return IN_TEXT;
                                }
                            }
                        }
                        stateContext.decrementIndentationLevel();
                        return IN_TEXT;
                    }
                    stateContext.incrementIndentationLevel();
                    return IN_TEXT;
                }
                return IN_STRING;
            }

            @Override // org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State
            void onExit(StateContext stateContext, State state) throws IOException {
                flush(stateContext);
            }

            @Override // org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State
            void onBufferFinished(StateContext stateContext) throws IOException {
                flush(stateContext);
            }
        },
        IN_STRING { // from class: org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State.3
            @Override // org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State
            State doHandleCharacter(char c, StateContext stateContext) {
                if (c == '\"') {
                    return IN_TEXT;
                }
                if (c == '\\') {
                    return IN_STRING_ESCAPED_CHAR;
                }
                return IN_STRING;
            }

            @Override // org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State
            void onExit(StateContext stateContext, State state) throws IOException {
                flush(stateContext);
            }

            @Override // org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State
            void onBufferFinished(StateContext stateContext) throws IOException {
                flush(stateContext);
            }
        },
        IN_STRING_ESCAPED_CHAR { // from class: org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State.4
            @Override // org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State
            State doHandleCharacter(char c, StateContext stateContext) {
                return IN_STRING;
            }

            @Override // org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State
            void onExit(StateContext stateContext, State state) throws IOException {
                flush(stateContext);
            }

            @Override // org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State
            void onBufferFinished(StateContext stateContext) throws IOException {
                flush(stateContext);
            }
        },
        IN_LINE_BREAK { // from class: org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State.5
            @Override // org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State
            State doHandleCharacter(char c, StateContext stateContext) {
                if (c == '\n') {
                    return AFTER_LINE_BREAK;
                }
                throw new IllegalArgumentException("Unexpected character: " + c);
            }
        },
        AFTER_LINE_BREAK { // from class: org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State.6
            @Override // org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State
            State doHandleCharacter(char c, StateContext stateContext) {
                if (c == '\n') {
                    stateContext.consecutiveLineBreaks++;
                    return AFTER_LINE_BREAK;
                }
                if (c == '\r') {
                    return IndentationCorrectingWriter.isWindows() ? IN_LINE_BREAK : AFTER_LINE_BREAK;
                }
                if (c != ' ') {
                    if (c != '{') {
                        if (c != '}') {
                            if (c != '(') {
                                if (c != ')') {
                                    return START_OF_LINE;
                                }
                            }
                        } else if (stateContext.consecutiveLineBreaks > 0) {
                            stateContext.consecutiveLineBreaks = 0;
                        }
                        stateContext.decrementIndentationLevel();
                        return START_OF_LINE;
                    }
                    stateContext.incrementIndentationLevel();
                    return START_OF_LINE;
                }
                return AFTER_LINE_BREAK;
            }

            @Override // org.mapstruct.ap.internal.writer.IndentationCorrectingWriter.State
            void onExit(StateContext stateContext, State state) throws IOException {
                stateContext.consecutiveLineBreaks++;
                if (state != IN_LINE_BREAK) {
                    int iMin = Math.min(stateContext.consecutiveLineBreaks, 2);
                    for (int i = 0; i < iMin; i++) {
                        stateContext.writer.append((CharSequence) IndentationCorrectingWriter.LINE_SEPARATOR);
                    }
                    stateContext.consecutiveLineBreaks = 0;
                }
            }
        };

        abstract State doHandleCharacter(char c, StateContext stateContext) throws IOException;

        void doOnEntry(StateContext stateContext) throws IOException {
        }

        void onBufferFinished(StateContext stateContext) throws IOException {
        }

        void onExit(StateContext stateContext, State state) throws IOException {
        }

        final State handleCharacter(char c, StateContext stateContext) throws IOException {
            return doHandleCharacter(c, stateContext);
        }

        final void onEntry(StateContext stateContext) throws IOException {
            stateContext.lastStateChange = stateContext.currentIndex;
            doOnEntry(stateContext);
        }

        protected void flush(StateContext stateContext) throws IOException {
            if (stateContext.characters == null || stateContext.currentIndex - stateContext.lastStateChange <= 0) {
                return;
            }
            stateContext.writer.write(stateContext.characters, stateContext.lastStateChange, stateContext.currentIndex - stateContext.lastStateChange);
        }
    }

    private static class StateContext {
        char[] characters;
        int consecutiveLineBreaks;
        int currentIndex;
        private int indentationLevel;
        int lastStateChange;
        final Writer writer;

        StateContext(Writer writer) {
            this.writer = writer;
        }

        void reset(char[] cArr, int i) {
            this.characters = cArr;
            this.lastStateChange = i;
            this.currentIndex = 0;
        }

        void incrementIndentationLevel() {
            this.indentationLevel++;
        }

        void decrementIndentationLevel() {
            int i = this.indentationLevel;
            if (i > 0) {
                this.indentationLevel = i - 1;
            }
        }

        int getIndentationLevel() {
            return this.indentationLevel;
        }
    }
}
