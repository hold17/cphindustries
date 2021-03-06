package dk.blackdarkness.g17.cphindustries.dataaccess;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
    private static List<Shoot> listOfShoots;
    private static List<Weapon> listOfWeapons;
    private static List<ShootWeapon> listOfShootWeapons;


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

        listOfShoots = (ArrayList<Shoot>) SharedPreferenceManager.getInstance().getObject(SAVED_SHOOTS_LOCATION, returnType);

        if (listOfShoots == null) {
            generateDemoData();
        }
        return listOfShoots;
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

        listOfWeapons = (ArrayList<Weapon>) SharedPreferenceManager.getInstance().getObject(SAVED_WEAPONS_LOCATION, returnType);

        if (listOfWeapons == null) {
            generateDemoData();
        }
        return listOfWeapons;
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

        listOfShootWeapons = (ArrayList<ShootWeapon>) SharedPreferenceManager.getInstance().getObject(SAVED_SHOOTWEAPON_LOCATION, returnType);

        if (listOfShootWeapons == null) {
            generateDemoData();
        }
        return listOfShootWeapons;
    }

    private static void generateDemoData() {

        // Demo Warnings
        final List<String> warnings = new ArrayList<>();
//        warnings.add("This is a warning!");
//        warnings.add("This is a secondary warning!");

        // Demo Weapons
        listOfWeapons = new ArrayList<>();
        listOfWeapons.add(new Weapon(1, "Brian's M16A4", warnings, FireMode.BURST, ConnectionStatus.NO_CONNECTION,"175.216.74.202","44-0E-1E-FA-58-0E", 47));
        listOfWeapons.add(new Weapon(2, "John's AK47", FireMode.FULL_AUTO, ConnectionStatus.FULL,"128.39.196.59","59-3B-7B-89-29-4E", 12));
        listOfWeapons.add(new Weapon(3, "Linda's P90", warnings , FireMode.SAFE, ConnectionStatus.NO_CONNECTION,"211.233.131.106","1F-D6-15-92-6B-4B", 100));
        listOfWeapons.add(new Weapon(4, "Brian's Smith & Wessern 9mm", FireMode.SINGLE, ConnectionStatus.BAR_2,"174.208.237.9","A9-78-15-DA-DF-1D", 84));
        listOfWeapons.add(new Weapon(5, "Kidnapper's Glock 18", warnings, FireMode.SINGLE, ConnectionStatus.BAR_3,"51.207.19.164","A2-19-02-EF-CA-6D", 33));
        listOfWeapons.add(new Weapon(6, "Kidnapper's MAC-10", warnings, FireMode.BURST, ConnectionStatus.BAR_1,"3.40.199.232","99-50-FD-6F-67-C4", 68));
        listOfWeapons.add(new Weapon(7, "Hostage's concealed Ruger LCP", FireMode.SINGLE, ConnectionStatus.BAR_2,"104.84.93.11","9C-28-EC-44-E5-57", 41));
        listOfWeapons.add(new Weapon(8, "Roof sniper's AS50", FireMode.FULL_AUTO, ConnectionStatus.BAR_1,"135.123.55.219","6F-5E-D6-D1-A3-39", 88));
        saveListOfWeapons(listOfWeapons);

        // Demo shoots
        listOfShoots = new ArrayList<>();
        listOfShoots.add(new Shoot(1, "Shoot 1", 1));
        listOfShoots.add(new Shoot(2, "Shoot 2", 1));
        listOfShoots.add(new Shoot(3, "Shoot 3", 1));
        listOfShoots.add(new Shoot(4, "Shoot 4", 2));
        listOfShoots.add(new Shoot(5, "Shoot 5", 3));
        listOfShoots.add(new Shoot(6, "Shoot 6", 3));
        saveListOfShoots(listOfShoots);

        // Demo scenes
        listOfScenes = new ArrayList<>();
        listOfScenes.add(new Scene(1, "The kidnapping"));
        listOfScenes.add(new Scene(2, "Robbing the bank"));
        listOfScenes.add(new Scene(3, "The escape"));
        saveListOfScenes(listOfScenes);

        // Demo Shootweapon
        listOfShootWeapons = new ArrayList<>();
        listOfShootWeapons.add(new ShootWeapon(1,1, 1));
        listOfShootWeapons.add(new ShootWeapon(2,1, 2));
        listOfShootWeapons.add(new ShootWeapon(3,1, 3));
        listOfShootWeapons.add(new ShootWeapon(4,1, 4));
        listOfShootWeapons.add(new ShootWeapon(5,1, 5));
        listOfShootWeapons.add(new ShootWeapon(6,2, 2));
        listOfShootWeapons.add(new ShootWeapon(7,2, 7));
        listOfShootWeapons.add(new ShootWeapon(8,3, 1));
        listOfShootWeapons.add(new ShootWeapon(9,3, 2));
        listOfShootWeapons.add(new ShootWeapon(10,3, 4));
        listOfShootWeapons.add(new ShootWeapon(11,4, 6));
        listOfShootWeapons.add(new ShootWeapon(12,4, 7));
        listOfShootWeapons.add(new ShootWeapon(13,4, 1));
        listOfShootWeapons.add(new ShootWeapon(14,5, 2));
        listOfShootWeapons.add(new ShootWeapon(15,6, 3));
        listOfShootWeapons.add(new ShootWeapon(16,6, 2));
        listOfShootWeapons.add(new ShootWeapon(17,6, 5));
        listOfShootWeapons.add(new ShootWeapon(18,6, 6));
        listOfShootWeapons.add(new ShootWeapon(19,6, 8));
        saveListOfShootWeapon(listOfShootWeapons);
    }
}
