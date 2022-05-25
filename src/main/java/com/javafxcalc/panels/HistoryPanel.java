package com.javafxcalc.panels;

import com.javafxcalc.services.HistoryService;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HistoryPanel {

    @Autowired
    private HistoryService historyService;

    private VBox vBox = new VBox();
    private TextArea textArea = new TextArea();

    public Node node() {
        return vBox;
    }

    public HistoryPanel() {
        vBox.getChildren().add(new Label("History"));
        vBox.getChildren().add(textArea);
        vBox.setMaxWidth(150);
        vBox.setPadding(new Insets(5));

        textArea.setEditable(false);

        VBox.setVgrow(textArea, Priority.ALWAYS);
    }

    public void update() {
        textArea.setText(historyService.getHistoryAsText());
    }

}
