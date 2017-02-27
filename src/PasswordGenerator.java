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

    @Override
    public void start(Stage stage) {
        initUI(stage);
    }

    private void initUI(Stage stage) {
        root = new GridPane();
        root.setVgap(10);
        root.setHgap(5);
        root.setPadding(new Insets(10));

//        createMenus(stage);
//        root.getChildren().add(menuBar);

        // Column constraints
        ColumnConstraints cons1 = new ColumnConstraints();
        cons1.setHgrow(Priority.NEVER);
        root.getColumnConstraints().add(cons1);

        ColumnConstraints cons2 = new ColumnConstraints();
        cons2.setHgrow(Priority.ALWAYS);

        root.getColumnConstraints().addAll(cons1, cons2);

        // Row constraints
        RowConstraints rcons1 = new RowConstraints();
        rcons1.setVgrow(Priority.NEVER);

        RowConstraints rcons2  = new RowConstraints();
        rcons2.setVgrow(Priority.ALWAYS);

        root.getRowConstraints().addAll(rcons1, rcons2);

        addBoxes();

        Scene scene = new Scene(root, 600, 350);

        stage.setTitle("Password Generator");
        stage.setScene(scene);

        stage.show();
    }

    private void createMenus(Stage stage) {
        menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(stage.widthProperty());

        // Top level
        Menu fileMenu = new Menu("_File");
        Menu helpMenu = new Menu("_Help");

        // Menu items
        MenuItem exitMi = new MenuItem("_Exit");
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
        Label lblSeed = new Label("Seed:");
        Label lblOffset = new Label("Offset:");

        TextField fieldSeed = new TextField();
        TextField fieldOffset = new TextField();

        root.add(lblSeed, 0,1);
        root.add(fieldSeed, 1,1,3,1);

        root.add(lblOffset, 0,2);
        root.add(fieldOffset, 1,2,3,1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
