/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_cine;

/**
 *
 * @author David 
 */
//cacorro
public class Pelicula {
    
    private int codigo;
    private String nombre;
    private String sinopsis;
    private String ruta_imagen;

    public Pelicula(){}
    
    public Pelicula(int codigo, String nombre, String sinopsis,String ruta){
        this.codigo = codigo;
        this.nombre = nombre;
        this.sinopsis = sinopsis;
        this.ruta_imagen = ruta;
        
    }


    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the nombre
     */
    public String getSinopsis() {
        return sinopsis;
    }

    /**
     * @param sinopsis the sinopsis to set
     */
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
    
    public String getRuta_imagen() {
        return ruta_imagen;
    }
    /**
     * @param ruta_imagen the ruta to set
     */
    public void setRuta_imagen(String ruta_imagen) {
        this.ruta_imagen = ruta_imagen;
    }


}
