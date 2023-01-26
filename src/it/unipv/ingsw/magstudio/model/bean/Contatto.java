package it.unipv.ingsw.magstudio.model.bean;

import it.unipv.ingsw.magstudio.model.exceptions.EmailFormatException;
import it.unipv.ingsw.magstudio.model.exceptions.TelefonoFormatException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contatto {
    private String email;
    private long telefono;

    public Contatto(String email) throws EmailFormatException {
        setEmail(email);
        this.telefono=0;
    }

    public Contatto(long telefono) throws TelefonoFormatException {
        setTelefono(telefono);
        this.email=null;
    }

    public Contatto(String email, long telefono) throws EmailFormatException, TelefonoFormatException {
        setEmail(email);
        setTelefono(telefono);
    }

    public String getEmail() {
        return email;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setEmail(String email) throws EmailFormatException {
        Pattern pattern = Pattern.compile("^(\\S+)@(\\S[a-zA-Z0-9-]+)\\.(\\S[a-zA-Z0-9]{1,5})$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.find()){
            this.email=email;
        }else{
            throw new EmailFormatException("Formato Email errato");
        }
    }

    public void setTelefono(long telefono) throws TelefonoFormatException {
        Pattern pattern = Pattern.compile("^[0-9]{6,12}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(Long.toString(telefono));
        if (matcher.find()){
            this.telefono=telefono;
        }else{
            throw new TelefonoFormatException("Formato numero di Telefono errato");
        }
    }

    @Override
    public String toString() {
        if (email!=null&&telefono!=0) {
            return "email:"+email+"\ntelefono:"+telefono;
        } else if(email==null) {
            return "email non presente"+telefono;
        } else{
            return "telefono non presente"+email;
        }
    }

}
