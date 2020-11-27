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
public class ControlTren implements Runnable {

    private Tren tren;

    public ControlTren(Tren tren) {
        this.tren = tren;
    }

    public void run() {
        while (true) {
            tren.iniciarViaje();
            this.viajar();
            tren.terminarViaje();
        }
    }

    private void viajar() {

        try {
            Thread.sleep(4000);
        } catch (Exception e) {
        }

    }

}
