public class Player {
    private Jogador jogador;
    private int multiplicador;
    private String simbolo;

    public Player(Jogador from, int multiplicador, String simbolo){
        jogador = from;
        this.multiplicador = multiplicador;
        this.simbolo = simbolo;
    }

    public Jogador getJogador(){
        return jogador;
    }

    public int getMultiplicador() {
        return this.multiplicador;
    }

    public String getSimbolo() {
        return this.simbolo;
    }    

    public void adicionarVitoria(){
        int n = jogador.getPartidasGanhas() + 1;
        jogador.setPartidasGanhas(n);
    }

    public void adicionarDerrota(){
        int n = jogador.getPartidasPerdidas() + 1;
        jogador.setPartidasPerdidas(n);
    }
}