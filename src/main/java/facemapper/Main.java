package facemapper;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.File;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception  {

        // Root HBox layout
        HBox root = new HBox();
        root.setAlignment(Pos.CENTER);
        root.setPrefHeight(500);

        // Left VBox layout
        VBox leftVBox = new VBox(10);
        leftVBox.setPadding(new javafx.geometry.Insets(10));

        // RTF File Picker
        HBox rtfFileBox = new HBox(5);
        Label rtfFileLabel = new Label("RTF File: ");
        TextField rtfFileTextField = new TextField();
        rtfFileTextField.setEditable(false);
        Button rtfBrowseButton = new Button("Browse");
        rtfFileBox.getChildren().addAll(rtfFileLabel, rtfFileTextField, rtfBrowseButton);

        // Graphics Directory File Picker
        HBox graphicsDirBox = new HBox(5);
        Label graphicsDirLabel = new Label("Image Directory: ");
        TextField graphicsDirTextField = new TextField();
        graphicsDirTextField.setEditable(false);
        Button graphicsDirBrowseButton = new Button("Browse");
        graphicsDirBox.getChildren().addAll(graphicsDirLabel, graphicsDirTextField, graphicsDirBrowseButton);

        // Mode Selection
        HBox modeBox = new HBox(5);
        Label modeLabel = new Label("Mode: ");
        ComboBox<String> modeComboBox = new ComboBox<>();
        modeComboBox.getItems().addAll("Overwrite", "Preserve");
        Label modeInfoLabel = new Label("Mode Info");
        modeBox.getChildren().addAll(modeLabel, modeComboBox, modeInfoLabel);

        // Reset & Map Faces Button
        HBox buttonsBox = new HBox(5);
        Button resetButton = new Button("Reset");
        Button mapFacesButton = new Button("Map Faces");
        buttonsBox.getChildren().addAll(resetButton, mapFacesButton);

        // Assemble leftVBox
        leftVBox.getChildren().addAll(rtfFileBox, graphicsDirBox, modeBox, buttonsBox);

        // Right VBox layout
        VBox rightVBox = new VBox(10);
        rightVBox.setPadding(new javafx.geometry.Insets(10));

        // UID display
        HBox uidBox = new HBox(5);
        Label uidLabel = new Label("Current UID: ");
        TextField uidTextField = new TextField();
        uidTextField.setEditable(false);
        uidBox.getChildren().addAll(uidLabel, uidTextField);

        // Image Preview
        ImageView imageView = new ImageView();
        imageView.setFitWidth(256);
        imageView.setFitHeight(256);
        imageView.setPreserveRatio(true);

        // set default image
        Image defaultImage = new Image(new File("src/main/java/utilities/newgen.png").toURI().toString());
        imageView.setImage(defaultImage);

        // Progress bar
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(256);
        Label progressLabel = new Label("0/0");
        StackPane progressPane = new StackPane();
        progressPane.getChildren().addAll(progressBar, progressLabel);

        // Assemble rightVBox
        rightVBox.getChildren().addAll(uidBox, imageView, progressPane);

        // Assemble root
        root.getChildren().addAll(leftVBox, rightVBox);

        // Set the scene
        Scene scene = new Scene(root, 750, 500);
        primaryStage.setTitle("FM24 Newgen Face Mapper");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}