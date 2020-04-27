/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scannerTxt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;
import logicaHash.*;

/**
 *
 * @author tecio
 */
public class scannerTxt {
    private static Vocabulario vocabulario = new Vocabulario();
    
    public static void main(String[] args) throws FileNotFoundException{
    
        String carpeta = "D:\\Tomi\\Facultad\\4To\\DLC\\Motor de Busqueda\\DocumentosPrueba";
        File c = new File(carpeta);
        File[] ficheros = c.listFiles();
        int cont = 0;
        for(File arch : ficheros) {
            if (arch.isFile()) {
                cont ++;
                System.out.println(cont);
                scannerArchivo(arch);
            }else {
            System.out.println("No entra");
            }
        }
        System.out.println(vocabulario.toString());
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
                pal = pal.replace("\"", "");
                pal = pal.replace(")", "");
                pal = pal.replace("]", "");
                pal = pal.replace("*", "");
                pal = pal.toLowerCase();
  
                meterEnHash(pal, arch);            
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void meterEnHash(String pal, File arch) {
        Documento doc = new Documento(arch.getName());
        Termino termino = new Termino(pal);
        
        vocabulario.put(termino, doc);
    }
}
