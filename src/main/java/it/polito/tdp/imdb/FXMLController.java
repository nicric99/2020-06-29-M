/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Model;
import it.polito.tdp.imdb.model.puntiDir;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaAffini"
    private Button btnCercaAffini; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxRegista"
    private ComboBox<Director> boxRegista; // Value injected by FXMLLoader

    @FXML // fx:id="txtAttoriCondivisi"
    private TextField txtAttoriCondivisi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Integer anno= boxAnno.getValue();
    	if(anno==null) {
    		txtResult.setText("NON HAI SELEZIONATO UN ANNO \n");
    		return;
    	}
    	model.creaGrafo(anno);
    	txtResult.appendText("N vertici: "+model.getVertici()+"\n");
    	txtResult.appendText("N archi: "+model.getArchi()+"\n");
    	for(Director d:model.getRegisti()) {
    		boxRegista.getItems().add(d);
    	}
    }

    @FXML
    void doRegistiAdiacenti(ActionEvent event) {
    	Director d= boxRegista.getValue();
    	if(d==null) {
    		txtResult.appendText("Attenzione nessun regista selezionato, bisogna prima creare il grafo");
    		return;
    	}
    	List<puntiDir> result= new ArrayList<>(model.getAdiacenti(d));
    	if(result.size()==0) {
    		txtResult.appendText("NEssun regista adiacente");
    		return;
    	}
    	for(puntiDir s:result) {
    		txtResult.appendText(s.getD().toString()+" "+s.getP()+"\n");
    	}
    	
    }

    @FXML
    void doRicorsione(ActionEvent event) {
    	Director d= boxRegista.getValue();
    	if(d==null) {
    		txtResult.appendText("Attenzione nessun regista selezionato, bisogna prima creare il grafo");
    		return;
    	}
    	String numero= txtAttoriCondivisi.getText();
    	Integer c;
    	try {
    		c= Integer.parseInt(numero);
    	}catch(NumberFormatException nfe){
    		txtResult.appendText("Valore nonn numerico selezionato, ritentare\n");
    		return;
    	}
    	txtResult.appendText(c.toString());
    	model.getCammino(d, c);
    	
    	List<Director> result= new ArrayList<>(model.getPercorso());
    	for(Director a:result) {
    		txtResult.appendText(a.toString()+"\n");
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaAffini != null : "fx:id=\"btnCercaAffini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxRegista != null : "fx:id=\"boxRegista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAttoriCondivisi != null : "fx:id=\"txtAttoriCondivisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
   public void setModel(Model model) {
    	
    	this.model = model;
    for(int i=2004;i<2007;i++) {
    	boxAnno.getItems().add(i);
    }
    	
    }
    
}
