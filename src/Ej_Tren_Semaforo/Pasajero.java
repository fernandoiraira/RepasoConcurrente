/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ej_Tren_Semaforo;

/**
 *
 * @author Fernando
 */
public class Pasajero implements Runnable {

    private Tren tren;

    public Pasajero(Tren tren) {
        this.tren = tren;
    }

    public void run() {
        tren.entrar();
        tren.salir();
    }

}
