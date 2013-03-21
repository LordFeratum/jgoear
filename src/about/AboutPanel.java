
package about;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author joanfont
 */
public class AboutPanel extends JPanel {
    
    private JTextArea jta;
    
    public AboutPanel(){
        initComponents();
    }
    
    private void initComponents(){
        jta = new JTextArea();
        jta.setEditable(false);
        jta.setColumns(20);
        jta.setLineWrap(true);
        jta.setRows(5);
        jta.setText("jGoear 1.0.2 \nThis soft has not relation with GoEar and Spotify");
        JScrollPane jp = new JScrollPane();
        jp.setViewportView(jta);
        
        setLayout(new BorderLayout());
        add(jp,BorderLayout.CENTER);
    }
    
    
}
