package com.jaume;

public class Libro {
	private String titulo;
	private String categoria;
	private int pag;
	public Libro(String titulo, String categoria, int pag) {
		super();
		this.titulo = titulo;
		this.categoria = categoria;
		this.pag = pag;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public int getPag() {
		return pag;
	}
	public void setPag(int pag) {
		this.pag = pag;
	}

}
