package seedu.eduke8.ui;

import seedu.eduke8.hint.Hint;
import seedu.eduke8.option.Option;
import seedu.eduke8.question.Question;

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
            + "|_____||____/|_____|\n";

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String HORIZONTAL_LINE = "-------------------------------------------------------------------";
    private static final String MESSAGE_ABOUT = "E-Duke-8 is a desktop app that helps CS2113/T students learn and "
            + "understand software engineering and OOP principles through a gamified platform and enhance their "
            + "learning experience. It also consolidates key concepts for easy revision.";
    private static final String MESSAGE_GREETINGS = "Hello! I'm E-Duke-8\nWhat can I do for you?";
    private static final String MESSAGE_EXIT = "Bye bye. Hope you have a nice day and see you soon!";
    private static final String MESSAGE_HELP = "These are the commands that you can used:";
    private static final String MESSAGE_COMMANDS = "1) about\n2) help\n3) topics\n4) textbook\n5) quiz\n6) stats\n"
            + "7) exit";
    private static final String MESSAGE_QUIZ_START = "Start of quiz:";
    private static final String MESSAGE_QUIZ_END = "This is the end of the quiz!\nHope you have learnt something new!";
    private static final String MESSAGE_ANSWER_WRONG = "Oops! Please try again! Do visit the textbook to read up more.";
    private static final String MESSAGE_ANSWER_CORRECT = "Congrats! This answer is correct! Well Done!";
    private static final String MESSAGE_TEXTBOOK = "The textbook for this module is available at:  " + TEXTBOOK_WEBSITE;


    public String getInputFromUser() {
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
        System.out.println(HORIZONTAL_LINE);
        System.out.println(questionNumber + ". " + question.getDescription());
    }

    public void printHint(Hint hint) {
        System.out.println("Hint: " + hint.getDescription());
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
        printMessage(MESSAGE_QUIZ_END);
    }

    public void printAnswerIsWrong() {
        printMessage(MESSAGE_ANSWER_WRONG);
    }

    public void printAnswerIsCorrect() {
        printMessage(MESSAGE_ANSWER_CORRECT);
    }

    //Shows list of commands that can be used.
    public void printHelp() {
        printMessage(MESSAGE_HELP);
        printMessage(MESSAGE_COMMANDS);
    }

    public void printAbout() {
        printMessage(MESSAGE_ABOUT);
    }

    public void printTextbook() {
        printMessage(MESSAGE_TEXTBOOK);
    }

    public void printError(String errorType) {
        switch (errorType) {
        case "unrecognised":
            printMessage("ERROR: Command received not recognised. Please try again.");
            break;
        case "empty":
            printMessage("ERROR: Command received is missing information. Please try again.");
            break;
        case "Eduk8 Constructor Exception":
            printMessage("ERROR: An exception was thrown while loading the save file from storage.");
            break;
        case "runCommandLoopUntilExit() Exception":
            printMessage("ERROR: An exception was thrown while running the main loop.");
            break;
        case "QuizCommand execute Exception":
            printMessage("ERROR: An exception was thrown while executing the Quiz Command.");
            break;
        case "Not implemented yet":
            printMessage("ERROR: This function has not been implemented yet.");
            break;
        case "Please choose the answer by index":
            printMessage("ERROR: Please choose the answer by using numeric digits as per the choices given.");
            break;
        default:
            break;
        }
        //Writing messages for the different errors
        //Completed when more error handling are done
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
