import java.util.*;

public class Word2Num {
    private static final String[] digits = {"zero","one","two","three","four","five","six","seven","eight","nine"};
    private static final String[] teens = {"eleven","twelve","thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","nineteen"};
    private static final String[] tens = {"ten","twenty","thirty","forty","fifty","sixty","seventy","eighty","ninety"};
    private static final String[] multipliers = {"hundred","thousand","million","billion"};
    
    private int addSum, multiplySum;
    private boolean headTracked;
    
    private List<Tracker> tracker; 
    private List<Integer> finalSum;
    private String[] words; //need to be able to access this in other functions without explicitly passing it
    
    public static void main(String[] args) {
        Word2Num word2Num = new Word2Num();
        String[] testWords = {"Hey 'sup, send me twenty three million seventy two thousand naira", 
            "I need like two thousand five hundred and eighteen dollars",
            "I need like two thousand five hundred and eighteen dollars and possibly twenty four cheeseburgers",
            "I need like two thousand dollars and possibly a hundred and twenty four cheeseburgers",
            "I need like two thousand five hundred and eighteen dollars and possibly a hundred and twenty four bloodymary",
            "Send me twenty three million seventy two thousand",
            "Give me one hundred thousand five hundred and eighteen naira"};
        String convertedWords = word2Num.convert(testWords[6]);
        System.out.println(convertedWords);
    }
    
    public String convert(String sentence){
        if( sentence == null || sentence.length() == 0)return "";
        String newSentence;
        addSum = 0; multiplySum = 0;
        tracker = new ArrayList<>();
        finalSum = new ArrayList<>();
        words = sentence.split("\\s+");
        for(int i = 0; i < words.length; i++){
            int number;
            if ((number=getDigits(words[i])) != -1){
                saveSum(number, i);
            }else if ((number=getTeens(words[i])) != -1){
                saveSum(number, i);
            }else if ((number=getTens(words[i])) != -1){
                saveSum(number, i);
            }else if ((number=getMultipliers(words[i])) != -1){
                saveMultiply(number, i);
            }else if (words[i].equalsIgnoreCase("a") && getMultipliers(getNextWord(words,i)) != -1){//@todo: will throw outOfBound exception
                saveSum(1, i);
            }else if (headTracked && words[i].equalsIgnoreCase("and")){
                //if we are currently tracking the head and we find "and", then we treat this as special case
                if(!nextIsNumber(words, i)){
                    //if next word is a number then, it is a continuation of the previous number 
                    //else if it is not a number close the tail
                    closeTail(i, multiplySum+addSum);
                }
            }
            else{
                //current word is not a number and we are currently tracking head, close the tail
                if (headTracked)closeTail(i, multiplySum+addSum);
            }
        }
        //if after at the end, head is open, close tail
        if (headTracked)closeTail(words.length, multiplySum+addSum);
  
        newSentence = readOut(words, finalSum, tracker);
        return newSentence;
    }
    
    private void saveSum(int number, int index){
        if(!headTracked) trackHead(index);
        addSum += number;
    }
    
    private void saveMultiply(int number, int index){
        if(!headTracked) trackHead(index);
        //@todo: modify to pass testConvertMultiMultipliers
        // if block handles this special case
        boolean[] prevNumType;
        if ((prevNumType = getTypePrevNum(words, index))[2]==true){
            multiplySum *= number;
        }else{
            //handles if first word is a multiplier
            multiplySum += (prevNumType[0]==false)? number : addSum * number;
        }
        addSum = 0;
    }
    
    private void trackHead(int currentIndex){
    //registers the index of the head pointer
        tracker.add(new Tracker(currentIndex));
        headTracked = true;
    }
    
    private void closeTail(int currentIndex, int numValue){
    //registers the tail pointer for the most recent head pointer in the Tracker List
        if (!tracker.isEmpty() && tracker!=null){
            tracker.get(tracker.size()-1).setTail(currentIndex-1);
            headTracked = false;
            saveNumber(numValue);
        }
    }
    
    private void saveNumber(int value){
        //reinitialize sums
        addSum = 0; multiplySum = 0;
        finalSum.add(value);
    }
    
    private boolean nextIsNumber(String[] words, int currentIndex){
		int i = currentIndex+1;
		if ( i >= words.length ) return false;
		return getDigits(words[i])!=-1 || getTeens(words[i])!=-1 || getTens(words[i])!=-1 || getMultipliers(words[i])!=-1;
    }
    
    private String getNextWord(String[] words, int currentIndex){
        if (words==null || words.length == 0)return "";
        int nextIndex = currentIndex+1;
        return (nextIndex >= words.length)? "" : words[nextIndex];
    }
    
    /*
    *@returns [bool, bool]
    * first boolean says it there is any word preceeding the word at the current index
    * second boolean says if its a number or not
    * third boolean says if its a additive or multiplier
    */
    private boolean[] getTypePrevNum(String[] words, int currentIndex){
		int i = currentIndex;
		if ( (i - 1) < 0  ) return new boolean[]{false, false, false};
                boolean isMultiplier = getMultipliers(words[i-1])!=-1;
                boolean isNumber = getDigits(words[i-1])!=-1 || getTeens(words[i-1])!=-1 || getTens(words[i-1])!=-1 || isMultiplier;
		return new boolean[]{true, isNumber, isMultiplier};
    }
    
    private int getDigits(String word){
            return indexIn(word, digits);
    }

    private int getTeens(String word){
        int index = indexIn(word, teens);
        return (index == -1) ? -1 :  index + 11;
    }

    private int getTens(String word){
        int index = indexIn(word, tens);
        return (index == -1) ? -1 : (index+1)*10;
    }

    private int getMultipliers(String word){
        int index = indexIn(word, multipliers);
        if (index == -1)return -1;
        switch(index){
            case(0):return 100;
            case(1):return 1000;
            case(2):return 1000000;//million
            case(3):return 1000000000;//billion
        }
        return index;
    }
    
    private int indexIn(String word, String[] digitGroup){
		return Arrays.asList(digitGroup).indexOf(word.toLowerCase());
    }
    
    private String readOut(String[] originalSentence, List<Integer> numbers, List<Tracker> pointers){
        StringBuilder rebuiltSentence = new StringBuilder();
        int pointerIndex = 0;
        Tracker currentPointer;
        for (int i = 0; i < originalSentence.length; i++){
            if (i == (currentPointer = pointers.get(pointerIndex)).getTrack()[0]){
                rebuiltSentence.append(numbers.get(pointerIndex));
                rebuiltSentence.append(" ");
                i = currentPointer.getTrack()[1];
                pointerIndex = pointerIndex>=pointers.size()-1 ? pointers.size()-1 : ++pointerIndex;
            }else{
                rebuiltSentence.append(originalSentence[i]);
                rebuiltSentence.append(" ");
            }
        }
        return rebuiltSentence.toString().trim();
    }
}

final class Tracker{
    private Integer head, tail;
    
    Tracker(Integer head){
        setHead(head);
    }
    
    Integer[] getTrack(){
        Integer[] track = {this.head, this.tail};
        return track;
    }
    
    void setHead(Integer head){
        this.head = head;
    }
    void setTail(Integer tail){
        this.tail = tail;
    }
    
    @Override
    public String toString(){
        return "["+this.head+"-"+this.tail+"]";
    }
    
}
