package dk.blackdarkness.g17.cphindustries.helper;

public enum FragmentType {
    SCENES, SHOOTS, WEAPONS;

    public String getTitle() {
        switch (this) {
            case SCENES: return "Scenes";
            case SHOOTS: return "Shoots";
            case WEAPONS: return "Weapons";
            default: return "Easter Egg";
        }
    }
}
