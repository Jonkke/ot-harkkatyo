package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void rahanLataaminenToimiiOikein() {
        kortti.lataaRahaa(25);
        assertEquals(35, kortti.saldo());
    }
    
    @Test
    public void saldoVaheneeJosRahaaOtetaan() {
        kortti.otaRahaa(9);
        assertEquals(1, kortti.saldo());
    }
    
    @Test
    public void saldoEiMuutuJosRahaaEiTarpeeksi() {
        kortti.otaRahaa(11);
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void otaRahaaPalauttaaTrueJosRahaRiittaa() {
        assertTrue(kortti.otaRahaa(10));
    }
    
    @Test
    public void otaRahaaPalauttaaFalseJosRahaEiRiita() {
        assertFalse(kortti.otaRahaa(11));
    }
    
    @Test
    public void stringOnOikeassaMuodoss() {
        kortti.lataaRahaa(3015);
        assertEquals("saldo: 30.25", kortti.toString());
    }
    
}
