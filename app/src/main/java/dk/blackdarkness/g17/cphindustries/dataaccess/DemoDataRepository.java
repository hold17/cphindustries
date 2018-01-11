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
import dk.blackdarkness.g17.cphindustries.dto.ShootWeapon;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

public class DemoDataRepository {
    private static final String SAVED_SCENES_LOCATION = "SAVED_SCENES_LIST";
    private static final String SAVED_SHOOTS_LOCATION = "SAVED_SHOOTS_LIST";
    private static final String SAVED_WEAPONS_LOCATION = "SAVED_WEAPONS_LIST";
    private static final String SAVED_SHOOTWEAPON_LOCATION = "SAVED_SHOOTWEAPON_LIST";

    private static List<Scene> listOfScenes;
    private static List<Shoot> listOfShoot;
    private static List<Weapon> listOfWeapon;
    private static List<ShootWeapon> listOfShootWeapon;


    public static void saveListOfScenes(List<Scene> listOfScenes) {
        if (SharedPreferenceManager.getInstance() == null)
            throw new NullPointerException("SharedPreferenceManager has not yet been initialized.");
        SharedPreferenceManager.getInstance().saveObject(SAVED_SCENES_LOCATION, listOfScenes);
    }

    public static List<Scene> loadListOfScenes() {
        if (SharedPreferenceManager.getInstance() == null)
            throw new NullPointerException("SharedPreferenceManager has not yet been initialized.");


        Type returnType = new TypeToken<ArrayList<Scene>>() {
        }.getType();

        listOfScenes = (ArrayList<Scene>) SharedPreferenceManager.getInstance().getObject(SAVED_SCENES_LOCATION, returnType);

        if (listOfScenes == null) {
            generateDemoData();
        }
        return listOfScenes;

    }

    public static void saveListOfShoots(List<Shoot> listOfShoots) {
        if (SharedPreferenceManager.getInstance() == null)
            throw new NullPointerException("SharedPreferenceManager has not yet been initialized.");
        SharedPreferenceManager.getInstance().saveObject(SAVED_SHOOTS_LOCATION, listOfShoots);

    }

    public static List<Shoot> loadListOfShoots() {
        if (SharedPreferenceManager.getInstance() == null)
            throw new NullPointerException("SharedPreferenceManager has not yet been initialized.");

        Type returnType = new TypeToken<ArrayList<Shoot>>() {
        }.getType();

        listOfShoot = (ArrayList<Shoot>) SharedPreferenceManager.getInstance().getObject(SAVED_SHOOTS_LOCATION, returnType);

        if (listOfShoot == null) {
            generateDemoData();
        }
        return listOfShoot;


    }

    public static void saveListOfWeapons(List<Weapon> listOfWeapons) {
        if (SharedPreferenceManager.getInstance() == null)
            throw new NullPointerException("SharedPreferenceManager has not yet been initialized.");
        SharedPreferenceManager.getInstance().saveObject(SAVED_WEAPONS_LOCATION, listOfWeapons);

    }

    public static List<Weapon> loadListOfWeapons() {
        if (SharedPreferenceManager.getInstance() == null)
            throw new NullPointerException("SharedPreferenceManager has not yet been initialized.");

        Type returnType = new TypeToken<ArrayList<Weapon>>() {
        }.getType();

        listOfWeapon = (ArrayList<Weapon>) SharedPreferenceManager.getInstance().getObject(SAVED_WEAPONS_LOCATION, returnType);

        if (listOfWeapon == null) {
            generateDemoData();
        }
        return listOfWeapon;

    }

    public static void saveListOfShootWeapon(List<ShootWeapon> listOfShootWeapon) {
        if (SharedPreferenceManager.getInstance() == null)
            throw new NullPointerException("SharedPreferenceManager has not yet been initialized.");
        SharedPreferenceManager.getInstance().saveObject(SAVED_SHOOTWEAPON_LOCATION, listOfShootWeapon);
    }

