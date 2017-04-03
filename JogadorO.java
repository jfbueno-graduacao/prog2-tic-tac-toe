public class JogadorO extends Jogador implements IJogador {
    public JogadorO(String nome){
        super(nome);
    }

    public int getMultiplicador(){
        return 5;
    }

    public String getSimbolo(){
        return "O";
    }
}