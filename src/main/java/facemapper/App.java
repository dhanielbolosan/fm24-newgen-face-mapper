package facemapper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class for application GUI
 */
public class App extends Application {

    /**
     * Creates the stage, scene, and scene graph
     * Handles GUI events
     *
     * @param primaryStage App stage of GUI
     * @throws Exception Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

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
        modeBox.getChildren().addAll(modeLabel, modeComboBox);
        Label modeInfoLabel = new Label("");

        // Reset & Map Faces & Hidden Stop Button
        HBox buttonsBox = new HBox(5);
        Button resetButton = new Button("Reset");
        Button mapFacesButton = new Button("Map Faces");
        Button stopButton = new Button("Stop");
        stopButton.setDisable(true);
        buttonsBox.getChildren().addAll(resetButton, mapFacesButton, stopButton);

        // Assemble leftVBox
        leftVBox.getChildren().addAll(rtfFileBox, graphicsDirBox, modeBox, modeInfoLabel, buttonsBox);

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

        // Set default image
        Image defaultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/newgen.png")));
        imageView.setImage(defaultImage);

        // Progress bar
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(256);
        Label progressLabel = new Label("0/0");
        StackPane progressPane = new StackPane();
        progressPane.getChildren().addAll(progressBar, progressLabel);

        // Assemble rightVBox
        rightVBox.getChildren().addAll(uidBox, imageView, progressPane);

        // GUI Actions //

        // Rtf Browse Button, validate .rtf extension
        rtfBrowseButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select RTF File");
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                if (!file.getName().toLowerCase().endsWith(".rtf")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a valid RTF file.");
                    alert.show();
                } else {
                    rtfFileTextField.setText(file.getAbsolutePath());
                }
            }
        });

        // Graphics Directory Browse Button, validate path
        graphicsDirBrowseButton.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Image Directory");
            File selectedDir = directoryChooser.showDialog(primaryStage);
            if (selectedDir != null) {
                String path = selectedDir.getAbsolutePath();

                if (!path.contains(File.separator + "graphics" + File.separator)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a folder inside the 'graphics' directory.");
                    alert.show();
                    return;
                }
                graphicsDirTextField.setText(selectedDir.getAbsolutePath());
            }
        });

        // Mode Combo Box action, change text
        modeComboBox.setOnAction(e -> {
            String selectedMode = modeComboBox.getValue();
            if (selectedMode.equals("Overwrite")) {
                modeInfoLabel.setText("Replaces all existing mappings with new ones.");
            } else if (selectedMode.equals("Preserve")) {
                modeInfoLabel.setText("Keeps existing mappings and only adds missing ones.");
            } else {
                modeInfoLabel.setText("");
            }
        });

        // Reset Button action, reset fields
        resetButton.setOnAction(e -> {
            rtfFileTextField.clear();
            graphicsDirTextField.clear();
            modeComboBox.getSelectionModel().clearSelection();
            modeInfoLabel.setText("");
            uidTextField.clear();
            progressBar.progressProperty().unbind();
            progressBar.setProgress(0);
            progressLabel.textProperty().unbind();
            progressLabel.setText("0/0");
            imageView.setImage(defaultImage);
        });

        // Map Faces button action
        mapFacesButton.setOnAction(e -> {
            // Initialize rtf path, graphics directory path, and mode
            String rtfPath = rtfFileTextField.getText();
            String graphicsDirPath = graphicsDirTextField.getText();
            String modeValue = modeComboBox.getValue();

            // Validate paths once again to make sure .rtf file is chosen and correct graphics directory
            if (!rtfPath.toLowerCase().endsWith(".rtf")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid RTF file selected.");
                alert.show();
                return;
            }
            if (!graphicsDirPath.contains(File.separator + "graphics" + File.separator) || graphicsDirPath.endsWith(File.separator + "graphics")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid graphics folder selected. Please choose a subfolder inside 'graphics'.");
                alert.show();
                return;
            }

            // Error handling, all fields must be filled
            if (rtfPath.isEmpty() || modeValue == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select RTF file, graphics directory, and mode");
                alert.show();
                return;
            }

            // Determine if overwrite or preserve
            XmlWriter.MappingMode mode;
            if (modeValue.equals("Overwrite")) {
                mode = XmlWriter.MappingMode.OVERWRITE;
            } else {
                mode = XmlWriter.MappingMode.PRESERVE;
            }

            // Disable buttons during processing & Show stop button
            mapFacesButton.setDisable(true);
            resetButton.setDisable(true);
            stopButton.setDisable(false);

            // Background task
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    // Initialize parser and writer
                    RtfParser parser = new RtfParser();
                    XmlWriter writer = new XmlWriter();

                    // Parse players
                    List<Player> players = parser.parseRtf(rtfPath);
                    int total = players.size();
                    int processed = 0;

                    // If mode is preserve, parse current config.xml
                    Map<String, Map<String, String>> existingMappings = null;
                    if (mode == XmlWriter.MappingMode.PRESERVE) {
                        XmlParser xmlParser = new XmlParser();
                        existingMappings = xmlParser.parseXml(graphicsDirPath + "/config.xml");
                    }

                    // Iterate through all players
                    for (Player player : players) {
                        // Break if cancelled
                        if (isCancelled()) break;

                        // Skip existing players if preserve
                        if (mode == XmlWriter.MappingMode.PRESERVE && existingMappings != null && existingMappings.containsKey(player.getUid())) {
                            processed++;
                            updateProgress(processed, total);
                            updateMessage(processed + "/" + total);
                            continue;
                        }

                        // Determine folders & random image
                        String folderName = NationalityMapper.getFolder(player.getNationality(), player.getSecondNationality());
                        player.setFolder(folderName);

                        // Get folder path
                        File folder = new File(graphicsDirPath, folderName);
                        String imagePath = null;
                        if (folder.exists() && folder.isDirectory()) {
                            imagePath = XmlWriter.getRandomImage(folder.getAbsolutePath());

                        }

                        final String uid = player.getUid();
                        final String currImagePath = imagePath;

                        // Batch update JavaFX ui, set uid text and current image path
                        if (processed % 1000 == 0) {
                            Platform.runLater(() -> {
                                uidTextField.setText(uid);
                                if (currImagePath != null) {
                                    imageView.setImage(new Image(new File(currImagePath).toURI().toString()));
                                }
                            });
                        }

                        // Update processed
                        processed++;
                        updateProgress(processed, total);
                        updateMessage(processed + "/" + total);
                    }
                    // Write to XML
                    writer.writeXml(players, mode, existingMappings, graphicsDirPath);
                    return null;
                }
            };
            // Bind progress bar and label
            progressBar.progressProperty().bind(task.progressProperty());
            progressLabel.textProperty().bind(task.messageProperty());

            // Task succeeded, fix GUI and show alert
            task.setOnSucceeded(ev -> {
                mapFacesButton.setDisable(false);
                resetButton.setDisable(false);
                stopButton.setDisable(true);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Mapping completed successfully!");
                successAlert.show();
            });
            // Task failed, fix GUI and show alert
            task.setOnFailed(ev -> {
                mapFacesButton.setDisable(false);
                resetButton.setDisable(false);
                stopButton.setDisable(true);
                Throwable error = task.getException();
                Alert errorAlert;
                if (error != null) {
                    errorAlert = new Alert(Alert.AlertType.ERROR, "Mapping failed: " + error.getMessage());
                } else {
                    errorAlert = new Alert(Alert.AlertType.ERROR, "Mapping failed: Unknown error");
                }
                errorAlert.show();
            });
            // Task cancelled, fix GUI and show alert
            task.setOnCancelled(ev -> {
                mapFacesButton.setDisable(false);
                resetButton.setDisable(false);
                stopButton.setDisable(true);
                Alert cancelAlert = new Alert(Alert.AlertType.INFORMATION, "Mapping was stopped.");
                cancelAlert.show();
            });
            // Stop button logic, cancel task
            stopButton.setOnAction(e2 -> task.cancel());

            new Thread(task).start();
        });

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