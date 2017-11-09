package dk.blackdarkness.g17.cphindustries;

/**
 * Created by awo on 03/11/2017.
 */

public class NavListItem {
    private boolean warningIsVisible;
    private boolean goBtnIsVisible;

    private String text;

    private ConnectionStatus connectionStatus;
    private FireMode fireMode;

    public NavListItem(boolean warningIsVisible, String text) {
        this.warningIsVisible = warningIsVisible;
        this.goBtnIsVisible = true;
        this.connectionStatus = null;
        this.text = text;
    }

    public NavListItem(boolean warningIsVisible, String text, ConnectionStatus connectionStatus) {
        this.warningIsVisible = warningIsVisible;
        this.goBtnIsVisible = false;
        this.connectionStatus = connectionStatus;
        this.text = text;
    }

    public NavListItem(boolean warningIsVisible, String text, FireMode fireMode, ConnectionStatus connectionStatus) {
        this(warningIsVisible, text, connectionStatus);
        this.fireMode = fireMode;
    }

    public boolean warningIsVisible() { return this.warningIsVisible; }
    public boolean goBtnIsVisible() { return this.goBtnIsVisible; }
    public FireMode getFireMode() { return this.fireMode; }
    public ConnectionStatus getConnectionStatus() { return this.connectionStatus; }
    public String getText() { return this.text; }
}
