/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailclient;

import javafx.beans.property.SimpleStringProperty;


/**
 *
 * @author Francesco
 */
public class Mail {
    private int id;
    private final SimpleStringProperty mittente;
    private final SimpleStringProperty destinatario;
    private final SimpleStringProperty testo;
    private final SimpleStringProperty argomento;
    private final SimpleStringProperty date;
        
        
    public Mail(){
        mittente = null;
        destinatario = null;
        testo = null;
        argomento = null;
        date = null;
    }
        
    public Mail(String mittente,
            String destinatario, 
            String testo, String argomento,
            String data){
        id++;
        this.mittente =  new SimpleStringProperty(mittente);
        this.destinatario =  new SimpleStringProperty(destinatario);
        this.argomento =  new SimpleStringProperty(argomento);
        this.testo =  new SimpleStringProperty(testo);
        this.date =  new SimpleStringProperty(data);
    }
        
    public int getId(){
        return id;
    }

       
    public String getDestinario(){
        return destinatario.get();
    }
        
    public String getMittente(){
        return mittente.get();
    }
        
    public String getTesto(){
        return testo.get();
    }
        
    public String getArgomento(){
        return argomento.get();
    }
        
    public String getDate(){
        return date.get();
    } 
}
