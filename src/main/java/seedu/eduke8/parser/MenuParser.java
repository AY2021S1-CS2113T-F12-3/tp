package seedu.eduke8.parser;

import seedu.eduke8.bookmark.BookmarkList;
import seedu.eduke8.command.AboutCommand;
import seedu.eduke8.command.BookmarkCommand;
import seedu.eduke8.command.Command;
import seedu.eduke8.command.ExitCommand;
import seedu.eduke8.command.HelpCommand;
import seedu.eduke8.command.IncorrectCommand;
import seedu.eduke8.command.StatsCommand;
import seedu.eduke8.command.TopicsCommand;
import seedu.eduke8.command.TextbookCommand;
import seedu.eduke8.command.NoteCommand;
import seedu.eduke8.command.QuizCommand;
import seedu.eduke8.common.DisplayableList;
import seedu.eduke8.exception.Eduke8Exception;
import seedu.eduke8.topic.TopicList;
import seedu.eduke8.ui.Ui;


import java.awt.print.Book;
import java.util.logging.Level;
import java.util.logging.Logger;

import static seedu.eduke8.exception.ExceptionMessages.ERROR_NOTE_WRONG_FORMAT;
import static seedu.eduke8.exception.ExceptionMessages.ERROR_QUIZ_WRONG_FORMAT;
import static seedu.eduke8.exception.ExceptionMessages.ERROR_UNRECOGNIZED_COMMAND;
import static seedu.eduke8.exception.ExceptionMessages.ERROR_QUIZ_TIMER_NEGATIVE;

/**
 * Parses user input from the main menu, in order to execute the correct option.
 */
public class MenuParser implements Parser {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String TOPIC_INDICATOR = "t/";
    private static final String TIMER_INDICATOR = "s/";
    private static final String NUMBER_OF_QUESTIONS_INDICATOR = "n/";
    private static final int LENGTH_OF_QUESTIONS_INDICATOR = 2;
    private static final int LENGTH_OF_TOPIC_INDICATOR = 2;
    private static final int LENGTH_OF_TIMER_INDICATOR = 2;
    private static final int BOOKMARK_DELETE_COMMANDARR_LENGTH = 3;
    private static final String BOOKMARK_LIST = "listing";
    private static final String COMMAND_ABOUT = "about";
    private static final String COMMAND_HELP = "help";
    private static final String COMMAND_TOPICS = "topics";
    private static final String COMMAND_TEXTBOOK = "textbook";
    private static final String COMMAND_QUIZ = "quiz";
    private static final String COMMAND_NOTE = "note";
    private static final String COMMAND_BOOKMARK = "bookmark";
    private static final String COMMAND_EXIT = "exit";
    private static final String COMMAND_STATS = "stats";
    private static final String COMMAND_NOTE_ADD = "add";
    private static final String COMMAND_NOTE_DELETE = "delete";
    private static final String COMMAND_NOTE_LIST = "list";

    private BookmarkList bookmarks;

    public MenuParser(BookmarkList bookmarks) {
        this.bookmarks = bookmarks;
    }

