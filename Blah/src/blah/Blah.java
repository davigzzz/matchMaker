package blah;

import java.io.*;
import java.util.*;

public class Blah {

    public static void escribir(String[] nuArch, String nombreArchivo) {
        File f;
        f = new File(nombreArchivo);
        
        try {
            FileWriter w = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            for(int i=0; i<nuArch.length;i++){               
                wr.write(nuArch[i]+"\n");               
            
            }
            wr.close();
            bw.close();
        } catch (IOException e) {
        }        
    }
    
    public static String[] creaListaPendientes(String pendientes) throws IOException {//Crea un arreglo a partir del archivo que contiene los número de ticket pendientes
        BufferedReader in = new BufferedReader(new FileReader(pendientes));//PVF
        String cad;
        List<String> list = new ArrayList<String>();
        while ((cad = in.readLine()) != null) {
            list.add(cad);
        }
        String[] lista = list.toArray(new String[0]);
        return lista;
    }

    public static String[] recorreCarpeta(String ruta) {
        String sDirectorio = ruta; //RUTA DE LA CARPETA "PVF" DEL MES CORRESPONDIENTE
        File r = new File(sDirectorio);
        File[] ficheros = r.listFiles();
        List<String> list = new ArrayList<String>();
        for (int x = 0; x <= ficheros.length; x++) {
            list.add(ficheros[x].getPath());
        }
        String[] rutas = list.toArray(new String[0]);
        return rutas;
    }

