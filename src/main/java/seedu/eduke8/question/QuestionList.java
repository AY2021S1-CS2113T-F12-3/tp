package seedu.eduke8.question;

import seedu.eduke8.common.Displayable;
import seedu.eduke8.common.DisplayableList;
import seedu.eduke8.exception.Eduke8Exception;

import java.util.ArrayList;

public class QuestionList implements DisplayableList {

    private ArrayList<Displayable> questions;  // list of questions for a particular topic

    public QuestionList(ArrayList<Displayable> questions) {
        // The question list for topics must contain some questions about the topic
        assert questions.size() > 0;

        this.questions = questions;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public ArrayList<Displayable> getInnerList() {
        return questions;
    }

    @Override
    public Displayable find(String description) throws Eduke8Exception {
        for (Displayable question : questions) {
            if (description.equalsIgnoreCase(question.getDescription())) {
                return question;
            }
        }
        throw new Eduke8Exception("No such question exists, did you spell it correctly?");
    }
}
