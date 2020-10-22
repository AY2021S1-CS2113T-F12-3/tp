package seedu.eduke8.ui;

import seedu.eduke8.common.Displayable;
import seedu.eduke8.hint.Hint;
import seedu.eduke8.note.Note;
import seedu.eduke8.option.Option;
import seedu.eduke8.question.Question;
import seedu.eduke8.topic.Topic;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {

    private static final int LAST_OPTION = 4;
    private static final String TEXTBOOK_WEBSITE =
            "https://nus-cs2113-ay2021s1.github.io/website/se-book-adapted/index.html";

    private static final String LOGO = " _____        _____\n"
            + "|  ___| ____ |  _  |\n"
            + "| |___ |  _ \\| |_| |\n"
            + "|  ___|| | | |  _  |\n"
            + "| |___ | |_| | |_| |\n"
            + "|_____||____/|_____|";

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String HORIZONTAL_LINE = "-------------------------------------------------------------------"
            + "-----------------------------------------------------";
    private static final String MESSAGE_ABOUT = "E-Duke-8 is a desktop app that helps CS2113/T students learn and"
            + System.lineSeparator() + "understand software engineering and OOP principles through a"
            + System.lineSeparator() + "gamified platform and enhances their " + "learning experience."
            + System.lineSeparator() + System.lineSeparator() + "You can attempt bite-sized quizzes based "
            + "on your choice of topic" + System.lineSeparator()
            + "and access key concepts for easy revision!";
    private static final String MESSAGE_GREETINGS = "Hello! I'm E-Duke-8!" + System.lineSeparator()
            + "What would you like to learn today?";
    private static final String MESSAGE_EXIT = "Bye bye. Hope you had a fruitful revision and see you soon!";
    private static final String MESSAGE_HELP = "These are the commands that you can use:"
            + System.lineSeparator() + "1) about" + System.lineSeparator() + "2) help" + System.lineSeparator()
            + "3) topics" + System.lineSeparator() + "4) textbook" + System.lineSeparator()
            + "5) quiz t/<topic> n/<number of questions>" + System.lineSeparator() + "6) exit";
    private static final String MESSAGE_QUIZ_START = "Start of quiz:";
    private static final String MESSAGE_QUIZ_END = "This is the end of the quiz!"
            + System.lineSeparator() + "Hope you have learnt something new!";
    private static final String MESSAGE_ANSWER_WRONG = "Oops! The correct answer is ";
    private static final String MESSAGE_ANSWER_WRONG_SECOND = "! Do visit the textbook to read up more.";
    private static final String MESSAGE_ANSWER_CORRECT = "Great Job! That is the correct answer! Keep it up!";
    private static final String MESSAGE_TEXTBOOK = "The textbook for this module is available at:"
            + System.lineSeparator() + TEXTBOOK_WEBSITE;
    private static final String MESSAGE_HINT = "Hint: ";

    public String getInputFromUser() {
        System.out.print("Enter your command or 'help': ");
        return SCANNER.nextLine();
    }

    public String getQuizInputFromUser() {
        System.out.print("Enter your answer or get a hint ('hint'): ");
        return SCANNER.nextLine();
    }

    private static void printMessage(String message) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(message);
        System.out.println(HORIZONTAL_LINE);
    }

    public void printGreetMessage() {
        System.out.println(LOGO);
        printMessage(MESSAGE_GREETINGS);
    }

    public void printExitMessage() {
        printMessage(MESSAGE_EXIT);
    }

    public void printOption(Option option, int optionNumber) {
        System.out.println(optionNumber + ") " + option.getDescription());
        if (optionNumber == LAST_OPTION) {
            System.out.println(HORIZONTAL_LINE);
        }
    }

    public void printQuestion(Question question, int questionNumber) {
        System.out.println(questionNumber + ". " + question.getDescription() + System.lineSeparator());
    }

    public void printHint(Hint hint) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(MESSAGE_HINT + hint.getDescription());
        System.out.println(HORIZONTAL_LINE);
    }

    //Formatting of topicsChosen: separated by ","
    public void printStartQuizPage(int numberOfQuestionsChosen, String topicsChosen) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(MESSAGE_QUIZ_START);

        //Showing the number of questions chosen by user
        printStartQuizQuestions(numberOfQuestionsChosen);

        //Showing the topics chosen by user
        printStartQuizTopics(topicsChosen);

        System.out.println(HORIZONTAL_LINE);
    }

    public void printEndQuizPage() {
        System.out.println(MESSAGE_QUIZ_END);
        System.out.println(HORIZONTAL_LINE);
    }

    public void printAnswerIsWrong(int correctAnswer, String explanation) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(MESSAGE_ANSWER_WRONG + correctAnswer + MESSAGE_ANSWER_WRONG_SECOND);
        System.out.println(System.lineSeparator() + "Explanation:" + System.lineSeparator() + explanation);
        System.out.println(HORIZONTAL_LINE);
        System.out.println(HORIZONTAL_LINE);
    }

    public void printAnswerIsCorrect(String explanation) {
        System.out.println(HORIZONTAL_LINE);
        System.out.println(MESSAGE_ANSWER_CORRECT);
        System.out.println(System.lineSeparator() + "Explanation:" + System.lineSeparator() + explanation);
        System.out.println(HORIZONTAL_LINE);
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
        System.out.println(HORIZONTAL_LINE);

        System.out.println("These are the available topics and the number of available questions in each:");
        for (int i = 0; i < topics.size(); i++) {
            Topic topic = (Topic) topics.get(i);
            System.out.println(topic.getDescription() + "   [" + topic.getQuestionList().getCount() + "]");
        }

        System.out.println(HORIZONTAL_LINE);
    }

    public void printNoteList(ArrayList<Displayable> notes) {

        if (notes.size() == 0) {
            System.out.println("There are no notes for this topic!");
        } else {
            System.out.println("These are the notes for this topic");
            for (int i = 0; i < notes.size(); i++) {
                System.out.println(HORIZONTAL_LINE);
                Note note = (Note) notes.get(i);
                System.out.println(i + ". " + note.getDescription());
            }
        }

        System.out.println(HORIZONTAL_LINE);
    }

    private void printStartQuizTopics(String topicsChosen) {
        if (topicsChosen.contains(",")) {
            int indexOfSplit = topicsChosen.indexOf(",");
            String topics1 = topicsChosen.substring(0, indexOfSplit).trim();
            String topics2 = topicsChosen.substring(indexOfSplit + 1).trim();
            System.out.println("The topics chosen are: " + topics1 + " and " + topics2);
        } else {
            System.out.println("The topic chosen is: " + topicsChosen);
        }
    }

    private void printStartQuizQuestions(int numberOfQuestionsChosen) {
        System.out.print("You have chosen to complete " + numberOfQuestionsChosen + " question");
        if (numberOfQuestionsChosen < 2) {
            System.out.println(".");
        } else {
            System.out.println("s.");
        }
    }
}
