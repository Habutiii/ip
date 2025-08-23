package HaBot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Storage: load/save round-trip and file creation")
class StorageTest {

    @Test
    @DisplayName("Creates file on construction and loads empty when new")
    void creates_file_and_loads_empty(@TempDir Path tmp) {
        Path f = tmp.resolve("tasks.txt");
        assertFalse(f.toFile().exists());
        Storage storage = new Storage(f.toString());
        assertTrue(f.toFile().exists());
        assertTrue(storage.load().isEmpty());
    }

    @Test
    @DisplayName("Save then load returns same lines including special chars")
    void save_then_load_roundtrip(@TempDir Path tmp) {
        Storage storage = new Storage(tmp.resolve("tasks.txt").toString());
        List<String> lines = List.of(
                "T| |alpha \\| beta",
                "D|X|submit report|2019-12-02T18:00"
        );
        storage.save(lines);
        List<String> loaded = storage.load();
        assertEquals(lines, loaded);
    }
}
