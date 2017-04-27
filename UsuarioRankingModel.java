public class UsuarioRankingModel {
    private int posicao;
    private Usuario usuario;

    public UsuarioRankingModel(int posicao, Usuario usuario){
        this.posicao = posicao;
        this.usuario = usuario;
    }

    @Override
    public String toString(){        
        String n = StringExtensions.padRight(this.usuario.getNome(), 12);
        String v = StringExtensions.padRight(this.usuario.getPartidasGanhas() + "", 3);
        String d = StringExtensions.padRight(this.usuario.getPartidasPerdidas() + "", 3);

        String strUsuario = String.format("%s Vitórias: %s Derrotas: %s", n, v, d);
        String strPosicao = StringExtensions.padRight(posicao + ".", 4);

        return String.format("%s %s", strPosicao, strUsuario);
    } 
}