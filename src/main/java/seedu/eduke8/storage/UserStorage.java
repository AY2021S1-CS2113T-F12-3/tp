package seedu.eduke8.storage;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import seedu.eduke8.bookmark.BookmarkList;
import seedu.eduke8.common.Displayable;
import seedu.eduke8.exception.Eduke8Exception;
import seedu.eduke8.note.Note;
import seedu.eduke8.note.NoteList;
import seedu.eduke8.question.Question;
import seedu.eduke8.question.QuestionList;
import seedu.eduke8.stats.TopicalStatsCalculator;
import seedu.eduke8.topic.Topic;
import seedu.eduke8.topic.TopicList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import static seedu.eduke8.exception.ExceptionMessages.ERROR_USER_JSON_LOAD;

public class UserStorage extends LocalStorage {

    private BookmarkList bookmarkList;
    private TopicList topicList;

    public UserStorage(String filePath, TopicList topicList, BookmarkList bookmarkList) {
        super(filePath);
        this.topicList = topicList;
        this.bookmarkList = bookmarkList;
    }

    /**
     * Saves user attributes to a local file in JSON format from the topicList and bookmarkList.
     * Returns the file where data was written to.
     *
     * @return File where data was written to.
     * @throws IOException If the file is not found or cannot be written to.
     */
    @Override
    @SuppressWarnings("unchecked")
    public File save() throws IOException {
        file = super.save();

        assert file.exists();

        // Get all the questions seen before
        JSONArray topics = new JSONArray();

        for (Displayable topicObject : topicList.getInnerList()) {
            topics.add(parseToTopicJson(topicObject));
        }

        //Write JSON file
        FileWriter writer = new FileWriter(file.getAbsolutePath());
        writer.write(topics.toJSONString());
        writer.flush();

        LOGGER.log(Level.INFO, "User data saved to file");

        return file;
    }

    /**
     * Loads user attributes from a local file in JSON format into the topicList and bookmarkList.
     * Returns the topics in the topicList.
     *
     * @return Topics from the topicList in an ArrayList.
     * @throws IOException          If the file is not found or cannot be read.
     * @throws ParseException       If the file contents cannot be parsed as a JSON.
     * @throws ClassCastException   If the the nesting of arrays and objects in the JSON is wrong
     * @throws NullPointerException If the keys required are not present in the file.
     * @throws Eduke8Exception      If the file has been tampered with wrongly.
     */
    @Override
    public ArrayList<Displayable> load()
            throws IOException, ParseException, Eduke8Exception, ClassCastException, NullPointerException {
        if (!file.exists()) {
            return super.load();
        }
        assert file.exists();

        JSONArray topicsAsJsonArray = getJsonArrayFromFile();

        ArrayList<Topic> topicObjects = new ArrayList<>();
        for (Object topic : topicsAsJsonArray) {
            topicObjects.add(parseFromTopicJson((JSONObject) topic));
        }

        LOGGER.log(Level.INFO, "User data loaded from file");

        for (Topic topicObject : topicObjects) {
            if (topicObject == null) {
                throw new Eduke8Exception(ERROR_USER_JSON_LOAD);
            }
        }

        return topicList.getInnerList();
    }


    private Topic parseFromTopicJson(JSONObject topic) {
        Topic topicObject;
        JSONArray questions;
        JSONArray notes;

        try {
            String topicDescription = ((String) topic.get(KEY_TOPIC)).replaceAll(" ", "_");
            topicObject = (Topic) topicList.find(topicDescription);
            questions = (JSONArray) topic.get(KEY_QUESTIONS);
        } catch (Eduke8Exception | NullPointerException | ClassCastException e) {
            return null;
        }

        // Do this before attempting to load notes so user stats are loaded as much as possible first.
        ArrayList<Question> questionObjects = loadQuestionAttributes(questions, topicObject);

        try {
            notes = (JSONArray) topic.get(KEY_NOTES);
        } catch (NullPointerException | ClassCastException e) {
            return null;
        }

        ArrayList<Note> noteObjects = loadNotes(notes, topicObject);

        for (Question questionObject : questionObjects) {
            if (questionObject == null) {
                return null;
            }
        }

        for (Note noteObject : noteObjects) {
            if (noteObject == null) {
                return null;
            }
        }

        return topicObject;
    }

