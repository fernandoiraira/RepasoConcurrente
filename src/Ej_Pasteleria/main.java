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
public class main {

    public static void main(String[] args) {
        int capacidadMostrador = 5;
        int pesoMaximoCaja = 16;
        int pesoA = 1;
        int pesoB = 3;
        int pesoC = 5;
        int cantEmpaquetadores = 2;

        Pasteleria pasteleria = new Pasteleria(capacidadMostrador, pesoMaximoCaja);

        Horno h = new Horno(pasteleria, pesoA);
        Thread hornoA = new Thread(h, "Horno A");
        hornoA.start();

        Horno h2 = new Horno(pasteleria, pesoB);
        Thread hornoB = new Thread(h2, "Horno B");
        hornoB.start();

        Horno h3 = new Horno(pasteleria, pesoC);
        Thread hornoC = new Thread(h3, "Horno C");
        hornoC.start();

        for (int i = 1; i <= cantEmpaquetadores; i++) {
            Empaquetador e = new Empaquetador(pasteleria);
            Thread empaquetador = new Thread(e, "Empaquetador " + i);
            empaquetador.start();
        }

        Brazo b = new Brazo(pasteleria);
        Thread brazo = new Thread(b, "BRAZO");
        brazo.start();
    }

}
