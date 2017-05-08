import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.ListCellRenderer;
import javax.swing.JList;

public class CellRenderer extends JLabel implements ListCellRenderer<Object> {

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
         setFont(new Font("Courier", Font.PLAIN, 14));
         setOpaque(true);
         return this;
     }
 }