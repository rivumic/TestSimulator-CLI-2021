/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsimulator;

import java.util.ArrayList;

/**
 *
 * @author Rama Nicholson
 */
public interface QuestionFinder{
    public default boolean containsQuestion(String questionID,
            ArrayList<Question> questions){
        
        for (int i=0;i<questions.size();i++){
            
            if (questionID.equals((questions.get(i)).getQuestionID())){
                return true;
            }
        }
        return false;
    }
}
