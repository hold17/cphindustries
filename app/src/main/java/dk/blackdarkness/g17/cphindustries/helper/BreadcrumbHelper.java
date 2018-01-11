package dk.blackdarkness.g17.cphindustries.helper;

import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;

public class BreadcrumbHelper {
    private static String separator = ", ";

    public static String getSubtitle(Scene scene, Shoot shoot) {
        return scene.getName() + separator + shoot.getName();
    }

    public static String getSubtitle(Scene scene) {
        return scene.getName();
    }
}
