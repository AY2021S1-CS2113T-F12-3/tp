package seedu.eduke8.ui;

import seedu.eduke8.bookmark.BookmarkList;
import seedu.eduke8.common.Displayable;
import seedu.eduke8.exception.Eduke8Exception;
import seedu.eduke8.hint.Hint;
import seedu.eduke8.note.Note;
import seedu.eduke8.note.NoteList;
import seedu.eduke8.option.Option;
import seedu.eduke8.question.Question;
import seedu.eduke8.topic.Topic;
import seedu.eduke8.topic.TopicList;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Represents the UI class.
 */
public class Ui {
    private static final int LAST_OPTION = 4;
    private static final int CONVERSION_FROM_SECONDS_TO_MILLIS = 1000;
    private static final String TEXTBOOK_WEBSITE =
            "https://nus-cs2113-ay2021s1.github.io/website/se-book-adapted/index.html";

    private static final String LOGO = " _____        _____" + System.lineSeparator()
            + "|  ___| ____ |  _  |" + System.lineSeparator()
            + "| |___ |  _ \\| |_| |" + System.lineSeparator()
            + "|  ___|| | | |  _  |" + System.lineSeparator()
            + "| |___ | |_| | |_| |" + System.lineSeparator()
            + "|_____||____/|_____|";

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String HORIZONTAL_LINE = "-------------------------------------------------------------------"
            + "-----------------------------------------------------";
    private static final String HORIZONTAL_LINE_FOR_TOPICAL_STATS_FIELDS = "---------------"
            + "---------------------------  ";
    private static final String MESSAGE_ABOUT = "E-Duke-8 is a desktop app that helps CS2113/T students learn and"
            + System.lineSeparator() + "understand software engineering and OOP principles through a"
            + System.lineSeparator() + "gamified platform and enhances their " + "learning experience."
            + System.lineSeparator()
            + System.lineSeparator() + "You can attempt bite-sized quizzes based on your choice of topic"
            + System.lineSeparator() + "and access key concepts for easy revision!";
    private static final String MESSAGE_GREETINGS = "Hello! I'm E-Duke-8!" + System.lineSeparator()
            + "What would you like to learn today?";
    private static final String MESSAGE_EXIT = "Bye bye. Hope you had a fruitful revision and see you soon!";
    private static final String MESSAGE_HELP = "These are the commands that you can use:"
            + System.lineSeparator() + "1) about"
            + System.lineSeparator() + "2) help"
            + System.lineSeparator() + "3) topics"
            + System.lineSeparator() + "4) textbook"
            + System.lineSeparator() + "5) quiz t/<topic> n/<number of questions> s/<time given to complete 1 question>"
            + System.lineSeparator() + "6) bookmark list / bookmark delete"
            + System.lineSeparator() + "7) stats"
            + System.lineSeparator() + "8) note add / note delete / note list"
            + System.lineSeparator() + "9) exit (saves your data too!)";
    private static final String MESSAGE_QUIZ_START = "Start of quiz:";
    private static final String MESSAGE_QUIZ_END = "This is the end of the quiz!"
            + System.lineSeparator() + "Hope you have learnt something new!"
            + System.lineSeparator() + "You can view how well you have done using the 'stats' command!";
    private static final String MESSAGE_ANSWER_WRONG = "Oops! The correct answer is ";
    private static final String MESSAGE_ANSWER_WRONG_SECOND = "! Do visit the textbook to read up more.";
    private static final String MESSAGE_ANSWER_CORRECT = "Great Job! That is the correct answer! Keep it up!";
    private static final String MESSAGE_TEXTBOOK = "The textbook for this module is available at:"
            + System.lineSeparator() + TEXTBOOK_WEBSITE;
    private static final String MESSAGE_HINT = "Hint: ";
    private static final String POINT_SYSTEM_RULE = "Point system in E-Duke-8: For correct answers, "
            + "you earn 2 points if you did not request for hint, "
            + System.lineSeparator() + "and 1 point if you did. No point is awarded for wrong answers.";
    private static final String MESSAGE_GET_INPUT_FROM_USER = "Enter your command or 'help': ";
    private static final String MESSAGE_GET_INPUT_FROM_USER_QUIZ = "Enter your answer, 'hint' or 'bookmark': ";
    private static final String MESSAGE_PRINT_TOPIC_LIST = "These are the available topics and the number of "
            + "available questions in each:";
    private static final String MESSAGE_EXPLANATION = "Explanation:";
    private static final String MESSAGE_PRINT_NOTE_LIST_NONE = "There are no notes for this topic!";
    private static final String MESSAGE_PRINT_NOTE_LIST = "These are the notes for this topic: ";
    private static final String MESSAGE_QUIZ_TOPIC_CHOSEN = "The chosen topic is: ";
    private static final String MESSAGE_QUIZ_QUESTION_CHOSEN = "You have chosen to complete ";
    private static final String MESSAGE_QUIZ_QUESTION_CHOSEN_SECOND = " question";
    private static final String MESSAGE_BOOKMARK_INDICATOR = "Bookmarked this question!";
    private static final String MESSAGE_BOOKMARK_LIST = "This is your list of bookmarks: ";
    private static final String MESSAGE_BOOKMARKED_ALREADY_INDICATOR = "This has already been bookmarked.";
    private static final String MESSAGE_BOOKMARK_DELETED_PART_ONE = "Bookmark number ";
    private static final String MESSAGE_BOOKMARK_DELETED_PART_TWO = " will be deleted right away!";
    private static final String MESSAGE_BOOKMARK_CORRECT_ANS_INDICATOR = " [Correct Answer] ";
    private static final String CLOSE_BRACKET = ") ";
    private static final String OPEN_SQUARE_BRACKET = "[";
    private static final String CLOSE_SQUARE_BRACKET = "] ";
    private static final String DOT = ".";
    private static final String DOT_SPACE = ". ";
    private static final String DOT_PLURAL = "s.";
    private static final String ADD_NOTE_PROMPT_FOR_TOPIC = "Enter the topic you would like to add a note to:";
    private static final String SPACE = " ";
    private static final String ADD_NOTE_PROMPT_FOR_NOTE_TITLE = "Enter a suitable title for your note";
    private static final String ADD_NOTE_PROMPT_FOR_NOTE_BODY = "Enter the contents of your note:";
    private static final String ADD_NOTE_SUCCESSFULLY = "Your note has been added!";
    private static final String ADD_NOTE_UNSUCCESSFULLY = "Your note was not added successfully."
            + " Please try again!";
    private static final String INVALID_TOPIC_NAME = "Please enter a valid topic name";
    private static final String DELETE_NOTE_PROMPT_FOR_TOPIC = "Which topic does the note you would like to delete"
            + " belong to?";
    private static final String DELETE_NOTE_PROMPT_FOR_INDEX = "What is the index of the note that you would like"
            + " to delete?";
    private static final String DELETE_NOTE_SUCCESSFULLY = "The note has been deleted!";
    private static final String DELETE_NOTE_UNSUCCESSFULLY = "The note was not deleted successfully. Try again!";
    private static final String INVALID_TOPIC = "Please enter a valid topic name";
    private static final String INVALID_TOPIC_INDEX = "Please enter a valid topic index";
    private static final String LIST_NOTE_PROMPT = "Which topic's notes would you like to view?";
    private static final String INPUT_ERROR = "Please provide a valid topic!";
    private static final String MESSAGE_SHOW_POINTS = "You have earned ";
    private static final String MESSAGE_SHOW_POINTS_SECOND = " points out of a total of ";
    private static final String MESSAGE_SHOW_POINTS_THIRD = " points available!";
    private static final String MESSAGE_USER_PROGRESS = "Therefore, your E-Duke-8 progress is ";
    private static final String MESSAGE_PRAISE = "Good job! Keep it up!";
    private static final String MESSAGE_MOTIVATE = "Let's do some quizzes!";
    private static final String TOPICS_PROGRESSION_SECTION_HEADER = "Topics' Progression";
    private static final String OUT_OF_SYMBOL = "/";
    private static final String MESSAGE_QUESTIONS_DONE = " questions done";
    private static final String MESSAGE_CORRECT_QUESTIONS_ANSWERED = " questions correctly answered";
    private static final String MESSAGE_HINT_USED_SINGULAR = " hint used";
    private static final String MESSAGE_HINT_USED_PLURAL = " hints used";
    private static final String MESSAGE_POINTS_EARNED_OUT_OF = " points earned / ";
    private static final String PERCENTAGE_SIGN = "%";
    private static final String MESSAGE_AVAILABLE_WORD = " available ";
    private static final String MESSAGE_ANSWER_INCOMPLETE = "The correct answer is ";
    private static final String MESSAGE_INCOMPLETE_ANSWER_TIMER = "Oops! You took more than ";
    private static final String MESSAGE_INCOMPLETE_ANSWER_TIMER_SECOND = " seconds to answer! ";
    private static final String DATA_LOADING = "Please wait while data is loading...";
    private static final String DATA_LOADED = "Data loaded successfully!";
    private static final String DATA_SAVING = "Please wait while data is saving...";
    private static final String DATA_SAVED = "Data saved successfully!";
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(1);
    public static final String ERROR_READING_INPUT = "Error reading input.";
    public static final String ERROR_USING_ROBOT = "Error using robot to enter key";
    public static final String MESSAGE_QUIZ_TO_PROCEED = "Press 'Enter' to proceed!";
    public static final String OS_NAME = "os.name";
    public static final String OS_LINUX = "nux";
    public static final String OS_MAC = "mac";
    public static final String NUMBERS_ONLY = "[0-9]+";
    public static final String EMPTY = "";

