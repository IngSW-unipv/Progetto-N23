package it.unipv.ingsw.magstudio.model.exceptions;

public class TelefonoFormatException extends Exception{
    /**
     * Eccezione generata in caso il Numero di Telefono non segue il pattern corretto
     * @param message Il messaggio da mostrare
     * @see Exception
     */
    public TelefonoFormatException(String message) {
        super(message);
    }
}
