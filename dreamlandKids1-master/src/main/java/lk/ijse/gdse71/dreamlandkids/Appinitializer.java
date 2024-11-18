package lk.ijse.gdse71.dreamlandkids;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Appinitializer extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader
                .load(this.getClass().getResource("/view/LoginView.fxml"))));
        stage.setTitle("Dreamland Kids");
        stage.centerOnScreen();
        stage.show();
//        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LodingScreenView.fxml"))));
//        stage.show();

//        Task<Scene> loadingTask = new Task<>() {
//            @Override
//            protected Scene call() throws Exception {
//                FXMLLoader fxmlLoader = new FXMLLoader(Appinitializer.class.getResource("/view/LoginView.fxml"));
//                return new Scene(fxmlLoader.load());
//            }
//        };

//        loadingTask.setOnSucceeded(event -> {
//            Scene value = loadingTask.getValue();

//            stage.setTitle("Dreamland Kids");
//            stage.getIcons().add(new Image(getClass().getResourceAsStream("/image/app_icon.png")));
//            stage.setMaximized(true);

//            stage.setScene(value);
//        });
//        loadingTask.setOnFailed(event -> {
//            System.err.println("Failed to load the login page.");
//        });
//        new Thread(loadingTask).start();
    }
}
