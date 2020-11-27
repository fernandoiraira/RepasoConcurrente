/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ej_Observatorio;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fernando
 */
public class Visitante implements Runnable {

    private Observatorio observatorio;
    private boolean enSillaDeRuedas;

    public Visitante(Observatorio obs, boolean enSilla) {
        this.observatorio = obs;
        this.enSillaDeRuedas = enSilla;
    }

    public void run() {
        try {
            observatorio.entrarVisitante(this.enSillaDeRuedas);
            this.recorrerObservatorio();
            observatorio.salir(this.enSillaDeRuedas);
        } catch (Exception e) {
        }

    }

    private void recorrerObservatorio() {

        try {
            System.out.println(Thread.currentThread().getName() + " esta recorriendo el observatorio...");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " termino el recorrido.");
        } catch (InterruptedException ex) {
            Logger.getLogger(Visitante.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
