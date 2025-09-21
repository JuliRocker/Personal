package cfpProyecto;
/**
 * Representa un producto con ID, nombre y precio.
 */
public class Producto {

    private String IDProducto;
    private String nombreProducto;
    private Double precio;

    /**
     * Constructor.
     *
     * @param IDProducto ID del producto
     * @param nombreProducto nombre del producto
     * @param precio precio del producto
     */
    public Producto(String IDProducto, String nombreProducto, Double precio) {
        this.IDProducto = IDProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
    }

    // Getters
    public String getIDProducto() {
        return IDProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public Double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        // formato legible en consola; puedes cambiarlo si prefieres otro separador
        return IDProducto + ";" + nombreProducto + ";" + precio;
    }
}
