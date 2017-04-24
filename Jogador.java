public class Jogador {
    private Usuario usuario;
    private int multiplicador;
    private String simbolo;

    public Jogador(Usuario usuario, int multiplicador, String simbolo){
        this.usuario = usuario;
        this.multiplicador = multiplicador;
        this.simbolo = simbolo;
    }

    public Usuario getUsuario(){
        return usuario;
    }

    public int getMultiplicador() {
        return this.multiplicador;
    }

    public String getSimbolo() {
        return this.simbolo;
    }    

    public void adicionarVitoria(){
        int n = usuario.getPartidasGanhas() + 1;
        usuario.setPartidasGanhas(n);
    }

    public void adicionarDerrota(){
        int n = usuario.getPartidasPerdidas() + 1;
        usuario.setPartidasPerdidas(n);
    }
}