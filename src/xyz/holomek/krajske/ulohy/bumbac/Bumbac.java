package xyz.holomek.krajske.ulohy.bumbac;

public class Bumbac {

    /*
    metoda na zavolani
     */
    public void start() {
        for (int i = 1; i <= 100; i++) {
            // jelikoz potrebujeme oddelavat carkou, lehka podminka
            if (i != 1) {
                System.out.print(", ");
            }
            // jednoduche podminky na deleni
            if (i % 5 == 0 && i % 3 == 0) {
                System.out.print("bumbác");
            } else if (i % 5 == 0) {
                System.out.print("bác");
            } else if (i % 3 == 0) {
                System.out.print("bum");
            } else {
                System.out.print(i);
            }
        }

    }

}
