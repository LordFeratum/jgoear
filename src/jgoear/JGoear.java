package jgoear;

import about.AboutPanel;
import download.DownloadsPanel;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import lib.Utils;
import search.SearchPanel;
import settings.SettingsFile;
import settings.SettingsPanel;
import spotify.SpotifyPanel;

public class JGoear extends JFrame {

    private static SearchPanel searchPanel;
    private static SpotifyPanel spotifyPanel;
    private static DownloadsPanel downloadPanel;
    private static SettingsPanel settingsPanel;
    private static AboutPanel aboutPanel;
    private static JTabbedPane panel;
    

    public JGoear() {
        initComponents();
    }

    private void initComponents() {

        setTitle("jGoear");
        setSize(640, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
       
        
        panel = new JTabbedPane();

        searchPanel = new SearchPanel();
        spotifyPanel = new SpotifyPanel();
        downloadPanel = new DownloadsPanel();
        settingsPanel = new SettingsPanel();
        aboutPanel = new AboutPanel();
        
        panel.addTab("Search", searchPanel);
        panel.addTab("Spotify",spotifyPanel);
        panel.addTab("Download", downloadPanel);
        panel.addTab("Settings", settingsPanel);
        panel.addTab("About",aboutPanel);
        add(panel);
    }

    public static void main(String args[]) {
        
        if(Utils.isOSX()){
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        } catch (UnsupportedLookAndFeelException ex) {
        }

        new JGoear().setVisible(true);
        try {
            File f = new File(SettingsFile.SETTINGS_FILE);
            if(!f.exists()){
                f.createNewFile();
            }
        }catch(Exception e){ 
            
        } 
    }

    public static DownloadsPanel getDownloadPanel() {
        return downloadPanel;
    }

    public static SearchPanel getSearchPanel() {
        return searchPanel;
    }
}
