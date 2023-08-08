import javax.swing.*;
import java.util.Random;

public class GuessTheNumberGame {
    public static void main(String[] args){
        JOptionPane.showMessageDialog(null, "Welcome to Guess the Number Game!");

        int max=100;
        int target=generateRandom(max);
        int maxAttempt=10;
        int attempt=0;

        while(attempt<maxAttempt){
            String guessStr=JOptionPane.showInputDialog("Enter your guess (1 to "+max+"):");

            if(guessStr==null){
                JOptionPane.showMessageDialog(null, "Thank you for playing!");
                break;
            }
            int guess=Integer.parseInt(guessStr);
            attempt++;

            if(guess==target){
                int score=max-attempt+1;
                JOptionPane.showMessageDialog(null, "Congratulations! You guessed the number in "+attempt+" attempt.\nYour score fr this round: "+score);
                target=generateRandom(max);
                attempt=0;
            } else if (guess<target) {
                JOptionPane.showMessageDialog(null, "Your guess is too low. Try Again.");
            }else {
                JOptionPane.showMessageDialog(null, "Your guess is too high. Try Again.");
            }
        }
        if(attempt>=maxAttempt){
            JOptionPane.showMessageDialog(null, "Oops! You've reached the maximum number of attempts.\nThe correct number was: "+target);
        }
    }
    public static int generateRandom(int max){
        Random random = new Random();
        return random.nextInt(max)+1;
    }
}