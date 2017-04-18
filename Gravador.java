import java.io.IOException;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Gravador {
    private static File arquivo = new File("arquivo.db");

    //public static void 

    public static void gravar(Jogador jogador) {
                
        try (FileWriter fw = new FileWriter (arquivo, true); 
            PrintWriter pw = new PrintWriter (fw);)
        {   
            System.out.println(jogador.getNome());
            pw.println(jogador.getNome());                        
        }
        catch (IOException e)
        {
            System.out.println (e.getMessage());
        }
    }
}