package dk.blackdarkness.g17.cphindustries.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by awo on 29/11/2017.
 */

public class Weapon extends Item {
    private List<String> warnings;
    private FireMode fireMode;
    private ConnectionStatus connectionStatus;
    private String ip;
    private String mac;

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
        this.warnings = new ArrayList<>();
    }

    public Weapon(int id, String name) {
        super(id, name);

        this.connectionStatus = ConnectionStatus.NO_CONNECTION;
        this.fireMode = FireMode.SAFE;
        this.warnings = new ArrayList<>();
    }

    /**
     * This constructor is used when a weapon has not been added to anything yet (not configured)
     * @param id
     * @param connectionStatus
     * @param ip
     * @param mac
     */
    public Weapon(int id, ConnectionStatus connectionStatus, String ip, String mac) {
        super(id, null);
        this.connectionStatus = connectionStatus;
        this.setIp(ip);
        this.setMac(mac);

        this.fireMode = FireMode.SAFE;

        this.warnings = new ArrayList<>();
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        if (verifyIp(ip)) {
            this.ip = ip;
        } else {
            this.ip = null;
            throw new NullPointerException("Failed to parse IP");
        }
    }

    private static boolean verifyIp(String ip) {
        final String ipv4Regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

        final Pattern pattern = Pattern.compile(ipv4Regex);
        final Matcher matcher = pattern.matcher(ip);

        return matcher.matches();
    }

    private static boolean verifyMac(String mac) {
        final String macRegex = "([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";

        final Pattern pattern = Pattern.compile(macRegex);
        final Matcher matcher = pattern.matcher(mac);

        return matcher.matches();
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        if (verifyMac(mac)) {
            this.mac = mac;
        } else {
            this.mac = null;
            throw new NullPointerException("Failed to parse mac");
        }
    }
}
