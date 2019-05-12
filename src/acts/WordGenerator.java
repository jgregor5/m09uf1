package acts;

/**
 *
 * @author julian
 */
public class WordGenerator {
    
    private WordChecker checker;
    private int maxLength;
    private char[] alphabet;
    
    public WordGenerator(WordChecker checker, int maxLength, char[] alphabet) {
        this.checker = checker;
        this.maxLength = maxLength;
        this.alphabet = alphabet;
    }
    
    public void check() {
        possibleStrings("");
    }
    
    private void possibleStrings(String curr) {

        if (curr.length() == maxLength) {
            checker.check(curr);
        } else {
            for (int i = 0; i < alphabet.length; i++) {
                String oldCurr = curr;
                curr += alphabet[i];
                possibleStrings(curr);
                curr = oldCurr;
            }
        }
    }
    
    public interface WordChecker {
        void check(String str);
    }
}