    private static String operatingSystem = null;

    public String getInputFromUser() {
        System.out.print(MESSAGE_GET_INPUT_FROM_USER);
        Future<String> userInputFuture = EXECUTOR_SERVICE.submit(SCANNER::nextLine);
        try {
            return userInputFuture.get().trim();
        } catch (InterruptedException | ExecutionException e) {
            printError(ERROR_READING_INPUT);
        }
        return SCANNER.nextLine().trim();
    }

    public void printQuizInputMessage() {
        System.out.print(MESSAGE_GET_INPUT_FROM_USER_QUIZ);
    }

    public boolean getEnterFromUser() {
        boolean enterIsUsed = false;

        System.out.print(MESSAGE_QUIZ_TO_PROCEED);

        String userInput;
        Future<String> userInputFuture = EXECUTOR_SERVICE.submit(SCANNER::nextLine);
        try {
            userInput = userInputFuture.get();
            if (userInput.isEmpty()) {
                enterIsUsed = true;
            }
        } catch (InterruptedException | ExecutionException e) {
            printError(ERROR_READING_INPUT);
        }

        return enterIsUsed;
    }

    private static String findUserOperatingSystem() {
        if (operatingSystem == null) {
            operatingSystem = System.getProperty(OS_NAME).toLowerCase();
        }
        return operatingSystem;
    }

