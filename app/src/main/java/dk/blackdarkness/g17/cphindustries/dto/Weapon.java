package dk.blackdarkness.g17.cphindustries.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awo on 29/11/2017.
 */

public class Weapon extends Item {
    private List<String> warnings;
    private FireMode fireMode;
    private ConnectionStatus connectionStatus;

    public Weapon(int id, String name, List<String> warnings, FireMode fireMode, ConnectionStatus connectionStatus) {
        super(id, name);
        this.warnings = warnings;
        this.fireMode = fireMode;
        this.connectionStatus = connectionStatus;
    }

    public Weapon(int id, String name, FireMode fireMode, ConnectionStatus connectionStatus) {
        super(id, name);
        this.fireMode = fireMode;
        this.connectionStatus = connectionStatus;

        this.warnings = new ArrayList<>();
    }

    /**
     * Probably the default constructor when creating new weapons. FireMode will always be in SAFE mode initially.
     * @param id
     * @param name
     * @param connectionStatus
     */
    public Weapon(int id, String name, ConnectionStatus connectionStatus) {
        super(id, name);
        this.connectionStatus = connectionStatus;

        this.fireMode = FireMode.SAFE;
    }

    public Weapon(int id, String name) {
        super(id, name);

        this.connectionStatus = ConnectionStatus.NO_CONNECTION;
        this.fireMode = FireMode.SAFE;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public FireMode getFireMode() {
        return fireMode;
    }

    public void setFireMode(FireMode fireMode) {
        this.fireMode = fireMode;
    }

    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
    }
}
