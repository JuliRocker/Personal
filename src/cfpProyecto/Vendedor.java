	package cfpProyecto;

	/**
	 * Representa un vendedor con tipo de documento, número, nombre y apellido.
	 */
	public class Vendedor {

	    private String tipoDocumento;
	    private String numeroDocumento;
	    private String nombre;
	    private String apellido;

	    /**
	     * Constructor.
	     *
	     * @param tipoDocumento tipo de documento (ej: "C.C")
	     * @param numeroDocumento número de documento
	     * @param nombre nombres del vendedor
	     * @param apellido apellidos del vendedor
	     */
	    public Vendedor(String tipoDocumento, String numeroDocumento, String nombre, String apellido) {
	        this.tipoDocumento = tipoDocumento;
	        this.numeroDocumento = numeroDocumento;
	        this.nombre = nombre;
	        this.apellido = apellido;
	    }

	    // Getters
	    public String getTipoDocumento() { return tipoDocumento; }
	    public String getNumeroDocumento() { return numeroDocumento; }
	    public String getNombre() { return nombre; }
	    public String getApellido() { return apellido; }

	    @Override
	    public String toString() {
	        // formato legible en consola; puedes cambiarlo si prefieres otro separador
	        return tipoDocumento + ";" + numeroDocumento + ";" + nombre + ";" + apellido;
	    }
	}

