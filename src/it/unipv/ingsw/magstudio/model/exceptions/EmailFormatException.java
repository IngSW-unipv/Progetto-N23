package it.unipv.ingsw.magstudio.model.exceptions;

public class EmailFormatException extends Exception{
    /**
     * Eccezione generata in caso l'indirizzo email non segue il pattern corretto
     * @param message Il messaggio da mostrare
     * @see Exception
     */
    public EmailFormatException(String message) {
        super(message);
    }
}
