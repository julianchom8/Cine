/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_cine;

import java.util.ArrayList;

/**
 *
 * @author David
 */
public class Proceso {
    
    private ArrayList<Object> a = new ArrayList<>();
    
    public Proceso(){}
    
    public Proceso(ArrayList<Object> a){
        this.a = a;
    }
    
    public void agregarRegistro(Pelicula p){
        this.a.add(p);
    }

    public void modificarRegistro(int i, Pelicula p){
        this.a.set(i, p);
    }
    
    public void eliminarRegistro(int i){
        this.a.remove(i);
    }
    
    public Pelicula obtenerRegistro(int i){
        return (Pelicula)a.get(i);
    }
    
    public int cantidadRegistro(){
        return this.a.size();
    }
    
    public int buscaCodigo(int codigo){
        for(int i = 0; i < cantidadRegistro(); i++){
            if(codigo == obtenerRegistro(i).getCodigo())return i;
        }
        return -1;
    }
    
}