    public static List<ShootWeapon> loadListOfShootWeapon() {

        if (SharedPreferenceManager.getInstance() == null)
            throw new NullPointerException("SharedPreferenceManager has not yet been initialized.");

        Type returnType = new TypeToken<ArrayList<ShootWeapon>>() {
        }.getType();

        listOfShootWeapon = (ArrayList<ShootWeapon>) SharedPreferenceManager.getInstance().getObject(SAVED_SHOOTWEAPON_LOCATION, returnType);

        if (listOfShootWeapon == null) {
            generateDemoData();
        }
        return listOfShootWeapon;


    }

    private static void generateDemoData() {
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
        final Weapon weapon1 = new Weapon(1, "Weapon 1", warnings, FireMode.BURST, ConnectionStatus.NO_CONNECTION);
        final Weapon weapon2 = new Weapon(2, "Weapon 2", FireMode.FULL_AUTO, ConnectionStatus.FULL);
        final Weapon weapon3 = new Weapon(3, "Weapon 3", ConnectionStatus.NO_CONNECTION);
        final Weapon weapon4 = new Weapon(4, "Weapon 4", FireMode.SINGLE, ConnectionStatus.BAR_2);
        final Weapon weapon5 = new Weapon(5, "weapon 5", warnings, FireMode.SINGLE, ConnectionStatus.BAR_3);
        final Weapon weapon6 = new Weapon(6, "weapon 6", warnings, FireMode.BURST, ConnectionStatus.BAR_1);
        final Weapon weapon7 = new Weapon(7, "weapon 7", FireMode.SINGLE, ConnectionStatus.BAR_2);
        final Weapon weapon8 = new Weapon(8, "weapon 8", FireMode.FULL_AUTO, ConnectionStatus.BAR_1);

        // Demo weapon lists
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

        // Demo shoots
        shoot1 = new Shoot(1, "Shoot 1", 1);
        shoot2 = new Shoot(2, "Shoot 2", 1);
        shoot3 = new Shoot(3, "Shoot 3", 1);
        shoot4 = new Shoot(4, "Shoot 4", 2);
        shoot5 = new Shoot(5, "Shoot 5", 3);
        shoot6 = new Shoot(6, "Shoot 6", 3);

        // Demo shoots list
        final List<Shoot> scene1Shoots = new ArrayList<>();
        scene1Shoots.add(shoot1);
        scene1Shoots.add(shoot2);
        scene1Shoots.add(shoot3);
        final List<Shoot> scene2Shoots = new ArrayList<>();
        scene2Shoots.add(shoot4);
        final List<Shoot> scene3Shoots = new ArrayList<>();
        scene3Shoots.add(shoot5);
        scene3Shoots.add(shoot6);

        // Demo scenes

        scene1 = new Scene(1, "1 - Ze Zjuting sihn");
        scene2 = new Scene(2, "22 - Robbing the bank");
        scene3 = new Scene(3, "54 - The escape");

        final List<Scene> scenes = new ArrayList<>();
        scenes.add(scene1);
        scenes.add(scene2);
        scenes.add(scene3);

        // Demo Shootweapon

        final ShootWeapon shoot1Weapon1 = new ShootWeapon(1,1, 1);
        final ShootWeapon shoot1Weapon2 = new ShootWeapon(2,1, 2);
        final ShootWeapon shoot1Weapon3 = new ShootWeapon(3,1, 3);
        final ShootWeapon shoot1Weapon4 = new ShootWeapon(4,1, 4);
        final ShootWeapon shoot1Weapon5 = new ShootWeapon(5,1, 5);

        final ShootWeapon shoot2Weapon2 = new ShootWeapon(6,2, 2);
        final ShootWeapon shoot2Weapon7 = new ShootWeapon(7,2, 7);

        final ShootWeapon shoot3Weapon1 = new ShootWeapon(8,3, 1);
        final ShootWeapon shoot3Weapon2 = new ShootWeapon(9,3, 2);
        final ShootWeapon shoot3Weapon4 = new ShootWeapon(10,3, 4);

        final ShootWeapon shoot4Weapon6 = new ShootWeapon(11,4, 6);
        final ShootWeapon shoot4Weapon7 = new ShootWeapon(12,4, 7);
        final ShootWeapon shoot4Weapon1 = new ShootWeapon(13,4, 1);

        final ShootWeapon shoot5Weapon2 = new ShootWeapon(14,5, 2);

        final ShootWeapon shoot6Weapon1 = new ShootWeapon(15,6, 1);
        final ShootWeapon shoot6Weapon2 = new ShootWeapon(16,6, 2);
        final ShootWeapon shoot6Weapon5 = new ShootWeapon(17,6, 5);
        final ShootWeapon shoot6Weapon6 = new ShootWeapon(18,6, 6);
        final ShootWeapon shoot6Weapon8 = new ShootWeapon(19,6, 8);

        // Demo Shootweapon list

        final List<ShootWeapon> shootWeaponList = new ArrayList<>();

        shootWeaponList.add(shoot1Weapon1);
        shootWeaponList.add(shoot1Weapon2);
        shootWeaponList.add(shoot1Weapon3);
        shootWeaponList.add(shoot1Weapon4);
        shootWeaponList.add(shoot1Weapon5);
        shootWeaponList.add(shoot2Weapon2);
        shootWeaponList.add(shoot2Weapon7);
        shootWeaponList.add(shoot3Weapon1);
        shootWeaponList.add(shoot3Weapon2);
        shootWeaponList.add(shoot3Weapon4);
        shootWeaponList.add(shoot4Weapon6);
        shootWeaponList.add(shoot4Weapon7);
        shootWeaponList.add(shoot4Weapon1);
        shootWeaponList.add(shoot5Weapon2);
        shootWeaponList.add(shoot6Weapon1);
        shootWeaponList.add(shoot6Weapon2);
        shootWeaponList.add(shoot6Weapon5);
        shootWeaponList.add(shoot6Weapon6);
        shootWeaponList.add(shoot6Weapon8);


        // Save data

        listOfScenes = new ArrayList<>();
        listOfShoot = new ArrayList<>();
        listOfWeapon = new ArrayList<>();
        listOfShootWeapon = new ArrayList<>();

        listOfScenes = scenes;

        listOfShoot.add(shoot1);
        listOfShoot.add(shoot2);
        listOfShoot.add(shoot3);
        listOfShoot.add(shoot4);
        listOfShoot.add(shoot5);
        listOfShoot.add(shoot6);

        listOfWeapon.add(weapon1);
        listOfWeapon.add(weapon2);
        listOfWeapon.add(weapon3);
        listOfWeapon.add(weapon4);
        listOfWeapon.add(weapon5);
        listOfWeapon.add(weapon6);
        listOfWeapon.add(weapon7);
        listOfWeapon.add(weapon8);

        listOfShootWeapon.add(shoot1Weapon1);
        listOfShootWeapon.add(shoot1Weapon2);
        listOfShootWeapon.add(shoot1Weapon3);
        listOfShootWeapon.add(shoot1Weapon4);
        listOfShootWeapon.add(shoot1Weapon5);
        listOfShootWeapon.add(shoot2Weapon2);
        listOfShootWeapon.add(shoot2Weapon7);
        listOfShootWeapon.add(shoot3Weapon1);
        listOfShootWeapon.add(shoot3Weapon2);
        listOfShootWeapon.add(shoot3Weapon4);
        listOfShootWeapon.add(shoot4Weapon6);
        listOfShootWeapon.add(shoot4Weapon7);
        listOfShootWeapon.add(shoot4Weapon1);
        listOfShootWeapon.add(shoot5Weapon2);
        listOfShootWeapon.add(shoot6Weapon1);
        listOfShootWeapon.add(shoot6Weapon2);
        listOfShootWeapon.add(shoot6Weapon5);
        listOfShootWeapon.add(shoot6Weapon6);
        listOfShootWeapon.add(shoot6Weapon8);

        saveListOfScenes(listOfScenes);
        saveListOfShoots(listOfShoot);
        saveListOfWeapons(listOfWeapon);
        saveListOfShootWeapon(listOfShootWeapon);


    }
}
