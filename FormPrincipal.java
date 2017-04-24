import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Component;

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
    private Map<String, Coordenadas> mapamentoTabuleiro = criarMapeamento();

    private int[][] tabuleiro;
    private Jogador[] jogadores;
    private int indexJogadorAtual = 0;

    private IRepositorio<Usuario, String> repositorio = new RepositorioUsuarios();

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
        novoJogo();
        bloquearTabuleiro();
    }

    private void setTabuleiro(){
        tabuleiro = new int[3][3];
    }

    private void novoJogo(){
        setTabuleiro();

        painelCentro.removeAll();
        painelCentro.repaint();
        painelCentro.revalidate();
        
        criarTabuleiro();
        painelCentro.repaint();
        painelCentro.revalidate();
        
        menuItemNovoJogo.setEnabled(false);    
        menuItemReiniciar.setEnabled(true);
    }

    private void finalizarPartida(Jogador vencedor){        
        bloquearTabuleiro();
        menuItemNovoJogo.setEnabled(true);        
        menuItemReiniciar.setEnabled(false);

        if(vencedor == null){
            JOptionPane.showMessageDialog(null, "Empate!", "Fim de jogo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String fraseVencedor = String.format("%s venceu!", vencedor.getUsuario().getNome());
        JOptionPane.showMessageDialog(null, fraseVencedor, "Fim de jogo", JOptionPane.INFORMATION_MESSAGE);

        vencedor.adicionarVitoria();

        for(Jogador jogador : jogadores){
            if(jogador != vencedor){
                jogador.adicionarDerrota();
            }
        }

        repositorio.atualizarOuCriar(jogadores[0].getUsuario());
        repositorio.atualizarOuCriar(jogadores[1].getUsuario());

        atualizarInfoJogadores();
    }

    private void bloquearTabuleiro(){
        trocarEstadoTabuleiro(true);
    }

    private void desbloquearTabuleiro(){
        trocarEstadoTabuleiro(false);
    }

    private void trocarEstadoTabuleiro(boolean bloquear){
        for(Component control : painelCentro.getComponents()){
            JLabel lb = (JLabel)control;
            lb.setEnabled(!bloquear);
        }
    }

    private void inicializarJogadores(){
        jogadores = new Jogador[2];

        String nomeUsuario1 = txtUsuario1.getText();
        String nomeUsuario2 = txtUsuario2.getText();

        Usuario usuario1 = repositorio.buscarPorChave(nomeUsuario1);
        Usuario usuario2 = repositorio.buscarPorChave(nomeUsuario2);

        usuario1 = usuario1 == null ? new Usuario(nomeUsuario1) : usuario1;
        usuario2 = usuario2 == null ? new Usuario(nomeUsuario2) : usuario2;

        jogadores[0] = new Jogador(usuario1, 3, "X");
        jogadores[1] = new Jogador(usuario2, 5, "O");        

        mostrarJogadorAtual();
        atualizarInfoJogadores(); 
    }

    private void atualizarInfoJogadores(){
        Usuario usuario1 = jogadores[0].getUsuario(), usuario2 = jogadores[1].getUsuario();

        lbInfoUsuario1.setText(String.format("%s ganhou %s partidas e perdeu %s", usuario1.getNome(), usuario1.getPartidasGanhas(), usuario1.getPartidasPerdidas()));
        lbInfoUsuario2.setText(String.format("%s ganhou %s partidas e perdeu %s", usuario2.getNome(), usuario2.getPartidasGanhas(), usuario2.getPartidasPerdidas()));
    }

    private void trocarJogadorAtual(){
        int prox = indexJogadorAtual == 0 ? 1 : 0;
        trocarJogadorAtual(prox);
    }

    private void trocarJogadorAtual(int indexProximo){
        indexJogadorAtual = indexProximo;
        mostrarJogadorAtual();
    }

    private void mostrarJogadorAtual(){
        lbJogadorAtual.setText(String.format("Vez de %s", jogadores[indexJogadorAtual].getUsuario().getNome()));
    }

    private Map<String, Coordenadas> criarMapeamento(){
        Map<String, Coordenadas> map = new HashMap<>();
        map.put("0", new Coordenadas(0, 0));
        map.put("1", new Coordenadas(0, 1));
        map.put("2", new Coordenadas(0, 2));
        map.put("3", new Coordenadas(1, 0));
        map.put("4", new Coordenadas(1, 1));
        map.put("5", new Coordenadas(1, 2));
        map.put("6", new Coordenadas(2, 0));
        map.put("7", new Coordenadas(2, 1));
        map.put("8", new Coordenadas(2, 2));
        return map;
    }
    
    private void verificaGanhador(){
        int[] somasColunas = {0, 0, 0};
        int[] somasDiagonais = {0, 0}; // 0 é a principal, 1 é a secundária

        int m0 = jogadores[0].getMultiplicador() * 3;
        int m1 = jogadores[1].getMultiplicador() * 3;

        for(int linha = 0; linha < tabuleiro.length; linha++){
            int somaLinha = 0;
            for(int coluna = 0; coluna < tabuleiro[linha].length; coluna++){
                somaLinha += tabuleiro[linha][coluna];
                somasColunas[coluna] += tabuleiro[linha][coluna];

                if(linha == coluna){ // Diagonal principal
                    somasDiagonais[0] += tabuleiro[linha][coluna];
                }
                
                if(linha + coluna == tabuleiro.length - 1){
                    somasDiagonais[1] += tabuleiro[linha][coluna];
                }

                if(somaLinha == m0|| somasColunas[coluna] == m0 || somasDiagonais[0] == m0 || somasDiagonais[1] == m0){
                    finalizarPartida(jogadores[0]);
                    return;
                }
                else if(somaLinha == m1 || somasColunas[coluna] == m1 || somasDiagonais[0] == m1 || somasDiagonais[1] == m1){
                    finalizarPartida(jogadores[1]);
                    return;
                }
                else if(casasDisponiveis() == 0){
                    finalizarPartida(null);
                    return;
                }
            }
        }
    }

    private int casasDisponiveis(){
        int qtdCasasVazias = 0;

        for(int l = 0; l < tabuleiro.length; l++){
            for(int c = 0; c < tabuleiro[l].length; c++){
                if(tabuleiro[l][c] == 0)
                    qtdCasasVazias += 1;
            }
        }

        return qtdCasasVazias;
    }

    /* ---- Criação da interface gráfica ---- */

    private void initComponents() {
        setTitle("Jogo da Velha");

        barraMenuPrincipal = new JMenuBar();
        
        menuJogo = new JMenu("Jogo");// menu externo
        
        menuItemReiniciar = new JMenuItem("Reiniciar jogo atual");
        menuItemReiniciar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(JOptionPane.showConfirmDialog(painelHeader, "Deseja abandonar a partida?\nO estado atual não será salvo.", "Reiniciar", JOptionPane.YES_NO_OPTION) == 0){
                    novoJogo();                    
                    trocarJogadorAtual(0);
                }                
            }
        });

        menuItemSair = new JMenuItem("Sair");// menu interno
        menuItemSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(painelHeader, "Deseja Realmente sair?", "Sair do jogo", JOptionPane.YES_NO_OPTION) == 0){
                    System.exit(0);
                }
            }
        });

        menuItemNovoJogo = new JMenuItem("Jogar novamente");      
        menuItemNovoJogo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                novoJogo();
            }
        });
        
        menuJogo.add(menuItemNovoJogo);
        menuJogo.add(menuItemReiniciar);
        menuJogo.add(menuItemSair);// add os items no menu
        barraMenuPrincipal.add(menuJogo);
        setJMenuBar(barraMenuPrincipal);

        BorderLayout borderLayout = new BorderLayout(0, 10);
        setLayout(borderLayout);

        criarPainelHeader();
        criarPainelCentro();
        criarPainelFooter();
    }

    private void criarTabuleiro(){
        for (int i = 0; i < 9; i++) {
            JLabel label = new JLabel();
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
                    
                    if(!label.isEnabled()){
                        return;
                    }

                    Coordenadas coordenadas =  mapamentoTabuleiro.get(label.getName());

                    tabuleiro[coordenadas.getX()][coordenadas.getY()] = jogadores[indexJogadorAtual].getMultiplicador();
                    label.setText(jogadores[indexJogadorAtual].getSimbolo());
                    trocarJogadorAtual();
                    label.removeMouseListener(this);
                    
                    // todo: Remover (Apenas para testes)
                    // --printTabuleiro();

                    verificaGanhador();
                }  
            });
            painelCentro.add(label);
        }
    }

    private void criarPainelCentro() {
        painelCentro = new JPanel(new GridLayout(3, 3));

        add(painelCentro, BorderLayout.CENTER);
    }

    private void criarPainelFooter() {
        GridLayout footerLayout = new GridLayout(2, 1);
        footerLayout.setHgap(550);

        painelFooter = new JPanel(footerLayout);        
        painelFooter.setPreferredSize(new Dimension(0, 100));
        painelFooter.setBackground(new Color(219, 219, 219));

        lbInfoUsuario1 = new JLabel("");
        lbInfoUsuario1.setFont(new Font("Verdana", 0, 20));
        lbInfoUsuario1.setHorizontalAlignment(SwingConstants.LEFT);
        painelFooter.add(lbInfoUsuario1);

        lbInfoUsuario2 = new JLabel("");
        lbInfoUsuario2.setFont(new Font("Verdana", 0, 20));
        lbInfoUsuario2.setHorizontalAlignment(SwingConstants.LEFT);
        painelFooter.add(lbInfoUsuario2);

        add(painelFooter, BorderLayout.PAGE_END);
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
                desbloquearTabuleiro();
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
    JMenu menuJogo;
    JMenuItem menuItemSair, menuItemNovoJogo, menuItemReiniciar;
    JLabel lbUsuario1, lbUsuario2, lbJogadorAtual, lbInfoUsuario1, lbInfoUsuario2;
    JTextField txtUsuario1, txtUsuario2;
    JButton btDefUsuarios;
}