package dk.blackdarkness.g17.cphindustries.dataaccess;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dk.blackdarkness.g17.cphindustries.dto.ConnectionStatus;
import dk.blackdarkness.g17.cphindustries.dto.FireMode;
import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

/**
 * Created by jonaslarsen on 04/01/2018.
 */

public class DemoData {
    private static final String SAVED_SCENES_LOCATION = "SAVED_SCENES_LIST";

    public static void save(List<Scene> allScenes){

        if (SharedPreferenceManager.getInstance() == null) throw new NullPointerException("SharedPreferenceManager has not yet been initialized.");
        SharedPreferenceManager.getInstance().saveObject(SAVED_SCENES_LOCATION, allScenes);
    }

    private static Object loadFromPrefs() {

        Type returnType = new TypeToken<ArrayList<Scene>>(){}.getType();
        return SharedPreferenceManager.getInstance().getObject(SAVED_SCENES_LOCATION,returnType);

    }


    public static List<Scene> load(){
        if (SharedPreferenceManager.getInstance() == null) throw new NullPointerException("SharedPreferenceManager has not yet been initialized.");

        if (loadFromPrefs() == null) {
            save(generateDemoData());
        }

        return (ArrayList<Scene>) loadFromPrefs();
        //return this.allScenes;
    }

    private static List<Scene> generateDemoData() {
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

        final Map<Integer,Weapon> s1weapons1 = new HashMap<>();
        s1weapons1.put(weapon1.getId(),weapon1);

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


}
