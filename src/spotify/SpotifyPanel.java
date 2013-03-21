package spotify;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import jgoear.JGoear;
import lib.Song;
import lib.Spotify;
import lib.Utils;

public class SpotifyPanel extends JPanel implements Observer {

    private SpotifyTableModel tableModel;
    private JTable table;
    private JButton downloadButton, searchButton;
    private Spotify s;

    public SpotifyPanel() {
        initComponents();
    }

    private void initComponents() {
        setSize(640, 480);
        tableModel = new SpotifyTableModel();
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel downloadsPanel = new JPanel();
        downloadsPanel.setLayout(new BorderLayout());
        downloadsPanel.add(new JScrollPane(table),
                BorderLayout.CENTER);

        // Set up buttons panel.
        JPanel buttonsPanel = new JPanel();


        downloadButton = new JButton("Download");
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionDownload();
            }
        });



        buttonsPanel.add(downloadButton);



        searchButton = new JButton("Get Links From Clipboard");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionClipboard();
            }
        });
        buttonsPanel.add(searchButton);

        setLayout(new BorderLayout());
        add(downloadsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void actionDownload() {
        Song song = s.getSong(table.getSelectedRow());
        JGoear.getDownloadPanel().addDownload(song, song.getSongURL());

    }

    private void actionClipboard() {
        tableModel.clearSongs();
        String urls[] = Utils.getClipboardData().split(System.getProperty("line.separator"));
        s = new Spotify(urls, tableModel);
        Thread t = new Thread(s);
        t.start();

    }

    @Override
    public void update(Observable o, Object o1) {
    }
}
