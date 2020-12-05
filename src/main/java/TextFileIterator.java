import java.io.*;
import java.util.Iterator;
import java.util.Stack;

public class TextFileIterator implements Iterator<String> {
    private BufferedReader reader;
    private IOException lastException;
    private boolean back;
    private String line;
    private Stack<String> lines;

    public TextFileIterator(String source, boolean back) throws FileNotFoundException {
        assert source != null : "source can not be null";
        this.back = back;
        reader = new BufferedReader(new FileReader(source));
        if (!back) {
            try {
                line = reader.readLine();
            } catch (IOException e) {
                lastException = e;
            }
        } else {
            try {
                lines = new Stack<>();
                String nextLine;
                while ((nextLine = reader.readLine()) != null) {
                    lines.push(nextLine);
                }
            } catch (IOException e) {
                lastException = e;
            }
        }
    }

    public TextFileIterator(String source) throws FileNotFoundException {
        this(source, false);
    }

    @Override
    public boolean hasNext() {
        return line != null;
    }

    @Override
    public String next() {
        String lastLine = line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            lastException = e;
        }
        return lastLine;
    }

    public boolean hasPrev() {
        return !lines.empty();
    }

    public String prev() {
        return lines.pop();
    }

    public IOException getLastException() {
        return lastException;
    }
}
