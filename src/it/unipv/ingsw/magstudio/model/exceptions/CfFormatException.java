package it.unipv.ingsw.magstudio.model.exceptions;

public class CfFormatException extends Exception{
    /**
     * Eccezione generata in caso il Codice Fiscale non segue il pattern corretto
     * @param message Il messaggio da mostrare
     * @see Exception
     */
    public CfFormatException(String message) {
        super(message);
    }
}
