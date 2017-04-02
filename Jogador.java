public abstract class Jogador {
    private String nome;
    private int partidasGanhas;
    public int partidasPerdidas;

    public Jogador(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPartidasGanhas() {
        return this.partidasGanhas;
    }

    public void setPartidasGanhas(int partidasGanhas) {
        this.partidasGanhas = partidasGanhas;
    }
}