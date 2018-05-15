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
public class Sala {
    
    private int codigoSala;
    private int capacidad;

    public Sala(){}
    
    public Sala(int codigoSala, int capacidad){
        this.codigoSala = codigoSala;
        this.capacidad = capacidad;

    }
        /**
     * @return the codigoSala
     */
        public int getCodigoSala() {
        return codigoSala;
    }

    /**
     * @param codigoSala the codigoSala to set
     */
    public void setCodigoSala(int codigoSala) {
        this.codigoSala = codigoSala;
    }


    /**
     * @return the capacidad
     */
    public int getCapacidad() {
        return capacidad;
    }
    /**
     * @param capacidad the capacidad to set
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

}