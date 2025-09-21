package cfpProyecto;

import java.io.PrintWriter;
import java.util.Random;

/**Clase encargada de generar los archivos de prueba (productos, vendedores, ventas).
 * cada método crea un archivo CSV con información pseudoaleatoria*/

public class GenerateInfoFiles {
	
	//Arrays donde se almacenan los valores que llevará el documento
	
	private static final String [] nombres = {"Julián","Yessica","Hector","Juan","Christian"};
	private static final String [] apellidos = {"Sierra","Díaz","Vargas","Valenzuela"};
	private static final String [] tiposDoc = {"C.C","C.E","T.I"};
	private static final String [] productos = {"Laptop","Mouse","Monitor","Teclado","Impresora"};
	private static final double [] precios = {2500000,50000,780000,95000,650000};
	
	
	/** Genera un archivo con información de ventas de un vendedor.
	 * 
	 * 
	 * @param randomSalesCount
	 * @param name
	 * @param id
	 */
	
	public static void createSalesMenFile(int salesmanCount) throws Exception {
		
		
        }
		
	/**Genera un archico CSV con información de productos
	 * cada producto tiene un ID, un nombre y un precio.
	 * @param productsCount cantidad de productos a generar
	 */
	public static void createProductsFile (int productsCount) {
		try {
	        // Creamos el archivo productos.csv en la carpeta del proyecto
	        java.io.PrintWriter writer = new java.io.PrintWriter("productos.csv", "UTF-8");

	        // Escribimos la primera línea (encabezados)
	        writer.println("IDProducto;NombreProducto;Precio");

	        // Recorremos hasta la cantidad de productos que queremos generar
	        for (int i = 0; i < productsCount; i++) {
	            // Creamos un ID en formato P001, P002, etc.
	            String id = String.format("P%03d", (i + 1));

	            // Tomamos el nombre y precio del array en la posición i
	            String nombre = productos[i % productos.length];  // % asegura que no se salga del array
	            double precio = precios[i % precios.length];

	            // Escribimos en el archivo una línea con los datos del producto
	            writer.println(id + ";" + nombre + ";" + precio);
	        }

	        // Cerramos el archivo para guardar los cambios
	        writer.close();

	        // Mensaje en consola de confirmación
	        System.out.println("Archivo productos.csv generado exitosamente.");

	    } catch (Exception e) {
	        // Si ocurre un error al escribir, lo mostramos en consola
	        System.err.println("Error al generar productos.csv: " + e.getMessage());
	    }
		
	}
	
	/**Genera un archivo que contiene la información de vendedores
	 * 
	 * @param salesmanCount
	 */
	public static void createSalesManInfoFile(int salesmanCount) {
		
		
	}

}