    private ArrayList<Note> loadNotes(JSONArray notes, Topic topicObject) {
        NoteList noteList = topicObject.getNoteList();
        ArrayList<Note> noteObjects = new ArrayList<>();
        for (Object note : notes) {
            noteObjects.add(parseFromNoteJson((JSONObject) note, noteList));
        }

        return noteObjects;
    }

    private Note parseFromNoteJson(JSONObject note, NoteList noteList) {
        String noteDescription;
        String text;
        try {
            noteDescription = (String) note.get(KEY_DESCRIPTION);
            text = (String) note.get(KEY_TEXT);
        } catch (NullPointerException | ClassCastException e) {
            return null;
        }
        Note noteObject = new Note(noteDescription, text);
        noteList.add(noteObject);
        return noteObject;
    }

    private ArrayList<Question> loadQuestionAttributes(JSONArray questions, Topic topicObject) {
        QuestionList questionList = topicObject.getQuestionList();
        ArrayList<Question> questionObjects = new ArrayList<>();
        for (Object question : questions) {
            questionObjects.add(parseFromQuestionJson(questionList, (JSONObject) question));
        }
        return questionObjects;
    }

    private Question parseFromQuestionJson(QuestionList questionList, JSONObject question) {
        Question questionObject;
        try {
            String questionDescription = (String) question.get(KEY_DESCRIPTION);
            questionObject = (Question) questionList.find(questionDescription);
            questionObject.markAsShown();
            if ((boolean) question.get(KEY_CORRECT)) {
                questionObject.markAsAnsweredCorrectly();
            }
            if ((boolean) question.get(KEY_BOOKMARKED)) {
                bookmarkList.add(questionObject);
            }
            if ((boolean) question.get(KEY_HINT)) {
                questionObject.getHint().markAsShown();
            }
        } catch (NullPointerException | ClassCastException e) {
            return null;
        }
        return questionObject;
    }

    @SuppressWarnings("unchecked")
    private JSONObject parseToTopicJson(Displayable topicObject) {
        JSONObject topic = new JSONObject();
        topic.put(KEY_TOPIC, topicObject.getDescription());

        JSONArray questions = getQuestionsJsonArray((Topic) topicObject);
        topic.put(KEY_QUESTIONS, questions);

        JSONArray notes = getNotesJsonArray(((Topic) topicObject).getNoteList());
        topic.put(KEY_NOTES, notes);
        return topic;
    }

    @SuppressWarnings("unchecked")
    private JSONArray getNotesJsonArray(NoteList noteList) {
        JSONArray notes = new JSONArray();
        for (Displayable noteObject : noteList.getInnerList()) {
            JSONObject note = parseToNoteJson((Note) noteObject);
            notes.add(note);
        }
        return notes;
    }

    @SuppressWarnings("unchecked")
    private JSONObject parseToNoteJson(Note noteObject) {
        JSONObject note = new JSONObject();

        note.put(KEY_DESCRIPTION, noteObject.getDescription());
        note.put(KEY_TEXT, noteObject.getNoteText());

        return note;
    }


    @SuppressWarnings("unchecked")
    private JSONArray getQuestionsJsonArray(Topic topicObject) {
        JSONArray questions = new JSONArray();
        TopicalStatsCalculator topicalStatsCalculator = new TopicalStatsCalculator(topicObject);
        ArrayList<Displayable> attemptedQuestions = topicalStatsCalculator.getTopicalAttemptedQuestions();
        for (Displayable questionObject : attemptedQuestions) {
            JSONObject question = parseToQuestionJson((Question) questionObject);
            questions.add(question);
        }
        return questions;
    }

    @SuppressWarnings("unchecked")
    private JSONObject parseToQuestionJson(Question questionObject) {
        JSONObject question = new JSONObject();

        question.put(KEY_DESCRIPTION, questionObject.getDescription());
        question.put(KEY_CORRECT, questionObject.wasAnsweredCorrectly());
        question.put(KEY_BOOKMARKED, bookmarkList.find(questionObject.getDescription()) != null);
        question.put(KEY_HINT, questionObject.wasHintShown());

        return question;
    }


}
