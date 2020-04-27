/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scannerTxt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author tecio
 */
public class scannerTxt {
    public static void main(String[] args) throws FileNotFoundException{
    
        String carpeta = "D:\\Tomi\\Facultad\\4To\\DLC\\Motor de Busqueda\\DocumentosPrueba";
        File c = new File(carpeta);
        File[] ficheros = c.listFiles();

        for(File arch : ficheros) {
            System.out.println("FOR");
            System.out.println(arch.getPath());
            if (arch.isFile()) {
                scannerArchivo(arch);
            }else {
            System.out.println("No entra");
            }
        }
    }

    private static void scannerArchivo(File arch) throws FileNotFoundException {
        Scanner sc = new Scanner(arch);
        try{
            while(sc.hasNext()){
                String pal = sc.next();
                
                pal = pal.replace(".", "");
                pal = pal.replace(",", "");
                pal = pal.replace(";", "");
                pal = pal.replace(":", "");
                pal = pal.replace("?", "");
                pal = pal.replace("!", "");
                pal = pal.toLowerCase();
                
                int hash = pal.hashCode();
                
                System.out.println(pal);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        
        
    }
}
