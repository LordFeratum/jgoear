
package settings;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;


public class SettingsFile {

    public final static String SETTINGS_FILE = "settings.properties";
    private final static String SAVE_PATH = "SAVE_PATH";
    private final static String TABLE_ITEMS = "TABLE_ITEMS";
    private Properties p;

    public SettingsFile() throws IOException {
        p = new Properties();
        p.load(new FileReader(SETTINGS_FILE));
    }

    public static void touch() {
        File f = new File(SETTINGS_FILE);
        try {
            f.createNewFile();
        } catch (IOException e) {
        }
    }

    public void setSavePath(String path) throws IOException {
        p.put(SAVE_PATH, path);
        p.store(new FileWriter(SETTINGS_FILE), "");
    }

    public String getSavePath() {
        return p.getProperty(SAVE_PATH);
    }

    public void setItems(String items) throws IOException {
        p.put(TABLE_ITEMS, items);
        p.store(new FileWriter(SETTINGS_FILE), "");
    }

    public String getItems() {
        return p.getProperty(TABLE_ITEMS);
    }
}
