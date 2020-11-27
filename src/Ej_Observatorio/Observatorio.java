/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ej_Observatorio;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Fernando
 */
public class Observatorio {

    private int capacidadMax;
    private int cantVisitantesActuales = 0;
    private int cantManitasActuales = 0;
    private int capacidadReducida;
    private int capacidadActual;
    private int cantPersonasEnSillaRuedas = 0;
    private boolean necesitaMantenimiento = false;
    private int cantVisitantesDesdeMantenimiento = 0;
    private boolean hayInvestigador = false;
    private int manitasPermitidos;

    private Lock mutex = new ReentrantLock();
    private Condition hayLugar = mutex.newCondition();
    private Condition noHayVisitantes = mutex.newCondition();
    private Condition esperandoParaMantenimiento = mutex.newCondition();

    public Observatorio(int capMax, int capRed) {
        this.capacidadMax = capMax;
        this.capacidadReducida = capRed;
        this.capacidadActual = this.capacidadMax;
        this.manitasPermitidos = this.capacidadMax;
    }

    public void entrarVisitante(boolean enSillaRuedas) throws InterruptedException {
        mutex.lock();

        while (this.cantVisitantesActuales >= this.capacidadActual || this.cantVisitantesDesdeMantenimiento >= 10) {
            System.out.println(Thread.currentThread().getName() + " no pudo entrar, tiene que esperar.");
            hayLugar.await();
        }

        System.out.println(Thread.currentThread().getName() + " pudo entrar al observatorio.");
        this.cantVisitantesActuales++;
        this.cantVisitantesDesdeMantenimiento++;

        if (enSillaRuedas) {
            this.cantPersonasEnSillaRuedas++;
            this.capacidadActual = this.capacidadReducida;
        }

        mutex.unlock();
    }

    public void entrarInvestigador() throws InterruptedException {
        mutex.lock();

        while (this.cantVisitantesActuales >= 0 || hayInvestigador) {
            System.out.println(Thread.currentThread().getName() + " no pudo entrar al observatorio.");
            this.noHayVisitantes.await();
        }

        System.out.println(Thread.currentThread().getName() + " pudo entrar al observatorio.");
        this.hayInvestigador = true;

        //SUPONGO QUE EL INVESTIGADOR NO ENSUCIA, POR LO QUE NO REQUIERE MANTENIMIENTO
        mutex.unlock();
    }

    public void entrarMantenimiento() throws InterruptedException {
        mutex.lock();
        try {
            while (!this.necesitaMantenimiento || this.cantManitasActuales >= this.capacidadActual || this.manitasPermitidos == 0) {
                System.out.println(Thread.currentThread().getName() + " no pudo entrar al observatorio.");
                this.esperandoParaMantenimiento.await();
            }

            System.out.println(Thread.currentThread().getName() + " pudo entrar al observatorio.");
            this.manitasPermitidos--;
            this.cantManitasActuales++;
        } finally {
            mutex.unlock();
        }
    }

    public void salirManitas() throws InterruptedException {
        mutex.lock();
        this.cantManitasActuales--;

        if (this.cantManitasActuales == 0) {
            System.out.println("----------MANTENIMIENTO FINALIZADO----------");
            this.necesitaMantenimiento = false;
            this.manitasPermitidos = this.capacidadMax;
            this.cantVisitantesDesdeMantenimiento = 0;
            this.hayLugar.signalAll();
        }

        mutex.unlock();
    }

    public void salir(boolean enSillaRuedas) {
        mutex.lock();

        this.cantVisitantesActuales--;
        System.out.println(Thread.currentThread().getName() + " salió del observatorio.");

        if (enSillaRuedas) {
            this.cantPersonasEnSillaRuedas--;

            if (this.cantPersonasEnSillaRuedas == 0) {
                this.capacidadActual = this.capacidadMax;
            }
        }

        if (this.cantVisitantesActuales == 0) {
            System.out.println("----------FLACO LIMPIAME ESTO POR FAVOR----------");
            this.necesitaMantenimiento = true;
            this.esperandoParaMantenimiento.signalAll();
        } else {
            hayLugar.signalAll();
        }

        //AVISAR EN ALGUN MOMENTO AL INVESTIGADOR
        mutex.unlock();
    }

    public void salirInvestigador() {
        mutex.lock();

        this.hayInvestigador = false;

        System.out.println(Thread.currentThread().getName() + " salió del observatorio.");

        mutex.unlock();
    }

    private boolean precisaMantenimiento() {
        return (this.cantVisitantesDesdeMantenimiento == 10 && this.cantVisitantesActuales == 0);
    }

}
