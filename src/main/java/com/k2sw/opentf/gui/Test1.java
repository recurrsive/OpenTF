package com.k2sw.opentf.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Test1 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        TilePane tile = new TilePane();
        tile.setHgap(10);
        tile.setVgap(10);
        tile.setPrefColumns(8);
        for (int i = 0; i < 20; i++) {
            Polygon hexagon = new Polygon();
            hexagon.getPoints().addAll(125.0,57.0,
                    75.0,57.0,
                    50.0,100.0,
                    75.0,143.0,
                    125.0,143.0,
                    150.0,100.0);

            tile.getChildren().add(hexagon);
        }
        primaryStage.setScene(new Scene(tile, 600, 600));
        primaryStage.show();
    }
}
