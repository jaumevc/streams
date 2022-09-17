package com.jaume;

import java.util.function.Predicate;

public class LibroUtils {
	//Predicate<T>: Evalúa un T y devuelve un boolean
	public static Predicate <Libro> filtroCategoria(String categoria) {
        return (Libro l) -> {
            return l.getCategoria().equals(categoria);
        };
    }
    public static boolean buenosLibros(Libro libro) {
        Predicate < Libro > p1 = (Libro l) -> l.getCategoria().equals("ciencia ficcion");
        Predicate < Libro > p2 = (Libro l) -> l.getCategoria().equals("fantasia");
        Predicate < Libro > p3 = (Libro l) -> l.getPag() >= 1000;
        Predicate < Libro > ptotal = p1.or(p2).and(p3);
        return ptotal.test(libro);
    }

}
