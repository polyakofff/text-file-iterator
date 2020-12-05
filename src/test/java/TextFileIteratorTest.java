import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TextFileIteratorTest {

    @Test
    void iterateForward() {
        try {
            String[] lines = new String[]{"hello", "world", "this", "is", "", "java"};

            String text = String.join("\n", Arrays.stream(lines).collect(Collectors.toList()));
            Path tempFile = Files.createTempFile("test", ".txt");
            Files.write(tempFile, text.getBytes());

            boolean ok = true;
            int i = 0;
            for (TextFileIterator iterator = new TextFileIterator(tempFile.toAbsolutePath().toString(), false); iterator.hasNext();) {
                String line = iterator.next();
                ok &= line.equals(lines[i]);
                i++;
            }
            ok &= i == lines.length;

            Assertions.assertTrue(ok);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void iterateBack() {
        try {
            String[] lines = new String[]{"hello", "world", "this", "is", "", "java"};

            String text = String.join("\n", Arrays.stream(lines).collect(Collectors.toList()));
            Path tempFile = Files.createTempFile("test1", ".txt");
            Files.write(tempFile, text.getBytes());

            boolean ok = true;
            int i = lines.length - 1;
            for (TextFileIterator iterator = new TextFileIterator(tempFile.toAbsolutePath().toString(), true); iterator.hasPrev();) {
                String line = iterator.prev();
                ok &= line.equals(lines[i]);
                i--;
            }
            ok &= i == -1;

            Assertions.assertTrue(ok);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
