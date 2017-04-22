import java.io.IOException;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Gravador {
    private static File arquivo = new File("arquivo.db");

    public static void update(Jogador jogador){
        List<String> fileContent = new ArrayList<>(Files.readAllLines(arquivo.toPath(), StandardCharsets.UTF_8));

        for(String linha : fileContent){
            System.out.println(linha);
        }
    }

    public static void gravar(Jogador jogador) {
                
        try (FileWriter fw = new FileWriter (arquivo, true); 
            PrintWriter pw = new PrintWriter (fw);)
        {   
            System.out.println(jogador.getString());
            pw.println(jogador.getString());                        
        }
        catch (IOException e)
        {
            System.out.println (e.getMessage());
        }
    }
}