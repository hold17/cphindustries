package dk.blackdarkness.g17.cphindustries.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Weapon extends Item {
    private List<String> warnings;
    private FireMode fireMode;
    private ConnectionStatus connectionStatus;
    private String ip;
    private String mac;
    private int batteryLevel;

    public Weapon() {
    }

    /**
     * Probably the default constructor when creating new weapons. FireMode will always be in SAFE mode initially.
     *
     * @param name
     * @param warnings
     * @param fireMode
     * @param connectionStatus
     * @param ip
     * @param mac
     */
    public Weapon(String name, List<String> warnings, FireMode fireMode, ConnectionStatus connectionStatus, String ip, String mac) {
        super(name);
        this.warnings = warnings;
        this.fireMode = fireMode;
        this.connectionStatus = connectionStatus;
        this.setIp(ip);
        this.setMac(mac);
    }

    /**
     * Probably the default constructor when creating new weapons. FireMode will always be in SAFE mode initially.
     *
     * @param id
     * @param name
     * @param warnings
     * @param fireMode
     * @param connectionStatus
     * @param ip
     * @param mac
     */
    public Weapon(int id, String name, List<String> warnings, FireMode fireMode, ConnectionStatus connectionStatus, String ip, String mac, int batteryLevel) {
        super(id, name);
        this.warnings = warnings;
        this.fireMode = fireMode;
        this.connectionStatus = connectionStatus;
        this.setIp(ip);
        this.setMac(mac);
        this.setBatteryLevel(batteryLevel);
    }

    /**
     * Probably the default constructor when creating new weapons. FireMode will always be in SAFE mode initially.
     *
     * @param id
     * @param name
     * @param fireMode
     * @param connectionStatus
     * @param ip
     * @param mac
     */
    public Weapon(int id, String name, FireMode fireMode, ConnectionStatus connectionStatus, String ip, String mac, int batteryLevel) {
        super(id, name);
        this.warnings = new ArrayList<>();
        this.fireMode = fireMode;
        this.connectionStatus = connectionStatus;
        this.setIp(ip);
        this.setMac(mac);
        this.setBatteryLevel(batteryLevel);
    }

    /**
     * Probably the default constructor when creating new weapons. FireMode will always be in SAFE mode initially.
     *
     * @param id
     * @param name
     * @param connectionStatus
     * @param ip
     * @param mac
     */
    public Weapon(int id, String name, ConnectionStatus connectionStatus, String ip, String mac) {
        super(id, name);
        this.warnings = new ArrayList<>();
        this.fireMode = FireMode.SAFE;
        this.connectionStatus = connectionStatus;
        this.setIp(ip);
        this.setMac(mac);
    }

    /**
     * Probably the default constructor when creating new weapons. FireMode will always be in SAFE mode initially.
     *
     * @param id
     * @param name
     * @param ip
     * @param mac
     */
    public Weapon(int id, String name, String ip, String mac) {
        super(id, name);
        this.warnings = new ArrayList<>();
        this.fireMode = FireMode.SAFE;
        this.connectionStatus = ConnectionStatus.NO_CONNECTION;
        this.setIp(ip);
        this.setMac(mac);
    }

    /**
     * This constructor is used when a weapon has not been added to anything yet (not configured)
     *
     * @param id
     * @param connectionStatus
     * @param ip
     * @param mac
     */
    public Weapon(int id, ConnectionStatus connectionStatus, String ip, String mac) {
        super(id, null);
        this.warnings = new ArrayList<>();
        this.fireMode = FireMode.SAFE;
        this.connectionStatus = connectionStatus;
        this.setIp(ip);
        this.setMac(mac);
    }

    public List<String> getWarnings() {
        final String errorMsg = "No connection to the device."; // TODO: Put this in "strings.xml"

        if (!this.warnings.contains(errorMsg) && this.connectionStatus == ConnectionStatus.NO_CONNECTION) {
            this.warnings.add(errorMsg);
        }

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

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        if (batteryLevel > 100) this.batteryLevel = 100;
        if (batteryLevel < 0)   this.batteryLevel = 0;
        else                    this.batteryLevel = batteryLevel;
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
        if (ip == null) {
            throw new NullPointerException("IP has not yet been initialized.");
        }

        final String ipv4Regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

        final Pattern pattern = Pattern.compile(ipv4Regex);
        final Matcher matcher = pattern.matcher(ip);

        return matcher.matches();
    }

    private static boolean verifyMac(String mac) {
        if (mac == null) {
            throw new NullPointerException("MAC has not yet been initialized.");
        }
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
            throw new NullPointerException("Failed to parse MAC");
        }
    }
}
