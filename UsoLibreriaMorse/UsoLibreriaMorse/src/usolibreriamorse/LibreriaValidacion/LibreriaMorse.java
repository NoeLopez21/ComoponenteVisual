/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package usolibreriamorse.LibreriaValidacion;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import javax.sound.sampled.*;
import javax.swing.JOptionPane;

/**
 * Clase de tipo final que permite convertir texto normal a código Morse, codigo binario y viceversa
 *ademas incluye funciones para traducir el texto normal y morse a su equivalente en audio
 * Autores: Oliver y Noé
 */
public final class LibreriaMorse {
    private static ArrayList<String> abecedario;

    private static ArrayList<String> morse;

    private static Clip clipPunto, clipRaya;

    /**
     * Constructor que inicializa las listas abecedario y morse que se ocupan en los diferentes metodos.
     * Estas listas son usadas para convertir letras a sus equivalentes Morse y viceversa
     */
    public LibreriaMorse() {
        // Inicializa la lista del abecedario en mayúsculas
        abecedario = new ArrayList<>(Arrays.asList(
            "A","B","C","D","E","F","G","H","I","J","K",
            "L","M","N","Ñ","O","P","Q","R","S","T","U","V","W","X","Y","Z"));

        // Inicializa la lista con el equivalente Morse de cada letra
        morse = new ArrayList<>(Arrays.asList(
            ".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",
            ".-..","--","-.","--.--","---",".--.","--.-",".-.","...","-","..-",
            "...-",".--","-..-","-.--","--.."));
    }

    /**
     * Método para cargar los sonidos de punto y raya una sola vez.
     * Llama a cargarClip para preparar los archivos de audio
     */
    private static void cargarSonidos() {
        // Carga el sonido del punto (.)
        clipPunto = cargarClip("sonidos/beepvlvF.wav");
        // Carga el sonido de la raya (-)
        clipRaya = cargarClip("sonidos/beepF.wav");
    }

    /**
     * Carga un archivo de sonido desde la carpeta de recursos del proyecto.
     * @param ruta Ruta relativa al archivo dentro del proyecto
     * @return Clip de audio listo para ser reproducido
     */
    private static Clip cargarClip(String ruta) {
        try {
            URL soundURL = LibreriaMorse.class.getResource(ruta);
            if (soundURL == null) {
                System.err.println("No se encontró el archivo: " + ruta);
                return null;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);

            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Reproduce un Clip de audio y espera a que termine antes de continuar.
     * Esto es necesario para que los sonidos no se revuelvan al momento de su ejecución
     * @param clip Clip de audio que se va a reproducir
     */
    private static void reproducir(Clip clip) {
        if (clip == null) return; 

        
        Object lock = new Object();
        try {
            clip.setFramePosition(0); 

            
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    synchronized (lock) {
                        lock.notify(); 
                    }
                }
            });

            
            clip.start();

            
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Este metodo transforma un texto morse a sonido.
     * Cada punto y raya es reproducido como un sonido con pausas entre letras y palabras.
     * @param mor Código Morse a reproducir
     */
    public static void morseASonido(String mor) {
        if (!mor.matches("[.\\-/ ]+")) {
        JOptionPane.showMessageDialog(null, "El texto Morse contiene caracteres no válidos",
                                      "Error", JOptionPane.WARNING_MESSAGE);
        return; 
    }
        cargarSonidos(); 
        for (int i = 0; i < mor.length(); i++) {
            char c = mor.charAt(i);
            if (c == '.') {
                reproducir(clipPunto); 
            } else if (c == '-') {
                reproducir(clipRaya);
            } else if (c == ' ' && i + 1 < mor.length() && mor.charAt(i + 1) == '/') {
                pausa(500); 
                i++; 
            } else {
                pausa(200); 
            }
        }
        
    }

    /**
     * Convierte una frase normal a Morse y luego reproduce el sonido correspondiente.
     * @param pala Frase en español que se quiere convertir y reproducir
     */
    public static void normalAsonido(String pala) {
        if (pala == null || !pala.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ ]+")) {
            JOptionPane.showMessageDialog(null,
                "El texto solo debe contener letras y espacios (sin números ni símbolos).",
                "Error", JOptionPane.WARNING_MESSAGE);
            return; 
        }
        String mors = normalAMorse(pala); 
        morseASonido(mors);               
    }

    /**
     * Pausa la ejecución por la cantidad de milisegundos especificada.
     * @param ms Milisegundos a pausar
     */
    private static void pausa(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Convierte una frase en texto normal a código Morse.
     * @param pala Frase en español
     * @return simbolos equivalentes en código Morse
     */
    public static String normalAMorse(String pala) {
        if (pala == null || !pala.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ ]+")) {
            JOptionPane.showMessageDialog(null,
                "El texto solo debe contener letras y espacios (sin números ni símbolos).",
                "Error", JOptionPane.WARNING_MESSAGE);
            return null; 
        }
        StringBuilder cadena = new StringBuilder();
        pala = pala.toUpperCase();
        for (int x = 0; x < pala.length(); x++) {
            cadena.append(recursionMorsa(Character.toString(pala.charAt(x)), 0)).append(" ");
        }
        return cadena.toString().trim();
    }

