package cfpProyecto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // 1. Cargar productos
        Map<String, Double> productos = cargarProductos("productos.csv");

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
    public static Map<String, Double> cargarProductos(String rutaArchivo) {
        Map<String, Double> productos = new HashMap<>();
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
                double precio = Double.parseDouble(partes[2]);
                productos.put(id, precio);
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
    public static double calcularTotalVentas(String rutaArchivo, Map<String, Double> productos) {
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
                    double precio = productos.get(idProducto);
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
    public static void generarReporteVendedores(ArrayList<Vendedor> vendedores, Map<String, Double> productos) {
        try (PrintWriter writer = new PrintWriter("reporte_vendedores.csv", "UTF-8")) {
            writer.println("Vendedor;Total");

            for (Vendedor v : vendedores) {
                String archivoVentas = "vendedor_" + v.getNumeroDocumento() + ".csv";
                double total = calcularTotalVentas(archivoVentas, productos);

                String nombreCompleto = v.getNombre() + " " + v.getApellido();
                writer.println(nombreCompleto + ";" + total);
            }

            System.out.println("Reporte de vendedores generado en reporte_vendedores.csv");
        } catch (Exception e) {
            System.err.println("Error generando reporte de vendedores: " + e.getMessage());
        }
    }

    /**
     * Generar reporte de productos vendidos (cantidad total).
     */
    public static void generarReporteProductos(Map<String, Double> productos, ArrayList<Vendedor> vendedores) {
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

        // Escribimos el reporte
        try (PrintWriter writer = new PrintWriter("reporte_productos.csv", "UTF-8")) {
            writer.println("IDProducto;Precio;CantidadVendida");

            for (Map.Entry<String, Integer> entry : ventasPorProducto.entrySet()) {
                String id = entry.getKey();
                int cantidad = entry.getValue();
                double precio = productos.getOrDefault(id, 0.0);

                writer.println(id + ";" + precio + ";" + cantidad);
            }

            System.out.println("Reporte de productos generado en reporte_productos.csv");
        } catch (Exception e) {
            System.err.println("Error generando reporte de productos: " + e.getMessage());
        }
    }
}
