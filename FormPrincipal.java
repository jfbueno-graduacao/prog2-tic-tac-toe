import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.border.Border;
import java.awt.Font;
import java.awt.GridBagLayout;

public class FormPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;

    public FormPrincipal() {
        super();
        initForm();
        initComponents();
    }

    private void initComponents() {
        barraMenuPrincipal = new JMenuBar();
        menuOperacoes = new JMenu("Operações");
        menuItemSair = new JMenuItem("Sair");
        menuItemSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuOperacoes.add(menuItemSair);
        barraMenuPrincipal.add(menuOperacoes);
        setJMenuBar(barraMenuPrincipal);

        BorderLayout borderLayout = new BorderLayout(0, 10);
        setLayout(borderLayout);

        criarPainelHeader();
        criarPainelCentro();
        criarPainelFooter();
        criarTabuleiro();
    }

    private void criarTabuleiro(){
        for (int i = 0; i < 9; i++) {
            JLabel label = new JLabel();
            label.setFont(new Font("Verdana", 0, 40));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            
            int leftBorder = (i % 3 == 0) ? 0 : 2;
            int topBorder = (i < 3) ? 0 : 2;
            int rightBorder = (i == 2 || i == 5 || i == 8) ? 0 : 2;
            int bottomBorder = (i > 5) ? 0 : 2;
            
            Border border = BorderFactory.createMatteBorder(topBorder, leftBorder, bottomBorder, rightBorder, Color.BLACK);
            
            label.setBorder(border);
            
            label.addMouseListener(new MouseAdapter() {  
                public void mouseClicked(MouseEvent e) {  
                   JLabel label = (JLabel) e.getSource();
                   label.setText("X");
                }  
            });
            
            painelCentro.add(label);
        }
    }

    private void criarPainelFooter() {
        painelFooter = new JPanel();
        painelFooter.setPreferredSize(new Dimension(0, 100));
        painelFooter.setBackground(Color.cyan);

        add(painelFooter, BorderLayout.PAGE_END);
    }

    private void criarPainelCentro() {
        painelCentro = new JPanel(new GridLayout(3, 3));

        add(painelCentro, BorderLayout.CENTER);
    }

    private void criarPainelHeader() {
        painelHeader = new JPanel(new GridBagLayout());
        painelHeader.setPreferredSize(new Dimension(0, 100));
        painelHeader.setBackground(Color.cyan);

        add(painelHeader, BorderLayout.PAGE_START);
    }

    private void initForm() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 720));
        pack();
        setLocationRelativeTo(null);
    }

    JPanel painelHeader, painelCentro, painelFooter;
    JMenuBar barraMenuPrincipal;
    JMenu menuOperacoes;
    JMenuItem menuItemSair;
}
