/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scannerTxt;

/*
    SELECT PARA PEDIR X PALABRA: 
        SELECT d.nombre, t.nombre, p.cant 
            FROM Documentos d, Posteo p, Terminos t 
            WHERE d.hashDocumentos=p.hashDocumentos 
                AND t.hashTermino=p.hashTermino
                AND t.nombre='tomi'
            ORDER BY p.cant DESC;
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import logicaHash.*;
import persistencia.*;

/**
 *
 * @author tecio
 */
public class scannerTxt { 
    private static Vocabulario vocabulario = new Vocabulario();
    
    
    private static final String todos = "D:\\Tomi\\Facultad\\4To\\DLC\\Motor de Busqueda\\DocumentosTP1-20200417T134605Z-001\\DocumentosTP1";
    private static final String prueba = "D:\\Tomi\\Facultad\\4To\\DLC\\Motor de Busqueda\\DocumentosPrueba";
    private static final String prueba2 = "D:\\Tomi\\Facultad\\4To\\DLC\\Motor de Busqueda\\DocumentosPrueba2";

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("doc_PU");
    
    private static DocumentosJpaController docJpa;
    private static TerminosJpaController terJpa;
    private static PosteoJpaController postJpa;
    
    
    public static void main(String[] args) {
        try {
            IndexarCarpeta(prueba2);
        } catch (Exception ex) {
            Logger.getLogger(scannerTxt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static void IndexarCarpeta(String fullPath) throws FileNotFoundException, Exception{
        File c = new File(fullPath);
        File[] ficheros = c.listFiles();
        int cont = 0;
        for(File arch : ficheros) {
            cont ++;
            System.out.println(cont);
            Documento d = new Documento(arch.getName());
            Documentos_EC doc = new Documentos_EC(d.hashCode(), d.getNombre());
            docJpa = new DocumentosJpaController(emf);            
            // Se fija si existe
            if(docJpa.findDocumentos(doc.getHashDocumentos()) == null){
                try {
                    docJpa.create(doc);
                } catch (Exception e) {
                    System.out.println("No carga el documento: " + e.getMessage());
                }
                scannerArchivo(arch);
            } else{ System.out.println("Ya existe el documento");}
        }
        cargarDatosABDD();
    }

    private static void scannerArchivo(File arch) throws FileNotFoundException {
        Scanner sc = new Scanner(arch);
        try{
            while(sc.hasNext()){
                String pal = sc.next();
                pal = pal.toLowerCase();
                pal = pal.replaceAll("[^a-z]","");
                meterEnHash(pal, arch);
            }
        }catch(Exception e){
            System.out.println("Error al insertar Documento: " + arch.getName()+ "---Error: " + e.getMessage());
        }
    }

    private static void meterEnHash(String pal, File arch) {
        Documento doc = new Documento(arch.getName());
        Termino termino = new Termino(pal);
        
        vocabulario.put(termino, doc);
    }

    private static void cargarDatosABDD() {
        Enumeration e = vocabulario.getVocabulario().elements();
        
        terJpa = new TerminosJpaController(emf);
        postJpa = new PosteoJpaController(emf);
        
        while(e.hasMoreElements()){
            Termino pal = (Termino) e.nextElement();
            Terminos_EC ter = new Terminos_EC(pal.hashCode(), pal.getNom());                

            try {
                terJpa.create(ter);
            } catch (Exception ex) {
                System.out.println("Error al insertar el Hash del termino: " + pal.hashCode() + "---Error: " + ex.getMessage());
            }
            
            Enumeration f = pal.getPosteo().elements();
            
            while(f.hasMoreElements()){
                Documento d = (Documento) f.nextElement();
                Documentos_EC doc = new Documentos_EC(d.hashCode(), d.getNombre());
                
                Posteo_EC post = new Posteo_EC(pal.hashCode(), doc.hashCode(), d.getCant());
                System.out.println();
                try {
                    postJpa.create(post);
                } catch (Exception ex) {
                    System.out.println("Error al insertar Posteo: " + ex.getMessage());
                }
            }
            
            
            
        }
    }
}
