package com.javafxcalc;

import com.javafxcalc.panels.CalcScene;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFxCalcApp extends Application {

    public static void main(String[] args) {
        Application.launch(JavaFxCalcApp.class, args);
    }

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        applicationContext = new SpringApplicationBuilder().sources(SpringStarter.class).run(args);
    }

    @Override
    public void stop() {
        this.applicationContext.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        CalcScene scene = applicationContext.getBean(CalcScene.class);
        primaryStage.setScene(scene);
        primaryStage.setWidth(600);
        primaryStage.setHeight(400);
        primaryStage.setTitle(scene.getTitle());
        primaryStage.show();
    }
}
