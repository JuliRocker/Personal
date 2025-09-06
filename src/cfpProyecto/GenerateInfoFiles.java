package cfpProyecto;

/**
 * Clase encargada de generar los archivos de prueba (productos, vendedores,
 * ventas). cada método crea un archivo CSV con información pseudoaleatoria
 */
public class GenerateInfoFiles {

    //Arrays donde se almacenan los valores que llevará el documento
    private static final String[] NOMBRE = {"Julián", "Yessica", "Hector", "Juan", "Christian"};
    private static final String[] APELLIDOS = {"Sierra", "Díaz", "Vargas", "Valenzuela", "Ruiz"};
    private static final String[] TIPOS_DOC = {"C.C", "C.E", "T.I"};
    private static final String[] PRODUCTOS = {"Laptop", "Mouse", "Monitor", "Teclado", "Impresora"};
    private static final double[] PRECIOS = {2500000, 50000, 780000, 95000, 650000};
		//variable para guardar los ids generados de los vendedores
        private static final int[] salesManIds = new int[NOMBRE.length];
		//guardamos los products ids
		private static final String[] productIds = new String[PRODUCTOS.length];

    public static void main(String[] args) {
        System.out.println("Generando archivo de productos...");
        createProductsFile(PRODUCTOS.length);
        System.out.println("Generando archivo de vendedores...");
        createSalesManInfoFile(NOMBRE.length);

				System.out.println("Generando archivo de ventas del primer vendedor...");
				createSalesMenFile(3, NOMBRE[0], salesManIds[0]);

				System.out.println("Generando archivo de ventas del segundo vendedor...");
				createSalesMenFile(4, NOMBRE[1], salesManIds[1]);

				System.out.println("Generando archivo de ventas del tercer vendedor...");
				createSalesMenFile(2, NOMBRE[2], salesManIds[2]);

				System.out.println("Generando archivo de ventas del cuarto vendedor...");
				createSalesMenFile(5, NOMBRE[3], salesManIds[3]);
    }

    /**
     * Genera un archico CSV con información de productos cada producto tiene un
     * ID, un nombre y un precio.
     *
     * @param productsCount cantidad de productos a generar
     */
    public static void createProductsFile(int productsCount) {
        try {
            // Creamos el archivo productos.csv en la carpeta del proyecto
            java.io.PrintWriter writer = new java.io.PrintWriter("productos.csv", "UTF-8");

            // Escribimos la primera línea (encabezados)
            writer.println("IDProducto;NombreProducto;Precio");

            // Recorremos hasta la cantidad de productos que queremos generar
            for (int i = 0; i < productsCount; i++) {
                // Creamos un ID en formato P001, P002, etc.
                String id = String.format("P%03d", (i + 1));
                // Guardamos el ID del producto en el array
                productIds[i] = id;

                // Tomamos el nombre y precio del array en la posición i
                String nombre = PRODUCTOS[i % PRODUCTOS.length];  // % asegura que no se salga del array
                double precio = PRECIOS[i % PRECIOS.length];

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

    /**
     * Genera un archivo que contiene la información de vendedores
     *
     * @param salesmanCount
     */
    public static void createSalesManInfoFile(int salesmanCount) {
        try {
            // Creamos el archivo vendedores.csv en la carpeta del proyecto
            java.io.PrintWriter writer = new java.io.PrintWriter("vendedores.csv", "UTF-8");

            // Escribimos la primera línea (encabezados)
            writer.println("IDVendedor;Nombre;Apellido;TipoDocumento;NumeroDocumento");

            // Recorremos hasta la cantidad de vendedores que queremos generar
            for (int i = 0; i < salesmanCount; i++) {
                // Creamos un ID en formato V001, V002, etc.
                String id = String.format("V%03d", (i + 1));

                // Tomamos el nombre, apellido y tipo de documento del array en la posición i
                String nombre = NOMBRE[i % NOMBRE.length];  // % asegura que no se salga del array
                String apellido = APELLIDOS[i % APELLIDOS.length];
                //tipo documento sacamos un random del array
                String tipoDoc = TIPOS_DOC[(int) (Math.random() * TIPOS_DOC.length)];

                // Generamos un número de documento pseudoaleatorio entre 10000000 y 99999999
                int numeroDoc = 10000000 + (int) (Math.random() * 89999999);

                // Guardamos el ID del vendedor en el array
                salesManIds[i] = numeroDoc;

                // Escribimos en el archivo una línea con los datos del vendedor
                writer.println(id + ";" + nombre + ";" + apellido + ";" + tipoDoc + ";" + numeroDoc);
            }

            // Cerramos el archivo para guardar los cambios
            writer.close();

            // Mensaje en consola de confirmación
            System.out.println("Archivo vendedores.csv generado exitosamente.");

        } catch (Exception e) {
            // Si ocurre un error al escribir, lo mostramos en consola
            System.err.println("Error al generar vendedores.csv: " + e.getMessage());
        }

    }

    /**
     * Genera un archivo con información de ventas de un vendedor.
     *
     * @param randomSalesCount
     * @param name
     * @param id
     */
    public static void createSalesMenFile(int randomSalesCount, String name, long id) {
				// creamos el archivo de ventas del vendedor
				String fileName = "ventas_" + id + ".csv";
				
				//formato de archivo a generar es IDProducto1;CantidadProducto1Vendido; sacando los productos random del array de productos
				try {
						java.io.PrintWriter writer = new java.io.PrintWriter(fileName, "UTF-8");
						// Escribimos la primera línea (encabezados)
						writer.println("IDProducto;CantidadVendida");
						// Recorremos hasta la cantidad de ventas que queremos generar
						for (int i = 0; i < randomSalesCount; i++) {
								// Tomamos un productId aleatorio del array de productIds
								String productId = productIds[(int) (Math.random() * productIds.length)];

								// Generamos una cantidad vendida pseudoaleatoria entre 1 y 10
								int cantidadVendida = 1 + (int) (Math.random() * 10);
								// Escribimos en el archivo una línea con los datos de la venta
								writer.println(productId + ";" + cantidadVendida);
						}
						// Cerramos el archivo para guardar los cambios
						writer.close();
						// Mensaje en consola de confirmación
						System.out.println("Archivo " + fileName + " generado exitosamente para el vendedor " + name + " con ID " + id + ".");
				} catch (Exception e) {
						// Si ocurre un error al escribir, lo mostramos en consola
						System.err.println("Error al generar " + fileName + ": " + e.getMessage());
				}
				

    }
}
