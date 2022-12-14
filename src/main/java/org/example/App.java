package org.example;
import org.apache.commons.net.ftp.FTPFile;
import org.w3c.dom.xpath.XPathNamespace;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class App 
{
    public static Scanner sc = new Scanner(System.in);
    public static Boolean Iterador = true;
    public static Boolean LogeoMenu = false;

    public static FileOutputStream Fos = null;
    public static FileInputStream Fis = null;

    public static void main( String[] args ) {
        while (Iterador) {
            try {
                Menu();
                System.out.println("Dime una upcion del menu: ");
                int Opcion = sc.nextInt();
                switch (Opcion) {
                    //OPCION - 1
                    case 1 -> Opcion1();

                    //OPCION - 2
                    case 2 -> {
                        if (!LogeoMenu) { break; }
                        if (!ComprobacionConexionFTP()) { break; }
                        Opcion2();
                    }
                    //OPCION - 3
                    case 3 -> {
                        if (!LogeoMenu) { break; }
                        if (!ComprobacionConexionFTP()) { break; }
                        Opcion3();
                    }
                    //OPCION - 4
                    case 4 -> {
                        if (!LogeoMenu) { break; }
                        if (!ComprobacionConexionFTP()) { break; }
                        Opcion4();
                    }
                    //OPCION - 5
                    case 5 -> {
                        if (!LogeoMenu) { break; }
                        if (!ComprobacionConexionFTP()) { break; }
                        Opcion5();
                    }
                    //OPCION - 6
                    case 6 -> {
                        if (!LogeoMenu) { break; }
                        if (!ComprobacionConexionFTP()) { break; }
                        Opcion6();
                    }
                    //OPCION - 7
                    case 7 -> {
                        if (!LogeoMenu) { break; }
                        if (!ComprobacionConexionFTP()) { break; }
                        Opcion7();
                    }
                    //OPCION - 8
                    case 8 -> Opcion8();
                    //OPCION - 9
                    case 9 -> Iterador = false;
                }
            } catch(IOException e){
                e.printStackTrace();
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
        Constants.UserFTP = Usuario;
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

        if(LogeoMenu){
            System.out.println("Inicio de sesion satisfactorio");
        }else{
            System.out.println("Error de inicio de sesion en el servidor");
        }
    }
    //OPCION - 2
    private static void Opcion4(){
        System.out.println("Dime el nombre del directorio: ");
        String nombreDirectorio = sc.next();
        try{
            Constants.FTPconnectionClient.makeDirectory(nombreDirectorio);
        } catch (IOException e){
            e.printStackTrace();
        }
    }



    //OPCION - 3
    private static void Opcion2(){
        Boolean FicheroSubido = false;
        System.out.println("Nombre del fichero que se quiere subir al servidor: ");
        String FicheroSubida = sc.next();
        String[] FicheroSplit = FicheroSubida.split("/");
        try {
            Fis = new FileInputStream(FicheroSubida);
            FicheroSubido = Constants.FTPconnectionClient.storeFile(FicheroSplit[FicheroSplit.length - 1], Fis);
            if (FicheroSubido) {
                System.out.println("Fcihero subido con exito al servidor");
            }else{
                System.out.println("Subida del fichero fallido");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //OPCION - 3
    private static void Opcion3(){
        System.out.println("Nombre del fichero que se quiere descargar: ");
        String FicheroDesccarga = sc.next();
        String[] FicheroSplit = FicheroDesccarga.split("\\.");
        try{
            Fos = new FileOutputStream(System.getProperty("user.dir") + "/" +FicheroSplit[0] + "_LocalFile" + "." + FicheroSplit[1]);
            Constants.FTPconnectionClient.retrieveFile(FicheroDesccarga, Fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //OPCION - 4
    private static void Opcion5(){
        boolean iterator = true;
        String PathFile = "";
        while (iterator){
            System.out.println("Dime la ruta del fichero que quieres eliminar");
            PathFile = sc.next();
            File f = new File(PathFile);
            if (!f.exists()){
                System.out.println("No se ha encontrado el fichero indicado, intentelo de nuevo");
            }else{
                iterator = false;
            }
        }
        boolean DeletedFile = false;
        try{
            DeletedFile = Constants.FTPconnectionClient.deleteFile(PathFile);
        }catch (IOException e){
            e.printStackTrace();
        }
        if(DeletedFile){
            System.out.println("Fichero borrado satisfactoriamente...");
        }else{
            System.out.println("No se ha podido borrar el fichero...");
        }
    }
    //OPCION - 5
    private static void Opcion6(){
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
    //OPCION - 6
    private static void Opcion7(){
        String PathName = "";
        Boolean DirectoryChanged = false;
        try{
            System.out.println("Dime el nombre del directorio al que quiere cambiarse");
            PathName = sc.next();
            DirectoryChanged = Constants.FTPconnectionClient.changeWorkingDirectory(PathName);
            if(DirectoryChanged){
                System.out.println("Directorio Cambiado Satisfactoriamente...");
            }else{
                System.out.println("No se ha podido cambiar de directorio, intentelo de nuevo...");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //OPCION - 7
    private static void Opcion8(){
        if(LogeoMenu){
            try {
                Constants.FTPconnectionClient.disconnect();
                Constants.UserFTP = "";
                Constants.PasswordFTP = "";
            }catch (IOException e){
                e.printStackTrace();
            }
            System.out.println("Deslogeo Realizado con exito...");
        }else{
            System.out.println("No se ha iniciado sesion en ningun servidor FTP");
        }
        LogeoMenu = Constants.FTPconnectionClient.isConnected();
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
//#### EL MENU NO HACER CASO A ESTO PQ SI ####
//###########################################################

    private static void Menu() throws IOException {
        System.out.println("<---------Menu-------->");
        if(LogeoMenu){
            System.out.println("|---- User: " + Constants.UserFTP + " ----|");
            System.out.println("|---- Current Working Directory: " + Constants.FTPconnectionClient.printWorkingDirectory() + " ----|");
        }
        System.out.println("1. Logeo en servidor FTP");
        if (LogeoMenu) {
            System.out.println("2. Subida de fichero/s al servidor FTP");
            System.out.println("3. Descarga de fichero/s al servidor FTP");
            System.out.println("4. Crear Directorio");
            System.out.println("5. Eliminar Directorio/fichero del servidor");
            System.out.println("6. Mostrar el arbol de directorios del servidor FTP");
            System.out.println("7. Cambiar de directorio de trabajo");
            System.out.println("8. Deslogeo del servidor FTP");
        }
        if (LogeoMenu){
            System.out.println("9. Salir");
        }else{
            System.out.println("2. Salir");
        }


        System.out.println("<---------Fin de Menu-------->");
    }

}
