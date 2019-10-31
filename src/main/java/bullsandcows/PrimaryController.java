package bullsandcows;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;

public class PrimaryController {

    public Spinner<Integer> num1;
    public Spinner<Integer> num2;
    public Spinner<Integer> num3;
    public Spinner<Integer> num4;
    public TableView<Turn> turns;
    private int turnCount = 0;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void doTurn(ActionEvent actionEvent) {
        int n1 = num1.getValue();
        int n2 = num2.getValue();
        int n3 = num3.getValue();
        int n4 = num4.getValue();
        turnCount++;

        String guess = "" + n1 + n2 + n3 + n4;
        Turn turn = new Turn();
        turn.setGuess(guess);
        turn.setTurnNr(turnCount);
        turns.getItems().add(turn);

        turns.sort();

        //System.out.printf("%s\n",guess);
    }

}
