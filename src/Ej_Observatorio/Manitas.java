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
public class Manitas implements Runnable {

    private Observatorio observatorio;

    public Manitas(Observatorio obs) {
        this.observatorio = obs;
    }

    public void run() {
        try {
            while (true) {
                this.observatorio.entrarMantenimiento();
                this.limpiar();
                this.observatorio.salirManitas();
            }

        } catch (Exception e) {
        }
    }

    private void limpiar() {
        try {

            System.out.println(Thread.currentThread().getName() + " esta realizando mantenimiento...");
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + " dejó su parte limpia y se fue.");

        } catch (InterruptedException ex) {

        }
    }
}
