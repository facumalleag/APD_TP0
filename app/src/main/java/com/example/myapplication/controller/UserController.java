package com.example.myapplication.controller;

import android.os.StrictMode;

import com.example.myapplication.model.Usuario;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class UserController {

    private ArrayList<Usuario> coleccion_usuarios= new ArrayList<Usuario>();

    private static UserController instancia;

    private String contenidoMail="Felicidades, se ha registrado en la aplicacion correctamente con su alias: ";
    private String correrohost="aplicacionmorfi@gmail.com";
    private String subject="Registro exitoso!";
    private  String passwordmail="AndroidTPO123";

    private String subjectRecupero="Mail de Recupero de Contraseña";
    private String mailCodigoRecupero= "Su codigo de verificacion es: ";

    private int codigo;

    Session session;


    public static UserController getInstancia() {
        if (instancia==null) {
            instancia=new UserController();
        }
        return instancia;
    }

    //El metodo corroborará si el alias y el mail estan dentro de la base de datos.
    public boolean validarDaatosRegistro(String dato_alias, String dato_email) {

        return true;
    }

    public ArrayList<String> sugerirAlias(String dato_alias, String dato_mail) {
        ArrayList<String> sugerencias= new ArrayList<String>();
        for(int i=0; i<3; i++){
            // Comparo que la sugerencia no exista en la base
            String aux="blank";
            String sugerencia=dato_alias + "_" + dato_mail;
            if (aux!=sugerencia){
                sugerencias.add(sugerencia);
            }
        }
        return sugerencias;
    }

    public void enviarMailConfirmacionRegistro(String dato_alias, String dato_email) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Properties properties=new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        try{
        session = Session.getDefaultInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(correrohost, passwordmail);
                    }
                });
            if (session!=null){
                Message  message=new MimeMessage(session);
                message.setFrom(new InternetAddress((correrohost)));
                message.setSubject(subject);
                message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(dato_email));
                message.setContent(contenidoMail + dato_alias,"text/html; chrset=utf-8");
                Transport.send(message);
            }


        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void enviarMailCodeRecovery(String dato_email) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Properties properties=new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        Random r = new Random();
        int codigo = (r.nextInt(9999-1000 +1) + 1000);

        try{
            session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(correrohost, passwordmail);
                }
            });
            if (session!=null){
                Message  message=new MimeMessage(session);
                message.setFrom(new InternetAddress((correrohost)));
                message.setSubject(subjectRecupero);
                message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(dato_email));
                message.setContent(mailCodigoRecupero + codigo,"text/html; chrset=utf-8");
                Transport.send(message);
            }


        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public int getCodRecupero(String mail){


        return codigo;
    }

    public Usuario getUsuario(String dni) {
        Usuario resultado = null;
        for (Usuario consulta : coleccion_usuarios) {
            if (consulta.getDni().equals(dni)) {
                resultado = consulta;
                break;
            }
        }
        return resultado;
    }

    public boolean comprobarCodigoRecupero(int codigo_input){
        return true;
    }

}
