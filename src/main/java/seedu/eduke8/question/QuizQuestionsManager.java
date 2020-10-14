package seedu.eduke8.question;

import seedu.eduke8.common.Displayable;
import seedu.eduke8.exception.Eduke8Exception;

import static seedu.eduke8.exception.ExceptionMessages.ERROR_QUIZ_INSUFFICIENT_TOPIC_QUESTIONS;
import static seedu.eduke8.exception.ExceptionMessages.ERROR_QUIZ_INVALID_QUESTION_NUMBER;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuizQuestionsManager {
    private static Logger logger = Logger.getLogger("Main");


    private ArrayList<Question> quizQuestions;
    private int currentQuestionNumber;

    private static final Random RANDOM = new Random();

    public QuizQuestionsManager(int numberOfQuestionsForQuiz,
                                ArrayList<Displayable> questionsInTopic) throws Eduke8Exception {
        currentQuestionNumber = 0;
        quizQuestions = new ArrayList<>();
        setQuizQuestions(numberOfQuestionsForQuiz, questionsInTopic);
    }

    public void setQuizQuestions(int numberOfQuestionsForQuiz,
                                 ArrayList<Displayable> questionsInTopic) throws Eduke8Exception {

        if (numberOfQuestionsForQuiz <= 0) {
            throw new Eduke8Exception(ERROR_QUIZ_INVALID_QUESTION_NUMBER);
        }

        if (numberOfQuestionsForQuiz > questionsInTopic.size()) {
            throw new Eduke8Exception(ERROR_QUIZ_INSUFFICIENT_TOPIC_QUESTIONS);
        }

        // Stores the questions' indexes selected from the topic question list
        ArrayList<Integer> integersChosen = new ArrayList<>();

        while (quizQuestions.size() < numberOfQuestionsForQuiz) {
            // Gets a random question that is within the bounds of the size of the available question list
            int randomQuestionIndex = RANDOM.nextInt(questionsInTopic.size());

            // To ensure we do not pick the same question again
            if (integersChosen.contains(randomQuestionIndex)) {
                logger.log(Level.INFO, "Chosen a repeated question");
                continue;
            }

            // choose a random question from the question list and add it to the questionsInTopic for quiz
            quizQuestions.add((Question) questionsInTopic.get(randomQuestionIndex));
            integersChosen.add(randomQuestionIndex);
        }
    }

    public Question getNextQuestion() {
        // Automatically increases question count when a question is shown to the user
        return quizQuestions.get(currentQuestionNumber++);
    }

    public int getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }

    public boolean areAllQuestionsAnswered() {
        return currentQuestionNumber == quizQuestions.size();
    }

    public int getQuizQuestionsCount() {
        return quizQuestions.size();
    }
}
