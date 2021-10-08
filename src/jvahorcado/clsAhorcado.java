/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jvahorcado;

import java.text.Normalizer;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author efras
 */
public class clsAhorcado {   
    // Atributos
    int numPalabras;
    int numIntentos;
    String[][] palabras;
    String palabraSeleccionada;
    char[][] letrasPlabra;
    // Constructor
    public clsAhorcado() {
        numPalabras = 0;
        numIntentos = 0;
        palabras = new String[2][2];
        letrasPlabra = new char[2][2];
        palabraSeleccionada = "";
    }
    // Propiedades get y set
    public int getNumPalabras() {
        return numPalabras;
    }

    public void setNumPalabras(int numPalabras) {
        this.numPalabras = numPalabras;
    }

    public int getNumIntentos() {
        return numIntentos;
    }

    public void setNumIntentos(int numIntentos) {
        this.numIntentos = numIntentos;
    }

    public String[][] getPalabras() {
        return palabras;
    }

    public void setPalabras(String[][] palabras) {
        this.palabras = palabras;
    }

    public String getPalabraSeleccionada() {
        return palabraSeleccionada;
    }

    public void setPalabraSeleccionada(String palabraSeleccionada) {
        this.palabraSeleccionada = palabraSeleccionada;
    }

    public char[][] getLetrasPlabra() {
        return letrasPlabra;
    }

    
    public void setLetrasPlabra(char[][] letrasPlabra) {
        this.letrasPlabra = letrasPlabra;
    }
    
    // Metodos
    public void generarNumPalabras(){
        int n = ThreadLocalRandom.current().nextInt(3, 12);
        setNumPalabras(n);
    }
    public int validadPalabra(String palabra, String[][]arrP){
        // 0 -> Valida
        // 1 -> error longitud
        // 2 -> error espacio
        // 3 -> error repetida
        // 4 -> valor númerico
        // 5 -> letra ñ
        int valida = 0, contRep = 0;
        boolean espacio = palabra.contains(" ");
        boolean letra = palabra.contains("ñ");
        if (palabra.length()<2) {
            valida = 1;
        }else if(espacio){
            valida = 2;
        }else if(letra){
            valida = 5;
        }else if(Pattern.matches("[a-zA-Z]+", palabra) == false){
            valida = 4;
        }else{
            for (int i = 0; i < arrP.length; i++) {
                if (arrP[0][i].equals(normalizarPalabra(palabra))) {
                    contRep++;
                }
            }
            if (contRep > 0) {
                valida = 3;
            }
        }
        return valida;
    }
    public int validarLetra(String letraV){
        // 0 -> Valida
        // 1 -> error longitud
        // 2 -> error espacio
        // 3 -> error repetida
        // 4 -> valor númerico
        // 5 -> letra ñ
        int valida = 0;
        boolean espacio = letraV.contains(" ");
        boolean letra = letraV.contains("ñ");
        if (letraV.length()!=1) {
            valida = 1;
        }else if(espacio){
            valida = 2;
        }else if(letra){
            valida = 5;
        }else if(Pattern.matches("[a-zA-Z]+", letraV) == false){
            valida = 4;
        }else{
            char let = letraV.charAt(0);
            for (int i = 0; i < letrasPlabra[0].length; i++) {
                if (let == letrasPlabra[0][i]) {
                    if (letrasPlabra[1][i] == '0') {
                        valida = 3;
                        numIntentos += 1;
                        break;
                    }
                }
            }
        }
        return valida;
    }
    public String normalizarPalabra(String palabra) { // Método para hacer convertir a minúsculas y retirar los acentos
        palabra = palabra.toLowerCase();
        palabra = Normalizer.normalize(palabra, Normalizer.Form.NFD) 
        .replaceAll("\\p{InCombiningDiacriticalMarks}+", ""); 
        return palabra;
    }
    public boolean seleccionarPalabra(){
        boolean palabraContrada = true;
        boolean palabrasRestantes = false;
        int n = 0;
        while (palabraContrada) {            
            n = ThreadLocalRandom.current().nextInt(0, numPalabras);
            // Validar que existan palabras sin seleccionar
            for (int i = 0; i < numPalabras; i++) {
                if (!palabras[1][i].equals("-1")) {
                    palabrasRestantes = true;
                    break;
                }
            }
            if (!palabrasRestantes) {
                palabraSeleccionada = "Ya no quedan palabras por seleccionar";
                break;
            }
            for (int i = 0; i < numPalabras; i++) {
                if (i == n) {
                    if (!palabras[1][i].equals("-1")) {
                        palabraSeleccionada = palabras[0][i];
                        letrasPlabra = new char[2][palabraSeleccionada.length()];
                        letrasPlabra[0] = palabraSeleccionada.toCharArray();
                        for (int j = 0; j < letrasPlabra[0].length; j++) {
                            letrasPlabra[1][j] = '1';
                        }
                        palabras[1][i] = "-1";
                        palabraContrada = false;
                        break;
                    }
                }
            }
        }
        return palabrasRestantes;
        
    }
    public boolean compararLetra(String letraV){
        boolean letraEncontrada = false;
        char letra = letraV.charAt(0);
        for (int i = 0; i < letrasPlabra[0].length; i++) {
            if (letra == letrasPlabra[0][i]) {
                letrasPlabra[1][i] = '0'; // Encontrada
                letraEncontrada = true;
            }
        }
        if (!letraEncontrada) {
            numIntentos += 1;
            return false;
        }else{
            return true;
        }
    }
    public void limpiarObjetos(){
        numPalabras = 0;
        numIntentos = 0;
        palabras = new String[2][2];
        letrasPlabra = new char[2][2];
        palabraSeleccionada = "";
    }
    
}
