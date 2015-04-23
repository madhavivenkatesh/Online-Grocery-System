import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

/**
   ShoppingCartWindow class
   / Shopping Cart System
*/
public class GrocerySystem extends JFrame  {
	   private FileOutputStream fos;
	   private PrintStream ps;
	   private String[] itemTitles;     // To hold the item titles
	   private String[] boughtItemTitles;     // Titles in the shopping cart
	   private double[] prices;         // To hold the item prices
	   private double[] qty;
	   private Formatter x;
	   
	   
	   private JLabel banner;           // To display a banner
	   private JPanel bannerPanel;      // To hold the banner
	   private JPanel itemPanel;        // To hold the book list
	   private JPanel boughtItemPanel;        // To hold the shopping cart
	   private JPanel buttonPanel;      // To hold the buttons
	   private JPanel quantitypanel;
	   private JLabel quantitylabel;
	   private JTextField quantitytextfield;      // To hold quantity
	   
	   
	   private JList itemList;          // To show a list of items
	   private JList boughtItemList;          // Shopping cart list
	   
	   private JButton addButton;       // To add an item to the cart
	   private JButton removeButton;    // To remove an item from the cart
	   private JButton checkOutButton;  // To check out
	   private JButton paymentButton;
	   
	   private double subtotal = 0.0;   // Selection subtotal
	   private double tax = 0.0;        // Sales tax
	   private double total = 0.0;      // Sale total
	   private double AmountLeft = 0.0;
	   private double AmountPaid = 0.0;
	   
	   // Constants
	   private final int LIST_ROWS = 10; // Number of rows to display in lists
	   private final double TAX_RATE = 0.06;  // Sales tax rate
	  
	                   
	   /**
	      Constructor
	   */
	   
	   public GrocerySystem() throws IOException
	   {
		   setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); //disposing JFrame on close
	      // Display a title.
	      setTitle("Grocery Store Shopping System");
	      
	      // Specify what happens when the close button is clicked.
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	      // Read the items titles and prices from the file.
	      readItemFile();
	      
	      // Create the banner on a panel and add it to the North region.
	      buildBannerPanel();
	      add(bannerPanel, BorderLayout.NORTH);
	            
	      // Create a list component to show the books and add it
	      // to the West region.
	      builditemPanel();
	      add(itemPanel, BorderLayout.WEST);
	      
	      // Create a list component for the shopping cart and
	      // add it to the East region.
	      buildboughtItemPanel();
	      add(boughtItemPanel, BorderLayout.EAST);
	      
	      // Build the button panel and add it to the center region.
	      buildButtonPanel();
	      add(buttonPanel, BorderLayout.CENTER);
	      
	      buildquantityPanel();
	      add(quantitypanel, BorderLayout.AFTER_LAST_LINE);