    /**
     * Parses the user input.
     *
     * @param topicList the topic list that contains the list of topics available in storage
     * @param userInput the string input typed by the user
     * @return a Command object which when executed will carry out the appropriate action
     */
    @Override
    public Command parseCommand(DisplayableList topicList, String userInput) {
        assert topicList != null;
        LOGGER.log(Level.INFO, "Begin parsing command.");
        String[] commandArr = userInput.trim().split(" ", 0);

        for (int i = 0; i < commandArr.length - 1; i++) {
            if (commandArr[i].equals("") || (i > 0 && (commandArr[i].equals(commandArr[i - 1])))) {
                int j = 1;
                while (commandArr[i + j].equals("") && ((i + j) < commandArr.length - 1)) {
                    j++;
                }
                commandArr[i] = commandArr[i + j];
                commandArr[i + j] = "";
            }
        }

        Ui ui = new Ui();
        switch (commandArr[0]) {
        case COMMAND_ABOUT:
            LOGGER.log(Level.INFO, "Parsing complete: about command chosen.");
            return new AboutCommand();
        case COMMAND_HELP:
            LOGGER.log(Level.INFO, "Parsing complete: help command chosen.");
            return new HelpCommand();
        case COMMAND_TOPICS:
            LOGGER.log(Level.INFO, "Parsing complete: topics command chosen.");
            return new TopicsCommand();
        case COMMAND_TEXTBOOK:
            LOGGER.log(Level.INFO, "Parsing complete: textbook command chosen.");
            return new TextbookCommand();
        case COMMAND_QUIZ:
            int numOfQuestions = 0;
            String topicName = "";
            int userTimer = 0;
            if ((commandArr.length > 4 && (!commandArr[3].contains("/") || commandArr[4].equals("")))
                    || commandArr.length < 4) {
                return new IncorrectCommand(ERROR_QUIZ_WRONG_FORMAT);
            }
            try {
                if (commandArr[1].contains(TIMER_INDICATOR)) {
                    if (commandArr[2].contains(NUMBER_OF_QUESTIONS_INDICATOR)) {
                        numOfQuestions = Integer.parseInt(commandArr[2].substring(
                                commandArr[2].indexOf(NUMBER_OF_QUESTIONS_INDICATOR) + LENGTH_OF_QUESTIONS_INDICATOR));
                        topicName = commandArr[3].substring(
                                commandArr[3].indexOf(TOPIC_INDICATOR) + LENGTH_OF_TOPIC_INDICATOR);
                        userTimer = Integer.parseInt(commandArr[1].substring(
                                commandArr[1].indexOf(TIMER_INDICATOR) + LENGTH_OF_TIMER_INDICATOR));

                    } else if (commandArr[2].contains(TOPIC_INDICATOR)) {
                        numOfQuestions = Integer.parseInt(commandArr[3].substring(
                                commandArr[3].indexOf(NUMBER_OF_QUESTIONS_INDICATOR) + LENGTH_OF_QUESTIONS_INDICATOR));
                        topicName = commandArr[2].substring(
                                commandArr[2].indexOf(TOPIC_INDICATOR) + LENGTH_OF_TOPIC_INDICATOR);
                        userTimer = Integer.parseInt(commandArr[1].substring(
                                commandArr[1].indexOf(TIMER_INDICATOR) + LENGTH_OF_TIMER_INDICATOR));
                    }

                } else if (commandArr[1].contains(NUMBER_OF_QUESTIONS_INDICATOR)) {
                    if (commandArr[2].contains(TIMER_INDICATOR)) {
                        numOfQuestions = Integer.parseInt(commandArr[1].substring(
                                commandArr[1].indexOf(NUMBER_OF_QUESTIONS_INDICATOR) + LENGTH_OF_QUESTIONS_INDICATOR));
                        topicName = commandArr[3].substring(
                                commandArr[3].indexOf(TOPIC_INDICATOR) + LENGTH_OF_TOPIC_INDICATOR);
                        userTimer = Integer.parseInt(commandArr[2].substring(
                                commandArr[2].indexOf(TIMER_INDICATOR) + LENGTH_OF_TIMER_INDICATOR));

                    } else if (commandArr[2].contains(TOPIC_INDICATOR)) {
                        numOfQuestions = Integer.parseInt(commandArr[1].substring(
                                commandArr[1].indexOf(NUMBER_OF_QUESTIONS_INDICATOR) + LENGTH_OF_QUESTIONS_INDICATOR));
                        topicName = commandArr[2].substring(
                                commandArr[2].indexOf(TOPIC_INDICATOR) + LENGTH_OF_TOPIC_INDICATOR);
                        userTimer = Integer.parseInt(commandArr[3].substring(
                                commandArr[3].indexOf(TIMER_INDICATOR) + LENGTH_OF_TIMER_INDICATOR));
                    }

                } else if (commandArr[1].contains(TOPIC_INDICATOR)) {
                    if (commandArr[2].contains(NUMBER_OF_QUESTIONS_INDICATOR)) {
                        numOfQuestions = Integer.parseInt(commandArr[2].substring(
                                commandArr[2].indexOf(NUMBER_OF_QUESTIONS_INDICATOR) + LENGTH_OF_QUESTIONS_INDICATOR));
                        topicName = commandArr[1].substring(
                                commandArr[1].indexOf(TOPIC_INDICATOR) + LENGTH_OF_TOPIC_INDICATOR);
                        userTimer = Integer.parseInt(commandArr[3].substring(
                                commandArr[3].indexOf(TIMER_INDICATOR) + LENGTH_OF_TIMER_INDICATOR));

                    } else if (commandArr[2].contains(TIMER_INDICATOR)) {
                        numOfQuestions = Integer.parseInt(commandArr[3].substring(
                                commandArr[3].indexOf(NUMBER_OF_QUESTIONS_INDICATOR) + LENGTH_OF_QUESTIONS_INDICATOR));
                        topicName = commandArr[1].substring(
                                commandArr[1].indexOf(TOPIC_INDICATOR) + LENGTH_OF_TOPIC_INDICATOR);
                        userTimer = Integer.parseInt(commandArr[2].substring(
                                commandArr[2].indexOf(TIMER_INDICATOR) + LENGTH_OF_TIMER_INDICATOR));
                    }
                }

            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                return new IncorrectCommand(ERROR_QUIZ_WRONG_FORMAT);
            }
            try {
                if (userTimer < 1) {
                    throw new Eduke8Exception(ERROR_QUIZ_TIMER_NEGATIVE);
                }
            } catch (Eduke8Exception e) {
                return new IncorrectCommand(ERROR_QUIZ_TIMER_NEGATIVE);
            }

            LOGGER.log(Level.INFO, "Parsing complete: quiz command chosen.");
            return new QuizCommand((TopicList) topicList, numOfQuestions, topicName, ui, bookmarks, userTimer);
        case COMMAND_BOOKMARK:
            LOGGER.log(Level.INFO, "Parsing complete: bookmark list command chosen.");
            if (commandArr.length == BOOKMARK_DELETE_COMMANDARR_LENGTH) {
                int deleteIndex = 0;
                deleteIndex = Integer.parseInt(commandArr[2]);
                return new BookmarkCommand(deleteIndex, commandArr[1], bookmarks);
            }
            return new BookmarkCommand(BOOKMARK_LIST, bookmarks);
        case COMMAND_NOTE:
            try {
                if (commandArr[1].equalsIgnoreCase(COMMAND_NOTE_ADD) || commandArr[1]
                        .equalsIgnoreCase(COMMAND_NOTE_DELETE) || commandArr[1].equals(COMMAND_NOTE_LIST)) {
                    LOGGER.log(Level.INFO, "Parsing complete: note command chosen");
                    return new NoteCommand(commandArr[1], (TopicList) topicList);
                } else {
                    break;
                }
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                return new IncorrectCommand(ERROR_NOTE_WRONG_FORMAT);
            }
        case COMMAND_EXIT:
            LOGGER.log(Level.INFO, "Parsing complete: exit command chosen.");
            return new ExitCommand();
        case COMMAND_STATS:
            LOGGER.log(Level.INFO, "Parsing complete: stats command chosen.");
            return new StatsCommand((TopicList) topicList);
        default:
            break;
        }
        LOGGER.log(Level.WARNING, "Parsing Error: Unrecognised command was entered.");
        return new IncorrectCommand(ERROR_UNRECOGNIZED_COMMAND);
    }

}
