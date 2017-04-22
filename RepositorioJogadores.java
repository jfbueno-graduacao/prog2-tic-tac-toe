import java.util.List;
import java.util.ArrayList;

import java.nio.charset.StandardCharsets;
import java.nio.charset.Charset;
import java.nio.file.Files;

import java.io.IOException;
import java.io.File;

import java.util.regex.Pattern;

public class RepositorioJogadores implements IRepositorio<Jogador, String> {
    private static final File arquivo = new File("_jogadores.db");
    private static final String separador = "|";
    private static final Charset charset = StandardCharsets.UTF_8;

    private List<String> fonteDados;

    public RepositorioJogadores(){
        carregarDados();      
    }

    public Jogador buscarPorChave(String chave){
        for(String linha : fonteDados){
            String[] atributos = linha.split(Pattern.quote(separador));

            if(atributos == null || atributos.length < 1){
                continue;
            }

            if(atributos[0].equals(chave)){
                Jogador encontrado = deserializar(atributos);
                return encontrado;
            }
        }

        return null;
    }

    public Jogador[] buscarTodos(){
        List<Jogador> lista = new ArrayList<>();

        for(String linha : fonteDados){
            String[] atributos = linha.split(Pattern.quote(separador));

            if(atributos == null || atributos.length < 1){
                continue;
            }
            
            Jogador jogador = deserializar(atributos);
            lista.add(jogador);             
        }

        return lista.toArray(new Jogador[lista.size()]);
    }

    public void atualizarOuCriar(Jogador entidade) { 
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

    private String serializar(Jogador entidade){
        String str = entidade.getNome() + separador + 
                     entidade.getPartidasGanhas() + separador + 
                     entidade.getPartidasPerdidas();

        return str;
    }

    private Jogador deserializar(String[] atributos){
        Jogador retorno = new Jogador(atributos[0]);
        retorno.setPartidasGanhas(Integer.parseInt(atributos[1]));
        retorno.setPartidasPerdidas(Integer.parseInt(atributos[2]));

        return retorno;
    }
}