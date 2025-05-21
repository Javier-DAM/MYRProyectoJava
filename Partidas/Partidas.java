package Partidas;

import java.io.*;
import java.util.ArrayList;

public class Partidas {

    private final String carpetaRuta = "C:/Partida/";
    private final String archivoJ1Ruta = carpetaRuta + "Jugador1.txt";
    private final String archivoJ2Ruta = carpetaRuta + "Jugador2.txt";

    /**
     * Guarda en los txt los resultados totales
     * @param enemigosDerrotadosJ1 del jugador 1
     * @param enemigosDerrotadosJ2 del jugador 2
     */
    public void guardarPartida(int enemigosDerrotadosJ1, int enemigosDerrotadosJ2) {
        File carpeta = new File(carpetaRuta);
        if (!carpeta.exists()) {
            carpeta.mkdir();
            System.out.println("Carpeta creada en: " + carpetaRuta);
        }

        // Guardar para Jugador 1
        int guardadoJ1 = leerPuntajeDesdeArchivo(archivoJ1Ruta);
        if (enemigosDerrotadosJ1 > guardadoJ1) {
            escribirPuntaje(archivoJ1Ruta, enemigosDerrotadosJ1);
            System.out.println("Jugador 1 ha superado su récord anterior.");
        } else {
            System.out.println("Jugador 1 no superó su récord anterior.");
        }

        // Guardar para Jugador 2
        int guardadoJ2 = leerPuntajeDesdeArchivo(archivoJ2Ruta);
        if (enemigosDerrotadosJ2 > guardadoJ2) {
            escribirPuntaje(archivoJ2Ruta, enemigosDerrotadosJ2);
            System.out.println("Jugador 2 ha superado su récord anterior.");
        } else {
            System.out.println("Jugador 2 no superó su récord anterior.");
        }
    }

    /**
     * Cargar el archito y y guardarlo en el array para enviarlo a Menu.drawMuerte()
     * @return
     */
    public ArrayList<String> cargarPartida() {
        ArrayList<String> datos = new ArrayList<>();
        int puntajeJ1 = leerPuntajeDesdeArchivo(archivoJ1Ruta);
        int puntajeJ2 = leerPuntajeDesdeArchivo(archivoJ2Ruta);

        datos.add(String.valueOf(puntajeJ1));
        datos.add(String.valueOf(puntajeJ2));

        return datos;
    }

    /**
     * Lee el puntaje
     * @param rutaArchivo recibe esto apra saber dónde está
     * @return el resultado (Si no existe, es 0)
     */
    private int leerPuntajeDesdeArchivo(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return 0;

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea = lector.readLine();
            if (linea != null && !linea.isEmpty()) {
                return Integer.parseInt(linea.trim());
            }
        } catch (Exception e) {
            System.out.println("Error al leer archivo: " + rutaArchivo + " - " + e.getMessage());
        }

        return 0;
    }

    /**
     * Escribe el puntaje en el txt
     * @param rutaArchivo
     * @param puntaje del jugador
     */
    private void escribirPuntaje(String rutaArchivo, int puntaje) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo))) {
            escritor.write(String.valueOf(puntaje));
        } catch (Exception e) {
            System.out.println("Error al escribir archivo: " + rutaArchivo + " - " + e.getMessage());
        }
    }
}


