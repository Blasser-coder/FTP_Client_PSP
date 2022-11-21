package org.example;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static Scanner sc = new Scanner(System.in);
    public static Boolean Iterador = true;
    public static Boolean LogeoMenu = false;

    public static FileOutputStream Fos = null;
    public static FileInputStream Fis = null;

    public static void main( String[] args ) {
        while (Iterador) {
            Menu();
            System.out.println("Dime una upcion del menu: ");
            int Opcion = sc.nextInt();

            switch (Opcion){
                //OPCION - 1
                case 1:
                    Opcion1();
                    break;
                //OPCION - 2
                case 2:
                    if (!LogeoMenu){break;}
                    if (ComprobacionConexionFTP()){break;}
                    Opcion2();
                    break;
                //OPCION - 3
                case 3:
                    if (!LogeoMenu){break;}
                    if (ComprobacionConexionFTP()){break;}
                    Opcion3();
                    break;
                //OPCION - 4
                case 4:
                    if (!LogeoMenu){break;}
                    if (ComprobacionConexionFTP()){break;}
                    Opcion4();
                    break;
                //OPCION - 5
                case 5:
                    Iterador = false;
                    break;
            }
        }
    }
//"ftp.dlptest.com", 21, "dlpuser", "rNrKYTX9g7z3RgJRmxWuGHbeu"

//##############################################################################################################
//#### PROGRAMANDO LAS OPCIONES DEL MENU (PUEDE ESPANDIRSE SI EL JAVIER NO ESTA CONTENTO CON LO QUE LE DOY) ####
//##############################################################################################################

    //OPCION - 1
    private static void Opcion1(){
        System.out.println("Escriba el nombre/IP del host: ");
        Constants.IP_FTP = sc.next();
        System.out.println("Escriba el Usuario de Inicio de sesion: ");
        String Usuario = sc.next();
        System.out.println("Escriba la password de Inicio de sesion: ");
        String Pass = sc.next();
        System.out.println("Escriba el puerto de la conexion: ");
        Constants.Puerto = sc.nextInt();
        try{
            Constants.FTPconnectionClient.connect(Constants.IP_FTP, Constants.Puerto);
            LogeoMenu = Constants.FTPconnectionClient.login(Usuario, Pass);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    //OPCION - 2
    public static void Opcion2(){
        System.out.println("Nombre del fichero que se quiere subir al servidor: ");
        String FicheroSubida = sc.next();
        String[] FicheroSplit = FicheroSubida.split(".");
        try {
            Fis = new FileInputStream("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //OPCION - 3
    public static void Opcion3(){
        System.out.println("Nombre del fichero que se quiere descargar: ");
        String FicheroDesccarga = sc.next();
        String[] FicheroSplit = FicheroDesccarga.split(".");
        try{
            Fos = new FileOutputStream(FicheroSplit[0] + "." + "_LocalFile" + "." + FicheroSplit[1]);
            Constants.FTPconnectionClient.retrieveFile(FicheroDesccarga, Fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //OPCION - 4
    private static void Opcion4(){
        try{
            FTPFile[] Files = Constants.FTPconnectionClient.listFiles();
            String WorkingDirectory = Constants.FTPconnectionClient.printWorkingDirectory();
            System.out.println("<---------- Current Working Directory: '"+ WorkingDirectory + "' --------->");
            for (FTPFile f : Files) {
                System.out.println( f.toFormattedString());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

//######################################
//### COMPROBACIONES DE SERVIDOR ETC ###
//######################################
    public static boolean ComprobacionConexionFTP(){
        Boolean b = Constants.FTPconnectionClient.isConnected();
        if (b) {
            return true;
        }else{
            return false;
        }
    }

//###########################################################
//#### EL PUTO MENU DE MIERDA NO HACER CASO A ESTO PQ SI ####
//###########################################################

    private static void Menu(){
        System.out.println("<---------Menu-------->");
        System.out.println("1. Logeo en servidor FTP");
        if (LogeoMenu) {
            System.out.println("2. Subida de Archivo/s al servidor FTP");
            System.out.println("3. Descarga de Archivo/s al servidor FTP");
            System.out.println("4. Mostrar el arbol de directorios del servidor FTP");
        }
        System.out.println("5. Salir");
        System.out.println("<---------Fin de Menu-------->");
    }

}
