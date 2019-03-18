/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mycompany.unicafe.Kassapaate;
import com.mycompany.unicafe.Maksukortti;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {

    Kassapaate kp;

    @Before
    public void setUp() {
        kp = new Kassapaate();
    }

    @Test
    public void alussaOikeaMaaraRahaaJaMyytyjaLounaita() {
        assertEquals(100000, kp.kassassaRahaa());
        assertEquals(0, kp.edullisiaLounaitaMyyty());
        assertEquals(0, kp.maukkaitaLounaitaMyyty());
    }

    @Test
    public void edullisenLounaanKateisOstoToimiiOikeinKunRahaaOn() {
        assertEquals(60, kp.syoEdullisesti(300));
        assertEquals(100240, kp.kassassaRahaa());
        assertEquals(1, kp.edullisiaLounaitaMyyty());
    }

    @Test
    public void edullisenLounaanKateisOstoToimiiOikeinKunRahaEiRiita() {
        assertEquals(220, kp.syoEdullisesti(220));
        assertEquals(100000, kp.kassassaRahaa());
        assertEquals(0, kp.edullisiaLounaitaMyyty());
    }

    @Test
    public void maukkaanLounaanKateisOstoToimiiOikeinKunRahaaOn() {
        assertEquals(100, kp.syoMaukkaasti(500));
        assertEquals(100400, kp.kassassaRahaa());
        assertEquals(1, kp.maukkaitaLounaitaMyyty());
    }

    @Test
    public void maukkaanLounaanKateisOstoToimiiOikeinKunRahaEiRiita() {
        assertEquals(350, kp.syoMaukkaasti(350));
        assertEquals(100000, kp.kassassaRahaa());
        assertEquals(0, kp.maukkaitaLounaitaMyyty());
    }

    @Test
    public void edullisenLounaanKorttiOstoToimiiOikeinKunSaldoRiittaa() {
        Maksukortti mk = new Maksukortti(1000);
        assertTrue(kp.syoEdullisesti(mk));
        assertEquals(760, mk.saldo());
        assertEquals(1, kp.edullisiaLounaitaMyyty());
        assertEquals(100000, kp.kassassaRahaa());
    }

    @Test
    public void edullisenLounaanKorttiOstoToimiiOikeinKunSaldoEiRiita() {
        Maksukortti mk = new Maksukortti(200);
        assertFalse(kp.syoEdullisesti(mk));
        assertEquals(200, mk.saldo());
        assertEquals(0, kp.edullisiaLounaitaMyyty());
        assertEquals(100000, kp.kassassaRahaa());
    }

    @Test
    public void maukkaanLounaanKorttiOstoToimiiOikeinKunSaldoRiittaa() {
        Maksukortti mk = new Maksukortti(1000);
        assertTrue(kp.syoMaukkaasti(mk));
        assertEquals(600, mk.saldo());
        assertEquals(1, kp.maukkaitaLounaitaMyyty());
        assertEquals(100000, kp.kassassaRahaa());
    }

    @Test
    public void maukkaanLounaanKorttiOstoToimiiOikeinKunSaldoEiRiita() {
        Maksukortti mk = new Maksukortti(350);
        assertFalse(kp.syoMaukkaasti(mk));
        assertEquals(350, mk.saldo());
        assertEquals(0, kp.maukkaitaLounaitaMyyty());
        assertEquals(100000, kp.kassassaRahaa());
    }

    @Test
    public void rahanLataaminenKortilleToimiiOikein() {
        Maksukortti mk = new Maksukortti(50);
        kp.lataaRahaaKortille(mk, 5000);
        assertEquals(5050, mk.saldo());
        assertEquals(105000, kp.kassassaRahaa());
    }

    @Test
    public void rahanLataaminenKortilleToimiiOikeinJosSummaOnNegatiivinen() {
        Maksukortti mk = new Maksukortti(50);
        kp.lataaRahaaKortille(mk, -100);
        assertEquals(50, mk.saldo());
        assertEquals(100000, kp.kassassaRahaa());
    }
}
