package dk.blackdarkness.g17.cphindustries.dataaccess;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import dk.blackdarkness.g17.cphindustries.dto.ConnectionStatus;
import dk.blackdarkness.g17.cphindustries.dto.FireMode;
import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

/**
 * Created by awo on 06-12-2017.
 */

public class SceneDaoDemo implements SceneDao {
    private static final String SAVED_SCENES_LOCATION = "SAVED_SCENES_LIST";
    private List<Scene> allScenes;

    private List<Scene> generateDemoData() {
        Scene scene1;
        Scene scene2;
        Scene scene3;

        // Scene 1
        Shoot shoot1;
        Shoot shoot2;
        Shoot shoot3;
        // Scene 2
        Shoot shoot4;
        // Scene 3
        Shoot shoot5;
        Shoot shoot6;

        // Demo Warnings
        final List<String> warnings = new ArrayList<>();
        warnings.add("This is a warning");
        warnings.add("This is a secondary warning!");

        // Demo Weapons
        final Weapon weapon1 = new Weapon(1337, "Weapon 1", warnings, FireMode.BURST, ConnectionStatus.NO_CONNECTION);
        final Weapon weapon2 = new Weapon(1, "Weapon 2", FireMode.FULL_AUTO, ConnectionStatus.FULL);
        final Weapon weapon3 = new Weapon(2, "Weapon 3", ConnectionStatus.NO_CONNECTION);
        final Weapon weapon4 = new Weapon(3, "Weapon 4", FireMode.SINGLE, ConnectionStatus.BAR_2);
        final Weapon weapon5 = new Weapon(4, "weapon 5", warnings, FireMode.SINGLE, ConnectionStatus.BAR_3);
        final Weapon weapon6 = new Weapon(5, "weapon 6", warnings, FireMode.BURST, ConnectionStatus.BAR_1);
        final Weapon weapon7 = new Weapon(6, "weapon 7", FireMode.SINGLE, ConnectionStatus.BAR_2);
        final Weapon weapon8 = new Weapon(7, "weapon 8", FireMode.FULL_AUTO, ConnectionStatus.BAR_1);

        // Demo Shoot weapon lists
        final List<Weapon> s1weapons = new ArrayList<>();
        s1weapons.add(weapon1);
        s1weapons.add(weapon2);
        s1weapons.add(weapon3);
        s1weapons.add(weapon4);
        s1weapons.add(weapon5);
        final List<Weapon> s2weapons = new ArrayList<>();
        s2weapons.add(weapon2);
        s2weapons.add(weapon7);
        final List<Weapon> s3weapons = new ArrayList<>();
        s3weapons.add(weapon1);
        s3weapons.add(weapon2);
        s3weapons.add(weapon4);
        final List<Weapon> s4weapons = new ArrayList<>();
        s4weapons.add(weapon6);
        s4weapons.add(weapon7);
        s4weapons.add(weapon1);
        final List<Weapon> s5weapons = new ArrayList<>();
        s5weapons.add(weapon2);
        final List<Weapon> s6weapons = new ArrayList<>();
        s6weapons.add(weapon1);
        s6weapons.add(weapon2);
        s6weapons.add(weapon5);
        s6weapons.add(weapon6);
        s6weapons.add(weapon8);

        shoot1 = new Shoot(1337, "Shoot 1", s1weapons);
        shoot2 = new Shoot(1, "Shoot 2", s2weapons);
        shoot3 = new Shoot(2, "Shoot 3", s3weapons);
        shoot4 = new Shoot(3, "Shoot 4", s4weapons);
        shoot5 = new Shoot(4, "Shoot 5", s5weapons);
        shoot6 = new Shoot(5, "Shoot 6", s6weapons);

        final List<Shoot> scene1Shoots = new ArrayList<>();
        scene1Shoots.add(shoot1);
        scene1Shoots.add(shoot2);
        scene1Shoots.add(shoot3);
        final List<Shoot> scene2Shoots = new ArrayList<>();
        scene2Shoots.add(shoot4);
        final List<Shoot> scene3Shoots = new ArrayList<>();
        scene3Shoots.add(shoot5);
        scene3Shoots.add(shoot6);

        scene1 = new Scene(1337, "1 - Ze Zjuting sihn", scene1Shoots);
        scene2 = new Scene(1, "22 - Robbing the bank", scene2Shoots);
        scene3 = new Scene(2, "54 - The escape", scene3Shoots);

        final List<Scene> scenes = new ArrayList<>();
        scenes.add(scene1);
        scenes.add(scene2);
        scenes.add(scene3);

        return scenes;
    }

    /**
     * This will not work if SharedPreferenceManager has not yet been initialized with a context.
     */
    SceneDaoDemo() {
        if (SharedPreferenceManager.getInstance() == null) return;

        Type returnType = new TypeToken<ArrayList<Scene>>(){}.getType();

        Object allScenesObj = SharedPreferenceManager.getInstance().getObject(SAVED_SCENES_LOCATION,returnType);

        if (allScenesObj == null) {
            this.allScenes = this.generateDemoData();
        }
        else {
            this.allScenes = (ArrayList<Scene>) allScenesObj;

        }
    }

    @Override
    public List<Scene> get() {
        return allScenes;
    }

    @Override
    public Scene get(int id) {
        for (Scene s : this.allScenes) {
            if (s.getId() == id) {
                return s;
            }
        }

        return null;
    }

    @Override
    public void create(Scene scene) {
        for (Scene s : this.allScenes) {
            if (s.getId() > scene.getId()) {
                scene.setId(s.getId() + 1);
            }
        }

        this.allScenes.add(scene);

        this.commitChanges();
    }

    @Override
    public void update(Scene updatedScene) {
        for (Scene s : this.allScenes) {
            if (s.getId() == updatedScene.getId()) {
                s.setShoots(updatedScene.getShoots());
                s.setName(updatedScene.getName());
            }
        }

        this.commitChanges();
    }

    @Override
    public void delete(int id) {
        for (Scene s : this.allScenes) {
            if (s.getId() == id) {
                this.allScenes.remove(s);
            }
        }

        this.commitChanges();
    }

    private void commitChanges() {
        if (SharedPreferenceManager.getInstance() == null) return;
        SharedPreferenceManager.getInstance().saveObject(SAVED_SCENES_LOCATION, this.allScenes);
    }
}
