package dk.blackdarkness.g17.cphindustries.dataaccess;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;

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
    public void createAndGetSceneTest() throws Exception {
        Shoot inputShoot = new Shoot(21, "ThisIsShoot21", 11);

        this.shootDao.create(inputShoot);

        Shoot outputScene = this.shootDao.getShoot(inputShoot.getId());

        assertEquals(inputShoot.getId(), outputScene.getId());
        assertEquals(inputShoot.getName(), outputScene.getName());
        assertEquals(inputShoot.getSceneId(), outputScene.getSceneId());
    }

    @Test
    public void deleteSceneTest() throws Exception {
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
    public void updateSceneTest() throws Exception {
        Shoot inputShoot = new Shoot(21, "ThisIsShoot21", 11);

        this.shootDao.create(inputShoot);

        //Update scene with ny name
        inputShoot.setName("UpdatedShoot");

        this.shootDao.update(inputShoot.getId(),inputShoot);

        Shoot updatedScene = this.shootDao.getShoot(inputShoot.getId());

        assertEquals(inputShoot.getId(),updatedScene.getId());
        assertEquals(inputShoot.getName(), updatedScene.getName());
        assertEquals(inputShoot.getSceneId(), updatedScene.getSceneId());
    }


}