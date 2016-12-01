import junit.framework.TestCase;
import Word2Num; //note: set package and fix import appropriately

public class Word2NumTest extends TestCase{
    
    @Override
    public void setUp() throws Exception{
        super.setUp();
    }
    
    @Override
    public void tearDown() throws Exception{
        super.tearDown();
    }
    
    public void testConvertEasy(){
        String testSentence = "Hey 'sup, send me twenty three million seventy two thousand naira"; 
        String rebuiltSentence = "Hey 'sup, send me 23072000 naira"; 
        Word2Num word2Num = new Word2Num();
        assertEquals(rebuiltSentence, word2Num.convert(testSentence));
    }
    
    public void testConvertEasyWithAnd(){
        String testSentence = "I need like two thousand five hundred and eighteen dollars"; 
        String rebuiltSentence = "I need like 2518 dollars"; 
        Word2Num word2Num = new Word2Num();
        assertEquals(rebuiltSentence, word2Num.convert(testSentence));
    }
    
    public void testConvertTwoNums(){
        String testSentence = "I need like two thousand five hundred and eighteen dollars and possibly twenty four cheeseburgers"; 
        String rebuiltSentence = "I need like 2518 dollars and possibly 24 cheeseburgers"; 
        Word2Num word2Num = new Word2Num();
        assertEquals(rebuiltSentence, word2Num.convert(testSentence));
    }
    
    public void testConvertTwoNumsWithAnd(){
        String testSentence = "I need like two thousand five hundred and eighteen dollars and possibly two hundred and twenty four cheeseburgers"; 
        String rebuiltSentence = "I need like 2518 dollars and possibly 224 cheeseburgers"; 
        Word2Num word2Num = new Word2Num();
        assertEquals(rebuiltSentence, word2Num.convert(testSentence));
    }

    public void testConvertTwoNumsWithA(){
        String testSentence = "I need like two thousand five hundred and eighteen dollars and possibly a hundred and twenty four cheeseburgers"; 
        String rebuiltSentence = "I need like 2518 dollars and possibly 124 cheeseburgers"; 
        Word2Num word2Num = new Word2Num();
        assertEquals(rebuiltSentence, word2Num.convert(testSentence));
    }
    
    public void testConvertEndsWithNumber(){
        String testSentence = "I need like two thousand five hundred and eighteen"; 
        String rebuiltSentence = "I need like 2518"; 
        Word2Num word2Num = new Word2Num();
        assertEquals(rebuiltSentence, word2Num.convert(testSentence));
    }
    
    public void testConvertBeginWithAnd(){
        String testSentence = "And I need like two thousand five hundred and eighteen"; 
        String rebuiltSentence = "And I need like 2518"; 
        Word2Num word2Num = new Word2Num();
        assertEquals(rebuiltSentence, word2Num.convert(testSentence));
    }
    
    public void testConvertMultiplierImpliedA(){
        String testSentence = "a thousand five hundred and eighteen members"; 
        String rebuiltSentence = "1518 members"; 
        Word2Num word2Num = new Word2Num();
        assertEquals(rebuiltSentence, word2Num.convert(testSentence));
    }
    
    public void testConvertMultiMultipliers(){
        String testSentence = "Give me one hundred thousand five hundred and eighteen naira"; 
        String rebuiltSentence = "Give me 100518 naira"; 
        Word2Num word2Num = new Word2Num();
        assertEquals(rebuiltSentence, word2Num.convert(testSentence));
    }
    
    public void testConvertCapitalWords(){
        String testSentence = "A Thousand five Hundred AND Eighteen members"; 
        String rebuiltSentence = "1518 members"; 
        Word2Num word2Num = new Word2Num();
        assertEquals(rebuiltSentence, word2Num.convert(testSentence));
    }
    
    public void testConvertStartingMultiplier(){
        String testSentence = "Thousand five hundred and eighteen members"; 
        String rebuiltSentence = "1518 members"; 
        Word2Num word2Num = new Word2Num();
        assertEquals(rebuiltSentence, word2Num.convert(testSentence));
    }
    
    public void testConverEndsWithA(){
        String testSentence = "Thousand five hundred and eighteen members a"; 
        String rebuiltSentence = "1518 members a"; 
        Word2Num word2Num = new Word2Num();
        assertEquals(rebuiltSentence, word2Num.convert(testSentence));
    }
    
}
