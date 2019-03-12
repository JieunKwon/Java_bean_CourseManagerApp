////////////////////////////////////////////////////////////////////////////////////////////
//      Java 2 : Final Project
//      Task : Sheridan Course Inventory
////////////////////////////////////////////////////////////////////////////////////////////
//     - current src file : CourseInventory.java
//     - current src task :  
//     - created by : Jieun Kwon
//     - created date : March 25, 2018
//     - modified date : March 25, 2018
////////////////////////////////////////////////////////////////////////////////////////////

package jieun;

import java.net.URL;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author JIEUN KWON
 */
public class CourseInventory extends Application {
    
    // Start 
    @Override
    public void start(Stage stage) throws Exception {
        
        // load FXML file
        //Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        URL url = getClass().getResource("MainForm.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = (Parent)loader.load();
        
        // create scene
        Scene scene = new Scene(root);
        
        // style sheet
        scene.getStylesheets().add(getClass().getResource("course.css").toString());
        
        // set scene
        stage.setScene(scene);
        
        // Stage title
        stage.setTitle("Sheridan Course Inventory");
        
        // Stage Icon
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon_s.png")));
        
        // Stage show
        stage.show();
        
        // pass the owner stage to the controller object
        MainFormController controller = loader.getController();
        controller.setStage(stage);
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
