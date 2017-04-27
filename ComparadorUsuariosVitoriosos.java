public class ComparadorUsuariosVitoriosos implements IComparador<Usuario> {
    public boolean comparar(Usuario a, Usuario b) {
        return a.getPartidasGanhas() < b.getPartidasGanhas() || 
        (a.getPartidasGanhas() == b.getPartidasGanhas() && a.getPartidasPerdidas() > b.getPartidasPerdidas());
    }
}