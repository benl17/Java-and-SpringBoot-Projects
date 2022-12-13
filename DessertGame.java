import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.application.Platform;
import javafx.geometry.Pos;

import java.awt.event.MouseEvent;
import java.util.Random;

public class DessertGame extends Application {
    private int score = 0;

    @Override
    public void start(final Stage stage) {
        // Step 3 & 4
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 640, 480);
        stage.setTitle("Dessert in the Desert JavaFX Game");

        // Step 5
        Label scoreLabel = new Label("Score: 0");
        borderPane.setTop(scoreLabel);
        BorderPane.setAlignment(scoreLabel, Pos.TOP_LEFT);

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> {
            Platform.exit();
        });
        borderPane.setBottom(exitButton);
        BorderPane.setAlignment(exitButton, Pos.BOTTOM_RIGHT);

        // Step 6
        Pane pane = new Pane();
        borderPane.setCenter(pane);
        BorderPane.setAlignment(pane, Pos.CENTER);

        // TODO: Step 7-10
        Button[] buttons = new Button[8];
        String dessertButtonInput = "dessertButton";
        buttons[0] = new Button(dessertButtonInput);
        buttons[0].setOnAction(e -> {
            score++;
            scoreLabel.setText("Score: " + score);
            randomizeButtonPositions(new Random(), buttons);
            exitButton.requestFocus();
        });
        pane.getChildren().add(buttons[0]);

        String desertButton = "desertButton";
        for(int i = 1; i < 8; i++) {
            buttons[i] = new Button(desertButton);
            buttons[i].setOnAction(e -> {
                score--;
                scoreLabel.setText("Score: " + score);
                randomizeButtonPositions(new Random(), buttons);
                exitButton.requestFocus();
            });
            pane.getChildren().add(buttons[i]);
        }

        randomizeButtonPositions(new Random(), buttons);
        stage.setScene(scene);
        stage.show();
        exitButton.requestFocus();
    }

    public void randomizeButtonPositions(Random random, Button[] buttons) {
        for(Button currButton : buttons) {
            currButton.setLayoutX(random.nextInt(600));
            currButton.setLayoutY(random.nextInt(400));
        }
    }

    public static void main(String[] args) {
        Application.launch();
    }
}
