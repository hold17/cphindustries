package dk.blackdarkness.g17.cphindustries.dto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by awo on 30/11/2017.
 */
public class WeaponTest {
    private Weapon w;

    @Before
    public void setUp() throws Exception {
        w = new Weapon(0, null);
    }

    @Test
    public void setIp_ValidIP() throws Exception {
        final String ip = "192.168.0.1";

        w.setIp(ip);
        assertEquals(w.getIp(), ip);
    }

    @Test(expected = NullPointerException.class)
    public void setIp_NotInt() throws Exception {
        final String ip = "hello";

        w.setIp(ip);
    }

    @Test(expected = NullPointerException.class)
    public void setIp_TooHigh() throws Exception {
        final String ip = "999.999.999.999";

        w.setIp(ip);
    }

    @Test(expected = NullPointerException.class)
    public void setIp_TooFew() throws Exception {
        final String ip = "123.123";

        w.setIp(ip);
    }

    @Test(expected = NullPointerException.class)
    public void setIp_TooMany() throws Exception {
        final String ip = "123.123.123.123.123";

        w.setIp(ip);
    }

    @Test
    public void setMac_ValidMac() throws Exception {
        final String mac = "AB:CD:EF:00:44:88";

        w.setMac(mac);
        assertEquals(w.getMac(), mac);
    }

    @Test(expected = NullPointerException.class)
    public void setMac_InvalidCharacters() throws Exception {
        final String mac = "Q2:AB:FF:33:55:44";

        w.setMac(mac);
    }

    @Test(expected = NullPointerException.class)
    public void setMac_TooMany() throws Exception {
        final String mac = "11:22:33:44:55:66:77";

        w.setMac(mac);
    }

    @Test(expected = NullPointerException.class)
    public void setMac_TooFew() throws Exception {
        final String mac = "11:22";

        w.setMac(mac);
    }
}