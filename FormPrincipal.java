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
    private IJogador[] jogadores;
    private int indexJogadorAtual = 0;

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
        setTitle("Jogo da Velha");
    }

    private void inicializarJogadores(){
        jogadores = new IJogador[2];

        jogadores[0] = new JogadorX(txtUsuario1.getText());
        jogadores[1] = new JogadorO(txtUsuario2.getText());   

        mostrarJogadorAtual();    
    }

    private void trocarJogadorAtual(){
        indexJogadorAtual = indexJogadorAtual == 0 ? 1 : 0;
        mostrarJogadorAtual();
    }

    private void mostrarJogadorAtual(){
        lbJogadorAtual.setText(String.format("Vez de %s", ((Jogador)jogadores[indexJogadorAtual]).getNome()));
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
        
        menuOperacoes = new JMenu("Operações");// menu externo
        JMenu menuHelp = new JMenu("Ajuda");
        menuHelp.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			//ação pro menu Ajuda
    		}
        });
        menuItemSair = new JMenuItem("Sair");// menu interno
        menuItemSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(null, "Deseja Realmente sair? ") == 0){
            		System.exit(0);// ação para sair
                }
            }
        });

        menuOperacoes.add(menuItemSair);// add os items no menu
        barraMenuPrincipal.add(menuOperacoes);
        barraMenuPrincipal.add(menuHelp);
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
            label.setFont(new Font("Verdana", 0, 50));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            
            final int borderWeight = 1;

            int leftBorder = (i % 3 == 0) ? 0 : borderWeight;
            int topBorder = (i < 3) ? 0 : borderWeight;
            int rightBorder = (i == 2 || i == 5 || i == 8) ? 0 : borderWeight;
            int bottomBorder = (i > 5) ? 0 : borderWeight;
            
            Border border = BorderFactory.createMatteBorder(topBorder, leftBorder, bottomBorder, rightBorder, Color.BLACK);
            label.setBorder(border);
            label.setName(String.valueOf(i));

            label.addMouseListener(new MouseAdapter() {  
                public void mouseClicked(MouseEvent e) {  
                   JLabel label = (JLabel) e.getSource();
                   int[] coordenadas = mapamentoTabuleiro.get(label.getName());

                   tabuleiro[coordenadas[0]][coordenadas[1]] = jogadores[indexJogadorAtual].getMultiplicador();
                   label.setText(jogadores[indexJogadorAtual].getSimbolo());
                   trocarJogadorAtual();
                   label.removeMouseListener(this);

                   // todo: Remover (Apenas para testes)
                   printTabuleiro();

                   verificaGanhador();
                }  
            });
            painelCentro.add(label);
        }
    }

    private void verificaGanhador(){
        int[] somasColunas = {0, 0, 0};
        int[] somasDiagonais = {0, 0}; // 0 é a principal, 1 é a secundária

    	for(int linha = 0; linha < tabuleiro.length; linha++){
            int somaLinha = 0;
            for(int coluna = 0; coluna < tabuleiro[linha].length; coluna++){
                somaLinha += tabuleiro[linha][coluna];
                somasColunas[coluna] += tabuleiro[linha][coluna];

                if(linha == coluna){ // Diagonal principal
                    somasDiagonais[0] += tabuleiro[linha][coluna];
                }else if(linha + coluna == tabuleiro.length){
                    somasDiagonais[1] += tabuleiro[linha][coluna];
                }

                if( somaLinha == 9 || somaLinha == 25 || 
                    somasColunas[coluna] == 9 || somasColunas[coluna] == 25 ||
                    somasDiagonais[0] == 9 || somasDiagonais[0] == 25 ||
                    somasDiagonais[1] == 9 || somasDiagonais[1] == 25)
                {
                    JOptionPane.showMessageDialog(null, "Temos um vencedor", "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private void criarPainelFooter() {
        painelFooter = new JPanel();
        painelFooter.setPreferredSize(new Dimension(0, 100));
        //painelFooter.setBackground(Color.GRAY);

        add(painelFooter, BorderLayout.PAGE_END);
    }

    private void criarPainelCentro() {
        painelCentro = new JPanel(new GridLayout(3, 3));

        add(painelCentro, BorderLayout.CENTER);
    }

    private void criarPainelHeader() {
        painelHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        painelHeader.setPreferredSize(new Dimension(0, 100));
        painelHeader.setBackground(new Color(219, 219, 219));

        lbUsuario1 = new JLabel("Jogador 1:");
        painelHeader.add(lbUsuario1);

        txtUsuario1 = new JTextField();
        txtUsuario1.setPreferredSize(new Dimension(110, 22));
        painelHeader.add(txtUsuario1);
       
        lbUsuario2 = new JLabel("Jogador 2:");
        painelHeader.add(lbUsuario2);

        txtUsuario2 = new JTextField();
        txtUsuario2.setPreferredSize(new Dimension(110, 22));
        painelHeader.add(txtUsuario2);

        btDefUsuarios = new JButton("Pronto");
        painelHeader.add(btDefUsuarios);

        lbJogadorAtual = new JLabel("");
        lbJogadorAtual.setFont(new Font("Verdana", 0, 20));
        lbJogadorAtual.setHorizontalAlignment(SwingConstants.CENTER);
        painelHeader.add(lbJogadorAtual);

        btDefUsuarios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                if(txtUsuario1.getText().trim().isEmpty() || txtUsuario2.getText().trim().isEmpty()){
                   JOptionPane.showMessageDialog(null, "Preencha corretamente os nomes dos jogadores", "Atenção", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                inicializarJogadores();
                btDefUsuarios.setEnabled(false);
                txtUsuario1.setEnabled(false);
                txtUsuario2.setEnabled(false);
            }
        });

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
    JLabel lbUsuario1, lbUsuario2, lbJogadorAtual;
    JTextField txtUsuario1, txtUsuario2;
    JButton btDefUsuarios;
}


