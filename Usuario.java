public class Usuario {
    private String nome;
    private int partidasGanhas;
    private int partidasPerdidas;

    public Usuario(String nome){
        this.nome = nome;
    }

    public Usuario(String nome, int partidasGanhas, int partidasPerdidas){
        this.nome = nome;
        this.partidasGanhas = partidasGanhas;
        this.partidasPerdidas = partidasPerdidas;
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

    public void setPartidasPerdidas(int partidasPerdidas){
    	this.partidasPerdidas = partidasPerdidas;
    }
    
    public int getPartidasPerdidas(){
    	return partidasPerdidas;
    }
}
