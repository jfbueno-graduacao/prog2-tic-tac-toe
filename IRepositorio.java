public interface IRepositorio <TEntidade, TChave> {    
    TEntidade buscarPorChave(TChave chave);
    TEntidade[] buscarTodos();

    void atualizarOuCriar(TEntidade entidade);
    void deletar(TChave chave);
}