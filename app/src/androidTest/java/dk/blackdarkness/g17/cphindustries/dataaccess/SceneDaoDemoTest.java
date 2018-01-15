package dk.blackdarkness.g17.cphindustries.dataaccess;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.Scene;

import static org.junit.Assert.*;

/**
 * Created by jonaslarsen on 14/01/2018.
 */
@RunWith(AndroidJUnit4.class)
public class SceneDaoDemoTest {

    SceneDao sceneDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();

        SharedPreferenceManager.init(context);

        this.sceneDao = ApplicationConfig.getDaoFactory().getSceneDao();
    }

    @After
    public void closeDb() throws IOException {

        SharedPreferenceManager.getInstance().clear();
    }


    @Test
    public void createAndGetSceneTest() throws Exception {
        Scene inputScene = new Scene(11, "ThisIsScene11");

        this.sceneDao.create(inputScene);

        Scene outputScene = this.sceneDao.getScene(inputScene.getId());

        assertEquals(inputScene.getId(), outputScene.getId());
        assertEquals(inputScene.getName(), outputScene.getName());
    }

    @Test
    public void deleteSceneTest() throws Exception {
        Scene inputScene = new Scene(11, "ThisIsScene11");
        this.sceneDao.create(inputScene);
        int size = this.sceneDao.getList().size();

        this.sceneDao.delete(inputScene.getId());

        Scene outputScene = this.sceneDao.getScene(inputScene.getId());
        int newSize = this.sceneDao.getList().size();

        assertNull(outputScene);
        assertEquals(size - 1, newSize);
    }

    @Test
    public void updateSceneTest() throws Exception {
        Scene inputScene = new Scene(11, "ThisIsScene11");
        this.sceneDao.create(inputScene);

        //Update scene with ny name
        inputScene.setName("UpdatedScene");

        //this.sceneDao.update(inputScene.getId(),inputScene);

        Scene updatedScene = this.sceneDao.getScene(inputScene.getId());

        assertEquals(inputScene.getId(),updatedScene.getId());
        assertEquals(inputScene.getName(), updatedScene.getName());
    }


}