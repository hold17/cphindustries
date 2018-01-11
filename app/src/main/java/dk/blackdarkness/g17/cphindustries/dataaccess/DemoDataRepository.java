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

class DemoDataRepository {
    private static final String SAVED_SCENES_LOCATION = "SAVED_SCENES_LIST";
    private static final String SAVED_SHOOTS_LOCATION = "SAVED_SHOOTS_LIST";
    private static final String SAVED_WEAPONS_LOCATION = "SAVED_WEAPONS_LIST";
    private static final String SAVED_SHOOTWEAPON_LOCATION = "SAVED_SHOOTWEAPON_LIST";

    private static List<Scene> listOfScenes;
    private static List<Shoot> listOfShoot;
    private static List<Weapon> listOfWeapon;
    private static List<ShootWeapon> listOfShootWeapon;


    static void saveListOfScenes(List<Scene> listOfScenes) {
        if (SharedPreferenceManager.getInstance() == null)
            throw new NullPointerException("SharedPreferenceManager has not yet been initialized.");
        SharedPreferenceManager.getInstance().saveObject(SAVED_SCENES_LOCATION, listOfScenes);
    }

    static List<Scene> loadListOfScenes() {
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

    static void saveListOfShoots(List<Shoot> listOfShoots) {
        if (SharedPreferenceManager.getInstance() == null)
            throw new NullPointerException("SharedPreferenceManager has not yet been initialized.");
        SharedPreferenceManager.getInstance().saveObject(SAVED_SHOOTS_LOCATION, listOfShoots);

    }

    static List<Shoot> loadListOfShoots() {
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

    static void saveListOfWeapons(List<Weapon> listOfWeapons) {
        if (SharedPreferenceManager.getInstance() == null)
            throw new NullPointerException("SharedPreferenceManager has not yet been initialized.");
        SharedPreferenceManager.getInstance().saveObject(SAVED_WEAPONS_LOCATION, listOfWeapons);
    }

    static List<Weapon> loadListOfWeapons() {
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

    static void saveListOfShootWeapon(List<ShootWeapon> listOfShootWeapon) {
        if (SharedPreferenceManager.getInstance() == null)
            throw new NullPointerException("SharedPreferenceManager has not yet been initialized.");
        SharedPreferenceManager.getInstance().saveObject(SAVED_SHOOTWEAPON_LOCATION, listOfShootWeapon);
    }

    static List<ShootWeapon> loadListOfShootWeapon() {

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

        // Demo Warnings
        final List<String> warnings = new ArrayList<>();
        warnings.add("This is a warning");
        warnings.add("This is a secondary warning!");

        // Demo Weapons
        listOfWeapon = new ArrayList<>();
        listOfWeapon.add(new Weapon(1, "Weapon 1", warnings, FireMode.BURST, ConnectionStatus.NO_CONNECTION));
        listOfWeapon.add(new Weapon(2, "Weapon 2", FireMode.FULL_AUTO, ConnectionStatus.FULL));
        listOfWeapon.add(new Weapon(3, "Weapon 3", ConnectionStatus.NO_CONNECTION));
        listOfWeapon.add(new Weapon(4, "Weapon 4", FireMode.SINGLE, ConnectionStatus.BAR_2));
        listOfWeapon.add(new Weapon(5, "weapon 5", warnings, FireMode.SINGLE, ConnectionStatus.BAR_3));
        listOfWeapon.add(new Weapon(6, "weapon 6", warnings, FireMode.BURST, ConnectionStatus.BAR_1));
        listOfWeapon.add(new Weapon(7, "weapon 7", FireMode.SINGLE, ConnectionStatus.BAR_2));
        listOfWeapon.add(new Weapon(8, "weapon 8", FireMode.FULL_AUTO, ConnectionStatus.BAR_1));
        saveListOfWeapons(listOfWeapon);

        // Demo shoots
        listOfShoot = new ArrayList<>();
        listOfShoot.add(new Shoot(1, "Shoot 1", 1));
        listOfShoot.add(new Shoot(2, "Shoot 2", 1));
        listOfShoot.add(new Shoot(3, "Shoot 3", 1));
        listOfShoot.add(new Shoot(4, "Shoot 4", 2));
        listOfShoot.add(new Shoot(5, "Shoot 5", 3));
        listOfShoot.add(new Shoot(6, "Shoot 6", 3));
        saveListOfShoots(listOfShoot);

        // Demo scenes
        listOfScenes = new ArrayList<>();
        listOfScenes.add(new Scene(1, "1 - Ze Zjuting sihn"));
        listOfScenes.add(new Scene(2, "22 - Robbing the bank"));
        listOfScenes.add(new Scene(3, "54 - The escape"));
        saveListOfScenes(listOfScenes);

        // Demo Shootweapon
        listOfShootWeapon = new ArrayList<>();
        listOfShootWeapon.add(new ShootWeapon(1,1, 1));
        listOfShootWeapon.add(new ShootWeapon(2,1, 2));
        listOfShootWeapon.add(new ShootWeapon(3,1, 3));
        listOfShootWeapon.add(new ShootWeapon(4,1, 4));
        listOfShootWeapon.add(new ShootWeapon(5,1, 5));
        listOfShootWeapon.add(new ShootWeapon(6,2, 2));
        listOfShootWeapon.add(new ShootWeapon(7,2, 7));
        listOfShootWeapon.add(new ShootWeapon(8,3, 1));
        listOfShootWeapon.add(new ShootWeapon(9,3, 2));
        listOfShootWeapon.add(new ShootWeapon(10,3, 4));
        listOfShootWeapon.add(new ShootWeapon(11,4, 6));
        listOfShootWeapon.add(new ShootWeapon(12,4, 7));
        listOfShootWeapon.add(new ShootWeapon(13,4, 1));
        listOfShootWeapon.add(new ShootWeapon(14,5, 2));
        listOfShootWeapon.add(new ShootWeapon(15,6, 1));
        listOfShootWeapon.add(new ShootWeapon(16,6, 2));
        listOfShootWeapon.add(new ShootWeapon(17,6, 5));
        listOfShootWeapon.add(new ShootWeapon(18,6, 6));
        listOfShootWeapon.add(new ShootWeapon(19,6, 8));
        saveListOfShootWeapon(listOfShootWeapon);
    }
}
