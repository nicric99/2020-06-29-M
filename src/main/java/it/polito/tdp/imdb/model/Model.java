package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	private List<Director> camminoMax;
	private Integer pesoMax;
	private Graph<Director, DefaultWeightedEdge> grafo;
	private ImdbDAO dao;
	private Map<Integer, Director> idMap;

	public Model() {
		dao = new ImdbDAO();
		idMap = new HashMap<Integer, Director>();
		camminoMax=new ArrayList<Director>();
	}
	public void creaGrafo(Integer anno) {
		grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		dao.listAllDirectors(idMap);
		for(Director d:dao.getVertici(anno, idMap)) {
			this.grafo.addVertex(d);
		}
		for(Adiacenza a: dao.getArchi(anno, idMap)) {
			Graphs.addEdge(this.grafo, a.getD1(), a.getD2(), a.getPeso());
		}
	}
	public Integer getVertici() {
		return this.grafo.vertexSet().size();
	}
	public Integer getArchi() {
		return this.grafo.edgeSet().size();
	}
	public Set<Director> getRegisti(){
		return this.grafo.vertexSet();
	}
	public List<puntiDir> getAdiacenti(Director direttore){
		List<puntiDir> result= new ArrayList<>();
		List<Director> lista= new ArrayList<Director>(Graphs.neighborListOf(this.grafo, direttore));
		for(Director d:lista) {
			DefaultWeightedEdge e= this.grafo.getEdge(d, direttore);
			Integer peso=(int) grafo.getEdgeWeight(e);
			puntiDir a= new puntiDir(d,peso);
			result.add(a);
		}
		Collections.sort(result);
		Collections.reverse(result);
		return result;
		
	}
	public void getCammino(Director sorgente,Integer c){
		List<Director> parziale= new ArrayList<>();
		parziale.add(sorgente);
		// in questo modo abbiamo 1 valore
		this.camminoMax.add(sorgente);
		int peso=0;
		this.pesoMax=0;
		int livello=0;
		cerca(parziale,peso,c,livello);
	}
	public void cerca(List<Director> parziale,Integer peso,Integer c,int livello) {
		if(peso>=c) {
			return;
		}
		if(livello!=0) {
			if(parziale.size()>camminoMax.size()) {
				
				this.camminoMax= new ArrayList<>(parziale);
				this.pesoMax=peso;
				
			}
		}
		// ciclare sui vicini dell'ultimo inserito
		// provo ad aggiungerli uno ad uno 
		for(Director vicino:Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1))) {
			
			DefaultWeightedEdge e=this.grafo.getEdge(parziale.get(parziale.size()-1), vicino);
			int tmp=(int) this.grafo.getEdgeWeight(e);
			if(!parziale.contains(vicino)) {
			parziale.add(vicino);
			peso=peso+tmp;
			livello++;
			cerca(parziale,peso,c,livello);
			peso=peso-tmp;
			parziale.remove(parziale.size()-1);
			}
		}
	}
	
	public List<Director> getPercorso(){
		return this.camminoMax;
	}

}
