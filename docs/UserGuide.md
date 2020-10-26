# User Guide for E-Duke-8

Table of contents:

- [1. Introduction](#1-introduction)
- [2. Quick Start](#2-quick-start)
- [3. Features](#3-features)
  * [3.1. Loading Data](#31-loading-data)
  * [3.2. Welcome Page](#32-welcome-page)
  * [3.3. Viewing E-Duke-8 information: `about`](#33-viewing-e-duke-8-information-about)
  * [3.4. Viewing available commands: `help`](#34-viewing-available-commands-help)
  * [3.5. Listing all topics: `topics`](#35-listing-all-topics-topics)
  * [3.6. Accessing the CS2113T textbook content: `textbook`](#36-accessing-the-cs2113t-textbook-content-textbook)
  * [3.7. Starting a quiz: `quiz`](#37-starting-a-quiz-quiz)
  * [3.8. Showing a hint: `hint`](#38-showing-a-hint-hint)
  * [3.9. Exiting the program: `exit`](#39-exiting-the-program-exit)
- [4. FAQ](#4-faq)
- [5. Command summary](#5-command-summary)
- [6. Glossary](#6-glossary)

## 1. Introduction

E-Duke-8 (pronounced "Educate") helps CS2113/T students **learn and understand software engineering and [OOP](#Command-Line-Interface-(CLI)) principles** through a gamified
platform and enhances their learning experience. 

It is a desktop application where CS2113/T students can attempt bite-sized quizzes, through the [**Command Line Interface (CLI)**](#Command-Line-Interface-(CLI)), to test their understanding of the concepts taught, and serves to consolidate key concepts for easy revision.


## 2. Quick Start

1. Ensure that you have Java 11 or above installed.
2. Download the latest version of **E-Duke-8** from [here](https://github.com/AY2021S1-CS2113T-F12-3/tp/releases).
3. Copy the files to the folder you want to use for the application.
4. Double click on eduke8.jar to start up the program. If the program does not start up, continue to steps 5 and 6, otherwise skip to step 7.
5. If double clicking on the jar file does not work, open up the command prompt in the same folder used in step 3.
6. Enter the following command `java -jar eduke8.jar`.
7. Type a command in the terminal and press Enter to execute it. e.g. typing 'help' and pressing Enter will list all the commands that you can use in this application. Here is a list of commands you can try:
   - about : Provides information about E-Duke-8.
   - topics : Lists CS2113/T topics that E-Duke-8 knows.
   - textbook : Provides you with a link to the CS2113/T website.
   - quiz : Starts a quiz. Eg. `quiz t/OOP n/5` will start a quiz with five questions based on OOP concepts.
   - hint	  :   Shows a hint to the current question.
   - exit : Exits the app.

8. Refer to the section on **Features** below for details of each command.

## 3. Features 

ℹ️ Notes about the command format:
- Words in UPPER_CASE are the parameters that you can specify for your usage.
- e.g. in `quiz t/TOPIC n/NUM_QUESTIONS`, 'TOPIC' is a parameter that allows you to choose the topic you want the quiz to be based on, and 'NUM_QUESTIONS' specifies the number of questions you want to have in your quiz. 

### 3.1. Loading Data 

Data is automatically loaded from the files in the **data** folder for use in quizzes and notes.

To ensure that data could be loaded successfully, ensure that the data folder that comes along with the eduke8.jar file is placed in the same folder as eduke8.jar. This is part of the setup and is to be done prior to the running of the app. 

### 3.2. Welcome Page

Upon successful startup, you should be greeted with a screen like this:

![welcome](./images/welcome.png)

### 3.3. Viewing E-Duke-8 information: `about`

Shows a message explaining what E-Duke-8 is about and your benefits of using it.

Example of usage:
```
about
```

What you should see:

![about](./images/about.png)


### 3.4. Viewing available commands: `help`

Shows a list of commands that you can use.

Example of usage:
```
help
```

What you should see:

![help](./images/help.png)


### 3.5. Listing all topics: `topics`

Shows the entire list of CS2113/T's topics that you can choose for your quiz to be based upon. 

You can see the number of questions available for each topic beside the topic itself.

Example of usage:
```
topics
```

What you should see:

![topics](./images/topics.png)


### 3.6. Accessing the CS2113T textbook content: `textbook`

You can find a link to the module's textbook using this command. 

Example of usage:
```
textbook
```

What you should see:

![textbook](./images/textbook.png)


### 3.7. Starting a quiz: `quiz`

Starts a quiz with the number of questions and topic as specified by you.

Regardless of right or wrong answer, an explanation about the question will be displayed after you give an answer. You will then proceed to the next question.

Note:
- Each of the questions in the quiz will be related to the topic you have chosen.
- You can choose any topic from the topic list.
- Refer to [topics](#3.5.-Listing-all-topics-:-`topics`) to see the number of questions you can choose from.
- The first question will be immediately displayed to you once the quiz starts.
- The quiz will only end when you have attempted all of the questions in the it.

Input format: `quiz t/TOPIC n/NUM_QUESTIONS`

Example of usage:
```
quiz t/OOP n/5
```
- This will start a quiz that contains 5 randomly-chosen questions from the topic on OOP.

What you should see:

![quiz](./images/quiz.png)

From here on you can either:

1) Enter in your answer by typing '1', '2', '3' or '4' and pressing enter.

2) If you do not know the answer, you can request for a hint by typing 'hint' and pressing enter. Refer to [**the Hint section**](#3.8.-Showing-a-hint-:-`hint`) for more information.

If you have entered the correct answer, this is what you will see:
![quiz](./images/quiz_correct.png)

If you have entered the wrong answer, this is what you will see:
![quiz](./images/quiz_wrong.png)

After attempting all of the questions, the quiz will automatically end:
![quiz](./images/quiz_end.png)

After the quiz ends, you will be prompted to enter in a new command. At this point, you can choose to start a new quiz again!

### 3.8. Showing a hint: `hint`

Shows a hint to the current question.

Note: 
- Each question only contains exactly one hint.

Example of usage:
```
hint
```

What you should see:

![hint](./images/hint.png)

### 3.9. Exiting the program: `exit`

Exits E-Duke-8.
- You can only exit the application when you are not in a quiz.
- If you are in the middle of a quiz and really want to exit, you can give random answers for the questions.

Example of usage:
```
exit
```

![exit](./images/exit.png)


## 4. FAQ

**Q**: How do I change the questions in the quizzes?

**A**: Open `topics.json` in any text editor to edit the questions, make sure to follow the format of the questions already provided.

## 5. Command summary

| Action | Format, Examples |
| ------ | ---------------- |
| Viewing E-Duke-8 information       | `about`                                                |
| Viewing available commands    | `help`                                              |
| Listing all topics        | `topics`  |
| Accessing the CS2113T textbook content        | `textbook`                                                 |
| Starting a quiz |  `quiz t/TOPIC n/QUESTIONS` <br/> e.g. `quiz t/OOP n/5`                                              |
| Showing a hint        |           `hint`                                      |
| Exit        | `exit`                                                 |

## 6. Glossary

##### 1. Command Line Interface (CLI)
CLI is a text-based interface that allows users to respond to visual prompts by typing single commands into the interface and receiving a reply in the same way. (From [techopedia](https://www.techopedia.com/definition/3337/command-line-interface-cli))

##### 2. Object-oriented Programming (OOP)
OOP is a programming paradigm. A programming paradigm guides programmers to analyze programming problems, and structure programming solutions, in a specific way. (From [CS2113/T textbook](https://nus-cs2113-ay2021s1.github.io/website/se-book-adapted/chapters/oop.html))