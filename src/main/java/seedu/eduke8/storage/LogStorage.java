package seedu.eduke8.storage;

import org.json.simple.parser.ParseException;
import seedu.eduke8.common.Displayable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.SimpleFormatter;

public class LogStorage extends LocalStorage {
    public LogStorage(String filePath) {
        super(filePath);
    }

    // Set up save to file inside of print to console
    @Override
    public File save() throws IOException {
        LogManager.getLogManager().reset();
        LOGGER.setLevel(Level.ALL);

        // Only log severe to console
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.SEVERE);
        LOGGER.addHandler(ch);

        // Log all to the file
        File logFile = createFileIfNotExists();

        FileHandler fh = new FileHandler(logFile.getAbsolutePath());
        fh.setFormatter(new SimpleFormatter());
        fh.setLevel(Level.ALL);
        LOGGER.addHandler(fh);

        LOGGER.log(Level.INFO, "Logging to file started");

        return logFile;
    }

    @Override
    public ArrayList<Displayable> load() throws IOException, ParseException {
        return null;
    }
}
