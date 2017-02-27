import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by kai-w on 26/02/17.
 */
public class PasswordGenerator extends Application {
    MenuBar menuBar;
    GridPane root;
    GridPane contentArea;
    Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        initUI();
    }

    private void initUI() {
        root = new GridPane();
        root.setHgap(10);
        root.setVgap(20);

        generateCols();
        createMenus();
        addBoxes();


        Scene scene = new Scene(root, 600, 350);
        stage.setTitle("Password Generator");
        stage.setScene(scene);
        stage.show();
    }

    private void generateCols() {
        ColumnConstraints col14 = new ColumnConstraints();
        col14.setPercentWidth(20);
        ColumnConstraints col23 = new ColumnConstraints();
        col23.setPercentWidth(30);

        root.getColumnConstraints().addAll(col14, col23, col23, col14);
    }

    private void createMenus() {
        menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(stage.widthProperty());

        // Top level
        Menu fileMenu = new Menu("_File");
        Menu helpMenu = new Menu("_Help");

        // Menu items
        MenuItem exitMi = new MenuItem("Exit");
        MenuItem aboutMi = new MenuItem("About");
        MenuItem helpMi = new MenuItem("Help");


        // Add actions
        exitMi.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });
        aboutMi.setOnAction((ActionEvent event) -> {
            createAboutModal();
        });
        helpMi.setOnAction((ActionEvent event) -> {
            createHelpModal();
        });

        // Add items to menus
        fileMenu.getItems().addAll(exitMi);
        helpMenu.getItems().addAll(aboutMi, helpMi);

        // Add menus to menu bar
        menuBar.getMenus().addAll(fileMenu, helpMenu);

        root.add(menuBar, 0,0,4,1);
    }

    private void createAboutModal() {
        String content = "This is a GUI version of my password generator program. I built this using JavaFX as a means of learning the platform.";

        Stage stageAbout = new Stage();

        HBox rootAbout = new HBox();
        rootAbout.setPadding(new Insets(10));

        Text text = new Text();
        text.setText(content);
        rootAbout.getChildren().add(text);

        Scene sceneAbout = new Scene(rootAbout, 500, 200);

        stageAbout.setTitle("About - Password Generator");
        stageAbout.setScene(sceneAbout);

        text.wrappingWidthProperty().bind(sceneAbout.widthProperty().subtract(20));

        stageAbout.show();
    }
    private void createHelpModal() {
        String content = "Enter a seed and an offset in the relevant boxes, click \"Generate\", and your password will be created.";

        Stage stageHelp = new Stage();

        HBox rootHelp = new HBox();
        rootHelp.setPadding(new Insets(10));

        Text text = new Text();
        text.setText(content);
        rootHelp.getChildren().add(text);

        Scene sceneHelp = new Scene(rootHelp, 500, 200);

        stageHelp.setTitle("Help - Password Generator");
        stageHelp.setScene(sceneHelp);

        text.wrappingWidthProperty().bind(sceneHelp.widthProperty().subtract(20));

        stageHelp.show();
    }

    private void addBoxes() {
        // Create input labels
        Label lblSeed = new Label("_Seed:");
        Label lblOffset = new Label("_Offset:");

        // Create input TextFields
        TextField fieldSeed = new TextField();
        TextField fieldOffset = new TextField();

        // Set mnemonics
        lblSeed.setLabelFor(fieldSeed);
        lblSeed.setMnemonicParsing(true);
        lblOffset.setLabelFor(fieldOffset);
        lblOffset.setMnemonicParsing(true);

        // Add each row
        root.add(lblSeed, 0,1,1,1);
        root.add(fieldSeed, 1,1,3,1);

        root.add(lblOffset, 0,2,3,1);
        root.add(fieldOffset, 1,2,3,1);

        // Create button and ouptut TextField
        Button btn = new Button("Generate");
        TextField output = new TextField();
        btn.setOnAction((ActionEvent event) -> {
            output.setText(new Generator().go());
        });

        root.add(btn, 0,4,1,1);
        root.add(output,0,5,4,1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
