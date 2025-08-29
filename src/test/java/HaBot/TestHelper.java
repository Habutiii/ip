//package habot;
//
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//
///**
// * Small helper to capture System.out during tests.
// */
//public final class TestHelper implements AutoCloseable {
//    private final PrintStream originalOut;
//    private final ByteArrayOutputStream buffer;
//
//
//    public TestHelper() {
//        this.originalOut = System.out;
//        this.buffer = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
//    }
//
//    public String getStdout() {
//
//        return buffer.toString(StandardCharsets.UTF_8);
//    }
//
//    /**
//     * Deletes the file at the given path if it exists.
//     */
//    public static void deleteFileIfExists(Path path) throws IOException {
//        Files.deleteIfExists(path);
//    }
//
//    @Override
//    public void close() {
//
//        System.setOut(originalOut);
//    }
//}
