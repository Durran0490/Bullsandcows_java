package bullsandcows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;

public class PrimaryController {

    public Spinner<Integer> num1;
    public Spinner<Integer> num2;
    public Spinner<Integer> num3;
    public Spinner<Integer> num4;
    public TableView<Turn> turns;


    public Button goButton;
    private int turnCount = 0;

    private Random rand = new Random();
    private List<Integer> myNumbers;


    public void initialize() {
        generateRandomNumber();

        goButton.disableProperty().bind(
                Bindings.createBooleanBinding(this::invalidNumbers,
                        num1.valueProperty(),
                        num2.valueProperty(),
                        num3.valueProperty(),
                        num4.valueProperty())
        );
    }

    private void generateRandomNumber() {
        myNumbers = new ArrayList<>();
        while (myNumbers.size() < 4) {
            int randNum = rand.nextInt(10);
            if (!myNumbers.contains(randNum)) {
                myNumbers.add(randNum);
            }
        }
        System.out.println(myNumbers);
    }

    private Boolean invalidNumbers() {
        var numSet = new HashSet<Integer>();
        numSet.add(num1.getValue());
        numSet.add(num2.getValue());
        numSet.add(num3.getValue());
        numSet.add(num4.getValue());
        return numSet.size() < 4;
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    private TurnResult calculateBullsCows(List<Integer> userNumbers) {
        int bullsCount = 0;
        int cowCount = 0;
        for (int i = 0; i < userNumbers.size(); i++) {
            for (int j = 0; j < userNumbers.size(); j++) {
                if (myNumbers.get(i).equals(userNumbers.get(j))) {
                    if (j == i) {
                        bullsCount++;
                    } else {
                        cowCount++;
                    }
                }
            }
        }
        return new TurnResult(bullsCount, cowCount);
    }

    /*private int calculateCows(List<Integer> userNumbers) {
        int cowCount = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (myNumbers.get(i).equals(userNumbers.get(j))) {
                    if (j != i) {
                        cowCount++;
                    }
                }
            }
        }
        return cowCount;
    }*/

    public void doTurn(ActionEvent actionEvent) throws IOException {
        int n1 = num1.getValue();
        int n2 = num2.getValue();
        int n3 = num3.getValue();
        int n4 = num4.getValue();
        turnCount++;
        String guess = "" + n1 + n2 + n3 + n4;

        var userNumbers = List.of(n1, n2, n3, n4);
        var bullsAndCows = calculateBullsCows(userNumbers);

        calculateBullsCows(userNumbers);

        Turn turn = new Turn();

        turn.setGuess(guess);
        turn.setBulls(bullsAndCows.getBulls());
        turn.setCows(bullsAndCows.getCows());
        turn.setTurnNr(turnCount);
        turns.getItems().add(turn);

        turns.sort();

        if (bullsAndCows.getBulls() == 4) {
            App.setRoot("secondary");
        }
    }
}