    public String getQuizInputFromUser(int timeLeft) throws IOException {

        operatingSystem = findUserOperatingSystem();

        //This is for Linus and Mac operating systems because Robot doesn't work on WSL
        if (operatingSystem.contains(OS_LINUX) || operatingSystem.contains(OS_MAC)) {
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            long startingTime = System.currentTimeMillis();

            while (((System.currentTimeMillis() - startingTime) < timeLeft * CONVERSION_FROM_SECONDS_TO_MILLIS)
                    && System.in.available() == 0) {
            }

            if (userInput.ready()) {
                return userInput.readLine().trim();
            } else {
                return null;
            }

            //This is for the Windows operating system
        } else {
            String userInput;
            Future<String> userInputFuture = EXECUTOR_SERVICE.submit(SCANNER::nextLine);
            try {
                userInput = userInputFuture.get(timeLeft, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | IllegalArgumentException | TimeoutException e) {
                try {
                    Robot robot = new Robot();
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                } catch (AWTException awtException) {
                    printError(ERROR_USING_ROBOT);
                }
                return null;
            }

            return userInput.trim();
        }
    }

    private static void printMessage(String message) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(message);
        System.out.println(HORIZONTAL_LINE);
    }

    public void printWithoutLines(String message) {
        System.out.println(message);
    }

    public void printGreetMessage() {
        System.out.println(LOGO);
        printMessage(MESSAGE_GREETINGS);
    }

    public void printExitMessage() {
        printMessage(MESSAGE_EXIT);
    }

    public void printOption(Option option, int optionNumber) {
        System.out.println(optionNumber + CLOSE_BRACKET + option.getDescription());
        if (optionNumber == LAST_OPTION) {
            System.out.println(HORIZONTAL_LINE);
        }
    }

