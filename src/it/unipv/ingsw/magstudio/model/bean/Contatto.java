package it.unipv.ingsw.magstudio.model.bean;

import it.unipv.ingsw.magstudio.model.exceptions.EmailFormatException;
import it.unipv.ingsw.magstudio.model.exceptions.TelefonoFormatException;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe che modella un contatto di una Persona, formato da una Email e un Numero di Telefono
 */
public class Contatto {
    private String email;
    private long telefono;

    /**
     * Crea un contatto formato da solo l'email
     * @param email Email di contatto
     * @throws EmailFormatException Eccezione lanciata in caso l'email inserita non segue il pattern di un indirizzo email
     */
    public Contatto(String email) throws EmailFormatException {
        setEmail(email);
        this.telefono=0;
    }

    /**
     * Crea un contatto formato da solo il numero di telefono
     * @param telefono Numero di telefono del contatto
     * @throws TelefonoFormatException Eccezione lanciata in caso il numero di telefono non segue il pattern di un numero di telefono
     */
    public Contatto(long telefono) throws TelefonoFormatException {
        setTelefono(telefono);
        this.email=null;
    }

    /**
     * Crea un contatto formato da un indirizzo email e un numero di telefono
     * @param email Email di contatto
     * @param telefono Numero di telefono del contatto
     * @throws EmailFormatException Eccezione lanciata in caso l'email inserita non segue il pattern di un indirizzo email
     * @throws TelefonoFormatException Eccezione lanciata in caso il numero di telefono non segue il pattern di un numero di telefono
     */
    public Contatto(String email, long telefono) throws EmailFormatException, TelefonoFormatException {
        setEmail(email);
        setTelefono(telefono);
    }

    /**
     * Restituisce l'indirizzo email del contatto. Se non presente restituisce null
     * @return L'indirizzo email se presente, altrimenti null
     */
    public Optional<String> getEmail() {
        if(email == null)
            return Optional.empty();
        return Optional.of(email);
    }

    /**
     * Restituisce il Numero di Telefono. Se non presente restituisce 0
     * @return Il numero di telfono se presente, altrimenti 0
     */
    public Optional<Long> getTelefono() {
        if(telefono == 0)
            return Optional.empty();
        return Optional.of(telefono);
    }

    /**
     * Sostituisce l'email attuale con quella passata solo se valida
     * @param email La nuova email
     * @throws EmailFormatException Eccezione lanciata se la nuova email non segue il pattern di un indirizzo email
     */
    public void setEmail(String email) throws EmailFormatException {
        Pattern pattern = Pattern.compile("^(\\S+)@(\\S[a-zA-Z0-9-]+)\\.(\\S[a-zA-Z0-9]{1,5})$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.find()){
            this.email=email;
        }else{
            throw new EmailFormatException("Formato Email errato");
        }
    }

    /**
     * Sostituisce il numero di telefono attuale con quello passato solo se valido
     * @param telefono Il nuovo numero di telefono
     * @throws TelefonoFormatException Eccezione lanciata se il nuovo numero di telefono non segue il pattern di un numero di telefono
     */
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