    public static String[] subRecorrido(String ruta) {
        String[] prueba = Blah.recorreCarpeta(ruta);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i <= prueba.length; i++) {
            String sDirectorio = prueba[i]; //RUTA DEL DIRECTORIO DE LOS PVAS
            File r = new File(sDirectorio);
            File[] ficheros = r.listFiles();

            for (int x = 0; x <= ficheros.length; x++) {
                list.add(ficheros[x].getPath());
            }
        }
        String[] routes = list.toArray(new String[0]);
        return routes;
    }

    public static String[] preparaPVF(String ruta) throws IOException {
        List<String> list = new ArrayList<String>();
        File f = new File(ruta);
        if (f.length() > 0) {
            FileReader rr = new FileReader(f);
            BufferedReader dd = new BufferedReader(rr);
            String cad;
            while ((cad = dd.readLine()) != null) {
                if(cad.isEmpty()){
                    continue;
                }
                list.add(cad);
            }
        }
        String[] pvf = list.toArray(new String[0]);
        return pvf;
    }

    public static String[] creaPVF(String[] ruta, String[] lista) throws IOException{
        List<String> list = new ArrayList<String>();
        for (int i = 0; i <= lista.length; i++) { //recorremos el arreglo con la lista de número de cliente            
            int x = 0;
            System.out.println(i+"/"+lista.length);
            while (x <= ruta.length) {//recorremos el pvf                
                try {                                    
                    String[] pvf = Blah.preparaPVF(ruta[x]);//creamos un arreglo del archivo leido
                    for (int y = 0; y < pvf.length; y++) {//recorremos el arreglo del archivo
                        String[] tmp;
                        tmp = pvf[y].split("\t");
                        if (lista[i].equals(tmp[9])) { //se hace el match
                            do {                                
                                //System.out.println(pvf[y]);
                                list.add(pvf[y]);
                                y++;
                                tmp = pvf[y].split("\t");                                
                            } while ((tmp[0].equals("I")));//Mientras el primer caracter de la línea sea I, imprimirá la línea. Por ser un Do While, siempre imprimira el primer header
                        }                       
                    }                   
                } catch (Exception e) {
                } finally {
                }
                x++;
            }
        }
        String[] pvf = list.toArray(new String[0]);        
        return pvf;
    }
    
    public static String[] NumTicket(String ruta) throws IOException{ //método para hacer la lista de número de cliente del pvf generado        
        BufferedReader in = new BufferedReader(new FileReader(ruta));
        String cad;
        String[] tmp;
        List<String> list = new ArrayList<String>();
        while ((cad = in.readLine()) != null) {
            if (cad.isEmpty()) {
                continue;
            }
            tmp = cad.split("\t");
            if (tmp[9].length()==9) {
                list.add(tmp[9]);
            }
            
        }
        String[] stringArr = list.toArray(new String[0]);
        return stringArr;
    }
    
    public static String[] CreaListaPVF(String ruta) throws IOException { //método para hacer la lista de número de cliente del pvf generado
        BufferedReader in = new BufferedReader(new FileReader(ruta));
        String cad;
        String[] tmp;
        List<String> list = new ArrayList<String>();
        while ((cad = in.readLine()) != null) {
            if (cad.isEmpty()) {
                continue;
            }
            tmp = cad.split("\t");
            if (tmp[6].isEmpty()) {
                continue;
            }
            list.add(tmp[6]);
        }
        String[] stringArr = list.toArray(new String[0]);
        return stringArr;
    }
    
    public static String[] CreaPVA(String[] listaNum, String[] rutaPVAs, String[] numtckt){
        List<String> list = new ArrayList<String>();
        for (int i = 0; i <= listaNum.length; i++) { //recorremos el arreglo con la lista de número de cliente
            System.out.println(i+"/"+listaNum.length);
            int x = 0;
            while (x <= rutaPVAs.length)/*for (x=0; x < rutas.length; x++)*/ { //recorremos los archivos                                         
                try {
                    File f = new File(rutaPVAs[x]);
                    FileReader rr = new FileReader(f);
                    BufferedReader dd = new BufferedReader(rr);
                    String lineaPVA; //VARIABLE PARA LEER CADA LÍNEA DEL ARCHIVO
                    String[] tmp; //VARIABLE DONDE GUARDAREMOS LOS NUM DE CLIENTES DE LOS ARCHIVOS PVAS
                    //ABRIMOS UN LOOP DENTRO DEL ARCHIVO EN EL QUE ESTAMOS, HAREMOS UN NÚMERO DE CLIENTE DE NUESTRA LISTA A LA VEZ                        
                    while ((lineaPVA = dd.readLine()) != null) { //HACEMOS LA LECTURA DEL ARCHIVO LINEA POR LINEA                                                                                                                 
                        tmp = lineaPVA.split("\t");//PARTIMOS NUESTRO PVA POR TABULACIONES
                        if (listaNum[i].equals(tmp[18])) {//SI EL NÚMERO DE CLIENTE i DE NUESTRA LISTA ES IGUAL AL NÚMERO DE CLIENTE DE LA LÍNEA DEL PVA QUE SE LEYÓ
                            if (tmp[0].isEmpty()) { //SI NO SE ENCUENTRA NÚMERO DE OFICINA, LO IMPRIMEROS A PARTIR DEL NÚMERO DE CLIENTE
                                //System.out.println(numtckt[i].substring(0, 3) + lineaPVA);//IMPRESION DE EL NUMERO DE OFICINA FALTANTE MAS LA LINEA DEL PVA (TODO EN CONSOLA)
                                list.add(numtckt[i].substring(0, 3) + lineaPVA);
                            } else {//SI SE ENCUENTRA NÚMERO DE OFICINA, SE IMPRIME LA LINEA DE MANERA NORMAL
                                //System.out.println(lineaPVA);//IMPRIMIREMOS EN CONSOLA LA LÍNEA DEL PVA EN LA QUE ESTÁ                                
                                list.add(lineaPVA);
                                //i++;//SI SE HIZO MATCH PASAMOS AL SIGUIENTE CLIENTE
                                x = 0;
                            }
                            break;
                        }
                        }
                dd.close();//se pueden eliminar estas dos líneas si se ejecuta en Windows
                rr.close();//la mac tiene problemas con el manejo de memoria (no cierra los buffers) por lo que quedan abiertos los archivos
                } catch (Exception e) {
                } finally {                                                         
                }
                x++;
            }
        }
        String[] lista = list.toArray(new String[0]);
    return lista;
    }
    
    public static void main(String[] args) throws IOException {                
        Scanner in = new Scanner(System.in);
        System.out.println("Ingresa ruta de la lista de pendientes: ");
        String pendientes = in.next();       
        System.out.println("Ingresa la ruta de PVFs: ");
        String rutaPVF = in.next();
        System.out.println("Ingresa ruta del PVA: ");
        String rutaPVA = in.next();
        System.out.println("Ingresa la ruta y nombre del nuevo archivo PVF: ");
        String archPVF = in.next();
        System.out.println("Ingresa la ruta y nombre del nuevo archivo PVA: ");
        String archPVA = in.next();
        
        //GENERAMOS PVF
        String[] lista = Blah.creaListaPendientes(pendientes);
        String[] ruta = Blah.subRecorrido(rutaPVF);        
        String[] pvf = Blah.creaPVF(ruta, lista);
        Blah.escribir(pvf, archPVF);
        System.out.println("PVF creado");
        
        //GENERAMOS PVA
        String[] numClie = Blah.CreaListaPVF(archPVF);
        String[] rutaPVAs = Blah.subRecorrido(rutaPVA);
        String[] tckt = Blah.NumTicket(archPVF);
        String[] pva = Blah.CreaPVA(numClie, rutaPVAs, tckt);
        Blah.escribir(pva, archPVA);

    }
}
