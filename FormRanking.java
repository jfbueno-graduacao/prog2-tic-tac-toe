import java.awt.Dimension;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Image;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.FlowLayout;

public class FormRanking extends JFrame {

    private void ordenar(Usuario[] usuarios){
        boolean troca = true;

        while(troca){            
            troca = false;
            for(int i = 0; i < usuarios.length - 1; i++) {
                if(comparadorUsuarios.comparar(usuarios[i], usuarios[i + 1])                )
                {
                    Usuario aux = usuarios[i + 1];
                    usuarios[i + 1] = usuarios[i];
                    usuarios[i] = aux;
                    troca = true;
                }
            }
        }
    }

    private IRepositorio<Usuario, String> repositorio = new RepositorioUsuarios();
    private Usuario[] usuarios;
    private UsuarioRankingModel[] arrayRanking;
    private final Dimension tamanhoDataList = new Dimension(385, 450);
    private boolean ascending = true;
    private IComparador<Usuario> comparadorUsuarios;
    private TipoOrdenacaoRanking ordemRanking;

    public FormRanking(TipoOrdenacaoRanking ordem){
        super();

        ordemRanking = ordem;
        if(ordemRanking == TipoOrdenacaoRanking.VITORIAS)
            comparadorUsuarios = new ComparadorUsuariosVitoriosos();
        else
            comparadorUsuarios = new ComparadorUsuariosDerrotados();

        initForm();

        usuarios = repositorio.buscarTodos();
        ordenar(usuarios);

        arrayRanking = new UsuarioRankingModel[usuarios.length];
        for(int i = 0; i < usuarios.length; i++){
            arrayRanking[i] = new UsuarioRankingModel(i + 1, usuarios[i]);
        }

        initComponents();
    }

    private void inverterOrderRanking(){
        int fim = arrayRanking.length - 1;
        for(int i = 0; i < arrayRanking.length / 2; i++){
            UsuarioRankingModel aux = arrayRanking[i];
            arrayRanking[i] = arrayRanking[fim];
            arrayRanking[fim] = aux;
            fim--;
        }
    }

    private void initComponents(){
        setLayout(new FlowLayout(FlowLayout.LEFT));

        rankingList = new JList(new RankingListModel(arrayRanking));
        rankingList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        rankingList.setVisibleRowCount(-1);
        rankingList.setCellRenderer(new CellRenderer());

        JScrollPane scrollPane = new JScrollPane(rankingList);
        scrollPane.setBackground(new Color(255, 255, 255));
        scrollPane.setPreferredSize(tamanhoDataList);

        JButton btInverterOrdem = new JButton("Inverter ordem");
        btInverterOrdem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                inverterOrderRanking();
                RankingListModel m = (RankingListModel)rankingList.getModel();
                m.update();
                ascending = !ascending;
                alterarLabelOrdenacao();
            }
        });

        lbOrdemAtual = new JLabel();
        alterarLabelOrdenacao();

        add(scrollPane);
        add(btInverterOrdem);
        add(lbOrdemAtual);
    }

    private void alterarLabelOrdenacao() {
        String str = String.format("Ordenado por %s - %s", 
                                    (ordemRanking == TipoOrdenacaoRanking.VITORIAS ? "vitórias" : "(menos) derrotas"),
                                    (ascending ? "ascendente" : "descendente"));
        lbOrdemAtual.setText(str);
    }

    private void initForm() {
        String titulo = String.format("Ranking de jogadores por %s", (ordemRanking == TipoOrdenacaoRanking.VITORIAS ? "vitórias" : "(menos) derrotas"));
        setTitle(titulo);

        setResizable(false);        
        setPreferredSize(new Dimension((int)tamanhoDataList.getWidth() + 15, (int)tamanhoDataList.getHeight() + 75));
        pack();
        setLocationRelativeTo(null);
    }

    JList rankingList;
    JLabel lbOrdemAtual;
}