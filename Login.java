import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
  
class Login extends JFrame implements ActionListener
 {
	public void close(){
		WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
	}
	
  JButton SUBMIT;
  JPanel panel;
  JLabel label1,label2;
  final JTextField  text1,text2;
   Login()
   {
   label1 = new JLabel();
   label1.setText("Username:");
   text1 = new JTextField(15);
 
   label2 = new JLabel();
   label2.setText("Password:");
   text2 = new JPasswordField(15);
  
   SUBMIT=new JButton("SUBMIT");
   
   panel=new JPanel(new GridLayout(3,1));
   panel.add(label1);
   panel.add(text1);
   panel.add(label2);
   panel.add(text2);
   panel.add(SUBMIT);
   add(panel,BorderLayout.CENTER);
   SUBMIT.addActionListener(this);
   setTitle("LOGIN FORM");
   
   }
  public void actionPerformed(java.awt.event.ActionEvent ae)
   {
	   // closing the Login Window after successfully entering the right details
   String value1=text1.getText();
   String value2=text2.getText();
   try
   {
   if ((value1.equals("niyi") && value2.equals("niyi"))||(value1.equals("suraj") && value2.equals("suraj")) || (value1.equals("oyem")&& value2.equals("oyem"))) {
   GrocerySystem page=new GrocerySystem();
   page.setVisible(true);
   
   JLabel label = new JLabel("Welcome:"+value1);
   page.getContentPane().add(label);
  JOptionPane.showMessageDialog(null, "Welcome "  +value1 );
  close();
   }
   else{
	   
   JOptionPane.showMessageDialog(this,"Incorrect login or password",
   "Error",JOptionPane.ERROR_MESSAGE);
   }
 }

catch(Exception e){
JOptionPane.showMessageDialog(null, "Pls Check your Details");
}
 }
 }
