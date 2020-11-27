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
public class Horno implements Runnable {

    private Pasteleria pasteleria;
    private int peso;

    public Horno(Pasteleria p, int peso) {
        this.pasteleria = p;
        this.peso = peso;
    }

    public void run() {
        try {
            while (true) {
                pasteleria.dejarPastel(this.peso);
            }
        } catch (Exception e) {
        }

    }

}
