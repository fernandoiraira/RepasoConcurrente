/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ej_Observatorio;

/**
 *
 * @author Fernando
 */
public class Investigador implements Runnable {

    private Observatorio observatorio;

    public Investigador(Observatorio obs) {
        this.observatorio = obs;
    }

    public void run() {
        try {
            this.observatorio.entrarInvestigador();
            this.investigar();
            this.observatorio.salir(false);
        } catch (Exception e) {
        }

    }

    private void investigar() {
        try {
            while (true) {
                System.out.println(Thread.currentThread().getName() + " esta realizando una investigacion...");
                Thread.sleep(6000);
                System.out.println(Thread.currentThread().getName() + " no descubrió nada pero bueno.");
            }

        } catch (InterruptedException ex) {

        }
    }

}
