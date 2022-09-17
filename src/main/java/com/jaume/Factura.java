package com.jaume;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class Factura {
	
	private int numero;
    private String concepto;
    private List<LineaFactura> lineas = new ArrayList<>();
 
    public void addLinea(LineaFactura lf) {
        lineas.add(lf);
    }
    
    public int getNumero() {
        return numero;
    }
 
    public void setNumero(int numero) {
        this.numero = numero;
    }
 
    public String getConcepto() {
        return concepto;
    }
 
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
 
    public List<LineaFactura> getLineas() {
        return lineas;
    }
 
    public void setLineas(List<LineaFactura> lineas) {
        this.lineas = lineas;
    }
 
    public double total() {
 
        double total = 0;
        for (LineaFactura l : lineas) {
 
            total += l.getImporte();
        }
        return total;
    }
    
    public double total(Predicate<LineaFactura> plinea) {
    	 
        return lineas.stream().filter(plinea).mapToDouble(l -> l.getImporte()).sum();
    }

}