    /**
     * Busca recursivamente el equivalente en Morse de una letra.
     * @param pal Letra que se va a convertir
     * @param i Índice actual en el abecedario que se va recorriendo con cada llamada recursiva
     * @return Código Morse correspondiente
     */
    private static String recursionMorsa(String pal, int i) {
        if (pal.equals(" ")) return "/"; // Espacio en texto normal se convierte en "/"
        if (pal.equals(abecedario.get(i))) return morse.get(i);
        return recursionMorsa(pal, i + 1); // Llama recursivamente con el siguiente índice
    }
    
    /**
     * Convierte una frase en texto Morse a código Normal.
     * @param morsa Frase en español
     * @return la palabra en español del xodigo morse
     */
    
    public static String morseANormal(String morsa) {
        String letra = "";
        String palabra = "";

        for (int x = 0; x < morsa.length(); x++) {
            char c = morsa.charAt(x);

            if (c == '/') {
                palabra += " ";
            } else if (c == ' ') {
                if (!letra.isEmpty()) {
                palabra += recursionLetra(letra, 0); // ← comenzamos en 0
                letra = "";
                }
            } else {
                letra += c;
            }
        }
        if (!letra.isEmpty()) {
        palabra += recursionLetra(letra, 0);
        }
        palabra =palabra.toLowerCase();
        return palabra;
    }
    
    /**
     * Busca recursivamente el equivalente en Español de un simbolo Morse.
     * @param pala simbolo correspondiente a una letra en Morse
     * @param i Índice actual en el abecedario que se va recorriendo con cada llamada recursiva
     * @return simbolos equivalentes en Español
     */
    private static String recursionLetra(String pal, int i) {
        if (i >= morse.size()) return "?";  // Evita error si no se encuentra
        if (pal.equals(morse.get(i))) {
            return abecedario.get(i);
        }
        return recursionLetra(pal, i + 1);
    }
    
    
    
    /**
     * transforma una palabra encódigo binario a su equivalente en código Ascci
     * @param binario código que se va a convertir
     * @return simbologia Ascci correspondiente al codigo introducido
     */
    public static String binarioAAscii(String binario) {
    if (binario == null || binario.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null,
            "No se ingresó ningún código binario.",
            "Error", JOptionPane.WARNING_MESSAGE);
        return ""; 
    }

    if (!binario.matches("[01\\s]+")) {
        JOptionPane.showMessageDialog(null,
            "El texto solo debe contener 0, 1 y espacios.",
            "Error", JOptionPane.WARNING_MESSAGE);
        return ""; 
    }

    binario = binario.replaceAll("\\s+", "");

    if (binario.length() % 8 != 0) {
        JOptionPane.showMessageDialog(null,
            "⚠️ El binario debe tener un número de bits múltiplo de 8.",
            "Error", JOptionPane.WARNING_MESSAGE);
        return "";
    }

    StringBuilder resultado = new StringBuilder();

   
    for (int i = 0; i < binario.length(); i += 8) {
        String byteStr = binario.substring(i, i + 8); 
        int valorDecimal = Integer.parseInt(byteStr, 2); 

        
        if (valorDecimal < 32 || valorDecimal > 126) {
            JOptionPane.showMessageDialog(null,
                "⚠️ El código binario ingresado contiene valores fuera del rango ASCII imprimible (32-126).",
                "Error", JOptionPane.WARNING_MESSAGE);
            return ""; 
        }

        resultado.append((char) valorDecimal); 
    }

    return resultado.toString();
}

    
    /**
     * transforma una palabra a su equivalente en código binario
     * @param palabra palabra que se va a convertir
     * @return código binario correspondiente al codigo introducido
     */
    public static String palabraABinario(String palabra) {
        if (palabra == null || !palabra.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ ]+")) {
            JOptionPane.showMessageDialog(null,
                "El texto solo debe contener letras y espacios (sin números ni símbolos).",
                "Error", JOptionPane.WARNING_MESSAGE);
            return null; 
        }
        
        String resultado = "";

        for (int i = 0; i < palabra.length(); i++) {
            int codigo = (int) palabra.charAt(i); 

            String binario = "";
            int resto;
            while (codigo > 0) {
               resto = codigo % 2;
                binario = resto + binario;
                codigo = codigo / 2;
            }


            while (binario.length() < 8) {
                binario = "0" + binario;
            }

            resultado += binario + " ";
        }

        return resultado.trim();
    }
}
