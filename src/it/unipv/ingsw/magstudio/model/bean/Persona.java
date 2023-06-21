package it.unipv.ingsw.magstudio.model.bean;

import jakarta.persistence.*;
import it.unipv.ingsw.magstudio.model.exceptions.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Entity
@Table(name = "PERSONA")
public class Persona {
    @Id
    @Column(name = "NOME_UTENTE")
    private String nomeUtente;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "COGNOME")
    private String cognome;
    @Column(name = "CF")
    private String cf;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_NASCITA")
    private Date dataNascita;
    @Embedded
    private Indirizzo indirizzo;
    @Embedded
    private Contatto contatto;

    public Persona(){}

    /**
     * Crea una Persona
     * @param nomeUtente Il nome utente associato alla persona
     * @param nome Il nome della Persona
     * @param cognome Il cognome della Persona
     * @param cf Il Codice Fiscale
     * @param dataNascita La Data di Nascita secondo il formato "yyyy-MM-dd"
     * @param indirizzo L'indirizzo di residenza
     * @param contatto Il Contatto con almeno o il numero di telefono o l'email
     * @throws CfFormatException Eccezione lanciata in caso il Codice Fisiscale inserito non segua lo standard del Codice Fiscale italiano
     * @see Indirizzo
     * @see Contatto
     * @see Date
     */
    public Persona(String nomeUtente, String nome, String cognome, String cf, Date dataNascita, Indirizzo indirizzo, Contatto contatto) throws CfFormatException {
        // TODO: controllare valori nulli
        this.nomeUtente = nomeUtente;
        this.nome = nome;
        this.cognome = cognome;
        setCf(cf);
        this.dataNascita = dataNascita;
        this.indirizzo = indirizzo;
        this.contatto = contatto;
    }

    /**
     * Costruttore utilizzato per richiedere DAO
     * @param nomeUtente Il nome utente
     */
    public Persona(String nomeUtente){
        // TODO: controllare valori nulli
        this.nomeUtente = nomeUtente;
    }

    /**
     * Restituisce il nome utente della Persona
     * @return Il nome utente
     */
    public String getNomeUtente() {
        return nomeUtente;
    }

    /**
     * Restituisce il nome della Persona
     * @return Il nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisce il cognome della Persona
     * @return Il cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Restituisce il Codice Fiscale della Persona
     * @return Il Codice Fiscale
     */
    public String getCf() {
        return cf;
    }

    /**
     * La Data di Nascita della Persona.
     * Segue il pattern dd/MM/yyyy
     * @return La Data di Nascita
     */
    public String getDataNascita() {
        return new SimpleDateFormat("dd-MM-yyyy").format(dataNascita);
    }

    /**
     * Restituisce l'Indirizzo della Persona
     * @return L'Indirizzo
     * @see Indirizzo
     */
    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    /**
     * Restituisce il Contatto della Persona
     * @return Il Contatto
     * @see Contatto
     */
    public Contatto getContatto() {
        return contatto;
    }

    /**
     * Sostituisce l'Indirizzo attuale con quello passato tramite parametro
     * @param indirizzo Il nuovo Indirizzo
     * @see Indirizzo
     */
    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

    /**
     * Imposta il Codice Fiscale solo se rispetta lo standard del Codice Fiscale italiano
     * @param cf Il Codice Fiscale
     * @throws CfFormatException Eccezione lanciata in caso il codice fiscale fornito non segua lo standard del Codice Fiscale italiano
     */
    private void setCf(String cf) throws CfFormatException {
        Pattern pattern = Pattern.compile("^([a-zA-Z]{3})([a-zA-Z]{3})([0-9]{2})([a-zA-Z]{1})([0-9]{2})([a-zA-Z]{1}[0-9]{3})([a-zA-Z]{1})$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(cf);
        if (matcher.find()){
            this.cf=cf;
        }else{
            throw new CfFormatException("Formato Codice Fiscale errato");

        }
    }

    /**
     * Modifica l'indirizzo email che viene passato attraverso il parametro
     * @param email L'oggetto Contatto che contiene il nuovo indirizzo email
     * @throws EmailFormatException Eccezione lanciata in caso l'email non segue il pattern di un indirizzo email
     */
    public void modificaEmail(Contatto email) throws EmailFormatException {
        this.contatto.setEmail(email.getEmail().get());
    }

    /**
     * Modifica il numero di telefono che viene passato attraverso il parametro
     * @param telefono L'oggetto Contatto che contiene il nuovo numero di telefono
     * @throws TelefonoFormatException Eccezione lanciata in caso il numero di telefono non segue il pattern di un numero di telefono
     */
    public void modificaTelefono(Contatto telefono) throws TelefonoFormatException {
        this.contatto.setTelefono(telefono.getTelefono().get());
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nome utente='" + getNomeUtente() +'\'' +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", cf='" + cf + '\'' +
                ", dataNascita=" + getDataNascita() +
                ", indirizzo=" + indirizzo +
                ", contatto=" + contatto +
                '}';
    }
}
