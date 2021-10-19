package hw4_1;

public class Words extends Thread {
    private final Object mon = new Object();
    private volatile char currentLetter = 'A';
    private final int num = 5;

    /*1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз
         (порядок – ABСABСABС).
         Используйте wait/notify/notifyAll.//
     */
    public static void main(String[] args) {
        Words words = new Words();
        Thread threadA = new Thread() {
            @Override
            public void run() {
                words.printA('A', 'B');
            }
        };
        Thread threadB = new Thread() {
            @Override
            public void run() {
                words.printA('B', 'C');
            }
        };
        Thread threadC = new Thread() {
            @Override
            public void run() {
                words.printA('C', 'A');
            }
        };

        threadA.start();
        threadB.start();
        threadC.start();
    }

    public void printA(char current, char next) {
        synchronized (mon) {
            boolean interrupted = false;
            try {
                for (int i = 0; i < num; i++) {
                    if (Thread.currentThread().isInterrupted() || interrupted) {
                        break;
                    }
                    while (currentLetter != current) {
                        mon.wait();
                    }
                    System.out.print(current);
                    currentLetter = next;
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                interrupted = true;
//                e.printStackTrace();
            }
        }
    }
}
