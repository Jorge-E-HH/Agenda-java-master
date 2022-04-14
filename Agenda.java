/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Godie007
 */
public class Agenda {

    private ArrayList<Contacto> contactos;
    private ContactoLaboral contLaboral;
    private ContactoPersonal contPersonal;

    public Agenda() {
        contactos = new ArrayList<Contacto>();


    }

    public ArrayList<Contacto> getContactos() {
        return contactos;
    }

    /**
     * Agrega un nuevo contacto personal a la lista de contactos
     *
     * @param id, número de identificación del contacto
     * @param n, nombre del contacto
     * @param tel, número telefónico del contacto
     * @param gen, género del contacto
     * @param f, fecha de cumpleaños del contacto
     * @return, true si el contacto se agrega, false si y existe
     */
    public boolean guardarContactoPersonal(String id, String n, String tel, String gen, Fecha f) throws IOException {
        Contacto c = buscarContacto(id);
        //Si el contacto existe como Contacto Personal, no se podrá agregar
        if (c != null && c instanceof ContactoPersonal) {
            return false;
        }
        contPersonal = new ContactoPersonal(id, n, gen, tel, f);
        contactos.add(contPersonal);

        return true;
    }

    /**
     * lee los datos de Contactos Personales y los agrega a la lista de
     * Contactos
     *
     * @throws IOException
     */
    public void cargarContactoPersonal() throws IOException {
        FileReader entrada = new FileReader("src/archivos/contactosPersonales.txt");
        BufferedReader archivo = new BufferedReader(entrada);
        String linea = archivo.readLine();

        //cargar info de apartamentso persistda  

        while (linea != null) {
            String datos[] = linea.split("/");
            String id = datos[0];
            String nombre = datos[1];
            String genero = datos[2];
            String telefono = datos[3];
            String fecha = datos[4];

            String datos2[] = fecha.split("-");
            int dia = Integer.parseInt(datos2[0]);
            int mes = Integer.parseInt(datos2[1]);
            int anio = Integer.parseInt(datos2[2]);
            Fecha fecha1 = new Fecha(dia, mes, anio);
            contPersonal = new ContactoPersonal(id, nombre, genero, telefono, fecha1);
            contactos.add(contPersonal);



            linea = archivo.readLine();
        }
    }

    /**
     * lee los datos de Contactos Laborales y los agrega a la lista de Contactos
     *
     * @throws IOException
     */
    public void cargarContactoLaboral() throws IOException {
        FileReader entrada = new FileReader("src/archivos/contactosLaborales.txt");
        BufferedReader archivo = new BufferedReader(entrada);
        String linea = archivo.readLine();

        //cargar info de apartamentso persistda  

        while (linea != null) {
            String datos[] = linea.split("/");
            String id = datos[0];
            String nombre = datos[1];
            String genero = datos[2];
            String telefono = datos[3];
            String email = datos[4];


            contLaboral = new ContactoLaboral(id, nombre, genero, telefono, email);
            contactos.add(contLaboral);


            linea = archivo.readLine();
        }
    }

    /**
     * Agrega un nuevo contacto laboral a la lista de contactos
     *
     * @param id, número de identificación del contacto
     * @param n, nombre del contacto
     * @param tel, número telefónico del contacto
     * @param gen, género del contacto
     * @param e, email del contacto
     * @return, true si el contacto se agrega, false si y existe
     */
    public boolean guardarContactoLaboral(String id, String n, String tel, String gen,
            String e) {
        Contacto c = buscarContacto(id);
        //Si el contacto existe como Contacto Laboral, no se podrá agregar
        if (c != null && c instanceof ContactoLaboral) {
            return false;
        }
        contLaboral = new ContactoLaboral(id, n, gen, tel, e);
        contactos.add(contLaboral);

        return true;
    }

    /**
     * Agregar las datos contactoLaboral al archovo de texto
     *
     * @throws IOException
     */
    public void agregarContactoLaboralTxt() throws IOException {
        FileWriter archivo = new FileWriter("src/archivos/contactosLaborales.txt");
        for (Contacto contact : contactos) {
            if (contact instanceof ContactoLaboral) {
                ContactoLaboral contacto = (ContactoLaboral) contact;

                archivo.write(
                        contacto.getId() + "/"
                        + contacto.getNombre() + "/"
                        + contacto.getGenero() + "/"
                        + contacto.getTelefono() + "/"
                        + contacto.getEmail() + "\n");
            }



        }
        archivo.close();
    }

    /**
     * Agregar las datos contactoPesonal al archovo de texto
     *
     * @throws IOException
     */
    public void agregarContactoPersonalTxt() throws IOException {
        FileWriter archivo = new FileWriter("src/archivos/contactosPersonales.txt");
        for (Contacto contact : contactos) {
            if (contact instanceof ContactoPersonal) {
                contPersonal = (ContactoPersonal) contact;

                archivo.write(
                        contPersonal.getId() + "/"
                        + contPersonal.getNombre() + "/"
                        + contPersonal.getGenero() + "/"
                        + contPersonal.getTelefono() + "/"
                        + contPersonal.getFechaCumpleaños().getDia() + "-"
                        + contPersonal.getFechaCumpleaños().getMes() + "-"
                        + contPersonal.getFechaCumpleaños().getAnio() + "\n");
            }
        }
        archivo.close();
    }

    /**
     * Busca un contacto en la lista de contactos
     *
     * @param id, número de identificación del contacto
     * @return, el contacto si se encuentra, null en caso contrario
     */
    public Contacto buscarContacto(String id) {
        for (int i = 0; i < contactos.size(); i++) {
            if (contactos.get(i).getId().equals(id)) {
                return contactos.get(i);
            }
        }
        return null;
    }

    /**
     * guarda un archivo con el informe de contacots personales
     *
     * @param selectedFile
     * @throws IOException
     */
    public void generarArchivoContactosPersonales(File selectedFile) throws IOException {
        FileWriter w = new FileWriter(selectedFile);
        BufferedWriter bw = new BufferedWriter(w);

        PrintWriter wr = new PrintWriter(bw);
        for (Contacto contacto : contactos) {
            if (contacto instanceof ContactoPersonal) {

                contPersonal = (ContactoPersonal) contacto;
                wr.write(
                        "Numbre:" + contPersonal.nombre + " Telefono:" + contPersonal.getTelefono() + "\n"
                        + "******************************************************************* \n");//escribimos en el archivo

            }
        }
        wr.close();
        bw.close();
    }

    /**
     * guarda un archivo con el informe de contacots laborales
     *
     * @param selectedFile
     * @throws IOException
     */
    public void generarArchivoContactosLaborales(File selectedFile) throws IOException {
        FileWriter w = new FileWriter(selectedFile);
        BufferedWriter bw = new BufferedWriter(w);

        PrintWriter wr = new PrintWriter(bw);
        for (Contacto contacto : contactos) {
            contLaboral = (ContactoLaboral) contacto;
            wr.write(
                    "Numbre:" + contLaboral.nombre + " Telefono:" + contLaboral.getTelefono() + "\n"
                    + "******************************************************************* \n");//escribimos en el archivo

        }
        wr.close();
        bw.close();
    }
}
