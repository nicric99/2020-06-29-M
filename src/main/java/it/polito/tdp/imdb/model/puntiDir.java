package it.polito.tdp.imdb.model;

public class puntiDir implements Comparable<puntiDir>{
private Director d;
private Integer p;
public  puntiDir(Director d, Integer p) {
	super();
	this.d = d;
	this.p = p;
}
public Director getD() {
	return d;
}
public void setD(Director d) {
	this.d = d;
}
public Integer getP() {
	return p;
}
public void setP(Integer p) {
	this.p = p;
}
@Override
public int compareTo(puntiDir o) {
	
	return Integer.compare(this.getP(), o.getP());
}

}
