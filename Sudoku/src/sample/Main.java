package sample;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;


public class Main extends Application {
    private GridPane gridpane = new GridPane();
    private StackPane pane = new StackPane();
    private Button start = new Button("Start");
    private int[][] started_grid = new int[9][9];
    private boolean[] clue_arr = new boolean[9];
    private int[][][] grid = new int[2][9][9];
    private Button[][] but_arr = new Button[9][9];
    Label[][] text_arr = new Label[9][9];
    private boolean isstarted = false;
    private int t_i = -1;
    private int t_k = -1;

    @Override
    public void start(Stage primaryStage) {
        gridpane.setHgap(4);
        gridpane.setVgap(4);
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(50, 50, 50), CornerRadii.EMPTY, Insets.EMPTY)));
        start.setStyle("-fx-background-color: #A9A9A9;  -fx-font: 17 arial; -fx-font-weight: bold");
        start.setPrefSize(200,65);
        pane.getChildren().add(start);
        StackPane.setAlignment(start, Pos.BOTTOM_CENTER);
        gridpane.setMaxSize(800,800);
        pane.getChildren().add(gridpane);
        StackPane.setAlignment(gridpane, Pos.TOP_CENTER);
        primaryStage.setTitle("Sudoku");
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                Button_create(i, k);
            }
        }
        start.setOnAction(actionEvent -> {
            pane.getChildren().remove(start);
            primaryStage.setMaxHeight(810);
            primaryStage.setMaxWidth(810);
            for (int j = 0; j < 9; j++) {
               for (int l = 0; l < 9; l++) {
                   grid[0][j][l] = started_grid[j][l];
               }
          }
            isstarted = true;
            clues();
        });
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(pane, 810, 870));
        primaryStage.show();

    }

    private void Button_create(int i, int k) {
        Button temp = new Button("   ");
        Label tempt = new Label("");
        text_arr[i][k] = tempt;
        but_arr[i][k] = temp;
        text_arr[i][k].setStyle("-fx-font: 12 arial; -fx-font-weight: bold; -fx-text-fill: #6C6C6C;");
        text_arr[i][k].setWrapText(true);
        but_arr[i][k].setMaxWidth(Double.MAX_VALUE);
        but_arr[i][k].setMaxHeight(Double.MAX_VALUE);
        GridPane.setHgrow(but_arr[i][k], Priority.ALWAYS);
        GridPane.setVgrow(but_arr[i][k], Priority.ALWAYS);
        GridPane.setMargin(but_arr[i][k], new Insets(2));
        but_arr[i][k].setStyle("-fx-background-color: #ffffff; -fx-font: 33 arial; -fx-font-weight: bold");
        GridPane.setValignment(text_arr[i][k], VPos.TOP);
        GridPane.setHalignment(text_arr[i][k], HPos.CENTER);
        gridpane.add(but_arr[i][k], k, i);
        gridpane.add(text_arr[i][k], k, i);
        but_arr[i][k].setOnAction(actionEvent -> {
            if(isstarted) {  started(i, k);  } else {
                if (t_i != -1) but_arr[t_i][t_k].setStyle("-fx-background-color: #ffffff; -fx-font: 33 arial; ");
                t_i = i;
                t_k = k;
                but_arr[i][k].setStyle("-fx-background-color: #8FBC8F; -fx-font: 33 arial;");
                gridpane.setOnMousePressed(mouseEvent -> but_arr[i][k].setStyle("-fx-background-color: #ffffff; -fx-font: 33 arial;"));
                AtomicBoolean pressed = new AtomicBoolean(false);
                but_arr[i][k].setOnKeyPressed(e -> {
                    if (!isstarted) {
                        if (!pressed.get()) {
                            but_arr[i][k].setStyle("-fx-background-color: #ffffff; -fx-font: 33 arial;");
                            try {
                                if (e.getCode().isDigitKey() && Integer.parseInt(String.valueOf(e.getText())) != 0)
                                    started_grid[i][k] = Integer.parseInt(e.getText());

                            } catch (Exception ignored) {
                            }
                            if (e.getCode().isDigitKey() && Integer.parseInt(String.valueOf(e.getText())) != 0)
                                but_arr[i][k].setText(String.valueOf(e.getText()));
                        }
                        pressed.set(true);
                    }
                });
            }
        });
    }


    private void started(int i, int k) {
        if (started_grid[i][k] == 0) {
                if (t_i != -1 && started_grid[t_i][t_k] == 0) {
                    if (grid[1][t_i][t_k] == 0)
                        but_arr[t_i][t_k].setStyle("-fx-background-color: #ffffff; -fx-font: 33 arial; -fx-text-fill: blue;");
                    else
                        but_arr[t_i][t_k].setStyle("-fx-background-color: #ffffff; -fx-font: 33 arial; -fx-text-fill: red;");
                }
            t_i = i;
            t_k = k;
            but_arr[i][k].setStyle("-fx-background-color: #8FBC8F; -fx-font: 33 arial;");
            gridpane.setOnMousePressed(mouseEvent -> but_arr[i][k].setStyle("-fx-background-color: #ffffff; -fx-font: 33 arial;"));
            AtomicBoolean pressed = new AtomicBoolean(false);
            but_arr[i][k].setOnKeyPressed(e -> {
                if (!pressed.get()) {
                    if (grid[1][i][k] == 0)
                        but_arr[i][k].setStyle("-fx-background-color: #ffffff; -fx-font: 33 arial; -fx-text-fill: blue;");
                    else but_arr[i][k].setStyle("-fx-background-color: #ffffff; -fx-font: 33 arial; -fx-text-fill: red;");
                    try {
                        if (e.getCode().isDigitKey() && Integer.parseInt(String.valueOf(e.getText())) != 0)
                            grid[0][i][k] = Integer.parseInt(e.getText());
                    } catch (Exception ignored) {
                    }
                    if (e.getCode().isDigitKey() && Integer.parseInt(String.valueOf(e.getText())) != 0) {
                        but_arr[i][k].setText(String.valueOf(e.getText()));
                        clues();
                    }
                    for (int j = 0; j < 9; j++) {
                        for (int l = 0; l < 9; l++) {
                            grid[1][j][l] = 0;
                        }
                    }
                    for (int j = 0; j < 9; j++) {
                        for (int l = 0; l < 9; l++) {
                            if (started_grid[j][l] == 0) {
                                color(j, l);
                                if (grid[1][j][l] == 0)
                                    but_arr[j][l].setStyle("-fx-background-color: #ffffff; -fx-font: 33 arial; -fx-text-fill: blue;");
                                if (grid[1][j][l] == 1)
                                    but_arr[j][l].setStyle("-fx-background-color: #ffffff; -fx-font: 33 arial; -fx-text-fill: red;");

                            }
                        }
                    }
                }
                pressed.set(true);
            });
        }
    }

    private void color(int i, int k) {
        for (int j = 0; j < 9; j++) {
            if (grid[0][i][j] == grid[0][i][k] && j != k) {grid[1][i][k] = 1; break;}

        }
        for (int j = 0; j < 9; j++) {
                if (grid[0][j][k] == grid[0][i][k] && j != i) {grid[1][i][k] = 1; break;}
        }
        boolean flag = false;
        for (int j = i/3*3; j < i/3*3+3; j++) {
            if (flag) break;
            for (int l = k/3*3; l < k/3*3+3; l++) {
                if (grid[0][j][l] == grid[0][i][k] && (j != i || k != l)) {grid[1][i][k] = 1; flag = true; break;}

            }
        }
    }

    private void clues() {
        String s = "";
            for (int i = 0; i < 9; i++) {
                for (int k = 0; k < 9; k++) {
                    if (grid[0][i][k] == 0) {
                        s = "";
                        for (int p = 0; p < 9; p++) {
                            clue_arr[p] = true;
                        }
                        for (int j = 0; j < 9; j++) {
                            if (grid[0][j][k] != 0) clue_arr[grid[0][j][k] - 1] = false;
                        }
                        for (int j = 0; j < 9; j++) {
                            if (grid[0][i][j] != 0) clue_arr[grid[0][i][j] - 1] = false;
                        }
                        for (int j = i / 3 * 3; j < i / 3 * 3 + 3; j++) {
                            for (int l = k / 3 * 3; l < k / 3 * 3 + 3; l++) {
                                if (grid[0][j][l] != 0) {
                                    clue_arr[grid[0][j][l] - 1] = false;
                                }

                            }
                        }
                        for (int j = 0; j < 9; j++) {
                            if (clue_arr[j]) {
                                if (!s.equals("")) s += " ";
                                s += j + 1;
                            }
                        }
                        if (s.length() == 1) { // Если длина строки с подсказками равна одному символу
                            text_arr[i][k].setText("");
                            grid[0][i][k] = Integer.parseInt(s); // Устанавливаем в массиве значение необходимого числа
                            but_arr[i][k].setStyle("-fx-background-color: #ffffff; -fx-font: 33 arial;"); // Задаём стиль кнопке
                            but_arr[i][k].setText(s); // Ставим текст кнопки
                        } else
                        text_arr[i][k].setText(s);

                    }
                    else text_arr[i][k].setText("");
                }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

}
