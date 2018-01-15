package dk.blackdarkness.g17.cphindustries.dataaccess;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.ConnectionStatus;
import dk.blackdarkness.g17.cphindustries.dto.FireMode;
import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

import static org.junit.Assert.*;

/**
 * Created by jonaslarsen on 14/01/2018.
 */
@RunWith(AndroidJUnit4.class)
public class WeaponDaoDemoTest {

    SceneDao sceneDao;
    ShootDao shootDao;
    WeaponDao weaponDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();

        SharedPreferenceManager.init(context);
        this.weaponDao = ApplicationConfig.getDaoFactory().getWeaponDao();
        
        //Test data scenes and shoot
        this.sceneDao = ApplicationConfig.getDaoFactory().getSceneDao();

        Scene scene11 = new Scene(11, "ThisIsScene11");
        Scene scene12 = new Scene(12, "ThisIsScene12");

        this.sceneDao.create(scene11);
        this.sceneDao.create(scene12);

        this.shootDao = ApplicationConfig.getDaoFactory().getShootDao();

        Shoot shoot21 = new Shoot(21, "ThisIsShoot21", 11);
        Shoot shoot22 = new Shoot(22, "ThisIsShoot22", 12);

        this.shootDao.create(shoot21);
        this.shootDao.create(shoot22);

    }

    @After
    public void closeDb() throws IOException {
        //resets database
        SharedPreferenceManager.getInstance().clear();
    }

    @Test
    public void getListTest(){
        // DemoDataRepository WeaponList.size = 8.

        int size = weaponDao.getList().size();

        assertEquals(8,size);

    }

    @Test
    public void createAndGetWeaponTest() throws Exception {
        // Demo Warnings
        final List<String> warnings = new ArrayList<>();
        warnings.add("This is a warning");
        warnings.add("This is a secondary warning!");

        // Demo Weapons
        Weapon inputWeapon = new Weapon( "Weapon 31", warnings, FireMode.BURST, ConnectionStatus.NO_CONNECTION, "175.216.74.201", "44-0E-1E-FA-58-1E");

        int size = this.weaponDao.getList().size();
        int expectedId = size +1;

        this.weaponDao.create(inputWeapon);

        Weapon outputWeapon = this.weaponDao.getWeapon(expectedId);

        assertEquals(expectedId, outputWeapon.getId());
        assertEquals(inputWeapon.getName(), outputWeapon.getName());
        assertEquals(inputWeapon.getWarnings(), outputWeapon.getWarnings());
        assertEquals(inputWeapon.getFireMode(), outputWeapon.getFireMode());
        assertEquals(inputWeapon.getConnectionStatus(), outputWeapon.getConnectionStatus());
        assertEquals(inputWeapon.getIp(), outputWeapon.getIp());
        assertEquals(inputWeapon.getMac(), outputWeapon.getMac());

    }

    @Test
    public void deleteWeaponTest() throws Exception {
        // Demo Warnings
        final List<String> warnings = new ArrayList<>();
        warnings.add("This is a warning");
        warnings.add("This is a secondary warning!");

        // Demo Weapons
        Weapon inputWeapon = new Weapon( "Weapon 31", warnings, FireMode.BURST, ConnectionStatus.NO_CONNECTION, "175.216.74.201", "44-0E-1E-FA-58-1E");

        this.weaponDao.create(inputWeapon);
        int size = this.weaponDao.getList().size();

        this.weaponDao.delete(inputWeapon.getId());

        Weapon outputWeapon = this.weaponDao.getWeapon(inputWeapon.getId());
        int newSize = this.weaponDao.getList().size();

        assertNull(outputWeapon);
        assertEquals(size - 1, newSize);
    }

    @Test
    public void updateWeaponTest() throws Exception {
        // Demo Warnings
        final List<String> warnings = new ArrayList<>();
        warnings.add("This is a warning");
        warnings.add("This is a secondary warning!");

        // Demo Weapons
        Weapon inputWeapon = new Weapon( "Weapon 31", warnings, FireMode.BURST, ConnectionStatus.NO_CONNECTION, "175.216.74.201", "44-0E-1E-FA-58-1E");

        this.weaponDao.create(inputWeapon);

        //Update scene with ny name
        inputWeapon.setName("UpdatedWeapon");
        warnings.remove(1);
        inputWeapon.setWarnings(warnings);
        inputWeapon.setFireMode(FireMode.SAFE);
        inputWeapon.setConnectionStatus(ConnectionStatus.BAR_3);
        inputWeapon.setIp("175.216.74.101");
        inputWeapon.setMac("44-0E-1E-FA-68-1E");

        this.weaponDao.update(inputWeapon);

        Weapon updatedWeapon = this.weaponDao.getWeapon(inputWeapon.getId());

        assertEquals(inputWeapon.getId(), updatedWeapon.getId());
        assertEquals(inputWeapon.getName(), updatedWeapon.getName());
        assertEquals(inputWeapon.getWarnings(), updatedWeapon.getWarnings());
        assertEquals(inputWeapon.getFireMode(), updatedWeapon.getFireMode());
        assertEquals(inputWeapon.getConnectionStatus(), updatedWeapon.getConnectionStatus());
        assertEquals(inputWeapon.getIp(), updatedWeapon.getIp());
        assertEquals(inputWeapon.getMac(), updatedWeapon.getMac());
    }

    @Test
   public void getListByShootTest(){
        List<Weapon> expectedListOfWeapons = new ArrayList<>();
        expectedListOfWeapons.add(new Weapon(2, "Weapon 2", FireMode.FULL_AUTO, ConnectionStatus.FULL,"128.39.196.59","59-3B-7B-89-29-4E"));
        expectedListOfWeapons.add(new Weapon(7, "weapon 7", FireMode.SINGLE, ConnectionStatus.BAR_2,"104.84.93.11","9C-28-EC-44-E5-57"));

        List<Weapon> actualListOfWeapons = this.weaponDao.getListByShoot(2);

        assertEquals(expectedListOfWeapons.get(0).getId(),actualListOfWeapons.get(0).getId());
        assertEquals(expectedListOfWeapons.get(1).getId(),actualListOfWeapons.get(1).getId());

   }


    @Test
    public void getShootsByWeaponTest(){
       List<Shoot> expectedListOfShoots = new ArrayList<>();
        expectedListOfShoots.add(new Shoot(1, "Shoot 1", 1));
        expectedListOfShoots.add(new Shoot(2, "Shoot 2", 1));
        expectedListOfShoots.add(new Shoot(3, "Shoot 3", 1));
        expectedListOfShoots.add(new Shoot(5, "Shoot 5", 3));
        expectedListOfShoots.add(new Shoot(6, "Shoot 6", 3));

        List<Shoot> actualListOfShoots = this.weaponDao.getShootsByWeapon(2);

        assertEquals(expectedListOfShoots.get(0).getId(),actualListOfShoots.get(0).getId());
        assertEquals(expectedListOfShoots.get(1).getId(),actualListOfShoots.get(1).getId());
        assertEquals(expectedListOfShoots.get(2).getId(),actualListOfShoots.get(2).getId());
        assertEquals(expectedListOfShoots.get(3).getId(),actualListOfShoots.get(3).getId());
        assertEquals(expectedListOfShoots.get(4).getId(),actualListOfShoots.get(4).getId());

    }

}