package cfpProyecto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // 1. Cargar productos
        Map<String, Producto> productos = cargarProductos("productos.csv");

        // 2. Cargar vendedores
        ArrayList<Vendedor> vendedores = cargarVendedores("vendedores.csv");

        // 3. Generar reporte de vendedores
        generarReporteVendedores(vendedores, productos);

        // 4. Generar reporte de productos
        generarReporteProductos(productos, vendedores);
    }

    // =====================================================
    // MÉTODOS
    // =====================================================

    /**
     * Cargar productos desde productos.csv
     */
    public static Map<String, Producto> cargarProductos(String rutaArchivo) {
        Map<String, Producto> productos = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            boolean primera = true;

            while ((linea = br.readLine()) != null) {
                if (primera) {
                    primera = false; // saltar encabezado
                    continue;
                }
                String[] partes = linea.split(";");
                String id = partes[0];
								String nombre = partes[1];
                double precio = Double.parseDouble(partes[2]);
                Producto p = new Producto(id, nombre, precio);
                productos.put(id, p);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo productos.csv: " + e.getMessage());
        }
        return productos;
    }

    /**
     * Cargar vendedores desde vendedores.csv
     */
    public static ArrayList<Vendedor> cargarVendedores(String rutaArchivo) {
        ArrayList<Vendedor> vendedores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            boolean primera = true;

            while ((linea = br.readLine()) != null) {
                if (primera) {
                    primera = false; // saltar encabezado
                    continue;
                }

                // El archivo tiene 5 columnas:
                // IDVendedor;Nombre;Apellido;TipoDocumento;NumeroDocumento
                String[] partes = linea.split(";");
                
                String nombre = partes[1];      
                String apellido = partes[2];      
                String tipoDoc = partes[3];        
                String numeroDoc = partes[4];      

                Vendedor v = new Vendedor(tipoDoc, numeroDoc, nombre, apellido);
                vendedores.add(v);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo vendedores.csv: " + e.getMessage());
        }
        return vendedores;
    }

    /**
     * Calcular total de ventas de un vendedor leyendo su archivo vendedor_ID.csv
     */
    public static double calcularTotalVentas(String rutaArchivo, Map<String, Producto> productos) {
				double total = 0.0;

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            boolean primera = true;

            while ((linea = br.readLine()) != null) {
                if (primera) { // saltar primera línea (encabezado)
                    primera = false;
                    continue;
                }

                String[] partes = linea.split(";");
                String idProducto = partes[0];
                int cantidad = Integer.parseInt(partes[1]);

								if (productos.containsKey(idProducto)) {
										double precio = productos.get(idProducto).getPrecio();
										total += precio * cantidad;
								} else {
										System.err.println("Producto no encontrado: " + idProducto);
								}
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo de ventas: " + e.getMessage());
        }

        return total;
    }

    /**
     * Generar reporte de vendedores en reporte_vendedores.csv
     */
    public static void generarReporteVendedores(ArrayList<Vendedor> vendedores, Map<String, Producto> productos) {
			//reordenar el array de vendedores por total de ventas descendente
			vendedores.sort((v1, v2) -> Double.compare(calcularTotalVentas("vendedor_" + v2.getNumeroDocumento() + ".csv", productos),
					calcularTotalVentas("vendedor_" + v1.getNumeroDocumento() + ".csv", productos)));

					// Formato para números sin notación científica
        DecimalFormat df = new DecimalFormat("#.##");
					
        try (PrintWriter writer = new PrintWriter("reporte_vendedores.csv", "UTF-8")) {
            writer.println("Vendedor;Total");

            for (Vendedor v : vendedores) {
                String archivoVentas = "vendedor_" + v.getNumeroDocumento() + ".csv";
                double total = calcularTotalVentas(archivoVentas, productos);

                String nombreCompleto = v.getNombre() + " " + v.getApellido();
                writer.println(nombreCompleto + ";" + df.format(total));
            }

            System.out.println("Reporte de vendedores generado en reporte_vendedores.csv");
        } catch (Exception e) {
            System.err.println("Error generando reporte de vendedores: " + e.getMessage());
        }
    }

    /**
     * Generar reporte de productos vendidos (cantidad total).
     */
    public static void generarReporteProductos(Map<String, Producto> productos, ArrayList<Vendedor> vendedores) {
        // Mapa para acumular cantidades vendidas por producto
        Map<String, Integer> ventasPorProducto = new HashMap<>();

        // Recorremos los archivos de cada vendedor
        for (Vendedor v : vendedores) {
            String archivoVentas = "vendedor_" + v.getNumeroDocumento() + ".csv";

            try (BufferedReader br = new BufferedReader(new FileReader(archivoVentas))) {
                String linea;
                boolean primera = true;

                while ((linea = br.readLine()) != null) {
                    if (primera) {
                        primera = false; // saltar encabezado
                        continue;
                    }

                    String[] partes = linea.split(";");
                    String idProducto = partes[0];
                    int cantidad = Integer.parseInt(partes[1]);

                    ventasPorProducto.put(idProducto, ventasPorProducto.getOrDefault(idProducto, 0) + cantidad);
                }

            } catch (IOException e) {
                System.err.println("Error leyendo " + archivoVentas + ": " + e.getMessage());
            }
        }

				//ordenar el mapa por cantidad vendida descendente
				List<Map.Entry<String, Integer>> listaOrdenada = new ArrayList<>(ventasPorProducto.entrySet());
				listaOrdenada.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // Formato para números sin notación científica
        DecimalFormat df = new DecimalFormat("#.##");

        // Escribimos el reporte
        try (PrintWriter writer = new PrintWriter("reporte_productos.csv", "UTF-8")) {
            writer.println("IDProducto;Nombre;Precio;CantidadVendida");

            for (Map.Entry<String, Integer> entry : listaOrdenada) {
                String id = entry.getKey();
                int cantidad = entry.getValue();
                double precio = 0.0;
                if (productos.get(id) != null) {
                    precio = productos.get(id).getPrecio();
                }
                String nombre = productos.get(id) != null ? productos.get(id).getNombreProducto() : "Desconocido";

                writer.println(id + ";" + nombre + ";" + df.format(precio) + ";" + cantidad);
            }

            System.out.println("Reporte de productos generado en reporte_productos.csv");
        } catch (Exception e) {
            System.err.println("Error generando reporte de productos: " + e.getMessage());
        }
    }
}
