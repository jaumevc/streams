package com.jaume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner{

	private static Logger LOG = LoggerFactory.getLogger(App.class);
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Persona p1 = new Persona("pedro", 20, "perez");
        Persona p2 = new Persona("juan", 25, "perez");
        Persona p3 = new Persona("ana", 30, "perez");
        Persona p4 = new Persona("jaume", 30, "perez");
        Persona p5 = new Persona("jaume", 30, "valls");
        Persona p6 = new Persona("jaume", 30, "valls");
        
        List <Persona> persones = new ArrayList<>();
        persones.add(p1);
        persones.add(p2);
        persones.add(p3);
        persones.add(new Persona("Alicia",19,"Gibert"));
        persones.add(new Persona("Jaume",49,"Valls"));
        persones.add(new Persona("Susana",46,"JimÃ©nez"));
		
//		LOG.warn("\nLAMBDA:");
//		exempleSortLambda(persones);
		
//		LOG.warn("\n\nLlistes - Lambda:");
//		llistesIcg();
        
//		LOG.warn("\n\nSorted Arrays - Lambda:");
		arraysIcg();
	
//		LOG.warn("\n\nSTREAM REDUCE:\n");
//		reduccio();		
//		mesReduccions(persones);
    
//	    LOG.warn("\n\nFILTRES i PREDICATS:\n");
//	    filtresMesPredicats();

//      LOG.warn("\n\nSTREAM MAP + ESTADISTIQUES:\n");
//      estadistiquesMap(persones);
       
//      LOG.warn("\n\nSTREAM SUM + BUSSINESS OBJECTS:\n");
//      streamsSumAndBusinesObjects();
        
//      LOG.warn("\n\nSTREAM + MAPS:\n");
//      tractarMapsAmbStreams();
		
		System.out.println("220917");
        
	}

	private void arraysIcg() {
		String[] lletres = {"abc","acb","cba","bac","bcd","cde","edc","ejk"};
		List<String> llista = Arrays.asList(lletres);
		
		Map<String, Integer> mapa = new HashMap<>();
		
		for(String llet: llista) {
			char[] arr= llet.toCharArray();
			Arrays.sort(arr);
			String convinades= new String(arr);
			
			if(mapa.containsKey(convinades)) {
				mapa.put(convinades, mapa.get(convinades)+1);
			}else {
				mapa.put(convinades, 1);
			}
		}
		
		mapa.entrySet().stream().sorted(Entry.comparingByValue(Comparator.reverseOrder())).forEach(System.out::println);
		
	}

	private void streamsSumAndBusinesObjects() {
		Factura f= new Factura();
        f.setNumero(1);
        f.setConcepto("miscompras");
        f.addLinea(new LineaFactura(1, "turron", 3));
        f.addLinea(new LineaFactura(1, "turron", 3));
        f.addLinea(new LineaFactura(1, "turron", 4));
        f.addLinea(new LineaFactura(1, "jamon", 5));
        f.addLinea(new LineaFactura(1, "jamon", 4));
        
        LOG.warn("\nAmb crida a mÃ¨todes del OBJECTE FACTRA:\n");  
        /*
          //public double total() {
        	//double total = 0;
        	//for (LineaFactura l : lineas) {
            	//total += l.getImporte();
        	//}
        		//return total;
    	  //}
         */
        
        System.out.println("Total vendes: "+f.total());  
        
        /*
			//public double total(Predicate<LineaFactura> plinea) {
				//return lineas.stream().filter(plinea).mapToDouble(l -> l.getImporte()).sum();
			//}
         */
        
        System.out.println("Vendes jamÃ³n: "+ f.total(lineas -> lineas.getConcepto().contains("jamon")));
       
        LOG.warn("\n\nDIRECTE DES DE STREAM():");
        f.getLineas().stream().filter(x->"jamon".equals(x.getConcepto())).map(l->l.getImporte()).reduce((x,y)->x+y).ifPresent(System.out::println);
//        double turrons= f.getLineas().stream().filter(lf->lf.getConcepto().equals("turron")).mapToDouble(l -> l.getImporte()).sum();
//        System.out.println("Vendes de torrons: "+turrons);
//        LOG.warn("\n\nDIRECTE DES DE STREAM() EN UNA SOLA LÃ�NEA, vendes de torrons:");
//        f.getLineas().stream().filter(lf->lf.getConcepto().equals("turron")).map(e->e.getImporte()).reduce(Double::sum).ifPresent(System.out::println);
		
	}

	private void estadistiquesMap(List<Persona> persones) {
      System.out.println("\nmapToInt - mapToDouble: orientats a estadistica");

      //Orientacion a estadisticas:mapToInt - mapToDouble
      int total = persones.stream().mapToInt(Persona::getEdad).sum();
      System.out.println("Suma de edats: "+total);
      persones.stream().mapToInt(Persona::getEdad).reduce(Integer::sum).ifPresent(System.out::println);
      System.out.println();
      
      persones.stream().mapToDouble(Persona::getEdad).average().ifPresent(System.out::println);
      persones.stream().mapToInt(Persona::getEdad).max().ifPresent(System.out::println);
      System.out.println();
      
      OptionalInt mesJove= persones.stream().mapToInt(Persona::getEdad).min();
      System.out.println("mes jove: "+mesJove.getAsInt());
      persones.stream().mapToInt(Persona::getEdad).reduce(Integer::min).ifPresent(System.out::println);
      System.out.println();
      
      long nombrePresones =persones.stream().mapToDouble(Persona::getEdad).count();
      System.out.println("nombre de persones evaluades: "+nombrePresones);
      
      System.out.println("\nStatistics: ESTADISTIQUES: \n");
      DoubleSummaryStatistics estadistica = persones.stream().mapToDouble(Persona::getEdad).summaryStatistics();
      System.out.println("AVERAGE: "+estadistica.getAverage()+"\n");
      System.out.println("MAX: "+estadistica.getMax()+"\n");
      System.out.println("MIN: "+estadistica.getMin()+"\n");
	}

	private void mesReduccions(List<Persona> persones) {
		System.out.println("\nEDATS DE LES PERSONES DE LA LLISTA");
		persones.stream().map(Persona::getEdad).forEach(System.out::println);
		LOG.warn("\nMes exemples de REDUCE:");
		System.out.println("\nObtenim la suma de edats:");
		persones.stream().map(Persona::getEdad).reduce((a,b)->a+b).ifPresent(System.out::println);
		
		System.out.println("\nObtenim l'Edat mÃ©s alta:");
		persones.stream().map(Persona::getEdad).reduce(Integer::max).ifPresent(System.out::println);
		
		System.out.println("\nObtenim la 1a Edat just mes jove de 30:");
		persones.stream().filter(x -> x.getEdad()<30).map(Persona::getEdad).reduce(Integer::max).ifPresent(System.out::println);	
	}

	private void filtresMesPredicats() {
		Libro lib = new Libro("El seÃ±or de los anillos", "fantasia", 1100);
		Libro lib2 = new Libro("El Juego de Ender", "ciencia ficcion", 1000);
		Libro lib3 = new Libro("La fundacion", "ciencia ficcion", 500);
		Libro lib4 = new Libro("Los pilares de la tierra", "historica", 1200);
		List<Libro> lista = Arrays.asList(lib, lib2, lib3, lib4);

//     stream filter i predicat amb lambda
//	   System.out.println("\nSTREAM() AMB FILTER I MAP AMB LAMBDA:");
//     optenim el titol de llibres amb mes de 1000 pagines
//	   lista.stream().filter(e->e.getPag()>1000).map(Libro::getTitulo).collect(Collectors.toList()).forEach(System.out::println);
//	   System.out.println("\n");
//	   lista.stream().filter(e->e.getPag()>1000).map(Libro::getTitulo).forEach(System.out::println);
//     lista.stream().filter(x -> x.getPag() > 1000).map(e -> e.getTitulo()).forEach(x -> System.out.println(x));
		
//		optenim llibres de ciencia ficcio o fantasia i que tingui >= de 1000 pag
//		Stream filter amb predicat en classe: Predicate<T>: EvalÃºa un T y devuelve un boolean
//		System.out.println("\nSTREAM() AMB FILTER I MAP AMB CRIDES A CLASSES I METODES PER FILTRAR (crida a mÃ¨tode que usa Predicate<T>)\nI OPTENIR INFORMACIO (MAP) amb GET:");
//	   
//		lista.stream().filter(LibroUtils::buenosLibros).map(Libro::getTitulo).forEach(System.out::println);
//		lista.stream().filter(LibroUtils::buenosLibros).map(Libro::getTitulo).collect(Collectors.toList()).forEach(System.out::println);
//		System.out.println("\nAmb LAMBDES:");
//		lista.stream().filter(l->l.getCategoria().equals("fantasia") || l.getCategoria().equals("ciencia ficcion") && l.getPag()>=1000).map(Libro::getTitulo).collect(Collectors.toList()).forEach(System.out::println);
//
//
//  	System.out.println("\nLibroUtils ==> metode Predicate <Libro> filtroCategoria(String categoria)");
//		lista.stream().filter(LibroUtils.filtroCategoria("historica")).map(Libro::getTitulo).forEach(System.out::println);		
//		lista.stream().filter(LibroUtils.filtroCategoria("ciencia ficcion")).map(libro -> libro.getTitulo()).forEach(System.out::println);

	}

	private void reduccio() {
		List<Integer> gastos= new ArrayList<>();
	    gastos.add(100);
	    gastos.add(200);
	    gastos.add(300);
	    
	    System.out.println("\nReduce Integer amb LAMBDA:\n");
	    gastos.stream().reduce((a,b)->{
	    	return a+b;
	    }).ifPresent(System.out::println);
	    
	    System.out.println("\nRefuce Integer amb STREAM:\n");
	    gastos.stream().reduce(Integer::sum).ifPresent(System.out::println);	    
	    
	    List<String> noms= new ArrayList<>();
	    noms.add("Jaume");
	    noms.add("Susana");
	    noms.add("Ares");
	    noms.add("Mariona");
	    
	    System.out.println("\nReduce String amb LAMBDA:\n");
	    noms.stream().reduce((a,b)->{
	    	return a.concat(b);
	    }).ifPresent(System.out::println);
	    
	    System.out.println("\nRefuce String amb STREAM:\n");
	    noms.stream().reduce(String::concat).ifPresent(System.out::println);
	
	}

	private void llistesIcg() {
		List<String> names= new ArrayList<>();
		names.add("Jaume");
		names.add("Susana");
		names.add("Ares");
		names.add("Ares");
		names.add("Mariona");
		names.add("Mariona");
		names.add("Mariona");
		
		Map<String, Integer> mapa = new HashMap<>();
		
		for(String name: names) {
			if(mapa.containsKey(name)) {
				mapa.put(name, mapa.get(name)+1);
			}else {
				mapa.put(name, 1);
			}
		}
		
		System.out.println("\nRecorrer la llista amb forEach + lambda:");
		mapa.forEach((x,y)->System.out.println("el nom: "+x+" es repeteix: "+y+" vegades"));
		System.out.println("\nRecorrer el MAP amb entrySet() + stream():");
		mapa.entrySet().stream().forEach(System.out::println);
		
		ordenar(mapa);
		mesComu(mapa);
		
	}


	private void exempleSortLambda(List<Persona> persones) {
		LOG.warn("\nSORT LAMBDES AMB STRING:");
		
		System.out.println("\nmostra les edats ordenades amb Collection.sort");
		Collections.sort(persones, (x,y)->x.getEdad().compareTo(y.getEdad()));
		persones.stream().map(Persona::getEdad).forEach(System.out::println);
		System.out.println();
		//mostra els noms per edat: alicia, pedro, juan, ana, susana,jaume
		persones.stream().map(Persona::getNombre).forEach(System.out::println);
		
		System.out.println("\nCREEM UN MAP DES DE UNA LIST<Persona>");	
		
		Map<String, Integer> mapaNoms = new HashMap<>();
		
		for(Persona p: persones) {
			mapaNoms.put(p.getNombre(), p.getEdad());
		}
		
		mapaNoms.entrySet().stream().forEach(System.out::println);
		System.out.println("\nORDENEM EL MAP PER EDAT:");
		mapaNoms.entrySet().stream().sorted(Entry.comparingByValue()).forEach(System.out::println);
		
		System.out.println("\n* Amb Collections.sort(coleccio, lambda )");
		Collections.sort(persones, (x, y) -> x.getNombre().compareTo(y.getNombre()));
		persones.forEach((n) -> System.out.println(n.getNombre()));

		System.out.println("\n* Amb stream()");
		persones.stream().map(Persona::getNombre).sorted().forEach(System.out::println);

		System.out.println("\n* Amb stream() + lambda obetnir dades en map() + Comparador.reverseOrder en el sorted():");
		persones.stream().map(e -> e.getNombre()).sorted(Comparator.reverseOrder()).forEach(System.out::println);

		LOG.warn("\nSORT LAMBDES AMB INTEGERS:");

		Collections.sort(persones, (x, y) -> x.getEdad().compareTo(y.getEdad()));
		persones.stream().map(Persona::getEdad).forEach(System.out::println);
		System.out.println();
		System.out.println("-----------------");
		LOG.warn("\nSORTED STREAMS AMB INTEGERS:");
	    persones.stream().map(Persona::getEdad).sorted().forEach(System.out::println);
	    System.out.println("-----------------");
	    persones.stream().map(Persona::getEdad).sorted(Comparator.reverseOrder()).forEach(System.out::println);
	}

	private void tractarMapsAmbStreams() {
		/*
		  diferents maneres de convertir un stream des de un map: 
		  Map<String, Integer> someMap = new HashMap<>();
		  *We can also get the key set associated with the Map:
		  Set<String> keySet = someMap.keySet();
		  *Or we could work directly with the set of values:
		  Collection<Integer> values = someMap.values();
		  
		  Set<Map.Entry<String, Integer>> entries = someMap.entrySet();
		  
		  *These each give us an entry point to process those collections by obtaining streams from them:
		  Stream<Map.Entry<String, Integer>> entriesStream = entries.stream();
		  Stream<String> keysStream = keySet.stream();
		  Stream<Integer> valuesStream = values.stream();

		 */
		
		Map<String, String> books = new HashMap<>();
		books.put(
		"979-0201633610", "Design patterns : elements of reusable object-oriented software");
		books.put(
		  "978-1617291999", "Java 8 in Action: Lambdas, Streams, and functional-style programming");
		books.put("978-0134685991", "Effective Java");
		books.put("978-0134685992", "Effective Java v2.0");

		System.out.println("\nGetting a Mapâ€˜s Keys Using Streams");
		LOG.warn("\n\nRETRIEVING A MATCH:");
		
//		books.entrySet().stream().filter(x->x.getValue().contains("Java")).map(Entry::getValue).findFirst().ifPresent(System.out::println);
		
//		Optional<String> optionalIsbn = books.entrySet().stream()
//				  .filter(e -> "Effective Java".equals(e.getValue()))
//				  .map(Map.Entry::getKey)
//				  .findFirst();
//		
//		optionalIsbn.ifPresent(System.out::println);
		
//		Optional<String> optionalIsbn2 = books.entrySet().stream()
//				  .filter(e -> "Non Existent Title".equals(e.getValue()))
//				  .map(Map.Entry::getKey).findFirst();
//		System.out.println("NO TORNA ISBNs:");
//		optionalIsbn2.ifPresent(System.out::println);
		
		LOG.warn("\n\nRETRIEVING MULTIPLE RESULTS:");		
		
		books.entrySet().stream().filter(b->b.getKey().contains("978")).map(Entry::getValue).collect(Collectors.toList()).forEach(System.out::println);
		
//		books.entrySet().stream().filter(e -> e.getValue().startsWith("Effective")).map(Entry::getValue).collect(Collectors.toList()).forEach(System.out::println);			
		
//		List<String> isbnCodes = books.entrySet().stream()
//				  .filter(e -> e.getValue().startsWith("Effective Java"))
//				  .map(Map.Entry::getKey)
//				  .collect(Collectors.toList());
//		isbnCodes.forEach(System.out::println);
//		
//		LOG.warn("\ngetting Map's Values Using Streams");
//		List<String> titles = books.entrySet().stream()
//				  .filter(e -> e.getKey().startsWith("978-0"))
//				  .map(Map.Entry::getValue)
//				  .collect(Collectors.toList());
//		
//		titles.forEach(System.out::println);
		
		System.out.println("\nDirecte:");
		
		books.entrySet().stream()
		  .filter(e -> e.getKey().startsWith("978-0"))
		  .map(Map.Entry::getValue)
		  .forEach(System.out::println);
		
		System.out.println("\nTRALARA:");
		
		Set<Entry<String,String>> nousTitols=books.entrySet();
		Stream<String> claus= nousTitols.stream().map(Entry::getValue);
		claus.forEach(System.out::println);
		
		System.out.println("\nTRALAFI:");	
	}
	
	private void mesComu(Map<String, Integer> mapa) {
		
		System.out.println("\nEl nom mÃ©s repetit amb lambda:");
		int comu= Collections.max(mapa.values());
		mapa.forEach((x,y)->{
			if(y == comu)System.out.println(x+", Ã©s el nom mes repetit, amb "+y+" repeticions");
		});
		
		System.out.println("\nEl nom mÃ©s repetit, directe amb entrySet + stream():");
		mapa.entrySet().stream().filter(e->e.getValue()==comu).forEach(System.out::println);

	}

	
	private void ordenar(Map<String, Integer> mapa) {
		System.out.println("\n* Ordenar mapa amb List<Entry<String, Integer> nouMapa + nouMapa.sort(Entry.comparingByValue):");
		
//		List<Entry<String, Integer>> nouMapa= new ArrayList<>(mapa.entrySet());
		Set<Entry<String, Integer>> nouMapa= mapa.entrySet();
//		Stream<Entry<String, Integer>> nouMapa= mapa.entrySet().stream();
		
		nouMapa.stream().sorted(Entry.comparingByValue());
//		nouMapa.sort(Entry.comparingByValue());
		nouMapa.forEach(System.out::println);
		
		System.out.println("\n* Reverse ordre:");
		nouMapa.stream().sorted(Entry.comparingByValue(Comparator.reverseOrder()));
//		nouMapa.sort(Entry.comparingByValue(Comparator.reverseOrder()));
		nouMapa.forEach(System.out::println);
		
		System.out.println("\n* Ordenar mapa directament entrySet() + stream() + sorted(Entry.comparingByValue()):");
		mapa.entrySet().stream().sorted(Entry.comparingByValue()).forEach(System.out::println);
		System.out.println("\n* Ordenar mapa directament entrySet() +sorted(Entry.comparingByValue(Comparator.reverseOrder()) + stream():");
		mapa.entrySet().stream().sorted(Entry.comparingByValue(Comparator.reverseOrder())).forEach(System.out::println);
	}

}
