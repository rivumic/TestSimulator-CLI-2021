/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsimulator;

/**
 *
 * @author Rama Nicholson
 */
public class MultipleChoiceQuestion extends Question{
    private char chosenAnswer;
    private char correctAnswer;
    private String[] answers;
    
    public MultipleChoiceQuestion(String questionID, int chapterNumber,
            String questionText, String answer1, String answer2, String answer3,
            String answer4, char correctAnswer){
        
        super(questionID, chapterNumber, questionText);
        
        this.answers = new String[]{answer1, answer2, answer3, answer4};
        
        this.correctAnswer = correctAnswer;
    }
    
    public boolean isAnswerCorrect(){
        return chosenAnswer==correctAnswer;
    }
    
    public String[] getAnswers(){
        return answers;
    }
    
    public char getCorrectAnswer(){
        return this.correctAnswer;
    }
    
    public char getChosenAnswer(){
        return this.chosenAnswer;
    }
    
    public void setChosenAnswer(char chosenAnswer){
        this.chosenAnswer = chosenAnswer;
    }
    
}
