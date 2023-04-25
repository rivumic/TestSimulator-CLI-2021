package testsimulator;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
/**
 *
 * @author Rama Nicholson
 */
public class TestSummary {
    public final String FILENAME_SUMMARY = "test-summary.txt";
    private final int TEST_REPORT_FIELDS = 4;
    private int[] chapterQuestionsAnswered = new int[]{0,0,0,0,0,0,0,0};
    private int[] chapterQuestionsCorrect = new int[]{0,0,0,0,0,0,0,0};
    
    public TestSummary(){
        this.summarisePerformance();
        this.reportPerformance();
    }
    
    public void summarisePerformance(){
        Scanner summary = null;
        try{
            summary = new Scanner(new FileInputStream(FILENAME_SUMMARY));
            while (summary.hasNextLine()){
                String currentTestName = summary.nextLine();
                System.out.println(currentTestName);

                Scanner currentTest = new Scanner(new FileInputStream(currentTestName));
                while (currentTest.hasNextLine()){
                    String currentQuestion = currentTest.nextLine();
                    System.out.println(currentQuestion);
                    String[] lineSplit=currentQuestion.split(",");
                    
                    if (lineSplit.length<this.TEST_REPORT_FIELDS){
                        throw new Exception("Question record has incorrect number of fields");
                    }
                    
                    int chapterNoIndex = Integer.parseInt(lineSplit[1]);
                    System.out.println(chapterNoIndex);
                    boolean chapterAllowed = false;
                    for (int i:new int[]{8,9,10,11,13,14,15,16}){
                        if (i==chapterNoIndex){
                            chapterAllowed=true;
                        }
                        
                    }
                    if (chapterAllowed==false){
                            throw new Exception("Chapter number was not one of the allowed chapter numbers.");
                        }
                    if (chapterNoIndex>11){
                        chapterNoIndex=chapterNoIndex-9;
                    }
                    else{
                        chapterNoIndex=chapterNoIndex-8;
                    }
                    
                    chapterQuestionsAnswered[chapterNoIndex]++;
                    if (lineSplit[2].equals(lineSplit[3])){
                        chapterQuestionsCorrect[chapterNoIndex]++;
                    }    
                }
                currentTest.close();
            }
            summary.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File Not found");
            System.out.println("System exiting...");
            System.exit(0);
        }
        catch (NumberFormatException e){
            System.out.println("Chapter number in test record was not numeric");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("System exiting...");
            System.exit(0);
        }
    }
    public String toString(){
        System.out.println(chapterQuestionsCorrect);
        System.out.println(chapterQuestionsAnswered);
        return "";
    }
    public void reportPerformance(){
        System.out.println(" Chapter  Correct Answered  Percent");
        System.out.println("-------- -------- -------- --------");
        int totalQuestionsCorrect = 0;
        int totalQuestionsAnswered = 0;
        
        for (int i =0;i<chapterQuestionsAnswered.length;i++){
            totalQuestionsCorrect
                    = totalQuestionsCorrect + chapterQuestionsCorrect[i];
            totalQuestionsAnswered =
                    totalQuestionsAnswered + chapterQuestionsAnswered[i];
            int chapterNumber;
            if (i<13)
                chapterNumber = i+8;
            else
                chapterNumber=i+9;
            double chapterScore;
            if(chapterQuestionsAnswered[i]!=0)
                chapterScore = 100*((double)chapterQuestionsCorrect[i]/(double)chapterQuestionsAnswered[i]);
            else
                chapterScore=0;
            
            System.out.printf("%8d %8d %8d %7.2f%%\n",chapterNumber,chapterQuestionsCorrect[i],chapterQuestionsAnswered[i],chapterScore);
           
        }
        System.out.println("-------- -------- -------- --------");            
        double score;
        if (totalQuestionsAnswered!=0)
            score = 100*((double)totalQuestionsCorrect/(double)totalQuestionsAnswered);
        else
            score=0;
        System.out.printf("   Total %8d %8d %7.2f%%\n", totalQuestionsCorrect, totalQuestionsAnswered, score);
    }
}
