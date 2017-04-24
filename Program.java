import javax.swing.SwingUtilities;

public class Program {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                criarMostrarGui();
            }
	    }); 
    }

    public static void criarMostrarGui(){
        FormPrincipal f = new FormPrincipal();
        f.setVisible(true);
    }
} 
