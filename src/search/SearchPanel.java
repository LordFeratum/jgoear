package search;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import jgoear.JGoear;
import lib.Goear;
import lib.Song;

public class SearchPanel extends JPanel implements Observer {

    private SearchTableModel tableModel;
    private JTable table;
    private JButton downloadButton, searchButton;
    private final static String SEARCH_QUERY = "Search Query...";
    private JTextField searchBox;
    

    public SearchPanel() {
        initComponents();
    }

    private void initComponents() {
        setSize(640, 480);
        tableModel = new SearchTableModel();
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel downloadsPanel = new JPanel();
        downloadsPanel.setLayout(new BorderLayout());
        downloadsPanel.add(new JScrollPane(table),
                BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();


        downloadButton = new JButton("Download");
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionDownload();
            }
        });



        buttonsPanel.add(downloadButton);


        searchBox = new JTextField(30);
        searchBox.setText(SEARCH_QUERY);
        searchBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionSearch();
            }
        });

        searchBox.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                gainedSearch();
            }

            @Override
            public void focusLost(FocusEvent fe) {
                lostSearch();
            }
        });

        buttonsPanel.add(searchBox);




        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionSearch();
            }
        });
        buttonsPanel.add(searchButton);

        setLayout(new BorderLayout());
        add(downloadsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void actionSearch() {
        if (tableModel.getRowCount() > 0) {
            clearList();
        }
        if (!searchBox.getText().equals("") && !searchBox.getText().equals(SEARCH_QUERY)) {
            Goear s = new Goear(searchBox.getText(), tableModel);
            Thread t = new Thread(s);
            t.start();
            

        }
    }

    private void clearList() {
        tableModel.clearSongs();
    }

    private void actionDownload() {
        Song s = (Song) tableModel.getSong(table.getSelectedRow());
        JGoear.getDownloadPanel().addDownload(s, s.getSongURL());
    }

    private void gainedSearch() {
        if(searchBox.getText().equals(SEARCH_QUERY)){
            searchBox.setText("");
        }
    }

    private void lostSearch() {
        if (searchBox.getText().equals("")) {
            searchBox.setText(SEARCH_QUERY);
        }
    }

    @Override
    public void update(Observable o, Object o1) {
    }
}
