package optionDialog;

import java.awt.*;
import javax.swing.*;

/** 
 * @version 1.0 2025-03-1
 * @author pile
 */
public class myproc
{
   public static void main(String[] args)
   {
      EventQueue.invokeLater(() -> {
         var frame = new myframe();
         frame.setTitle("扫雷");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setVisible(true);
      });
   }
}
