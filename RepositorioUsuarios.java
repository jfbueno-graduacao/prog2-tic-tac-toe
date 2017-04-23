import java.util.List;
import java.util.ArrayList;

import java.nio.charset.StandardCharsets;
import java.nio.charset.Charset;
import java.nio.file.Files;

import java.io.IOException;
import java.io.File;

import java.util.regex.Pattern;

public class RepositorioUsuarios implements IRepositorio<Usuario, String> {
    private static final File arquivo = new File("usuarios.db");
    private static final String separador = "|";
    private static final Charset charset = StandardCharsets.UTF_8;

    private List<String> fonteDados;

    public RepositorioUsuarios(){
        carregarDados();      
    }

    public Usuario buscarPorChave(String chave){
        for(String linha : fonteDados){
            String[] atributos = linha.split(Pattern.quote(separador));

            if(atributos == null || atributos.length < 1){
                continue;
            }

            if(atributos[0].equals(chave)){
                Usuario encontrado = deserializar(atributos);
                return encontrado;
            }
        }

        return null;
    }

    public Usuario[] buscarTodos(){
        List<Usuario> lista = new ArrayList<>();

        for(String linha : fonteDados){
            String[] atributos = linha.split(Pattern.quote(separador));

            if(atributos == null || atributos.length < 1){
                continue;
            }
            
            Usuario usuario = deserializar(atributos);
            lista.add(usuario);             
        }

        return lista.toArray(new Usuario[lista.size()]);
    }

    public void atualizarOuCriar(Usuario entidade) { 
        if(entidade == null){
            //Lançar exceção
            return;
        }

        String strEntidade = serializar(entidade);

        for (int i = 0; i < fonteDados.size(); i++) {
            String[] atributos = fonteDados.get(i).split(Pattern.quote(separador));

            if (entidade.getNome().equals(atributos[0])) {
                fonteDados.set(i, strEntidade);
                salvar();
                return;
            }
        }

        fonteDados.add(serializar(entidade));
        salvar();
    }

    public void deletar(String chave) { 

    }

    private void carregarDados(){        
        try {            
            arquivo.createNewFile();
            fonteDados = new ArrayList<String>(Files.readAllLines(arquivo.toPath(), charset));
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }  
    }

    private void salvar(){
        try {
            Files.write(arquivo.toPath(), fonteDados, charset);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }  

        carregarDados();
    }

    private String serializar(Usuario entidade){
        String str = entidade.getNome() + separador + 
                     entidade.getPartidasGanhas() + separador + 
                     entidade.getPartidasPerdidas();

        return str;
    }

    private Usuario deserializar(String[] atributos){
        Usuario retorno = new Usuario(atributos[0]);
        retorno.setPartidasGanhas(Integer.parseInt(atributos[1]));
        retorno.setPartidasPerdidas(Integer.parseInt(atributos[2]));

        return retorno;
    }
}