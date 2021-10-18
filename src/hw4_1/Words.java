package hw4_1;

public class Words extends Thread {
    private final Object mon = new Object();
    private volatile char currentLetter = 'A';

    /*1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз
         (порядок – ABСABСABС).
         Используйте wait/notify/notifyAll.//
     */
    public static void main(String[] args) {
        Words words = new Words();
        Thread threadA = new Thread(){
            @Override
            public void run() {
                words.printA();
            }
        };
        Thread threadB = new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    words.printB();
                }
            }
        };
        Thread threadC = new Thread(){
            @Override
            public void run() {
                words.printC();
            }
        };

        threadA.start();
        threadB.start();
        threadC.start();

    }

    public void printA() {
        synchronized (mon) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'A') {
                        mon.wait();
                    }
                    System.out.print("A");
                    currentLetter = 'B';
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printB() {
        synchronized (mon) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'B') {
                        mon.wait();
                    }
                    System.out.print("B");
                    currentLetter = 'C';
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printC() {
        synchronized (mon) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'C') {
                        mon.wait();
                    }
                    System.out.print("C");
                    currentLetter = 'A';
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
