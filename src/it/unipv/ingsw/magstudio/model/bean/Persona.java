package it.unipv.ingsw.magstudio.model.bean;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Persona {
    private String nome;
    private String cognome;
    private String cf;
    private Date dataNascita;
    private Indirizzo indirizzo;
    private Contatto contatto;

    public Persona(String nome, String cognome, String cf, Date dataNascita, Indirizzo indirizzo, Contatto contatto) {
        this.nome = nome;
        this.cognome = cognome;
        setCf(cf);
        this.dataNascita = dataNascita;
        this.indirizzo = indirizzo;
        this.contatto = contatto;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCf() {
        return cf;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public Contatto getContatto() {
        return contatto;
    }

    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

    private void setCf(String cf) {
        Pattern pattern = Pattern.compile("^([a-zA-Z]{3})([a-zA-Z]{3})([0-9]{2})([a-zA-Z]{1})([0-9]{2})([a-zA-Z]{1}[0-9]{3})([a-zA-Z]{1})$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(cf);
        if (matcher.find()){
            this.cf=cf;
        }else{
            //TODO:fare eccezione per errore
        }
    }

    public void modificaEmail(Contatto email){
        this.contatto.setEmail(email.getEmail());
    }

    public void modificaTelefono(Contatto telefono){
        this.contatto.setTelefono(telefono.getTelefono());
    }

    @Override
    public String toString() {
        //TODO: aggiustare
        return "Persona{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", cf='" + cf + '\'' +
                ", dataNascita=" + dataNascita +
                ", indirizzo=" + indirizzo +
                ", contatto=" + contatto +
                '}';
    }
}
