/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ej_Pasteleria;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Fernando
 */
public class Pasteleria {

    private int capacidadMostrador;
    private int[] pastel;
    private int indiceMostrador = 0;
    private Lock mutex = new ReentrantLock();
    private Condition esperaParaDepositarPastelEnMostrador = mutex.newCondition();
    private Condition esperaParaRetirarPastelDelMostrador = mutex.newCondition();
    private Condition esperaParaReponerCaja = mutex.newCondition();
    private Condition esperaAQueReponganCaja = mutex.newCondition();
    private boolean hayCaja = true;
    private int pesoMaxCaja;
    private int pesoActualCaja = 0;

    public Pasteleria(int capMostrador, int pesoMaxCaja) {
        this.capacidadMostrador = capMostrador;
        pastel = new int[this.capacidadMostrador];
        this.pesoMaxCaja = pesoMaxCaja;
    }

    public void dejarPastel(int peso) throws InterruptedException { //CORRESPONDE AL HORNO
        mutex.lock();
        try {
            while (this.indiceMostrador >= this.capacidadMostrador) {
                this.esperaParaDepositarPastelEnMostrador.await();
            }

            this.pastel[indiceMostrador] = peso;
            this.indiceMostrador++;
            System.out.println(Thread.currentThread().getName() + " puso un pastel en el mostrador.");

            this.esperaParaRetirarPastelDelMostrador.signalAll();
        } finally {
            mutex.unlock();
        }
    }

    public int tomarPastel() throws InterruptedException {
        mutex.lock();
        int pesoPastel;

        try {

            while (this.indiceMostrador == 0) {
                System.out.println(Thread.currentThread().getName() + " no encontro pastel en el mostrador, esta a la espera.");
                this.esperaParaRetirarPastelDelMostrador.await();
            }

            this.indiceMostrador--;
            pesoPastel = this.pastel[this.indiceMostrador];
            this.pastel[this.indiceMostrador] = 0;
            System.out.println(Thread.currentThread().getName() + " tomo un pastel del mostrador.");

            this.esperaParaDepositarPastelEnMostrador.signalAll();
        } finally {
            mutex.unlock();
        }

        return pesoPastel;
    }

    public void soltarPastel(int pesoPastel) throws InterruptedException {
        mutex.lock();
        try {
            while (pesoPastel + this.pesoActualCaja >= this.pesoMaxCaja) {

                this.hayCaja = false;
                this.esperaParaReponerCaja.signal();

                this.esperaAQueReponganCaja.await();
            }

            this.pesoActualCaja = this.pesoActualCaja + pesoPastel;
            System.out.println("Peso actual de la caja: " + this.pesoActualCaja);
        } finally {
            mutex.unlock();
        }
    }

    public void retirarCaja() throws InterruptedException {
        mutex.lock();
        try {
            while (this.hayCaja) {
                this.esperaParaReponerCaja.await();
            }

            this.hayCaja = true;

            System.out.println(Thread.currentThread().getName() + " esta retirando la caja...");

        } finally {
            mutex.unlock();
        }
    }

    public void reponerCaja() {
        mutex.lock();

        System.out.println(Thread.currentThread().getName() + " esta poniendo una caja nueva...");

        System.out.println(Thread.currentThread().getName() + " repuso la caja.");

        this.pesoActualCaja = 0;

        this.esperaAQueReponganCaja.signalAll();

        mutex.unlock();
    }

}
