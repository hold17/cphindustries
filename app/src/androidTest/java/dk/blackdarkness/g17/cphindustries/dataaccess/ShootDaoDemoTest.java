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
public class ShootDaoDemoTest {

    SceneDao sceneDao;
    ShootDao shootDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();

        SharedPreferenceManager.init(context);
        this.shootDao = ApplicationConfig.getDaoFactory().getShootDao();


        //Test scenes
        this.sceneDao = ApplicationConfig.getDaoFactory().getSceneDao();

        Scene scene11 = new Scene(11, "ThisIsScene11");
        Scene scene12 = new Scene(12, "ThisIsScene12");

        this.sceneDao.create(scene11);
        this.sceneDao.create(scene12);

    }

    @After
    public void closeDb() throws IOException {
        //resets database
        SharedPreferenceManager.getInstance().clear();
    }

    @Test
    public void getListTest() {
        // DemoDataRepository shootList.size = 6.

        int size = shootDao.getList().size();

        assertEquals(6, size);

    }

    @Test
    public void createAndGetShootTest() throws Exception {
        Shoot inputShoot = new Shoot("ThisIsShoot21", 11);

        int size = this.shootDao.getList().size();
        int expectedId = size + 1;

        this.shootDao.create(inputShoot);

        Shoot outputScene = this.shootDao.getShoot(expectedId);

        assertEquals(expectedId, outputScene.getId());
        assertEquals(inputShoot.getName(), outputScene.getName());
        assertEquals(inputShoot.getSceneId(), outputScene.getSceneId());
    }

    @Test
    public void deleteShootTest() throws Exception {
        Shoot inputShoot = new Shoot(21, "ThisIsShoot21", 11);

        this.shootDao.create(inputShoot);

        int size = this.shootDao.getList().size();

        this.shootDao.delete(inputShoot.getId());

        Shoot outputScene = this.shootDao.getShoot(inputShoot.getId());
        int newSize = this.shootDao.getList().size();

        assertNull(outputScene);
        assertEquals(size - 1, newSize);
    }

    @Test
    public void updateShootTest() throws Exception {
        Shoot inputShoot = new Shoot(21, "ThisIsShoot21", 11);

        this.shootDao.create(inputShoot);

        //Update scene with ny name
        inputShoot.setName("UpdatedShoot");
        inputShoot.setSceneId(12);

        this.shootDao.update(inputShoot);

        Shoot updatedShoot = this.shootDao.getShoot(inputShoot.getId());

        assertEquals(inputShoot.getId(), updatedShoot.getId());
        assertEquals(inputShoot.getName(), updatedShoot.getName());
        assertEquals(inputShoot.getSceneId(), updatedShoot.getSceneId());
    }

    @Test
    public void getListBySceneTest() {

        List<Shoot> expectedListOfShoots = new ArrayList<>();
        expectedListOfShoots.add(new Shoot(1, "Shoot 1", 1));
        expectedListOfShoots.add(new Shoot(2, "Shoot 2", 1));
        expectedListOfShoots.add(new Shoot(3, "Shoot 3", 1));

        List<Shoot> actualListOfShoots = (ArrayList<Shoot>) this.shootDao.getListByScene(1);



        assertEquals(expectedListOfShoots.get(0).getId(),actualListOfShoots.get(0).getId());
        assertEquals(expectedListOfShoots.get(1).getId(),actualListOfShoots.get(1).getId());
        assertEquals(expectedListOfShoots.get(2).getId(),actualListOfShoots.get(2).getId());

    }

    @Test
    public void getWeaponsByShootTest(){

        List<Weapon> expectedListOfWeapons = new ArrayList<>();
        expectedListOfWeapons.add(new Weapon(2, "Weapon 2", FireMode.FULL_AUTO, ConnectionStatus.FULL,"128.39.196.59","59-3B-7B-89-29-4E"));
        expectedListOfWeapons.add(new Weapon(7, "weapon 7", FireMode.SINGLE, ConnectionStatus.BAR_2,"104.84.93.11","9C-28-EC-44-E5-57"));

        List<Weapon> actualListOfWeapons = this.shootDao.getWeaponsByShoot(2);

        assertEquals(expectedListOfWeapons.get(0).getId(),actualListOfWeapons.get(0).getId());
        assertEquals(expectedListOfWeapons.get(1).getId(),actualListOfWeapons.get(1).getId());

    }


}