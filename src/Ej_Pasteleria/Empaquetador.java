/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ej_Pasteleria;

/**
 *
 * @author Fernando
 */
public class Empaquetador implements Runnable {

    private Pasteleria pasteleria;
    private int pesoPastel = 0;

    public Empaquetador(Pasteleria p) {
        this.pasteleria = p;
    }

    public void run() {
        try {
            while (true) {
                this.pesoPastel = pasteleria.tomarPastel();
                this.moverPastel(this.pesoPastel);
                this.pasteleria.soltarPastel(this.pesoPastel);
            }
        } catch (Exception e) {
        }

    }

    private void moverPastel(int peso) {
        //Tarda segun el peso del pastel
        try {
            Thread.sleep(peso * 1000);
        } catch (InterruptedException ex) {
        }
    }

}
