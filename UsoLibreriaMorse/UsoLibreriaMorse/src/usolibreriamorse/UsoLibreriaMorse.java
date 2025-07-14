package usolibreriamorse;

import morsafinal.LibreriaMorse;
import java.util.Scanner;

public class UsoLibreriaMorse {
    

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        LibreriaMorse libreria = new LibreriaMorse();
        // Convertir texto normal a Morse
        System.out.print("Ingresa una frase para convertir a código Morse: ");
        String palabra = sc.nextLine();
        System.out.println("Texto a Morse: " + libreria.normalAMorse(palabra));
        
        // Convertir texto Morse a normal
        System.out.print("Ingresa una frase en morse para convertir a español: ");
        String palabrr = sc.nextLine();
        System.out.println("Texto a Morse: " + libreria.morseANormal(palabrr));
        

        // Convertir binario a ASCII
        System.out.print("\nIngresa un número en binario para convertir a ASCII: ");
        String bin = sc.nextLine();
        System.out.println("Binario a ASCII: " + libreria.binarioAAscii(bin));

        // Convertir texto normal a binario
        System.out.print("\nIngresa una frase para convertir a binario: ");
        String fras = sc.nextLine();
        System.out.println("Texto a Binario: " + libreria.palabraABinario(fras));

        // Reproducir Morse como sonido
        System.out.print("\nIngresa una frase en Morse para reproducir: ");
        String mor = sc.nextLine();
        libreria.morseASonido(mor);
        System.out.println("Morse reproducido correctamente.");

        // Convertir texto normal a su equivalencia en audio del codigo Morse
        System.out.print("\nIngresa una frase normal para que suene en Morse: ");
        String lol = sc.nextLine();
        libreria.normalAsonido(lol);
        System.out.println("Texto reproducido en Morse.");
        
        
        /*NOtaPARA QUIEN LO LEA: las validaciones del .jar se hicieron usando JOptionPane,
        por tanto no pueden mostrarse al momento de ejecutar el Main*/
    }
}
