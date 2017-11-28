/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailclient;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Francesco
 */
public class FXMLDocumentMailController implements Initializable {
    
    private static Model model;
    
    @FXML 
    private javafx.scene.control.Button closeButton;
    @FXML
    private TextArea textText; 
    @FXML
    private TextField textObject;
    @FXML
    private TextField textSender;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        
    }

    @FXML
    private void handleSendAction(javafx.event.ActionEvent event) {
        model = Model.get();
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Mail Send");
        
        //Gettin e-mail information
        String mittente = model.getAccount();             
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        String testo = textText.getText();
        String oggetto = textObject.getText();
        String destinatario = textSender.getText(); 
        
        String[] split = destinatario.split(",");
        
        //Se ci sono più destinatari invio tante e-mail quanti sono 
        if(split.length > 1){
            for (String email : split) {
                Mail mail = new Mail(mittente, email.trim(), testo, oggetto, date);
                model.addMail(model.getMailsSended(), mail); 
            }
            alert.setHeaderText("Mails Send");
        } else if (split.length == 1){
            Mail mail = new Mail(mittente, destinatario, testo, oggetto, date);
            model.addMail(model.getMailsSended(), mail);
            alert.setHeaderText("Mail Send");
        } else {
            alert.setHeaderText("Mail not send, wrong sender!");
            alert.showAndWait();
        }
        
        alert.showAndWait();
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleDeleteAction(javafx.event.ActionEvent event) {
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    
    public void setReceiver(String sender){
        this.textSender.setText(sender);
    }

    public void setText(String text){
        this.textText.setText(text);
    }
    
    
}
