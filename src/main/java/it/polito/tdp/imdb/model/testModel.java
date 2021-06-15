package it.polito.tdp.imdb.model;

public class testModel {

	public static void main(String[] args) {
		Model model=new Model();
		model.creaGrafo(2005);
		System.out.println(model.getVertici());
		System.out.println(model.getArchi());

	}

}
