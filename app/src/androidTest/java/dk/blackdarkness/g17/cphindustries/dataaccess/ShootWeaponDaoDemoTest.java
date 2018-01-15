package dk.blackdarkness.g17.cphindustries.dataaccess;

/**
 * Created by jonaslarsen on 15/01/2018.
 */

import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.dto.ShootWeapon;

import static org.junit.Assert.*;

/**
 * Created by jonaslarsen on 14/01/2018.
 */
@RunWith(AndroidJUnit4.class)
public class ShootWeaponDaoDemoTest {

    ShootWeaponDao shootWeaponDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();

        SharedPreferenceManager.init(context);

        this.shootWeaponDao = ApplicationConfig.getDaoFactory().getShootWeaponDao();
    }

    @After
    public void closeDb() throws IOException {

        SharedPreferenceManager.getInstance().clear();
    }

    @Test
    public void getListTest(){
        // DemoDataRepository shootList.size = 19.

        int size = shootWeaponDao.getList().size();

        assertEquals(19,size);

    }

    @Test
    public void createAndGetShootWeaponTest() throws Exception {
        ShootWeapon inputShootWeapon = new ShootWeapon(1, 2);

        this.shootWeaponDao.create(inputShootWeapon);

        ShootWeapon outputShootWeapon = this.shootWeaponDao.getShootWeapon(inputShootWeapon.getShootId(),inputShootWeapon.getWeaponId());

        // Test inputShootWeapon
        assertEquals(inputShootWeapon.getShootId(), outputShootWeapon.getShootId());
        assertEquals(inputShootWeapon.getWeaponId(), outputShootWeapon.getWeaponId());

        int size = this.shootWeaponDao.getList().size();
        int expectedId = size +1;

        ShootWeapon inputShootWeapon2 = new ShootWeapon(100, 150);
        this.shootWeaponDao.create(inputShootWeapon2);
        ShootWeapon outputShootWeapon2 = this.shootWeaponDao.getShootWeapon(inputShootWeapon2.getShootId(),inputShootWeapon2.getWeaponId());

        //Test if inputShootWeapon2's shootWeaponId match expected shootWeaponId
        assertEquals(expectedId,outputShootWeapon2.getShootWeaponId());


    }

    @Test
    public void deleteShootWeaponTest() throws Exception {
        ShootWeapon inputShootWeapon = new ShootWeapon(1, 2);
        this.shootWeaponDao.create(inputShootWeapon);
        int size = this.shootWeaponDao.getList().size();

        this.shootWeaponDao.delete(1,2);

        ShootWeapon outputShootWeapon = this.shootWeaponDao.getShootWeapon(1,2);
        int newSize = this.shootWeaponDao.getList().size();

        assertNull(outputShootWeapon);
        assertEquals(size - 1, newSize);
    }

    @Test
    public void updateShootWeaponTest() throws Exception {
        ShootWeapon inputShootWeapon = new ShootWeapon(1, 2);
        this.shootWeaponDao.create(inputShootWeapon);

        //Update scene with ny name
        inputShootWeapon.setShootId(2);
        inputShootWeapon.setWeaponId(3);

        this.shootWeaponDao.update(1,2, inputShootWeapon);

        ShootWeapon updatedShootWeapon = this.shootWeaponDao.getShootWeapon(2,3);

        assertEquals(inputShootWeapon.getShootId(), updatedShootWeapon.getShootId());
        assertEquals(inputShootWeapon.getWeaponId(), updatedShootWeapon.getWeaponId());
    }

}
