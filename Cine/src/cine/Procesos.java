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
public class Procesos {
    
    private ArrayList<Object> b = new ArrayList<>();
    
    public Procesos(){}
    
    public Procesos(ArrayList<Object> b){
        this.b = b;
    }
    
    public void agregarRegistro(Sala s){
        this.b.add(s);
    }

    public void modificarRegistro(int i, Sala s){
        this.b.set(i, s);
    }
    
    public void eliminarRegistro(int i){
        this.b.remove(i);
    }
    
    public Sala obtenerRegistro(int i){
        return (Sala)b.get(i);
    }
    
    public int cantidadRegistro(){
        return this.b.size();
    }
    
    public int buscaCodigo(int codigo){
        for(int i = 0; i < cantidadRegistro(); i++){
            if(codigo == obtenerRegistro(i).getCodigoSala())return i;
        }
        return -1;
    }
    
}
