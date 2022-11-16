package org.example;

import com.sun.org.apache.bcel.internal.Const;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static Scanner sc = new Scanner(System.in);

    static void main( String[] args )
    {
        Menu();
        System.out.println("Dime una upcion del menu: ");
        int Opcion = sc.nextInt();

        switch (Opcion){
            case 1:
                Opcion1();
                break;
            case 2:
                if (!Constants.FTPconnectionClient.isConnected()){
                    System.out.println("No se ha definido un servidor FTP al que conectarse");
                    break;
                }
                break;
            case 3:
                if (Constants.FTPconnectionClient.isConnected()){
                    System.out.println("No se ha definido un servidor FTP al que conectarse");
                    break;
                }
                break;
            case 4:
                break;
        }
    }

    private static void Opcion1(){
        System.out.println("Escriba el nombre/IP del host: ");
        Constants.IP_FTP = sc.next();
        System.out.println("Escriba el Usuario de Inicio de sesion: ");
        Constants.UserFTP = sc.next();
        System.out.println("Escriba la password de Inicio de sesion: ");
        Constants.PasswordFTP = sc.next();
        System.out.println("Escriba el puerto de la conexion: ");
        Constants.Puerto = sc.nextInt();
        try{
            Constants.FTPconnectionClient.connect(Constants.IP_FTP, Constants.Puerto);
            Constants.FTPconnectionClient.user(Constants.UserFTP);
            Constants.FTPconnectionClient.pass(Constants.PasswordFTP);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void Menu(){
        System.out.println("<---------Menu-------->");
        System.out.println("1. Logeo en servidor FTP");
        System.out.println("2. Subida de Archivo/s al servidor FTP");
        System.out.println("3. Descarga de Archivo/s al servidor FTP");
        System.out.println("4. Mostrar el arbol de directorios del servidor FTP");
        System.out.println("5. Salir");
        System.out.println("<---------Fin de Menu-------->");
    }

}
