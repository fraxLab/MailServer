/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailclient;

import java.util.Observable;
import java.util.Observer;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 *
 * @author Francesco
 */
public class MailDataObserver implements Observer{
    private final Model model;
    
    private TableView<Mail> mailTable;
    private TableColumn<Mail, String> subject;
    private TableColumn<Mail, String> from;
    private TableColumn<Mail, String> data;
    private TreeView treeView;
    
    public MailDataObserver(){
        model = Model.get();
    }
    
     public void setupView(TreeView tree) {
        this.treeView = tree;

    }

    @Override
    public void update(Observable o, Object arg) {
        TreeItem root = this.treeView.getRoot();
        //removing all items from the treeview
        root.getChildren().remove(0, root.getChildren().size());
        
        TreeItem<String> boxIncomingM = new TreeItem<>("Messaggi in arrivo: " + model.getMails().size());
        TreeItem<String> boxBozzeM = new TreeItem<>("Bozze: " + model.getMailsBozze().size());
        TreeItem<String> boxSendedM = new TreeItem<>("Poste inviata: " + model.getMailsSended().size());
        TreeItem<String> boxDeletedM = new TreeItem<>("Cestino: " + model.getMailsDeleted().size());
        
        root.getChildren().add(boxIncomingM);
        root.getChildren().add(boxBozzeM);
        root.getChildren().add(boxSendedM);
        root.getChildren().add(boxDeletedM);
        treeView.setRoot(root);
    }
    
}
