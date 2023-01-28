package it.unipv.ingsw.magstudio.model.bean;

import it.unipv.ingsw.magstudio.model.exceptions.CfFormatException;
import it.unipv.ingsw.magstudio.model.exceptions.TelefonoFormatException;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestIndirizzoPersona {
    public static void main(String[] args) {

        Indirizzo i=new Indirizzo(TipoStrada.VIA, "dei pini", "14/A", 27000, "vidigulfo", "pavia",Regione.LOMBARDIA);
        System.out.println(i);

        Persona p= null;
        try {
            p = new Persona("admin","Mario","Rossi","ABCDEF12G34H567I",new Date(2000,10,10), i, new Contatto(Long.parseLong("1112345678")));
        } catch (CfFormatException e) {
            System.out.println(e.getMessage());
        } catch (TelefonoFormatException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(p);

        regexEmail();
        regexTelefono();
        regexCF();
    }
    public static void regexEmail(){
        Pattern pattern = Pattern.compile("^(\\S+)@(\\S[a-zA-Z0-9-]+)\\.(\\S[a-zA-Z0-9]{1,5})$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher("mario-rossi@gmail.com");
        boolean matchFound = matcher.find();
        System.out.println(matchFound);
    }
    public static void regexTelefono(){
        Pattern pattern = Pattern.compile("^[0-9]{6,12}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher("3333333333");
        boolean matchFound = matcher.find();
        System.out.println(matchFound);
    }
    public static void regexCF(){
        Pattern pattern = Pattern.compile("^([a-zA-Z]{3})([a-zA-Z]{3})([0-9]{2})([a-zA-Z]{1})([0-9]{2})([a-zA-Z]{1}[0-9]{3})([a-zA-Z]{1})$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher("ABCDEF12G34H567I");
        boolean matchFound = matcher.find();
        System.out.println(matchFound);
    }
}
