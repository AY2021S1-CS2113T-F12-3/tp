package seedu.eduke8;

import seedu.eduke8.storage.Storage;
import seedu.eduke8.topic.TopicList;
import seedu.eduke8.ui.Ui;

public class Eduke8 {
    private static final String FILE_PATH = "data";

    private Parser parser;
    private Storage storage;
    private TopicList topicList;
    private Ui ui;

    private Eduke8(String filePath) {
        parser = new Parser();
        storage = new Storage(filePath);
        ui = new Ui();
        topicList = new TopicList(storage.load());
    }

    private void run() {
        start();
        String userInput = ui.getInputFromUser();
        parser.parseCommand(userInput);
        exit();
    }

    private void start() {
        ui.printGreetMessage();
    }

    private void exit() {
        ui.printExitMessage();
    }

    public static void main(String[] args) {
        new Eduke8(FILE_PATH).run();
    }
}
