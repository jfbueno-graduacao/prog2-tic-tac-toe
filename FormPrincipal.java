import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.util.HashMap;
import java.util.Map;

public class FormPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;
    private int[][] tabuleiro = new int[3][3];
    private Map<String, int[]> mapamentoTabuleiro = criarMapeamento();

    //Apenas para teste
    private boolean jogador = false;

    //Apenas para teste
    private void printTabuleiro(){
        for(int l = 0; l < tabuleiro.length; l++){
            for(int c = 0; c < tabuleiro[l].length; c++){
                System.out.print(tabuleiro[l][c] + "   ");
            }

            System.out.print("\n");
        }
    }

    public FormPrincipal() {
        super();
        initForm();
        initComponents();
    }

    private Map<String, int[]> criarMapeamento(){
        Map<String, int[]> map = new HashMap<>();
        map.put("0", new int[] {0, 0});
        map.put("1", new int[] {0, 1});
        map.put("2", new int[] {0, 2});
        map.put("3", new int[] {1, 0});
        map.put("4", new int[] {1, 1});
        map.put("5", new int[] {1, 2});
        map.put("6", new int[] {2, 0});
        map.put("7", new int[] {2, 1});
        map.put("8", new int[] {2, 2});
        return map;
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
            label.setName(String.valueOf(i));

            label.addMouseListener(new MouseAdapter() {  
                public void mouseClicked(MouseEvent e) {  
                   JLabel label = (JLabel) e.getSource();
                   int[] coordenadas = mapamentoTabuleiro.get(label.getName());

                   // Validar o valor a ser colocado - vai depender de quem for o jogador
                   tabuleiro[coordenadas[0]][coordenadas[1]] = jogador ? 3 : 5;

                   //Apenas para testes
                   printTabuleiro();
                   label.setText(jogador ? "O" : "X");

                   jogador = !jogador;
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
        painelHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelHeader.setPreferredSize(new Dimension(0, 60));
        painelHeader.setBackground(Color.cyan);

        lbUsuario1 = new JLabel("Jogador 1:");
        painelHeader.add(lbUsuario1);

        txtUsuario1 = new JTextField();
        txtUsuario1.setPreferredSize(new Dimension(120, 22));
        painelHeader.add(txtUsuario1);
       
        lbUsuario2 = new JLabel("Jogador 2:");
        painelHeader.add(lbUsuario2);

        txtUsuario2 = new JTextField();
        txtUsuario2.setPreferredSize(new Dimension(120, 22));
        painelHeader.add(txtUsuario2);

        btDefUsuarios = new JButton("Pronto");
        painelHeader.add(btDefUsuarios);

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
    JLabel lbUsuario1, lbUsuario2;
    JTextField txtUsuario1, txtUsuario2;
    JButton btDefUsuarios;
}
