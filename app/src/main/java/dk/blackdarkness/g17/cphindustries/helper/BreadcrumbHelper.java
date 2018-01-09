package dk.blackdarkness.g17.cphindustries.helper;

import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;

/**
 * Created by Thoma on 01/09/2018.
 */

public class BreadcrumbHelper {
    private static String seperator = ", ";

    public static String getSubtitle(Scene scene, Shoot shoot) {
        return scene.getName() + seperator + shoot.getName();
    }

    public static String getSubtitle(Scene scene) {
        return scene.getName();
    }
}
