/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailclient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Francesco
 */
public class Model extends Observable{
    
    private static Model state = null;    
    private final ObservableList<Mail> mailList = FXCollections.observableArrayList();
    private final ObservableList<Mail> mailListDeleted = FXCollections.observableArrayList();
    private final ObservableList<Mail> mailListBozze = FXCollections.observableArrayList();
    private final ObservableList<Mail> mailListSended = FXCollections.observableArrayList();
    private final String account;
    
    
    public Model(){
        account = "frax@gmail.com";
        fillDataModel();  
    }
    
    /**
     * 
     * @return  the curret instance of the Model    
     */
    public static Model get() {
        if (state == null)
            state = new Model();
        return state;
    }
    
    public ObservableList<Mail> getMails(){
        return mailList;
    }
    
    public ObservableList<Mail> getMailsDeleted(){
        return mailListDeleted;
    }
    
    
    public ObservableList<Mail> getMailsSended(){
        return mailListSended;
    }
    
    public ObservableList<Mail> getMailsBozze(){
        return mailListBozze;
    }
    
    public String getAccount(){
        return account;
    }
    
    /**
     * Delete mail in ObservableList box
     * 
     * @param mail: mail to delete
     * @param box : box where delete mail
     * 
     * @return true 
     */
    
    public boolean deleteMail(ObservableList<Mail> box, Mail mail){
        if(!box.equals(mailListDeleted))
            mailListDeleted.add(mail);
        box.remove(mail);
        setChanged();
        notifyObservers();
        return true;
    }
    
    /**
     * Add mail in ObservableList boz
     * 
     * @param mail: mail to add
     * @param box : box where add mail
     * 
     * @return true 
     */
    
    public boolean addMail(ObservableList<Mail> box, Mail mail){
        box.add(mail);
        setChanged();
        notifyObservers();
        return true;
    }
    
    /**
     * Search in where box there is the param mail
     * 
     * @param mail
     * @return box that contains mail
     */
    public ObservableList<Mail> searchBox(Mail mail){
        if(mailList.contains(mail)){
            return mailList;
        }else if(mailListDeleted.contains(mail))
            return mailListDeleted;
        else if(mailListBozze.contains(mail))
            return mailListBozze;
        else if(mailListSended.contains(mail))
            return mailListSended;
        return null;
    }
    
    private void fillDataModel(){
        for(int i = 0; i < 10; i++){
            mailList.add(new Mail("andreaa@gmail.com","fra@gmail.com","Spesso un thread non può eseguire un metodo sincronizzato, anche\n" +
                                                                      "se ha ottenuto il possesso del lock, perché deve aspettare che si\n" +
                                                                      "verifichi una qualche condizione che non dipende da lui. Per\n" +
                                                                      "esempio, deve aspettare che la risorsa condivisa entri in un certo\n" +
                                                                      "stato prima di utilizzarla","Relazione" + i*2, 
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())));
        }
        for(int i = 0; i < 9; i++){
            mailListDeleted.add(new Mail("aliasss@gmail.com","fra@gmail.com","In questo caso, per non occupare inutilmente la CPU, il thread\n" +
                                                                            "deve rilasciare il lock, mettendosi in attesa della condizione,\n" +
                                                                            "eseguendo il metodo wait(), in modo che qualche altro thread\n" +
                                                                            "possa entrare e realizzare la condizione. ","Relazione" + i*2, 
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())));
        }
        for(int i = 0; i < 14; i++){
            mailListBozze.add(new Mail("aa@gmail.com","fra@gmail.com","A questo genere di azioni appartengono la sospensione e la terminazione "
                    + "di un processo. In entrambi i casi la rimozione dalla memoria dello spazio "
                    + "di indirizzamento si ripercuote su tutti i thread che condividono quello spazio"
                    + " di indirizzamento e che entrano quindi tutti insieme nello stato di sospensione "
                    + "o di terminazion","Relazione" + i*2, 
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())));
            
        }
        for(int i = 0; i < 5; i++){
            mailListSended.add(new Mail("cavallerizza@gmail.com","fra@gmail.com","Il vantaggio principale dei Thread è nelle prestazioni: "
                    + "operazioni come creazione, terminazione e cambio tra due thread di un processo "
                    + "richiedono meno tempo rispetto alla creazione, terminazione e cambio di processi. "
                    + "","Relazione" + i*2, 
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())));
        }        
    }
    
}
