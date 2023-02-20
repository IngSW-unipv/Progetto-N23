package it.unipv.ingsw.magstudio.model.bean;

import it.unipv.ingsw.magstudio.model.exceptions.CfFormatException;
import it.unipv.ingsw.magstudio.model.exceptions.TelefonoFormatException;
import org.junit.Test;

import java.util.Date;

public class PersonaTest {
    @Test(expected = CfFormatException.class)
    public void formatoCfErrato() throws CfFormatException, TelefonoFormatException {
        Persona p = new Persona(
                "admin",
                "Mario",
                "Rossi",
                "ABCDEF12G34gfgfH567I",
                new Date(2000,10,10),
                new Indirizzo(TipoStrada.VIA, "dei pini", "14/A", 27000, "vidigulfo", "pavia",Regione.LOMBARDIA),
                new Contatto(1112345678L)
        );
    }

    @Test(expected = TelefonoFormatException.class)
    public void formatoTelefonoErrato() throws CfFormatException, TelefonoFormatException {
        Persona p = new Persona(
                "admin",
                "Mario",
                "Rossi",
                "ABCDEF12G34H567I",
                new Date(2000,10,10),
                new Indirizzo(TipoStrada.VIA, "dei pini", "14/A", 27000, "vidigulfo", "pavia",Regione.LOMBARDIA),
                new Contatto(111L)
        );
    }
}
