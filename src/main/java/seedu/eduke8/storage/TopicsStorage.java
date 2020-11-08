package seedu.eduke8.storage;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import seedu.eduke8.common.Displayable;
import seedu.eduke8.exception.Eduke8Exception;
import seedu.eduke8.explanation.Explanation;
import seedu.eduke8.hint.Hint;
import seedu.eduke8.option.Option;
import seedu.eduke8.option.OptionList;
import seedu.eduke8.question.Question;
import seedu.eduke8.question.QuestionList;
import seedu.eduke8.topic.Topic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import static seedu.eduke8.exception.ExceptionMessages.ERROR_TOPICS_JSON_NOT_FOUR_OPTIONS;
import static seedu.eduke8.exception.ExceptionMessages.ERROR_TOPICS_JSON_NO_CORRECT;
import static seedu.eduke8.exception.ExceptionMessages.ERROR_TOPICS_JSON_PREFACE;
import static seedu.eduke8.exception.ExceptionMessages.ERROR_TOPICS_JSON_QUESTION;
import static seedu.eduke8.exception.ExceptionMessages.ERROR_TOPICS_JSON_TOO_MANY_CORRECT;
import static seedu.eduke8.exception.ExceptionMessages.ERROR_TOPICS_JSON_TOPIC;

public class TopicsStorage extends LocalStorage {
    private boolean wasCorrectAnswerMarked;
    private String currentQuestionDescription;
    private String currentTopicTitle;

    public TopicsStorage(String filePath) {
        super(filePath);
        wasCorrectAnswerMarked = false;
        currentQuestionDescription = "";
        currentTopicTitle = "";
    }

    /**
     * Returns topics loaded from a local file in JSON format.
     *
     * @return Topics loaded into an ArrayList.
     * @throws IOException  If the file is not found or cannot be read.
     * @throws ParseException  If the file contents cannot be parsed as a JSON.
     * @throws ClassCastException If the the nesting of arrays and objects in the JSON is wrong
     * @throws NullPointerException If the keys required are not present in the file.
     * @throws Eduke8Exception      If the file has been tampered with wrongly.
     */
    @Override
    public ArrayList<Displayable> load()
            throws Eduke8Exception, IOException, ParseException, ClassCastException, NullPointerException {
        JSONArray topicsAsJsonArray = getJsonArrayFromFile();

        ArrayList<Displayable> topicsAsObjects = new ArrayList<>();
        for (Object topic : topicsAsJsonArray) {
            topicsAsObjects.add(parseToTopicObject((JSONObject) topic));
        }

        assert topicsAsObjects.get(0) instanceof Topic;

        LOGGER.log(Level.INFO, "Topics loaded from file");

        return topicsAsObjects;
    }

    private Topic parseToTopicObject(JSONObject topic) throws Eduke8Exception {
        currentTopicTitle = ((String) topic.get(KEY_TOPIC)).replaceAll(" ", "_");

        JSONArray questionsAsJsonArray = (JSONArray) topic.get(KEY_QUESTIONS);

        ArrayList<Displayable> questionsAsObjects = new ArrayList<>();
        for (Object question : questionsAsJsonArray) {
            questionsAsObjects.add(parseToQuestionObject((JSONObject) question));
        }

        assert questionsAsObjects.get(0) instanceof Question;

        QuestionList questionList = new QuestionList(questionsAsObjects);

        return new Topic(currentTopicTitle, questionList);
    }

    private Question parseToQuestionObject(JSONObject question) throws Eduke8Exception {
        currentQuestionDescription = (String) question.get(KEY_DESCRIPTION);
        JSONArray optionsAsJsonArray = (JSONArray) question.get(KEY_OPTIONS);

        wasCorrectAnswerMarked = false;

        ArrayList<Displayable> optionsAsObjects = new ArrayList<>();
        for (Object option : optionsAsJsonArray) {
            optionsAsObjects.add(parseToOptionObject((JSONObject) option));
        }

        if (optionsAsObjects.size() != 4) {
            throw new Eduke8Exception(ERROR_TOPICS_JSON_PREFACE
                    + System.lineSeparator() + ERROR_TOPICS_JSON_QUESTION + currentQuestionDescription
                    + ERROR_TOPICS_JSON_TOPIC + currentTopicTitle
                    + ERROR_TOPICS_JSON_NOT_FOUR_OPTIONS);
        }

        if (!wasCorrectAnswerMarked) {
            throw new Eduke8Exception(ERROR_TOPICS_JSON_PREFACE
                    + System.lineSeparator() + ERROR_TOPICS_JSON_QUESTION + currentQuestionDescription
                    + ERROR_TOPICS_JSON_TOPIC + currentTopicTitle
                    + ERROR_TOPICS_JSON_NO_CORRECT);
        }

        assert optionsAsObjects.get(0) instanceof Option;

        OptionList optionList = new OptionList(optionsAsObjects);

        String hintDescription = (String) question.get(KEY_HINT);

        Hint hint = new Hint(hintDescription);

        String explanationDescription = (String) question.get(KEY_EXPLANATION);

        Explanation explanation = new Explanation(explanationDescription);

        return new Question(currentQuestionDescription, optionList, hint, explanation);
    }

    private Option parseToOptionObject(JSONObject option) throws Eduke8Exception {
        String optionDescription = (String) option.get(KEY_DESCRIPTION);
        boolean isCorrectAnswer = (boolean) option.get(KEY_CORRECT);

        Option optionAsObject = new Option(optionDescription);

        if (isCorrectAnswer) {
            if (!wasCorrectAnswerMarked) {
                optionAsObject.markAsCorrectAnswer();
                wasCorrectAnswerMarked = true;
            } else {
                throw new Eduke8Exception(ERROR_TOPICS_JSON_PREFACE
                        + System.lineSeparator() + ERROR_TOPICS_JSON_QUESTION + currentQuestionDescription
                        + ERROR_TOPICS_JSON_TOPIC + currentTopicTitle
                        + ERROR_TOPICS_JSON_TOO_MANY_CORRECT);
            }
        }

        assert optionAsObject.isCorrectAnswer() == isCorrectAnswer;

        return optionAsObject;
    }
}
