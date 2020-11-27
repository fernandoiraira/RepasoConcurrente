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
public class main {

    public static void main(String[] args) {
        int capacidadMax = 5;
        int capacidadRed = 2;
        int cantVisitantes = 15;
        int cantManitas = 10;

        Observatorio obs = new Observatorio(capacidadMax, capacidadRed);

        for (int i = 1; i <= cantVisitantes; i++) {
            Visitante v = new Visitante(obs, true);
            Thread visitante = new Thread(v, "Visitante " + i);
            visitante.start();
        }

        for (int i = 1; i <= cantManitas; i++) {
            Manitas m = new Manitas(obs);
            Thread manitas = new Thread(m, "MANITAS " + i);
            manitas.start();
        }

    }

}