	      // Pack and display the window.
	      pack();
	      setVisible(true);
	   }

	   /**
	      The readItemFile method reads the contents of the 
	      BookPrices.txt file and populates the itemTitles
	      and Prices arrays.
	      @exception IOException When an IO error occurs.
	   */
	   
	   private void readItemFile() throws IOException
	   {
	      String input;  // To hold a line from the file
	      int i = 0;     // To use as an array index
	      
	      // Get the number of items;
	      int numItems = countItems();
	      
	      // Create the arrays.
	      itemTitles = new String[numItems];
	      prices = new double[numItems];
	      qty = new double[numItems];
	      
	      
	      // Open the file.
	      File file = new File("ItemPrices.txt");
	      Scanner inFile = new Scanner(file);
	      
	      // Read the file.
	      while (inFile.hasNext())
	      {
	         // Read a line from the file.
	         input = inFile.nextLine();
	         
	         // Tokenize the line.
	         StringTokenizer strTok = new StringTokenizer(input, ",");
	         
	         // Get the item title.
	         itemTitles[i] = strTok.nextToken();
	         
	         // Get the item price.
	         prices[i] = Double.parseDouble(strTok.nextToken());
	         
	         //Get the item Quantity
	         qty[i] = Double.parseDouble(strTok.nextToken());
	        
	         // Update the index.
	         i++;
	      }
	      
	      // Close the file.
	      inFile.close();
	   }
	      
	   /**
	      The countItems method counts the number of books
	      in the BookPrices.txt file.
	      @return The number of books in the file.
	   */
	   
	   private int countItems() throws IOException
	   {
	      int count = 0;
	      
	      // Open the file.
	      FileReader fr = new FileReader("ItemPrices.txt");
	      BufferedReader inFile = new BufferedReader(fr);
	      
	      while (inFile.readLine() != null)
	         count++;
	      
	      inFile.close();
	      return count;
	   }

	   /**
	      The buildBannerPanel method builds a panel with a banner on it.
	   */
	      
	   private void buildBannerPanel()
	   {
	      bannerPanel = new JPanel();
	      bannerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	      banner = new JLabel("Welcome to the Grocery Store!");
	      banner.setFont(new Font("SanSerif", Font.BOLD, 24));
	      bannerPanel.add(banner);
	      
	      
	   }

	   /**
	      The builditemPanel method builds a panel with a list of
	      items on it.
	   */
	   
	   private void builditemPanel()
	   {
	      JLabel bookMsg = new JLabel("Available Items");
	      itemPanel = new JPanel();
	      itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	      itemList = new JList(itemTitles);
	      itemList.setVisibleRowCount(LIST_ROWS);
	      JScrollPane bookScrollPane = new JScrollPane(itemList);
	      itemPanel.setLayout(new BorderLayout());
	      itemPanel.add(bookMsg, BorderLayout.NORTH);
	      itemPanel.add(bookScrollPane, BorderLayout.CENTER);
	   }

	   /**
	      The buildboughtItemPanel method builds a panel to show a list of
	      selected items.
	   */
	      
	   private void buildboughtItemPanel()
	   {
	      JLabel cartMsg = new JLabel("Bought Items");
	      boughtItemPanel = new JPanel();
	      boughtItemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	      boughtItemList = new JList();
	      boughtItemList.setVisibleRowCount(LIST_ROWS);
	      JScrollPane cartScrollPane = new JScrollPane(boughtItemList);
	      boughtItemPanel.setLayout(new BorderLayout());
	      boughtItemPanel.add(cartMsg, BorderLayout.NORTH);
	      boughtItemPanel.add(cartScrollPane, BorderLayout.CENTER);
	      
	   }


	   /**
	      The buildButtonPanel method creates a panel containing
	      buttons.
	   */
	      
	   private void buildButtonPanel()
	   {
	      // Create a button to add items to the shopping cart.
	      addButton = new JButton("Add Selected Item");
	      
	      // Add an action listener to the button.
	      addButton.addActionListener(new AddButtonListener());
	      
	      // Create a button to remove an item from the shopping cart.
	      removeButton = new JButton("Remove Selected Item");
	      
	      // Add an action listener to the button.
	      removeButton.addActionListener(new RemoveButtonListener());

	      // Create a button to check out.
	      checkOutButton = new JButton("Check Out");
	      
	      // Add an action listener to the button.
	      checkOutButton.addActionListener(new CheckOutButtonListener());
	      
	      paymentButton = new JButton("Payment");
	      paymentButton.addActionListener(new PaymentButtonListener());

	      // Put the buttons in their own panel.
	      buttonPanel = new JPanel();
	      buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
	      buttonPanel.setLayout(new GridLayout(3, 1));
	      buttonPanel.add(addButton);
	      buttonPanel.add(removeButton);
	      buttonPanel.add(checkOutButton);
	      buttonPanel.add(paymentButton);
	   }
	   private void buildquantityPanel()
	   {
		   //create a label for quantity
	    quantitylabel = new JLabel("Amount Paid");
	    
	    
	    quantitytextfield = new JTextField(5);
	    
	    

	      // Put the buttons in their own panel.
	      quantitypanel = new JPanel();
	      quantitypanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
	      quantitypanel.setLayout(new GridLayout(3, 1));
	      quantitypanel.add(quantitylabel);
	      quantitypanel.add(quantitytextfield);
	      
	   }
	   
	   /**
	      The addToCart method adds a book to the shopping cart.
	      @param str The book info.
	 * @throws FileNotFoundException 
	   */
	      
	   private void addToCart(String str) throws FileNotFoundException
	   {
		  double stock=0, qtyy;
	      String[] temp;
	      String input;
	      
	      
	      if (boughtItemTitles == null)
	         temp = new String[1];
	      else
	      {
	         // Make a copy of the boughtItemTitles array.
	         temp = new String[boughtItemTitles.length + 1];
	         for (int i = 0; i < boughtItemTitles.length; i++)
	            temp[i] = boughtItemTitles[i];
	      }
	      
	      // Add the argument to the end of the temp array.
	      temp[temp.length - 1] = str;
	      
	      // Replace the boughtItemTitles array with the temp array.
	      boughtItemTitles = temp;
	      
	      // Update the boughtItemList component.
	      boughtItemList.setListData(boughtItemTitles);
	      
	      // Find the item's price and update the subtotal.
	      boolean found = false;
	      int index = 0;
	      while (!found && index < itemTitles.length)
	      {
	         if (str.equals(itemTitles[index]))
	         {
	        	 input = JOptionPane.showInputDialog("Enter the " + "quantity of item.");
		         qtyy = Double.parseDouble(input);
		         
		         if(qtyy>qty[index]){
		        	 JOptionPane.showMessageDialog(null, "Out of Stock......!");
		        	 continue;
		        	
		         }
		         stock = qty[index]-qtyy;		//To get the remaining stock at hand
		         if(stock<=5){
		        	 JOptionPane.showMessageDialog(null, "please try and update your stock");
		         }
	            subtotal += prices[index] * qtyy;
	            
	            fos = new FileOutputStream("Transaction.txt", true);	//storing the transaction
	            ps = new PrintStream(fos);
	            ps.println(itemTitles[index]+"," +qtyy+ "," +subtotal+ "," +stock);
	            try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            found = true;
	         }
	         index++;
	         
	      }
	   }
	   
	   


	   /**
	      The removeFromCart method removes a book from the shopping cart.
	      @param elementToDelete The element of the boughtItemTitles array
	             containing the element to delete.
	      @param str The title of the item to delete.
	   */
	      
	   private void removeFromCart(int elementToDelete, String str)
	   {
	      String[] temp = null;
	      
	      if (boughtItemTitles != null && boughtItemTitles.length > 0)
	      {
	         // Make temp 1 element smaller than boughtItemTitles.
	         temp = new String[boughtItemTitles.length - 1];
	         
	         // Copy the items to keep.
	         for (int tempIndex = 0, cartIndex = 0; tempIndex < temp.length; cartIndex++)
	         {
	            if (cartIndex != elementToDelete)
	            {
	               temp[tempIndex] = boughtItemTitles[cartIndex];
	               tempIndex++;
	            }
	         }
	      }
	      
	      // Replace the boughtItemTitles array with the temp array.
	      boughtItemTitles = temp;
	      
	      // Update the boughtItemList component.
	      boughtItemList.setListData(boughtItemTitles);
	      
	      // Use the book's title (str) to find the item's
	      // price and update the subtotal.
	      boolean found = false;
	      int index = 0;
	      while (!found && index < itemTitles.length)
	      {
	         if (str.equals(itemTitles[index]))
	         {
	            subtotal -= prices[index];
	            
	            found = true;
	         }
	         index++;
	      }
	   }

	   /**
	      AddButtonListener is an action listener class for the
	      addButton component.
	   */
	   
	   private class AddButtonListener implements ActionListener
	   {
	      /**
	         actionPerformed method
	         @param e An ActionEvent object.
	      */
	      
	      public void actionPerformed(ActionEvent e)
	      {
	         if (itemList.getSelectedValue() != null)
	         {
	            String selected = (String) itemList.getSelectedValue();
	            try {
					addToCart(selected);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	         }
	      }
	   } // End of inner class 

	   /**
	      RemoveButtonListener is an action listener class for the
	      removeButton component.
	   */
	   
	   private class RemoveButtonListener implements ActionListener
	   {
	      /**
	         actionPerformed method
	         @param e An ActionEvent object.
	      */
	            
	      public void actionPerformed(ActionEvent e)
	      {
	         int selectedIndex = boughtItemList.getSelectedIndex();
	         String str = (String) boughtItemList.getSelectedValue();
	         if (selectedIndex > -1)
	         {
	            removeFromCart(selectedIndex, str);
	         }
	      }
	   } // End of inner class 

	   /**
	      CheckOutButtonListener is an action listener class for the
	      checkOutButton component.
	   */
	   
	   private class CheckOutButtonListener implements ActionListener
	   {
		  
		   
		  
	      /**
	         actionPerformed method
	         @param e An ActionEvent object.
	      */
	            
	      public void actionPerformed(ActionEvent e)
	      {
	    	  
	         // Create a DecimalFormat object.
	         DecimalFormat dollar = new DecimalFormat("#,##0.00");
	         
	            try{
	         // Compute the sales tax and total.
	         tax = TAX_RATE * subtotal;
	         total = subtotal + tax;
	         
	         
	         
	         
	         
	         // Display the total.
	         JOptionPane.showMessageDialog(null, "Subtotal: £" +
	                                             dollar.format(subtotal) + 
	                                             "\nTax: £" + dollar.format(tax) +
	                                             "\nTotal: £" + dollar.format(total));
	         
	        
	         
	            }
	            catch(Exception error){
	            	JOptionPane.showMessageDialog(null, "Select an Item");
	            }
	            
	            
	          }

	      
	   } // End of inner class
	   
	   private class PaymentButtonListener implements ActionListener
	   {
		   String input;
		   
		   
	      /**
	         actionPerformed method
	         @param e An ActionEvent object.
	      */
	            
	      public void actionPerformed(ActionEvent e)
	      {
	    	  String filename = "sales.txt";
	         // Create a DecimalFormat object.
	         DecimalFormat dollar = new DecimalFormat("#,##0.00");
	         input = quantitytextfield.getText();
	         
	            try{
	         // Compute the sales tax and total.
	         
	         AmountPaid = Double.parseDouble(input);
	         AmountLeft = Double.parseDouble(input) - total ;
	         FileWriter fwriter = new FileWriter(filename, true);
	         PrintWriter outputFile = new PrintWriter(fwriter);
	         
	      // Print the report header.
	          outputFile.println("ITEM BOUGHT\tTOTAL\tCASH\tCHANGE");
	          outputFile.println("-----------------------------------" + "--------------");
	         
	         
	         // Display the total.
	         JOptionPane.showMessageDialog(null,  "\nAmount Paid: £" + dollar.format(AmountPaid) +
	                                             "\nAmount Left: £" + dollar.format(AmountLeft));
	         
	         
	         outputFile.println("\t" + dollar.format(total) + "\n\n" + dollar.format(AmountPaid) +  "\n\n" + dollar.format(AmountLeft));
	         outputFile.close();
	         //x.format("%s%s%s%s","TOTAL"," ", "CASH", " " +"CHANGE", " ");
	         //x.format("%s%s%s%s%s", " ",dollar.format(total)," ",  dollar.format(AmountPaid), " " +dollar.format(AmountLeft));
	         //x.close();
	            }
	            catch(Exception error){
	            	JOptionPane.showMessageDialog(null, "Please Input the Amount Paid");
	            }
	            
	         }
	         
	          
	   // Close the file.
	       
	      
	   } // End of inner class

	   /**
	      The main method creates an instance of the ShoppingCart
	      class, causing it to display its window.
	   */

	   public static void main(String[] args) throws IOException
	   {
	      GrocerySystem sc = new GrocerySystem();
	      
	      
	   }
	}
