import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.ListCellRenderer;
import javax.swing.JList;

public class CellRenderer extends JLabel implements ListCellRenderer<Object> {

     // This is the only method defined by ListCellRenderer.
     // We just reconfigure the JLabel each time we're called.

     public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
         String s = value.toString();
         setText(s);
         
         if (isSelected) {
             setBackground(list.getSelectionBackground());
             setForeground(list.getSelectionForeground());
         } else {
             setBackground(list.getBackground());
             setForeground(list.getForeground());
         }

         setEnabled(list.isEnabled());
         setFont(new Font("Consolas", Font.PLAIN, 14));
         setOpaque(true);
         return this;
     }
 }