    public void printQuestion(Question question, int questionNumber) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(questionNumber + DOT_SPACE + question.getDescription() + System.lineSeparator());
    }

    public void printHint(Hint hint) {
        printMessage(MESSAGE_HINT + hint.getDescription());
    }

    public void printStartQuizPage(int numberOfQuestionsChosen, String topicsChosen) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(MESSAGE_QUIZ_START);

        //Shows the number of questions chosen by user
        printStartQuizQuestions(numberOfQuestionsChosen);

        //Shows the topics chosen by user
        System.out.println(MESSAGE_QUIZ_TOPIC_CHOSEN + topicsChosen);

        System.out.println(HORIZONTAL_LINE);
    }

    public void printEndQuizPage() {
        printMessage(MESSAGE_QUIZ_END);
    }

    public void printAnswerIsWrong(int correctAnswer, String explanation) {
        printMessage(MESSAGE_ANSWER_WRONG + correctAnswer + MESSAGE_ANSWER_WRONG_SECOND + System.lineSeparator()
                + System.lineSeparator() + MESSAGE_EXPLANATION + System.lineSeparator() + explanation);
        System.out.println(HORIZONTAL_LINE);
    }

    public void printAnswerIsCorrect(String explanation) {
        printMessage(MESSAGE_ANSWER_CORRECT + System.lineSeparator() + System.lineSeparator() + MESSAGE_EXPLANATION
                + System.lineSeparator() + explanation);
        System.out.println(HORIZONTAL_LINE);
    }

    public void printIncompleteAnswer(int correctAnswer, String explanation, int userTimer) {
        System.out.println();
        printMessage(MESSAGE_INCOMPLETE_ANSWER_TIMER + userTimer + MESSAGE_INCOMPLETE_ANSWER_TIMER_SECOND
                + MESSAGE_ANSWER_INCOMPLETE + correctAnswer + MESSAGE_ANSWER_WRONG_SECOND + System.lineSeparator()
                + System.lineSeparator() + MESSAGE_EXPLANATION + System.lineSeparator() + explanation);
        System.out.println(HORIZONTAL_LINE);
    }

    public void printHelp() {
        printMessage(MESSAGE_HELP);
    }

    public void printAbout() {
        printMessage(MESSAGE_ABOUT);
    }

    public void printTextbook() {
        printMessage(MESSAGE_TEXTBOOK);
    }

    public void printError(String errorMessage) {
        printMessage(errorMessage);
    }

    public void printTopicList(ArrayList<Displayable> topics) {
        String topicListString = MESSAGE_PRINT_TOPIC_LIST + System.lineSeparator();
        for (int i = 0; i < topics.size(); i++) {
            Topic topic = (Topic) topics.get(i);
            topicListString += OPEN_SQUARE_BRACKET + topic.getQuestionList().getCount() + CLOSE_SQUARE_BRACKET
                    + topic.getDescription();
            if (i < topics.size() - 1) {
                topicListString += System.lineSeparator();
            }
        }

        printMessage(topicListString);
    }

    public void printTopicsError(TopicList topics) {
        System.out.println(HORIZONTAL_LINE);

        System.out.println(MESSAGE_PRINT_TOPIC_LIST);
        for (int i = 0; i < topics.getCount(); i++) {
            Topic topic = topics.get(i);
            printMessage(OPEN_SQUARE_BRACKET + topic.getQuestionList().getCount() + CLOSE_SQUARE_BRACKET
                    + topic.getDescription());
        }

        System.out.println(HORIZONTAL_LINE);
    }

    public void addNoteInteractions(TopicList topicList) {
        printMessage(ADD_NOTE_PROMPT_FOR_TOPIC);
        String topicName = SCANNER.nextLine().trim();
        Ui ui = new Ui();

        try {
            if (topicList.doesTopicExist(topicName)) {
                printMessage(ADD_NOTE_PROMPT_FOR_NOTE_TITLE);

                String noteName = SCANNER.nextLine().trim();

                while (noteName.replace(SPACE, EMPTY).isEmpty()) {
                    printMessage(ADD_NOTE_PROMPT_FOR_NOTE_TITLE);
                    noteName = SCANNER.nextLine();
                }

                printMessage(ADD_NOTE_PROMPT_FOR_NOTE_BODY);
                String noteBody = SCANNER.nextLine().trim();

                while (noteBody.replace(SPACE, EMPTY).isEmpty()) {
                    printMessage(ADD_NOTE_PROMPT_FOR_NOTE_BODY);
                    noteBody = SCANNER.nextLine();
                }

                Note note = new Note(noteName, noteBody);
                Topic topic = (Topic) topicList.find(topicName);
                topic.getNoteList().add(note);
                printMessage(ADD_NOTE_SUCCESSFULLY);
            } else {
                printMessage(INVALID_TOPIC);
                ui.printTopicsError(topicList);
                printMessage(INPUT_ERROR + "\n" + ADD_NOTE_UNSUCCESSFULLY);
            }
        } catch (Eduke8Exception e) {
            ui.printError(e.getMessage());
        }
    }

    public void deleteNoteInteractions(TopicList topicList) {
        Ui ui = new Ui();
        int noteCount = 0;
        Topic topic = null;
        NoteList noteList = null;

        printMessage(DELETE_NOTE_PROMPT_FOR_TOPIC);
        String topicName = SCANNER.nextLine();
        
        try {
            topic = (Topic) topicList.find(topicName);
            noteList = topic.getNoteList();
            noteCount = noteList.getCount();
        } catch (Eduke8Exception e) {
            ui.printError(e.getMessage());
        }

        if (topicList.doesTopicExist(topicName) && noteCount > 0) {
            ui.printNoteList(noteList);

            System.out.println(DELETE_NOTE_PROMPT_FOR_INDEX);
            String input = SCANNER.nextLine().trim();

            if (input.matches(NUMBERS_ONLY) && Integer.parseInt(input) > 0
                    && Integer.parseInt(input) <= noteList.getCount()) {
                int index = Integer.parseInt(input);
                topic.getNoteList().delete(index - 1);
                printMessage(DELETE_NOTE_SUCCESSFULLY);
            } else {
                printMessage(INVALID_TOPIC_INDEX + System.lineSeparator() + DELETE_NOTE_UNSUCCESSFULLY);
            }
        } else {
            System.out.println(INVALID_TOPIC);
            ui.printTopicsError(topicList);
            printMessage(INPUT_ERROR + "\n" + DELETE_NOTE_UNSUCCESSFULLY);
        }
    }

    public void listInteraction(TopicList topicList) {
        Ui ui = new Ui();

        printMessage(LIST_NOTE_PROMPT);
        String topicName = SCANNER.nextLine().trim();

        try {
            if (topicList.doesTopicExist(topicName)) {
                Topic topic = (Topic) topicList.find(topicName);
                NoteList noteListTopic = topic.getNoteList();
                ui.printNoteList(noteListTopic);
            } else {
                printMessage(INVALID_TOPIC);
                ui.printTopicsError(topicList);
                printMessage(INPUT_ERROR);
            }
        } catch (Eduke8Exception e) {
            ui.printError(e.getMessage());
        }
    }

    public void printNoteList(NoteList notes) {

        if (notes.getCount() == 0) {
            printMessage(MESSAGE_PRINT_NOTE_LIST_NONE);
        } else {
            printMessage(MESSAGE_PRINT_NOTE_LIST);
            for (int i = 0; i < notes.getCount(); i++) {
                printWithoutLines(HORIZONTAL_LINE);
                Note note = (Note) notes.get(i);
                printWithoutLines((i + 1) + DOT + note.getDescription());
                printWithoutLines(note.getNoteText());
            }
        }

        printWithoutLines(HORIZONTAL_LINE);
    }

    private void printStartQuizQuestions(int numberOfQuestionsChosen) {
        System.out.print(MESSAGE_QUIZ_QUESTION_CHOSEN + numberOfQuestionsChosen + MESSAGE_QUIZ_QUESTION_CHOSEN_SECOND);
        if (numberOfQuestionsChosen < 2) {
            System.out.println(DOT);
        } else {
            System.out.println(DOT_PLURAL);
        }
    }

    public void printPointSystemRules() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(POINT_SYSTEM_RULE + System.lineSeparator());
    }

    public void printPointsEarned(int pointsEarned, int pointsAvailable) {
        System.out.println(MESSAGE_SHOW_POINTS + pointsEarned + MESSAGE_SHOW_POINTS_SECOND
                + pointsAvailable + MESSAGE_SHOW_POINTS_THIRD);
    }

    public void printTotalProgression(int progressionLevelPercentage, boolean progressOverHalf) {
        System.out.print(MESSAGE_USER_PROGRESS + progressionLevelPercentage + PERCENTAGE_SIGN + DOT_SPACE);
        if (progressOverHalf) {
            System.out.println(MESSAGE_PRAISE + System.lineSeparator());
        } else {
            System.out.println(MESSAGE_MOTIVATE + System.lineSeparator());
        }
    }

    public void printTopicProgressionHeader() {
        System.out.println(TOPICS_PROGRESSION_SECTION_HEADER);
        System.out.println(HORIZONTAL_LINE);
    }

    public void printTopicDescriptionForStats(Displayable topic) {
        System.out.println(topic.getDescription());
    }

    public void printTopicCompletionLevel(int questionsAttempted, int questionsTotal) {
        System.out.println(HORIZONTAL_LINE_FOR_TOPICAL_STATS_FIELDS + questionsAttempted
                + OUT_OF_SYMBOL + questionsTotal + MESSAGE_QUESTIONS_DONE);
    }

    public void printTopicAccuracyLevel(int questionsAnsweredCorrectly, int questionsAttempted) {
        System.out.println(HORIZONTAL_LINE_FOR_TOPICAL_STATS_FIELDS + questionsAnsweredCorrectly + OUT_OF_SYMBOL
                + questionsAttempted + MESSAGE_CORRECT_QUESTIONS_ANSWERED);
    }

    public void printTopicalHintUsage(int hintUsage) {
        System.out.println(HORIZONTAL_LINE_FOR_TOPICAL_STATS_FIELDS + hintUsage
                + (hintUsage <= 1 ? MESSAGE_HINT_USED_SINGULAR : MESSAGE_HINT_USED_PLURAL));
    }

    public void printTopicalPoints(int pointsEarned, int pointsAvailable, int progressionPercentage) {
        System.out.println(HORIZONTAL_LINE_FOR_TOPICAL_STATS_FIELDS + pointsEarned + MESSAGE_POINTS_EARNED_OUT_OF
                + pointsAvailable + MESSAGE_AVAILABLE_WORD + OPEN_SQUARE_BRACKET
                + progressionPercentage + PERCENTAGE_SIGN + CLOSE_SQUARE_BRACKET);
    }

    public void printBookmarkedIndicator() {
        printMessage(MESSAGE_BOOKMARK_INDICATOR);
    }

    public void printAlreadyBookmarkedIndicator() {
        printMessage(MESSAGE_BOOKMARKED_ALREADY_INDICATOR);
    }

    public void printListOfBookmarkedQuestions(BookmarkList bookmarks) {
        String list = listOfBookmarkedQuestions(bookmarks);

        printMessage(MESSAGE_BOOKMARK_LIST + System.lineSeparator() + list);
    }

    public void printDeletedBookmarkIndicator(int deleteIndex) {
        String messageOutput = MESSAGE_BOOKMARK_DELETED_PART_ONE + deleteIndex + MESSAGE_BOOKMARK_DELETED_PART_TWO;

        printMessage(messageOutput);
    }

    private String listOfBookmarkedQuestions(BookmarkList bookmarks) {
        int i = 1;
        ArrayList<Displayable> allBookmarks;
        allBookmarks = bookmarks.getInnerList();
        String output = "";
        for (Displayable question : allBookmarks) {
            String optionOutput = "";
            Question properQuestion = (Question) question;
            ArrayList<Displayable> optionsAvailable = properQuestion.getOptionList().getInnerList();
            int j = 1;
            for (Displayable option : optionsAvailable) {
                Option castedOption = (Option) option;
                optionOutput += System.lineSeparator() + "    " + j + CLOSE_BRACKET + option.getDescription()
                        + (castedOption.isCorrectAnswer() ? MESSAGE_BOOKMARK_CORRECT_ANS_INDICATOR : "")
                        + ((j == optionsAvailable.size()) ? System.lineSeparator() : "");
                j++;
            }
            output += i + DOT_SPACE + question.getDescription() + optionOutput + SPACE
                    + ((i == allBookmarks.size()) ? "" : System.lineSeparator());
            i++;
        }
        return output;
    }

    public void printDataLoading() {
        printMessage(DATA_LOADING);
    }

    public void printDataLoaded() {
        printMessage(DATA_LOADED);
    }

    public void printDataSaving() {
        printMessage(DATA_SAVING);
    }

    public void printDataSaved() {
        printMessage(DATA_SAVED);
    }
}
