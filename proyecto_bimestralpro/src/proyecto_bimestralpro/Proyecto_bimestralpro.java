package proyecto_bimestralpro;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;
public class Proyecto_bimestralpro {
    public static void main(String[] args) {
        int limFilas = 29, limColumnas = 2, limFilasUbi = 72, limFilasOpti = 19;
        String seguir;
        String buscar;
        Scanner teclado = new Scanner(System.in);
        try {
            String[][] datos = leerArchivo("C://documentoInfo.csv", limFilas, limColumnas);
            String[][] datos1 = aumentar("C://documentoInfo.csv", limFilas, limColumnas);
            String[][] ubicacion = leerArchivo("C://ubicaciones.csv", limFilasUbi, limColumnas);
            String[][] opti = leerArchivo("C://optimizacion.csv", limFilasOpti, limColumnas);
            String[][] datosSalidaRutas = new String [limFilas][limColumnas];
            String[][] datosSalidaUbi = new String [limFilasUbi][limColumnas];
            String[][] datosSalidaOpti = new String [limFilasOpti][limColumnas];
            escribirArchivoRutas(datosSalidaRutas, datos1);
            escribirArchivoUbi(datosSalidaUbi, ubicacion);
            escribirArchivoOpti(datosSalidaOpti, opti);
            System.out.println("============ HORARIOS DE LOS BUSES ===============");
            imprimirMatriz(datos);
            System.out.println("");
            System.out.println("============ UBICACIÓN DE LAS PARADAS ============");
            imprimirUbi(ubicacion);
            System.out.println("");
            System.out.println("==============OPTIMIZACIÓN DE RUTAS===============");
            imprimirOpti(opti);
            System.out.println("¿Desea buscar alguna parada? SI/NO");
            seguir = teclado.nextLine();
            while (seguir.equalsIgnoreCase("si")) {
                System.out.println("Ingrese la parada que desea buscar");
                buscar = teclado.nextLine();
                boolean encontrada1 = false;
                System.out.println("RUTAS:");
                for (int i = 0; i < datos.length; i++) {
                    if (datos[i][1].toLowerCase().contains(buscar.toLowerCase())) {
                        System.out.println(datos[i][0] + " | " + datos[i][1]);
                        encontrada1 = true;
                    }
                }
                if(encontrada1 == false){
                    System.out.println("No se encontró información en este parámetro");
                }
                for (int i = 0; i < datos.length; i++) {
                    if (datos[i][1].toLowerCase().contains(buscar.toLowerCase()) && i >= 10 && i <= 24) {
                        System.out.println("==================================================================");
                        System.out.println("AVISO: EN LOS HORARIOS DE 09:30 A 18:10 HAY CONGESTIÓN ESTUDIANTIL");
                        System.out.println("==================================================================");
                        break;
                    }
                }
                System.out.println("UBICACIONES: ");
                boolean encontrada2 = false;
                for (int i = 0; i < ubicacion.length; i++) {
                    if (ubicacion[i][0].toLowerCase().contains(buscar.toLowerCase())) {
                        System.out.println(ubicacion[i][0] + " | " + ubicacion[i][1]);
                        encontrada2 = true;
                    }
                }
                if(encontrada2 == false){
                    System.out.println("No se encontró información en este parámetro");
                }
                System.out.println("¿Desea buscar otra parada? SI/NO");
                seguir = teclado.nextLine();
                if (seguir.equalsIgnoreCase("no")) {
                    System.out.println("=======================");
                    System.out.println("GRACIAS POR PREFERIRNOS");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Archivo no encontrado.");
        }
    }
    public static String[][] leerArchivo(String ruta, int filas, int columnas) throws FileNotFoundException {
        Scanner fileIn = new Scanner(new File(ruta));
        String[][] datos = new String[filas][columnas];
            for (int i = 0; i < datos.length; i++) {
                if (fileIn.hasNextLine()) {
                    String linea = fileIn.nextLine();
                    String[] partes = linea.split(";", 2);
                    if (partes.length == 2) {
                        datos[i][0] = partes[0];
                        datos[i][1] = partes[1];
                    }
                }
            }
        return datos;
    }
    public static String[][] aumentar(String ruta, int filas, int columnas) throws FileNotFoundException {
        Scanner fileIn = new Scanner(new File(ruta));
        String[][] datos = new String[filas][columnas];
            for (int i = 0; i < datos.length; i++) {
                if (fileIn.hasNextLine()) {
                    String linea = fileIn.nextLine();
                    String[] partes = linea.split(";", 2);
                    if (partes.length == 2) {
                        int numparadas = partes[1].split(",").length;
                        datos[i][0] = partes[0] + "(" + numparadas + ")";
                        datos[i][1] = partes[1];
                        
                        
                    }
                }
            }
        return datos;
    }
    public static void imprimirMatriz(String[][] matriz) {
        System.out.println("HORAS | RECORRIDO");
        System.out.println("============================================");
        for (String[] fila : matriz) {
            System.out.println(fila[0] + " | " + fila[1]);
        }
    }
    public static void imprimirUbi(String[][] ubi) {
        System.out.println("PARADAS                        | UBICACIÓN");
        System.out.println("====================================================================");
        for (String[] fila : ubi) {
            System.out.println(fila[0] + " | " + fila[1]);
        }
    }
    public static void imprimirOpti(String[][] opti) {
        System.out.println("HORAS(NUM PARADAS) | PARADAS RECURRENTES");
        System.out.println("====================================================================");
        for (String[] fila : opti) {
            System.out.println(fila[0] + "      | " + fila[1]);
        }
    }
    public static void escribirArchivoRutas(String datosSalida[][], String datosEntrada[][]) {
        try {
            for (int i = 0; i < datosSalida.length; i++) {
                for (int j = 0; j < datosSalida[0].length; j++) {
                    datosSalida[i][j] = datosEntrada[i][j];
                }
            }
            Formatter flujoSalida = new Formatter("rutasSalida.csv");
            for (String[] fila : datosSalida) {
                flujoSalida.format("%s;%s\n",fila[0], fila[1]);
            }
            flujoSalida.close();
        } catch (Exception e) {
        }
    }
    public static void escribirArchivoUbi(String datosSalida[][], String datosEntrada[][]) {
        try {
            for (int i = 0; i < datosEntrada.length; i++) {
                for (int j = 0; j < datosEntrada[0].length; j++) {
                    datosSalida[i][j] = datosEntrada[i][j];
                }
            }
            Formatter flujoSalida = new Formatter("ubiSalida.csv");
           for (String[] fila : datosSalida) {
                flujoSalida.format("%s;%s\n",fila[0], fila[1]);
            }
            flujoSalida.close();
        } catch (Exception e) {
        }
    }
    public static void escribirArchivoOpti(String datosSalida[][], String datosEntrada[][]) {
        try {
            for (int i = 0; i < datosSalida.length; i++) {
                for (int j = 0; j < datosSalida[0].length; j++) {
                    datosSalida[i][j] = datosEntrada[i][j];
                }
            }
            Formatter flujoSalida = new Formatter("optiSalida.csv");
            for (String[] fila : datosSalida) {
                flujoSalida.format("%s;%s\n",fila[0], fila[1]);
            }
            flujoSalida.close();
        } catch (Exception e) {
        }
    }
}
