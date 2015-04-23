import javax.swing.JFrame;
import javax.swing.JOptionPane;


class LoginDemo
 {
   public static void main(String arg[])
   {
	   Login frame=new Login();
	   frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //disposing JFrame on close
   try
   {
	   
   
   frame.setSize(500,200);
   frame.setVisible(true);
   }
   catch(Exception e)
   {
	   JOptionPane.showMessageDialog(null, e.getMessage());}
   }
 }
 
