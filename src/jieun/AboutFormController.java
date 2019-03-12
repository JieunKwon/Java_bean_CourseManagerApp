////////////////////////////////////////////////////////////////////////////////////////////
//      Java 2 : Final Project
//      Task : Sheridan Course Inventory - About Window
////////////////////////////////////////////////////////////////////////////////////////////
//     - current src file : AboutFormController.java
//     - current src task : Controller of About
//     - created by : Jieun Kwon
//     - created date : April 10, 2018
//     - modified date :  
////////////////////////////////////////////////////////////////////////////////////////////

package jieun;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable; 
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author JIEUN KWON
 */
public class AboutFormController implements Initializable {

    Stage stage = new Stage(); 
    @FXML
    private Label lblInfomation;
    @FXML
    private ImageView ivLogo;
    @FXML
    private Label lblInfo2;
    
    /////////////////////////////////////////////////////////////////
    // initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try{
            // set image
            Image image = new Image(getClass().getResource("icon.png").toString());
            ivLogo.setImage(image);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        // label value
        lblInfomation.setText("Sheridan Course Inventory 1.0");
        
        // label value (style - italic)
        lblInfo2.setText("Copyright 2018. JI EUN KWON");
    }    

    /////////////////////////////////////////////////////////////////
    // set Stage
    public void setStage(Stage stage){
        this.stage = stage;
    }
    
    /////////////////////////////////////////////////////////////////
    // Close
    @FXML
    private void handleButtonClose(ActionEvent event) {
        
        if(stage != null)
            stage.close();
    }
    
}
