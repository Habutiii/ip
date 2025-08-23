package HaBot;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Small helper to capture System.out during tests.
 */
public final class TestHelper implements AutoCloseable {
    private final PrintStream originalOut;
    private final ByteArrayOutputStream buffer;

    public TestHelper() {
        this.originalOut = System.out;
        this.buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
    }

    public String getStdout() {
        return buffer.toString(StandardCharsets.UTF_8);
    }

    @Override
    public void close() {
        System.setOut(originalOut);
    }
}

