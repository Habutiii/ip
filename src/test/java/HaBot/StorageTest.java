package habot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("Storage: load/save round-trip and file creation")
class StorageTest {

    @Test
    @DisplayName("Creates file on construction and loads empty when new")
    void createsFileAndLoadsEmpty(@TempDir Path tmp) {
        Path f = tmp.resolve("tasks.txt");
        assertFalse(f.toFile().exists());
        Storage storage = new Storage(f.toString());
        assertTrue(f.toFile().exists());
        assertTrue(storage.load().isEmpty());
    }

    @Test
    @DisplayName("Save then load returns same lines including special chars")
    void saveThenLoadRoundtrip(@TempDir Path tmp) {
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
