package seedu.eduke8.quiz;

import seedu.eduke8.bookmark.BookmarkList;
import seedu.eduke8.command.Command;
import seedu.eduke8.command.AnswerCommand;
import seedu.eduke8.command.IncorrectCommand;
import seedu.eduke8.command.BookmarkCommand;
import seedu.eduke8.command.HintCommand;
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

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingleTopicQuiz implements Quiz {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private Topic topic;
    private int numberOfQuestions;
    private QuizParser quizParser;
    private BookmarkList bookmarks;

    public SingleTopicQuiz(Topic topic, int numberOfQuestions, BookmarkList bookmarks) {
        assert topic != null;

        this.topic = topic;
        this.numberOfQuestions = numberOfQuestions;
        this.bookmarks = bookmarks;
        quizParser = new QuizParser(bookmarks);
    }

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

            assert question.wasShown();

            OptionList optionList = question.getOptionList();

            ArrayList<Displayable> options = optionList.getInnerList();

            for (int i = 0; i < options.size(); i++) {
                ui.printOption((Option) options.get(i), i + 1);
            }

            quizParser.setQuestion(question);

            Command command = getCommand(ui, optionList);

            assert (command instanceof AnswerCommand || command instanceof HintCommand
                    || command instanceof BookmarkCommand);

            while (!(command instanceof AnswerCommand)) {
                command.execute(optionList, ui);
                command = getCommand(ui, optionList);
                if (command instanceof IncorrectCommand) {
                    LOGGER.log(Level.INFO, "Invalid answer given for question");
                } else {
                    LOGGER.log(Level.INFO, "Hint shown");
                }
            }

            LOGGER.log(Level.INFO, "Question answered");

            command.execute(optionList, ui);
        }
    }

    private Command getCommand(Ui ui, OptionList optionList) {
        String userInput = ui.getQuizInputFromUser();
        return quizParser.parseCommand(optionList, userInput);
    }
}
