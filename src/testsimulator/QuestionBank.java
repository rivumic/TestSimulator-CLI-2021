/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsimulator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Rama Nicholson
 */
public class QuestionBank implements QuestionFinder{
    public static final int[] CHAPTERS = new int[]{8, 9, 10, 11, 13, 14, 15, 16};
    private final String MULTIPLE_CHOICE_FILE = "multiple-choice-questions.csv";
    private final String TRUE_FALSE_FILE = "true-false-questions.csv";
    private final int MULTIPLE_CHOICE_FIELDS = 8;
    private final int TRUE_FALSE_FIELDS = 4;
    private char[] allowedAnswers = new char[]{'a', 'b', 'c', 'd'};
    private ArrayList<Question> questions = new ArrayList<Question>();
    
    public QuestionBank(){
        this.loadMultipleChoiceQuestions();
        this.loadTrueFalseQuestions();
        System.out.println("Loaded "+questions.size()+" questions from the"
                + " question bank.");
    }
    
    public int getLength(){
        return questions.size();
    }
    
    public Question getQuestion(int index){
        return questions.get(index);
    }
    
    public void loadMultipleChoiceQuestions(){
        Scanner file = null;
        try{
            file = new Scanner(new FileInputStream(MULTIPLE_CHOICE_FILE)).useDelimiter(",");
            while (file.hasNextLine()){
                String currentLine = file.nextLine();
                //splits line read into individual fields and error checks
                String[] lineSplit = currentLine.split(",");
                
                if (lineSplit.length<MULTIPLE_CHOICE_FIELDS){
                    throw new Exception("File row has incorrect number of fields");
                }
                
                //parse and error-checking of chapterNumber
                int currentChapterNo = Integer.parseInt(lineSplit[1]);
                boolean chapterAllowed = false;
                for (int i:CHAPTERS){
                    if (i==currentChapterNo){
                        chapterAllowed=true;
                    }
                }
                
                if (chapterAllowed==false){
                    throw new Exception("Chapter number was not one of the allowed chapter numbers.");
                }
                
                //parse and error-checking of correctAnswer
                char correctAnswer = lineSplit[7].charAt(0);
                boolean charAllowed = false;
                for (char i:allowedAnswers){
                    if (correctAnswer==i){
                        charAllowed=true;
                    }
                }
                if (charAllowed==false){
                    throw new Exception("The \"correct answer\" for a multiple"
                         + "choice question was not one of the allowed values");
                }
                
                questions.add(new MultipleChoiceQuestion(lineSplit[0],
                        currentChapterNo,lineSplit[2],lineSplit[3],lineSplit[4],
                        lineSplit[5],lineSplit[6],correctAnswer));
            }
            file.close();
        }
        catch (NumberFormatException e){
            System.out.println("Chapter Number field was not numeric");
            System.out.println("System exiting...");
            System.exit(0);
        }
        catch (FileNotFoundException e){
            System.out.println("File Not found");
            System.out.println("System exiting...");
            System.exit(0);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("System exiting...");
            System.exit(0);
        }
    }
    
    public void loadTrueFalseQuestions(){
        Scanner file = null;
        
        try{
            file = new Scanner(new FileInputStream(TRUE_FALSE_FILE)).useDelimiter(",");
            while (file.hasNextLine()){
                String currentLine = file.nextLine();
                String[] currentSplit = currentLine.split(",");
                
                if (currentSplit.length<TRUE_FALSE_FIELDS){
                    throw new Exception("File row has incorrect number of fields");
                }
                int currentChapterNo = Integer.parseInt(currentSplit[1]);
                boolean chapterAllowed = false;
                for (int i:CHAPTERS){
                    if (i==currentChapterNo){
                        chapterAllowed=true;
                    }
                }
                if (chapterAllowed==false){
                    throw new Exception("Chapter number was not one of the allowed chapter numbers.");
                }
                if(!currentSplit[3].equals("TRUE")&&!currentSplit[3].equals("FALSE")){
                    throw new Exception("The answer to one of the questions was"
                            + "invalid.");
                }
                questions.add(new TrueFalseQuestion(currentSplit[0],
                        currentChapterNo,currentSplit[2],
                        Boolean.parseBoolean(currentSplit[3])));
            }
            file.close();
        }
        catch (NumberFormatException e){
            System.out.println("Chapter Number field was not numeric");
            System.out.println("System exiting...");
            System.exit(0);
        }
        catch (FileNotFoundException e){
            System.out.println("File Not found");
            System.out.println("System exiting...");
            System.exit(0);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("System exiting...");
            System.exit(0);
        }
    }
}
