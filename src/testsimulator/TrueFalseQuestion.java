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
public class TrueFalseQuestion extends Question{
    private boolean correctAnswer;
    private boolean chosenAnswer;
    
    public TrueFalseQuestion(String questionID, int chapterNumber,
        String questionText,boolean correctAnswer){
    
        super(questionID, chapterNumber, questionText);
    
        this.correctAnswer = correctAnswer;
}
            
    public boolean isAnswerCorrect(){
        return chosenAnswer==correctAnswer;
    }
    
    public boolean getCorrectAnswer(){
        return this.correctAnswer;
    }
    
    public boolean getChosenAnswer(){
        return this.chosenAnswer;
    }
    
    public void setChosenAnswer(boolean chosenAnswer){
        this.chosenAnswer = chosenAnswer;
    }
   
}
