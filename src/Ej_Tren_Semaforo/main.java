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
public class main {

    public static void main(String[] args) {
        int capacidadMaxTren = 5;
        int cantPasajeros = 15;

        Tren tren = new Tren(capacidadMaxTren);

        ControlTren ct = new ControlTren(tren);
        Thread control = new Thread(ct, "CONTROL TREN");
        control.start();

        VendedorTickets v = new VendedorTickets(tren);
        Thread vendedor = new Thread(v, "VENDEDOR");
        vendedor.start();

        for (int i = 1; i <= cantPasajeros; i++) {
            Pasajero p = new Pasajero(tren);
            Thread pasajero = new Thread(p, "Pasajero " + i);
            pasajero.start();
        }

    }

}
