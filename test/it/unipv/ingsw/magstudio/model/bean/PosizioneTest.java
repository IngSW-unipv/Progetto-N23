package it.unipv.ingsw.magstudio.model.bean;


import java.util.HashSet;
import java.util.Set;

public class PosizioneTest {
    public static void main(String[] args) {
        Set<Posizione> set = new HashSet<>();

        Posizione p1 = new Posizione(1,1,1,1);
        Posizione p2 = new Posizione(8,1,1,1);
        Posizione p3 = new Posizione(1,1,4,1);

        set.add(p1);
        set.add(p2);
        set.add(p3);

        set.forEach(posizione -> {
            System.out.println(posizione.toString());
        });
    }


}
