package seedu.eduke8.parser;

import seedu.eduke8.command.Command;
import seedu.eduke8.common.DisplayableList;

public interface Parser {
    Command parseCommand(DisplayableList displayableList, String userInput);
}
