package application;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.PauseTransition;

public class Main extends Application { 
	// Declare the HuffmanTable array to store the table for Huffman encoding and decoding
	@SuppressWarnings("unused")
	private HuffmanTable[] huffmanTable; 
	// Declare variables to store the files for compression and output
	private File selectedFile;
	private File outputFile;
	// TextArea to display content (probably for showing file content or messages)
	TextArea Content = new TextArea();
	// Another output file variable (seems unused here)
	private File outputFile1;

	@Override
	public void start(Stage primaryStage) {
		// Create and style the title label
		Label titlelabel = new Label("- File compression and decompression System -");
		titlelabel.setAlignment(Pos.TOP_CENTER);  // Align the label at the top
		titlelabel.setPadding(new Insets(20, 0, 0, 0)); // Add padding on top to center the title
		titlelabel.setStyle("-fx-text-fill: #008B8B; "); // Set the text color
		titlelabel.setFont(new Font("Impact", 20)); // Set the font style and size
		
		// Load the image for the title screen (a GIF image)
		Image image = new Image("file:intro.gif");
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(800);  // Set the image width
		imageView.setFitHeight(800); // Set the image height
		imageView.setPreserveRatio(true); // Maintain the aspect ratio of the image

		// Create a StackPane to hold the image and other components
		Pane pane = new StackPane();

		// Create a "Start" button to begin the process
		Button start = new Button("Start");
		// Set up a horizontal box to align the button
		HBox boxstart = new HBox(start);
		boxstart.setAlignment(Pos.BOTTOM_CENTER); // Align the button at the bottom center
		boxstart.setPadding(new Insets(0, 0, 20, 580)); // Add padding to place it in the correct position

		// Style the button with a gradient background and other properties
		start.setStyle("-fx-font-size: 20px; " 
						+ "-fx-background-color: linear-gradient(#00BFFF, #5F9EA0); "
						+ "-fx-font-weight: bold; "
						+ "-fx-text-fill: #008B8B; " 
						+ "-fx-cursor: hand; " 
						+ "-fx-border-width: 2px; "
						+ "-fx-padding: 2px 2px;" 
						+ "-fx-border-radius: 10px;");

		// Add an effect when the mouse hovers over the button (increases font size and changes colors)
		start.setOnMouseEntered(
				e -> start.setStyle("-fx-font-size: 22px; " 
						+ "-fx-background-color: linear-gradient(#5F9EA0, #00BFFF); "
						+ "-fx-font-weight: bold; "
						+ "-fx-text-fill: #00BFFF; "
						+ "-fx-cursor: hand; "
						+ "-fx-border-width: 2px; "
						+ "-fx-padding: 2px 2px;" 
						+ "-fx-border-radius: 10px;"));

		// Revert the button style when the mouse exits
		start.setOnMouseExited(
				e -> start.setStyle("-fx-font-size: 20px; " 
						+ "-fx-background-color: linear-gradient(#00BFFF, #5F9EA0); "
						+ "-fx-font-weight: bold; "
						+ "-fx-text-fill: #008B8B; "
						+ "-fx-cursor: hand; "
						+ "-fx-border-width: 2px; "
						+ "-fx-padding: 2px 2px;" 
						+ "-fx-border-radius: 10px;"));
	

		start.setOnAction(e -> {
		    // When the 'Start' button is clicked, it shows a loading screen
		    // Create a GIF image to show during the loading process
		    Image gifImage = new Image("file:load.gif");
		    ImageView gifView = new ImageView(gifImage);

		    gifView.setFitWidth(800);  // Set the width of the image
		    gifView.setFitHeight(800); // Set the height of the image
		    gifView.setPreserveRatio(true); // Keep the image's aspect ratio intact

		    // Create a StackPane to display the GIF
		    StackPane loadPane = new StackPane(gifView);
		    Scene loadScene = new Scene(loadPane, 720, 540); // Create a scene for the loading screen with size 720x540

		    // Set the new loading scene and title for the stage (window)
		    primaryStage.setScene(loadScene);
		    primaryStage.setTitle("Loading...");
		    primaryStage.show(); // Show the loading screen

		    // Add a delay of 3 seconds to simulate the loading process
		    PauseTransition delay = new PauseTransition(Duration.seconds(3));
		    delay.setOnFinished(event -> {
		        // After the delay, initialize the HuffmanTable and Decoding objects
		        huffmanTable = new HuffmanTable[256]; // Initialize the Huffman table with a size of 256 (2^8 for 8-bit)

		        // Create a new scene for the main content after loading
		        Label title = new Label("Welcome to file compressed and decompressed system!");
		        title.setFont(new Font("Impact", 20)); // Set the font style and size
		        title.setTextFill(Color.web("00BFFF")); // Set the text color
		        title.setAlignment(Pos.CENTER); // Center align the title
		        title.setPadding(new Insets(35, 0, 0, 120)); // Add padding to the title

		        // Label to guide the user to choose a file
		        Label chooseFile = new Label("choose file that you want \n to do");
		        chooseFile.setFont(new Font("Impact", 15)); // Set the font size and style
		        chooseFile.setTextFill(Color.web("00BFFF")); // Set the text color
		        chooseFile.setAlignment(Pos.CENTER); // Center align the text

		        // Create buttons for decompressing and compressing files
		        Button Decompressed = new Button("Decompressed");
		        Decompressed.setPrefWidth(110); // Set preferred width for the button
		        Decompressed.setPrefHeight(15); // Set preferred height for the button
		        // Style the button with gradient colors, font, and border radius
		        Decompressed.setStyle("-fx-font-size: 15px; " + "-fx-background-color: linear-gradient(#00BFFF,#5F9EA0); "
		                + "-fx-font-weight: bold; " + "-fx-text-fill: #008B8B; " + "-fx-cursor: hand; "
		                + "-fx-border-width: 2px; " + "-fx-padding: 2px 2px;" + "-fx-border-radius: 10px;");

		        // Add hover effects for the 'Decompressed' button
		        Decompressed.setOnMouseEntered(x -> Decompressed
		                .setStyle("-fx-font-size: 17px; " + "-fx-background-color: linear-gradient(#5F9EA0,#00BFFF); "
		                        + "-fx-font-weight: bold; " + "-fx-text-fill: #00BFFF; " + "-fx-cursor: hand; "
		                        + "-fx-border-width: 2px; " + "-fx-padding: 2px 2px;" + "-fx-border-radius: 10px;"));
		        Decompressed.setOnMouseExited(x -> Decompressed
		                .setStyle("-fx-font-size: 15px; " + "-fx-background-color: linear-gradient(#00BFFF,#5F9EA0); "
		                        + "-fx-font-weight: bold; " + "-fx-text-fill: #008B8B; " + "-fx-cursor: hand; "
		                        + "-fx-border-width: 2px; " + "-fx-padding: 2px 2px;" + "-fx-border-radius: 10px;"));

		        // Create a 'Compress' button with similar styling and hover effects
		        Button Compress = new Button("Compress");
		        Compress.setPrefWidth(110);
		        Compress.setPrefHeight(15);
		        Compress.setStyle("-fx-font-size: 15px; " + "-fx-background-color: linear-gradient(#00BFFF,#5F9EA0); "
		                + "-fx-font-weight: bold; " + "-fx-text-fill: #008B8B; " + "-fx-cursor: hand; "
		                + "-fx-border-width: 2px; " + "-fx-padding: 2px 2px;" + "-fx-border-radius: 10px;");
		        Compress.setOnMouseEntered(x -> Compress
		                .setStyle("-fx-font-size: 17px; " + "-fx-background-color: linear-gradient(#5F9EA0,#00BFFF); "
		                        + "-fx-font-weight: bold; " + "-fx-text-fill: #00BFFF; " + "-fx-cursor: hand; "
		                        + "-fx-border-width: 2px; " + "-fx-padding: 2px 2px;" + "-fx-border-radius: 10px;"));
		        Compress.setOnMouseExited(x -> Compress
		                .setStyle("-fx-font-size: 15px; " + "-fx-background-color: linear-gradient(#00BFFF,#5F9EA0); "
		                        + "-fx-font-weight: bold; " + "-fx-text-fill: #008B8B; " + "-fx-cursor: hand; "
		                        + "-fx-border-width: 2px; " + "-fx-padding: 2px 2px;" + "-fx-border-radius: 10px;"));

		        // Place the 'Compress' and 'Decompressed' buttons inside a VBox for vertical layout
		        VBox b = new VBox(10, Compress, Decompressed);
		        b.setPadding(new Insets(0, 0, 0, 20)); // Add padding to the VBox

		        // Create a VBox to hold the 'choose file' label and the buttons
		        VBox fileBox = new VBox(15, chooseFile, b);
		        fileBox.setPadding(new Insets(140, 0, 0, 20)); // Add padding to the VBox

		        // Create buttons for 'Huffman', 'statistic', and 'Header' with similar styles
		        // These buttons are currently disabled but can be enabled later if needed
		        Button DTable = new Button("Huffman");
		        DTable.setDisable(true);
		        DTable.setStyle("-fx-font-size: 16px; " + "-fx-background-color: linear-gradient(#00BFFF,#5F9EA0); "
		                + "-fx-font-weight: bold; " + "-fx-text-fill: #008B8B; " + "-fx-cursor: hand; "
		                + "-fx-border-width: 2px; " + "-fx-padding: 2px 2px;" + "-fx-border-radius: 10px;");
		        // Add hover effects for the 'Huffman' button
		        DTable.setOnMouseEntered(x -> DTable
		                .setStyle("-fx-font-size: 18px; " + "-fx-background-color: linear-gradient(#5F9EA0,#00BFFF); "
		                        + "-fx-font-weight: bold; " + "-fx-text-fill: #00BFFF; " + "-fx-cursor: hand; "
		                        + "-fx-border-width: 2px; " + "-fx-padding: 2px 2px;" + "-fx-border-radius: 10px;"));
		        DTable.setOnMouseExited(x -> DTable
		                .setStyle("-fx-font-size: 16px; " + "-fx-background-color: linear-gradient(#00BFFF,#5F9EA0); "
		                        + "-fx-font-weight: bold; " + "-fx-text-fill: #008B8B; " + "-fx-cursor: hand; "
		                        + "-fx-border-width: 2px; " + "-fx-padding: 2px 2px;" + "-fx-border-radius: 10px;"));

		        // Similar buttons for 'statistic' and 'header', all disabled for now
		        Button statistic = new Button("statistic");
		        statistic.setDisable(true);
		        statistic.setStyle("-fx-font-size: 16px; " + "-fx-background-color: linear-gradient(#00BFFF,#5F9EA0); "
		                + "-fx-font-weight: bold; " + "-fx-text-fill: #008B8B; " + "-fx-cursor: hand; "
		                + "-fx-border-width: 2px; " + "-fx-padding: 2px 2px;" + "-fx-border-radius: 10px;");
		        // Hover effects for the 'statistic' button
		        statistic.setOnMouseEntered(x -> statistic
		                .setStyle("-fx-font-size: 18px; " + "-fx-background-color: linear-gradient(#5F9EA0,#00BFFF); "
		                        + "-fx-font-weight: bold; " + "-fx-text-fill: #00BFFF; " + "-fx-cursor: hand; "
		                        + "-fx-border-width: 2px; " + "-fx-padding: 2px 2px;" + "-fx-border-radius: 10px;"));
		        statistic.setOnMouseExited(x -> statistic
		                .setStyle("-fx-font-size: 16px; " + "-fx-background-color: linear-gradient(#00BFFF,#5F9EA0); "
		                        + "-fx-font-weight: bold; " + "-fx-text-fill: #008B8B; " + "-fx-cursor: hand; "
		                        + "-fx-border-width: 2px; " + "-fx-padding: 2px 2px;" + "-fx-border-radius: 10px;"));

		        // Similarly style and add hover effects to the 'Header' button
		        Button header = new Button("Header");
		        header.setDisable(true);
		        header.setStyle("-fx-font-size: 16px; " + "-fx-background-color: linear-gradient(#00BFFF,#5F9EA0); "
		                + "-fx-font-weight: bold; " + "-fx-text-fill: #008B8B; " + "-fx-cursor: hand; "
		                + "-fx-border-width: 2px; " + "-fx-padding: 2px 2px;" + "-fx-border-radius: 10px;");
		        header.setOnMouseEntered(x -> header
		                .setStyle("-fx-font-size: 18px; " + "-fx-background-color: linear-gradient(#5F9EA0,#00BFFF); "
		                        + "-fx-font-weight: bold; " + "-fx-text-fill: #00BFFF; " + "-fx-cursor: hand; "
		                        + "-fx-border-width: 2px; " + "-fx-padding: 2px 2px;" + "-fx-border-radius: 10px;"));
		        header.setOnMouseExited(x -> header
		                .setStyle("-fx-font-size: 16px; " + "-fx-background-color: linear-gradient(#00BFFF,#5F9EA0); "
		                        + "-fx-font-weight: bold; " + "-fx-text-fill: #008B8B; " + "-fx-cursor: hand; "
		                        + "-fx-border-width: 2px; " + "-fx-padding: 2px 2px;" + "-fx-border-radius: 10px;"));

		        // Create a horizontal box (HBox) to hold the operation buttons (DTable, statistic, header)
		        HBox operationBox = new HBox(20, DTable, statistic, header);
		        operationBox.setAlignment(Pos.CENTER); // Center align the buttons

		        // Set text wrapping and preferred size for the content area
		        Content.setWrapText(true);
		        Content.setPrefWidth(500);
		        Content.setPrefHeight(350);
		        Content.setEditable(false); // Make the content area non-editable
		
		     // When the 'Compress' button is clicked
		        Compress.setOnAction(x -> {
		            fileCompress();  // Call the fileCompress method (compress the file)
		            Content.setText(" "); // Clear any existing text in the content area

		            try {
		                // Perform Huffman encoding on the selected file and get the output file
		                outputFile = Hufman.encoding(selectedFile);

		                // Set an action for the 'DTable' (Huffman Table) button
		                DTable.setOnAction(l -> {
		                    try {
		                        // Display the headers for the Huffman table information
		                        Content.setText("Character\t\tASCII\t\t Frequency\tCode Length\t\tHuffman Code\n");

		                        // Loop through the Huffman array and display the character data
		                        for (int i = 0; i < Hufman.array.length; i++) {
		                            // Skip tabs and newlines in the table
		                            if ((int) Hufman.array[i].getCharacter() == '\t' || (int) Hufman.array[i].getCharacter() == '\n')
		                                continue;

		                            // Append each character's Huffman code information to the content area
		                            Content.appendText("\t" + Hufman.array[i].getCharacter() + "\t\t\t"
		                                    + String.valueOf((int) Hufman.array[i].getCharacter()) + "\t\t\t"
		                                    + String.valueOf(Hufman.array[i].getFrequency()) + "\t\t\t"
		                                    + Hufman.array[i].getCodeLength() + "\t\t\t\t" + Hufman.array[i].getHuffCode() + "\n");
		                        }
		                    } catch (Exception e1) {
		                        e1.printStackTrace(); // Handle any exceptions during the process
		                    }
		                });

		                // Set an action for the 'statistic' button to show file statistics
		                statistic.setOnAction(l -> {
		                    try {
		                        Content.setText(" "); // Clear the content area before displaying statistics
		                        if (selectedFile != null) {
		                            // Get file statistics and append them to the content area
		                            String statInfo = Hufman.statistic(selectedFile, outputFile);
		                            Content.appendText(statInfo);
		                        } else {
		                            showAlert("An error occurred, check out your files!!!"); // Show alert if no file is selected
		                        }
		                    } catch (Exception a) {
		                        showAlert("Error while choosing file, check out your files!!"); // Handle file selection errors
		                    }
		                });

		                // Set an action for the 'header' button to display file header information
		                header.setOnAction(l -> {
		                    try {
		                        Content.setText(" "); // Clear the content area before displaying header
		                        if (selectedFile != null) {
		                            // Get file header information and append it to the content area
		                            String headerInfo = Hufman.header(selectedFile.getPath(), outputFile.getPath(), (int) selectedFile.length(), Hufman.harrSize);
		                            Content.appendText(headerInfo);
		                        } else {
		                            showAlert("An error occurred, check out your files!!!"); // Show alert if no file is selected
		                        }
		                    } catch (Exception a) {
		                        showAlert("Error while choosing file, check out your files!!"); // Handle file selection errors
		                    }
		                });

		                // Enable the 'DTable', 'statistic', and 'header' buttons after compressing
		                DTable.setDisable(false);
		                statistic.setDisable(false);
		                header.setDisable(false);

		            } catch (Exception e1) {
		                e1.printStackTrace(); // Handle any exceptions that occur during compression
		            }
		        });

		     // When the 'Decompressed' button is clicked
		        Decompressed.setOnAction(x -> {
		            fileDecompress();  // Call the fileDecompress method (decompress the file)
		            Content.setText("");  // Clear any existing text in the content area

		            // Check if a valid file is selected and exists
		            if (selectedFile != null && selectedFile.exists()) {
		                try {
		                    // Perform Huffman decompression on the selected file and get the output file
		                    outputFile1 = Hufman.deCompress(selectedFile);

		                    // Inform the user that the file was decompressed successfully
		                    Content.appendText("File decompressed successfully!\n\n");

		                    // Get and display the header information for the decompressed file
		                    String headerInfo = Hufman.DeComp(selectedFile, outputFile1);
		                    Content.appendText(headerInfo);  // Append header info to the content area

		                } catch (Exception ll) {
		                    // Handle any errors during decompression and show an alert
		                    showAlert("Error during decompression: " + ll.getMessage());
		                    ll.printStackTrace();  // Print stack trace for debugging
		                }
		            } else {
		                // If no valid file is selected, show an alert
		                showAlert("No valid file selected for decompression!");
		            }

		            // Disable the buttons related to compression and table operations after decompression
		            DTable.setDisable(true);
		            statistic.setDisable(true);
		            header.setDisable(true);
		        });

				
				
		     // Create a VBox (vertical box) to arrange the content and operation buttons with a gap of 20 pixels
		        VBox b1 = new VBox(20, Content, operationBox);
		        b1.setPadding(new Insets(0, 20, 0, 0)); // Add padding to the VBox for spacing

		        // Create a HBox (horizontal box) to arrange fileBox and b1 horizontally with a gap of 25 pixels
		        HBox b2 = new HBox(25, fileBox, b1);

		        // Create a VBox to arrange the title and b2 vertically with a gap of 30 pixels
		        VBox b3 = new VBox(30, title, b2);

		        // Create a StackPane to overlay components (background and VBox)
		        Pane pane11 = new StackPane();

		        // Load the background image for the StackPane from the file path "back1.gif"
		        Image image11 = new Image("file:back1.gif");
		        ImageView imageView11 = new ImageView(image11);
		        imageView11.setFitWidth(700);  // Set the image width to 700 pixels
		        imageView11.setFitHeight(700);  // Set the image height to 700 pixels
		        imageView11.setPreserveRatio(true);  // Preserve the image aspect ratio when resizing
		        // Add the background image and the VBox (b3) to the StackPane
		        pane11.getChildren().addAll(imageView11, b3);

		        // Set the background color of the StackPane to black
		        pane11.setStyle("-fx-background-color: black;");

		        // Create a new Scene with the StackPane, setting its dimensions to 720x540
		        Scene scene11 = new Scene(pane11, 720, 540);

		        // Set the newly created scene to the primary stage and give it a title
		        primaryStage.setScene(scene11);
		        primaryStage.setTitle("File Management System");

		        // Show the primary stage with the scene
		        primaryStage.show();
		    });
		        // Start the delay (transition effect) to proceed with further actions
		        delay.play();


		});
		Button home = new Button("←");
		home.setAlignment(Pos.BOTTOM_RIGHT);
		home.setStyle("-fx-font-size: 70px; " + "-fx-background-color: black; "
                 + "-fx-font-weight: bold; " + "-fx-text-fill: #00BFFF; " + "-fx-cursor: hand; "
                 + "-fx-border-width: none; " + "-fx-padding: 2px 2px;");

		// Create a VBox to arrange the title and button vertically
		VBox vbox = new VBox(450, titlelabel, boxstart);
		vbox.setAlignment(Pos.CENTER); // Align the VBox contents to the left

		// Add the logo and VBox to the StackPane
		pane.getChildren().addAll(imageView, vbox);

		// Create a scene with the StackPane and set its dimensions
		Scene scene = new Scene(pane, 720, 540);
		primaryStage.setScene(scene); // Set the scene to the primary stage
		primaryStage.setTitle("Main Screen"); // Set the window title
		primaryStage.setResizable(false); // Make the window size fixed
		primaryStage.show(); // Show the primary stage

	}

