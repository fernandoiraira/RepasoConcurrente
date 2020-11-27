/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ej_Tren_Semaforo;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Fernando
 */
public class Tren {

    private Semaphore semVendedor = new Semaphore(0);
    private Semaphore semTickets;
    private Semaphore semSubirTren = new Semaphore(0);
    private Semaphore semIniciarViaje = new Semaphore(0);
    private Semaphore semBajarTren = new Semaphore(0);
    private Semaphore mutex = new Semaphore(1);
    private int capacidadMax;
    private int capacidadActual = 0;

    public Tren(int capacidadMaxTren) {
        this.capacidadMax = capacidadMaxTren;
        this.semTickets = new Semaphore(this.capacidadMax);
    }

    public void entrar() {

        try {
            System.out.println(Thread.currentThread().getName() + " intenta entrar al tren...");
            this.semTickets.acquire();

            this.semVendedor.release();

            this.semSubirTren.acquire();

            System.out.println(Thread.currentThread().getName() + " pudo entrar al tren...");

            this.mutex.acquire();

            this.capacidadActual++;

            if (this.capacidadActual == this.capacidadMax) {
                this.semIniciarViaje.release();
            }

            this.mutex.release();

        } catch (Exception e) {
        }

    }

    public void salir() {

        try {
            this.semBajarTren.acquire();

            System.out.println(Thread.currentThread().getName() + " sali+o del tren y se fue.");
        } catch (Exception e) {
        }

    }

    public void atender() {

        try {
            System.out.println(Thread.currentThread().getName() + " está esperando a los pasajeros...");
            this.semVendedor.acquire();

            System.out.println(Thread.currentThread().getName() + " está atendiendo a un pasajero...");

            Thread.sleep(2000);

            System.out.println(Thread.currentThread().getName() + " le dio el ticket al pasajero...");

            this.semSubirTren.release();

        } catch (Exception e) {
        }

    }

    public void iniciarViaje() {

        try {
            System.out.println("El tren esta en espera...");
            this.semIniciarViaje.acquire();

            System.out.println("El tren dejó de aceptar pasajeros y esta en movimiento...");
        } catch (Exception e) {
        }

    }

    public void terminarViaje() {

        System.out.println("El tren llegó a su destino. Los pasajeros se estan bajando...");

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
        }

        this.capacidadActual = 0;

        this.semBajarTren.release(this.capacidadMax);
        this.semTickets.release(this.capacidadMax);
    }
}
