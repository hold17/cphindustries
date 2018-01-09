package dk.blackdarkness.g17.cphindustries.helper;

import java.util.ArrayList;
import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

/**
 * Created by awo on 09/01/2018.
 */

public class ItemConverter {
    public static List<Item> sceneToItem(List<Scene> scenes) {
        final List<Item> items = new ArrayList<>();

        items.addAll(scenes);

        return items;
    }

    public static List<Item> shootToItem(List<Shoot> shoots) {
        final List<Item> items = new ArrayList<>();

        items.addAll(shoots);

        return items;
    }

    public static List<Item> weaponToItem(List<Weapon> weapons) {
        final List<Item> items = new ArrayList<>();

        items.addAll(weapons);

        return items;
    }
}
