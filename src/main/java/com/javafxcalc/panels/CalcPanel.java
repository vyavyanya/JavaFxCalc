package com.javafxcalc.panels;

import com.javafxcalc.calculator.Calculator;
import com.javafxcalc.services.HistoryService;
import com.javafxcalc.calculator.Operation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

@Component
public class CalcPanel {
    public Parent node() { return borderPane; }

    private VBox vBox = new VBox();
    private HistoryPanel historyPanel;
    private BorderPane borderPane = new BorderPane();

    private ToggleButton showHideHistory = new ToggleButton("show/hide history");

    private Calculator calculator;

    private TextField numberTextField = new TextField("0");

    private class CalcButton extends Button {
        private Operation op;
        public CalcButton(String text, Runnable r) {
            setText(text);
            setOnAction(e -> {
                r.run();
                update();
            });
            setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            setStyle("-fx-font-size: 30px");
        }
    }


    public CalcPanel(Calculator calculator, HistoryService historyService, HistoryPanel historyPanel) {
        this.calculator = calculator;
        this.historyPanel = historyPanel;

        numberTextField.setStyle("-fx-font-size: 30px");
        numberTextField.setAlignment(Pos.CENTER_RIGHT);
        numberTextField.setEditable(false);

        showHideHistory.setOnAction(e -> update());

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        vBox.getChildren().addAll(numberTextField, gridPane, showHideHistory);
        borderPane.setPadding(new Insets(5));
        borderPane.setCenter(vBox);

        for (int i = 0; i <= 9; i++) {
            final int d = 9 - i;
            int col = i % 3;
            int row = i / 3;
            gridPane.add(new CalcButton("" + d, () -> calculator.inputDigit(d)), col, row);
        }
        gridPane.add(new CalcButton("=", () -> calculator.inputResult()), 1, 3);
        gridPane.add(new CalcButton("C", () -> calculator.inputClear()), 2, 3);



        gridPane.add(new CalcButton("+", () -> calculator.inputOperation(Operation.ADD)), 3 ,0);
        gridPane.add(new CalcButton("-", () -> calculator.inputOperation(Operation.SUB)), 3 ,1);

        gridPane.add(new CalcButton("*", () -> calculator.inputOperation(Operation.MUL)), 3, 2);
        gridPane.add(new CalcButton("/", () -> calculator.inputOperation(Operation.DIV)), 3, 3);

        gridPane.add(new CalcButton("a^b", () -> calculator.inputOperation(Operation.POW)), 4, 0);
        gridPane.add(new CalcButton("x^2", () -> calculator.inputOperation(Operation.POW2)), 4, 1);

        gridPane.add(new CalcButton("sqrt", () -> calculator.inputOperation(Operation.SQRT)), 4, 2);
        gridPane.add(new CalcButton("root", () -> calculator.inputOperation(Operation.ROOT)), 4, 3);

        calculator.setOnResult(s -> { historyService.add(s); update();});

        historyService.add("--");
    }

    public void update() {
        numberTextField.setText(calculator.getCurrentNumber());
        borderPane.setRight(null);
        if (showHideHistory.isSelected()) {
            borderPane.setRight(historyPanel.node());
        }
        historyPanel.update();
    }


}
