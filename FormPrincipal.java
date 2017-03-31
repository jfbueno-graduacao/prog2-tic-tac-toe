import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.border.Border;
import java.awt.Font;

public class FormPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;

	public FormPrincipal(){
        super();
        initForm();
        initComponents();
    }

    private void initComponents(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File"); 
        JMenuItem menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuFile.add(menuItem);
        menuBar.add(menuFile);
        super.setJMenuBar(menuBar);

        GridLayout gridLayout = new GridLayout(3, 3);
        setLayout(gridLayout);

        for(int i = 0; i < 9; i++){
            JLabel label = new JLabel(i % 2 == 0 ? "X" : "O");
            label.setFont(new Font("Verdana", 0, 40));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            Border border = BorderFactory.createMatteBorder(0, 0, 2, 2, Color.BLACK);
            label.setBorder(border);
            this.add(label);
        }
    }

    private void initForm(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setPreferredSize(new Dimension(500, 500));
        pack();
        setLocationRelativeTo(null);
    }
}
