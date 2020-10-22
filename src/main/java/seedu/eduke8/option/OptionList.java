package seedu.eduke8.option;

import seedu.eduke8.common.Displayable;
import seedu.eduke8.common.DisplayableList;
import seedu.eduke8.exception.Eduke8Exception;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OptionList implements DisplayableList {
    private final ArrayList<Displayable> options;
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String NO_RIGHT_ANSWER_ERROR = "Error with question: No right answer specified";

    public OptionList(ArrayList<Displayable> options) {
        this.options = options;
    }

    @Override
    public ArrayList<Displayable> getInnerList() {
        return this.options;
    }

    @Override
    public Displayable find(String description) throws Eduke8Exception {
        for (Displayable option : options) {
            if (description.equals(option.getDescription())) {
                return option;
            }
        }
        throw new Eduke8Exception("No such option exists, did you spell it correctly?");
    }

    public int findCorrectOptionIndex() throws Eduke8Exception {
        for (int i = 0; i < getCount(); i++) {
            if (((Option) options.get(i)).isCorrectAnswer()) {
                return i;
            }
        }

        LOGGER.log(Level.WARNING, NO_RIGHT_ANSWER_ERROR);
        throw new Eduke8Exception(NO_RIGHT_ANSWER_ERROR);
    }

    @Override
    public int getCount() {
        return options.size();
    }
}
