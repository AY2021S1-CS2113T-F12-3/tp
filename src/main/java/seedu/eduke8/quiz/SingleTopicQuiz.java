package seedu.eduke8.quiz;

import seedu.eduke8.bookmark.BookmarkList;
import seedu.eduke8.command.AnswerCommand;
import seedu.eduke8.command.BookmarkCommand;
import seedu.eduke8.command.Command;
import seedu.eduke8.command.HintCommand;
import seedu.eduke8.command.IncompleteCommand;
import seedu.eduke8.command.IncorrectCommand;
import seedu.eduke8.common.Displayable;
import seedu.eduke8.exception.Eduke8Exception;
import seedu.eduke8.option.Option;
import seedu.eduke8.option.OptionList;
import seedu.eduke8.parser.QuizParser;
import seedu.eduke8.question.Question;
import seedu.eduke8.question.QuestionList;
import seedu.eduke8.question.QuizQuestionsManager;
import seedu.eduke8.topic.Topic;
import seedu.eduke8.ui.Ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingleTopicQuiz implements Quiz {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final int CONVERSION_FROM_SECONDS_TO_MILLIS = 1000;

    private Topic topic;
    private int numberOfQuestions;
    private QuizParser quizParser;
    private BookmarkList bookmarks;
    private int userTimer;

    public SingleTopicQuiz(Topic topic, int numberOfQuestions, BookmarkList bookmarks, int userTimer) {
        assert topic != null;

        this.topic = topic;
        this.numberOfQuestions = numberOfQuestions;
        this.bookmarks = bookmarks;
        this.userTimer = userTimer;
        quizParser = new QuizParser(bookmarks);
    }

    /**
     * Starts a quiz with a single topic.
     *
     * @param ui  Ui to print quiz messages.
     * @throws Eduke8Exception if numberOfQuestions used to construct QuizQuestionsManager <= 0
     *     or > number of question in the topic.
     */
    @Override
    public void startQuiz(Ui ui) throws Eduke8Exception {
        LOGGER.log(Level.INFO, "New quiz started.");

        QuestionList topicQuestionList = topic.getQuestionList();

        QuizQuestionsManager quizQuestionsManager =
                new QuizQuestionsManager(numberOfQuestions, topicQuestionList.getInnerList());

        assert !quizQuestionsManager.areAllQuestionsAnswered();

        ui.printStartQuizPage(numberOfQuestions, topic.getDescription());

        goThroughQuizQuestions(ui, quizQuestionsManager);

        ui.printEndQuizPage();

        LOGGER.log(Level.INFO, "Quiz ended.");
    }

    private void goThroughQuizQuestions(Ui ui, QuizQuestionsManager quizQuestionsManager) {
        while (!quizQuestionsManager.areAllQuestionsAnswered()) {
            Question question = quizQuestionsManager.getNextQuestion();
            ui.printQuestion(question, quizQuestionsManager.getCurrentQuestionNumber());

            question.markAsShown();
            assert question.wasShown();

            OptionList optionList = question.getOptionList();

            ArrayList<Displayable> options = optionList.getInnerList();

            for (int i = 0; i < options.size(); i++) {
                ui.printOption((Option) options.get(i), i + 1);
            }

            quizParser.setQuestion(question, userTimer);

            ui.printQuizInputMessage();
            long startTime = System.currentTimeMillis();

            Command command = getCommand(ui, optionList, userTimer);

            assert (command instanceof AnswerCommand || command instanceof HintCommand
                    || command instanceof BookmarkCommand || command instanceof IncompleteCommand);

            while (!(command instanceof AnswerCommand || command instanceof IncompleteCommand)) {
                command.execute(optionList, ui);
                ui.printQuizInputMessage();

                long timePassed = (System.currentTimeMillis() - startTime) / CONVERSION_FROM_SECONDS_TO_MILLIS;
                command = getCommand(ui, optionList, (int) (userTimer - timePassed));

                if (command instanceof IncorrectCommand) {
                    LOGGER.log(Level.INFO, "Invalid answer given for question");
                } else if (command instanceof HintCommand) {
                    LOGGER.log(Level.INFO, "Hint shown");
                } else if (command instanceof BookmarkCommand) {
                    LOGGER.log(Level.INFO, "Question bookmarked");
                }
            }

            LOGGER.log(Level.INFO, "Question answered or time's up");

            command.execute(optionList, ui);

            //User has to press enter so that the next question will be shown 
            boolean enterIsUsed = false;
            while (!enterIsUsed && !quizQuestionsManager.areAllQuestionsAnswered()) {
                enterIsUsed = ui.getEnterFromUser();
            }
        }
    }

    private Command getCommand(Ui ui, OptionList optionList, int timeLeft) {
        try {
            String userInput = ui.getQuizInputFromUser(timeLeft);
            return quizParser.parseCommand(optionList, userInput);
        } catch (IOException e) {
            return new IncorrectCommand(e.getMessage());
        }
    }
}
