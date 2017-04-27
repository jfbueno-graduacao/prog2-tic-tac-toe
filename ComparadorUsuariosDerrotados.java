public class ComparadorUsuariosDerrotados implements IComparador<Usuario> {
    public boolean comparar(Usuario a, Usuario b) {
        return (a.getPartidasPerdidas() > b.getPartidasPerdidas() ||    
          (a.getPartidasPerdidas() == b.getPartidasPerdidas() && a.getPartidasGanhas() < b.getPartidasGanhas()));
    }
}