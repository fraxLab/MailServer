/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailclient;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Francesco
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private TableView<Mail> mailTable;
    @FXML
    private TableColumn<Mail, String> subject;
    @FXML
    private TableColumn<Mail, String> from;
    @FXML
    private TableColumn<Mail, String> data;
    @FXML
    private TreeView treeView;
    @FXML
    public TextArea mailText;
    @FXML
    public Label showSender;
    @FXML
    public Label showDate;
    @FXML
    public Label showObject;
    @FXML
    public Button buttonSend;
    @FXML
    public Button buttonDelete;
    
    private Model model;
    
    /**
     * wirteMail SetonAction : Create a new scene to write e-mail
     * 
     * @param event : Mouse clik 
     */
    
    @FXML
    private void handleMailAction(javafx.event.ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/mailclient/FXMLDocumentMail.fxml"));
        Stage stage = new Stage();
        
        //Fill stage with content of the DocumentMail
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * heandle of answer button : Create a new scene to write e-mail
     * 
     * @param event : Mouse clik 
     */
    
    @FXML
    private void handleMailAnswerAction(javafx.event.ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        
        //mail selezionata e suo box di contenimento
        Mail obj = (Mail)mailTable.getSelectionModel().getSelectedItem();
        TreeItem selectedBox = (TreeItem) treeView.getSelectionModel().selectedItemProperty().getValue();

        if(obj != null && selectedBox != null){    
            String[] split = selectedBox.toString().split(":");
            String value = split[1].trim() + ": " + model.getMails().size();
            String value1 = split[1].trim() + ": " + "frax@gmail.com";
            // Devo assicurarmi che sto selezionando un messaggio da quelli
            // in arrivo che sono visibili anche nel caso è selezionato il treeitem account
            if(value.compareTo("Messaggi in arrivo: " + model.getMails().size()) == 0 || value1.compareTo("account: frax@gmail.com") == 0){
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mailclient/FXMLDocumentMail.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();

                    FXMLDocumentMailController controller = fxmlLoader.getController();
                    controller.setReceiver(obj.getMittente());
                    controller.setText(obj.getTesto() 
                            + "\n------------------------------------\n");
                    
                    //Open new windows with e-mail pre-compilated
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            } else {
                alert.setHeaderText("Devi selezionare un messaggio dai messaggi in arrivo!");
                alert.showAndWait();
            } 
 
        } else {
            alert.setHeaderText("Devi selezionare un messaggio!");
            alert.showAndWait();
            
        }
    }
    
    
    /**
     * wirteMail SetonAction : delete e-mail selected
     * 
     * @param event : Mouse clik 
     */
    
    @FXML
    private void handleMailDeleteAction(javafx.event.ActionEvent event) {
        Mail obj = (Mail)mailTable.getSelectionModel().getSelectedItem();
        if(model.searchBox(obj) != null)
            model.deleteMail(model.searchBox(obj),obj);
    }
    
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public FXMLDocumentController () {}
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.model = Model.get();
        MailDataObserver mailObserver = new MailDataObserver();
        model.addObserver(mailObserver);
        createTreeView();
        mailObserver.setupView(treeView);
        
        //removing lateral scroll 
        mailText.setWrapText(true);

       
        // Setting the 3 colum of the table view with the 
        subject.setCellValueFactory(new PropertyValueFactory<>("argomento"));
        from.setCellValueFactory(new PropertyValueFactory<>("mittente"));
        data.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        //Fill the mailTable with the Observable list of e-mail
        mailTable.setItems(model.getMails());
        
        //Addeded listner to the click of a row of the table 
        //using ChangeListner interface that require 2 argoments not used
        mailTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showMail(newValue));
        
    }
    
    /**
     * Show the content of the e-mail in mailText
     * 
     * @param mail : Mail selected 
     */    
    
    private void showMail(Mail mail){
        if(mail != null){
            mailText.setText(mail.getTesto());
            showDate.setText(mail.getDate());
            showSender.setText(mail.getMittente());
            showObject.setText(mail.getArgomento());
        }
    }
    
    private void createTreeView(){
        
        TreeItem<String> root = new TreeItem<>("account: " + model.getAccount());
        TreeItem<String> boxIncomingM = new TreeItem<>("Messaggi in arrivo: " + model.getMails().size());
        TreeItem<String> boxBozzeM = new TreeItem<>("Bozze: " + model.getMailsBozze().size());
        TreeItem<String> boxSendedM = new TreeItem<>("Poste inviata: " + model.getMailsSended().size());
        TreeItem<String> boxDeletedM = new TreeItem<>("Cestino: " + model.getMailsDeleted().size());
        root.getChildren().add(boxIncomingM);
        root.getChildren().add(boxBozzeM);
        root.getChildren().add(boxSendedM);
        root.getChildren().add(boxDeletedM);
        treeView.setRoot(root);
        
        //Adding listner to mouseclick on tree item
        treeView.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                TreeItem<String> selectedItem = (TreeItem<String>) newValue;            
                if(selectedItem.getValue().compareTo("Bozze: " + model.getMailsBozze().size()) == 0)
                    mailTable.setItems(model.getMailsBozze());
                if(selectedItem.getValue().compareTo("Messaggi in arrivo: " + model.getMails().size()) == 0
                        || selectedItem.getValue().compareTo("account: " + model.getAccount()) == 0)
                    mailTable.setItems(model.getMails());
                if(selectedItem.getValue().compareTo("Poste inviata: " + model.getMailsSended().size()) == 0)
                    mailTable.setItems(model.getMailsSended());
                if(selectedItem.getValue().compareTo("Cestino: " + model.getMailsDeleted().size()) == 0)
                    mailTable.setItems(model.getMailsDeleted());
               }
        });
        
        treeView.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue observable, Object oldValue, Object newValue) -> {
            TreeItem<String> selectedItem = (TreeItem<String>) newValue;
            if(selectedItem.getValue().compareTo("Messaggi in arrivo: " + model.getMails().size()) == 0
                    || selectedItem.getValue().compareTo("account: " + model.getAccount()) == 0)
                buttonSend.setVisible(true);
            else
                buttonSend.setVisible(false);
        });

    }
    
}


    

   
    

