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
public class Brazo implements Runnable {

    private Pasteleria pasteleria;

    public Brazo(Pasteleria p) {
        this.pasteleria = p;
    }

    public void run() {

        try {
            while (true) {
                this.pasteleria.retirarCaja();
                this.reponer();
                this.pasteleria.reponerCaja();
            }
        } catch (Exception e) {
        }

    }

    private void reponer() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {

        }

    }
}
