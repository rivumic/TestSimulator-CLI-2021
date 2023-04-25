/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsimulator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
/**
 *
 * @author Rama Nicholson
 */
public class Test implements QuestionFinder {

    private ArrayList<Question> questions = new ArrayList<>();

    public Test(int numQuestions, QuestionBank questionBank) {
        selectQuestions(numQuestions, questionBank);
    }

    private void selectQuestions(int numQuestions, QuestionBank questionBank) {
        if (numQuestions > questionBank.getLength()) {
            System.out.println("This question bank does not have"
                    + " " + numQuestions + "\nThe test will have "
                    + questionBank.getLength() + " questions instead.");
            numQuestions = questionBank.getLength();
        }
        ArrayList<Integer> testQuestions = new ArrayList<>();
        Random rndm = new Random();
        boolean isDuplicate = true;

        for (int i = 0; i < numQuestions; i++) {

            testQuestions.add(rndm.nextInt(questionBank.getLength()));
            while (isDuplicate) {
                isDuplicate = false;

                for (int h = 0; h < testQuestions.size() - 1; h++) {
                    if (Objects.equals(testQuestions.get(h),
                            testQuestions.get(testQuestions.size() - 1))) {
                        isDuplicate = true;
                    }
                }
                if (isDuplicate) {
                    testQuestions.set(testQuestions.size() - 1,
                            rndm.nextInt(questionBank.getLength()));
                }
            }
            isDuplicate = true;
        }

        for (int i = 0; i < numQuestions; i++) {
            this.questions.add(questionBank.getQuestion(testQuestions.get(i)));
        }

        /*for(Question q: questions){
            System.out.println(q.getQuestionID());
        }*/
    }

    public boolean runTest() {
        Scanner keyboard = new Scanner(System.in);
        char userResponse;
        String[] answers = null;
        for (int i=0;i<questions.size();i++){
            System.out.println("Question "+(i+1)+": ");
            
            System.out.println(questions.get(i).getQuestionText());
            userResponse='z';
            if (questions.get(i) instanceof TrueFalseQuestion){
                System.out.println("(a) True\n(b) False\nEnter your answer: ");
                
                while (userResponse=='z'){
                userResponse = keyboard.next().charAt(0);
                   
                switch (userResponse){
                    case'a':
                        ((TrueFalseQuestion)questions.get(i)).setChosenAnswer(true);
                        break;
                    case'b':
                        ((TrueFalseQuestion)questions.get(i)).setChosenAnswer(false);
                        break;
                    case'q':
                        break;
                    default:
                        userResponse='z';
                        System.out.println("Invalid response: Please enter 'a',"
                                + " 'b', or 'q' (to quit).");
                        break;

                    }
                }
                if (userResponse=='q'){
                    System.out.println("Quitting...");
                    break;
                }
                if (((TrueFalseQuestion)questions.get(i)).getChosenAnswer()
                    ==((TrueFalseQuestion)questions.get(i)).getCorrectAnswer())
                    System.out.println("Feedback: Correct!\n");
                else
                    System.out.println("Incorrect. The correct answer was "+
                    ((TrueFalseQuestion)questions.get(i)).getCorrectAnswer()+".\n");
                
            }
            else{
               String[] newArray = ((MultipleChoiceQuestion)questions.get(i)).getAnswers();
               System.out.println();
               char option = 'a';
               for (String answer : ((MultipleChoiceQuestion)questions.get(i)).getAnswers()){
                   System.out.println("("+option+++") "+answer+"\n");
               }
               System.out.println("Enter your Answer: ");
               
               while (userResponse=='z'){
                userResponse = keyboard.next().charAt(0);
                   
                switch (userResponse){
                    case'a':
                    case'b':
                    case'c':
                    case'd':
                        ((MultipleChoiceQuestion)questions.get(i)).setChosenAnswer(userResponse);
                        if (userResponse==((MultipleChoiceQuestion)questions.get(i)).getCorrectAnswer())
                            System.out.println("Feedback: Correct!\n");
                        else
                            System.out.println("Feedback: Incorrect. Correct"+
                                " answer was ("+((MultipleChoiceQuestion)
                                questions.get(i)).getCorrectAnswer()+").\n");
                        
                    case'q':
                        break;
                    default:
                        userResponse='z';
                        System.out.println("Invalid response: Please enter 'a',"
                                + " 'b', 'c', 'd' or 'q' (to quit).");
                        break;

                    }
                }
                if (userResponse=='q'){
                    System.out.println("Quitting...");
                    break;
                }
            }
        }
        System.out.println("Test is finished.\n");
        return true;
    }

    public void showTestSummary() {
        int correctAnswers=0;
        for (Question q:questions){
            if (q instanceof TrueFalseQuestion){
                if(((TrueFalseQuestion)q).isAnswerCorrect()){
                    correctAnswers++;
                }
            }
            else{
                if(((MultipleChoiceQuestion)q).isAnswerCorrect()){
                    correctAnswers++;
                }
            }
        }
        double score = ((double)correctAnswers/(double)questions.size())*100;
        System.out.println("You answered "+correctAnswers+" correctly out of "+
                questions.size()+".");
        System.out.printf("Your score was %4.2f%%.\n\n", score);
    }

    public void saveTestResult() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd-HHmmss");
        String dateString = date.format(formatter)+".txt";
        PrintWriter outputStream = null;
        try{
            outputStream = new PrintWriter(new FileOutputStream(dateString));
            for (Question q:questions){
                if (q instanceof MultipleChoiceQuestion){
                    outputStream.println(q.getQuestionID()+","+q.getChapterNumber()+
                            ","+((MultipleChoiceQuestion) q).getCorrectAnswer()+","+
                            ((MultipleChoiceQuestion) q).getChosenAnswer());
                }
                else{
                    outputStream.println(q.getQuestionID()+","+q.getChapterNumber()+
                            ","+((TrueFalseQuestion) q).getCorrectAnswer()+","+
                            ((TrueFalseQuestion) q).getChosenAnswer());
                }
            }
            outputStream.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File Not found");
            System.out.println("System exiting...");
            System.exit(0);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("System exiting...");
            System.exit(0);
        }
        
        try{
            outputStream = new PrintWriter(new FileOutputStream("test-summary.txt", true));
            outputStream.println(dateString);
            outputStream.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
            System.out.println("System exiting...");
            System.exit(0);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("System exiting...");
            System.exit(0);
        }
    }
}
