public class JogadorX extends Jogador implements IJogador {
    public JogadorX(String nome){
        super(nome);
    }

    public int getMultiplicador(){
        return 3;
    }

    public String getSimbolo(){
        return "X";
    }
}