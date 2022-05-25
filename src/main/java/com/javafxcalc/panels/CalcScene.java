package com.javafxcalc.panels;

import javafx.scene.Scene;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CalcScene extends Scene {
    @Value("${appName}")
    private String appName;
    public String getTitle() {
        return appName;
    }

    public CalcScene(CalcPanel root) {
        super(root.node());
    }
}
