package testsimulator;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Rama Nicholson
 */
public class TestSimulator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int selection = 0;
        while (selection!=3){
            selection = getSelection();
            switch (selection){
                case 1:
                    System.out.println("\nWelcome to your test.\n"
                            + "The test will have 5 questions.\n"
                            + "You may hit 'q' to quit the test any time, "
                            + "but progress or results will\n"
                            + "not be saved.\nStarting your test now ...\n");
                    QuestionBank questions = new QuestionBank();
                    Test myTest = new Test(5, questions);
                    if(myTest.runTest()){
                        myTest.showTestSummary();
                        myTest.saveTestResult();
                    }
                    break;
                case 2:
                    System.out.println("\nPerformance report ...\n");
                    TestSummary summarise = new TestSummary();
                    break;
                case 3:
                    System.out.println("\nThank you for using TestSimulator!");
                    break;
            }
        }
    }
    public static int getSelection(){
        System.out.println("Welcome to the TestSimulator program menu.\n"
        +"Select from one of the following options.\n"
        +"(1) New test.\n"+"(2) Test summary.\n"+"(3) Exit.\n"
        +"Enter your selection: ");
        
        Scanner keyboard = new Scanner(System.in);
        int userChoice=0;//stores incumbent input
        while (userChoice==0){//keeps prompting until valid input is entered
            if (keyboard.hasNextInt()){//checks data type validity
                userChoice = keyboard.nextInt();//accepts input
                if (userChoice<1||userChoice>3){
                    /*
                    if data type of the input is valid but the content is not
                    an expected value, the user is prompted to enter the input
                    again
                    */
                userChoice=0;
                System.out.println("\u001B[31mInvalid selection,"
                        + " please try again: \u001B[0m");
            }
            }
            else{//prompts re-input if data was unexcepted
                keyboard.next();
                System.out.println("\u001B[31mInvalid selection,"
                        + " please try again: \u001B[0m");
            }
        }
        return userChoice;
    }
}