	private void fileCompress() {
		FileChooser fileChooser = new FileChooser();
		Stage stage = new Stage();// new stage fore file chooser
		selectedFile = fileChooser.showOpenDialog(stage);// open the file chooser

		if (selectedFile != null) {
			if (selectedFile.getName().endsWith(".huff")) {
				// Show an error message if the file is of type .huff
				showAlert("Error: .huff files are not allowed for compression.");
			} else {
				// If the file is not of type .huff, process it
				showAlert("File selected: " + selectedFile.getAbsolutePath());
				// Add code here to process the file for compression
			}
		}else {
			showAlert("error while selecting file be carefull!!!!");// error massege while choosign the file
		}
	}



	private void fileDecompress() {
		// Create a new file chooser for decompression
		FileChooser fileChooserDecompress = new FileChooser();

		// Add filter to allow only .huff files
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Huff Files (*.huff)", "*.huff");
		fileChooserDecompress.getExtensionFilters().add(extFilter);
		Stage stage = new Stage();

		// Open the file chooser dialog
		selectedFile = fileChooserDecompress.showOpenDialog(stage);

		// Check if a file was selected
		if (selectedFile != null) {
			showAlert("File selected: " + selectedFile.getAbsolutePath());
		}

	}


	// This method is used to show an alert with a custom message
	private void showAlert(String message) {
	    // Create a new alert of type INFORMATION (used for informational messages)
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    
	    // Set the header text of the alert to null (no header will be displayed)
	    alert.setHeaderText(null);
	    
	    // Set the content text of the alert to the message passed as a parameter
	    alert.setContentText(message);
	    
	    // Show the alert and wait for the user to close it before continuing
	    alert.showAndWait();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
