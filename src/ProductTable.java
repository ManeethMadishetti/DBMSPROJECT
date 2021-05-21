import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ProductTable {

	private JPanel p1;
	private JFrame frame;

	private JMenuItem miInsert1,miUpdate1,midelete1,miView1;
	private JLabel lblpid,lblpname,lbltype,lblcost,lblquantity;
	private JTextField txtpid,txtpname,txttype,txtcost,txtquantity;
	private JButton btn;
	private JTextArea txtmsg;
	//private static Statement stmt;
	private Connection connection;
	private Statement statement;
	public ProductTable(JPanel p1,JFrame frame,JMenuItem miInsert1,JMenuItem miUpdate1,JMenuItem midelete1,JMenuItem miView1) {
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} 
		catch (Exception e) 
		{
			System.err.println("Unable to find and load driver");
			System.exit(1);
		}
		connectToDB();
		this.frame=frame;
		this.p1=p1;
		this.miInsert1=miInsert1;
		this.midelete1=midelete1;
		this.miUpdate1=miUpdate1;
		this.miView1=miView1;
		txtpid=new JTextField(20);
		txtpname=new JTextField(20);
		txttype=new JTextField(20);
		txtcost=new JTextField(20);
		txtquantity=new JTextField(20);
		txtmsg=new JTextArea(8,50);
		lblpid=new JLabel("Product Id:");
		lblpname=new JLabel("Product Name:");
		lbltype=new JLabel("TYPE:");
		lblcost=new JLabel("Cost:");
		lblquantity=new JLabel("Quantity:");
		//queryHandler();
	}
	public void connectToDB() 
    {
		try 
		{
		  connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","mydbms","mydbms");
		  statement = connection.createStatement();

		} 
		catch (SQLException connectException) 
		{
		  System.out.println(connectException.getMessage());
		  System.out.println(connectException.getSQLState());
		  System.out.println(connectException.getErrorCode());
		  System.exit(1);
		}
    }
	public void regis_product() {
		miInsert1.addActionListener(new ActionListener() {	
			 public void actionPerformed(ActionEvent ae) {	
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				
				JPanel p=new JPanel();
				txtpid=new JTextField(20);
				txtpname=new JTextField(20);
				txttype=new JTextField(20);
				txtcost=new JTextField(20);
				txtquantity=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				//a grid of lbl and txtfield
				p.add(lblpid);
				p.add(txtpid);
				p.add(lblpname);
				p.add(txtpname);
				p.add(lbltype);
				p.add(txttype);
				p.add(lblcost);
				p.add(txtcost);
				p.add(lblquantity);
				p.add(txtquantity);
				p.setLayout(new GridLayout(5,2));
				
				p1.add(p);
				p1.add(btn);
				p1.add(txtmsg);//msg text area added to panel
				btn.setText("INSERT");
				p1.setLayout(new FlowLayout());
				frame.add(p1,BorderLayout.CENTER);
				frame.validate();
				
				//register listener
				btn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try {
							Statement statement = connection.createStatement();
							String query= "INSERT INTO product VALUES(" + txtpid.getText() + ", " + "'" + txtpname.getText() + "'," + "'" + txttype.getText() + "'," + txtcost.getText() + ","+ txtquantity.getText()+")";
							int i = statement.executeUpdate(query);
								txtmsg.append("\nInserted " + i + " rows successfully");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
						//	e1.printStackTrace();
							txtmsg.append(e1.getMessage());
						}  
					}
				});
				
			}
		});
		
		miUpdate1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtpid=new JTextField(20);
				txtpname=new JTextField(20);
				txttype=new JTextField(20);
				txtcost=new JTextField(20);
				txtquantity=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				txtmsg.setEditable(false);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT pid FROM product");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("pid"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblpid);
				p.add(txtpid);
				p.add(lblpname);
				p.add(txtpname);
				p.add(lbltype);
				p.add(txttype);
				p.add(lblcost);
				p.add(txtcost);
				p.add(lblquantity);
				p.add(txtquantity);
				p.setLayout(new GridLayout(5,2));
				p1.add(p);
				p1.add(btn);
				p1.add(txtmsg);
				btn.setText("Update");
				p1.setLayout(new FlowLayout());
				
				frame.add(p1,BorderLayout.CENTER);
				frame.validate();
			
				idlist.addItemListener(new ItemListener()
				{
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						// TODO Auto-generated method stub
				
						try 
						{
						   ResultSet rs = statement.executeQuery("SELECT * FROM product where PID ="+idlist.getSelectedItem());
							rs.next();
							txtpid.setText(rs.getString("PID"));
							txtpname.setText(rs.getString("PNAME"));
							txttype.setText(rs.getString("type"));
							txtcost.setText(rs.getString("cost"));
							txtquantity.setText(rs.getString("quantity"));
						} 
						catch (SQLException selectException) 
						{
							txtmsg.append(selectException.getMessage());
						}
					}
				});		
				
				
				btn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try 
						{
							Statement statement = connection.createStatement();
							txtmsg.append("UPDATE product "
									+ "SET pname='" + txtpname.getText() + "', "
									+ "type='" + txttype.getText() + "', "
									+ "cost ="+ txtcost.getText() + ",quantity ="+ txtquantity.getText() + " WHERE pid = "
									+ idlist.getSelectedItem()+"\n");
							int i = statement.executeUpdate("UPDATE product "
							+ "SET pname='" + txtpname.getText() + "', "
							+ "type='" + txttype.getText() + "', "
							+ "cost ="+ txtcost.getText() + ",quantity ="+ txtquantity.getText() + " WHERE pid = "
							+ idlist.getSelectedItem());
							System.out.println("successful");
							
							txtmsg.append("\nUpdated " + i + " rows successfully");
							//idlist.removeAll();
							//loadSailors();
						} 
						catch (SQLException insertException) 
						{
							txtmsg.append(insertException.getMessage());
						}
						
					}
				});
			}
		});


		midelete1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				JPanel p=new JPanel();
				txtpid=new JTextField(20);
				txtpname=new JTextField(20);
				txttype=new JTextField(20);
				txtcost=new JTextField(20);
				txtquantity=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT pid FROM product");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("pid"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lblpid);
				p.add(txtpid);
				p.add(lblpname);
				p.add(txtpname);
				p.add(lbltype);
				p.add(txttype);
				p.add(lblcost);
				p.add(txtcost);
				p.add(lblquantity);
				p.add(txtquantity);
				p.setLayout(new GridLayout(5,2));
				p1.add(p);
				p1.add(btn);
				p1.add(txtmsg);
				btn.setText("Delete");
				p1.setLayout(new FlowLayout());
				
				frame.add(p1,BorderLayout.CENTER);
				frame.validate();
				
				idlist.addItemListener(new ItemListener()
				{
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						// TODO Auto-generated method stub
				
						try 
						{
						   ResultSet rs = statement.executeQuery("SELECT * FROM product where PID ="+idlist.getSelectedItem());
							rs.next();
							txtpid.setText(rs.getString("PID"));
							txtpname.setText(rs.getString("PNAME"));
							txttype.setText(rs.getString("TYPE"));
							txtcost.setText(rs.getString("cost"));
							txtquantity.setText(rs.getString("QUANTITY"));
						} 
						catch (SQLException selectException) 
						{
							txtmsg.append(selectException.getMessage());
						}
					}
				});		
				
				
				btn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try 
						{
							Statement statement = connection.createStatement();
							int i = statement.executeUpdate("delete from product where pid=" + idlist.getSelectedItem());
							txtmsg.append("\nDeleted " + i + " rows successfully");
							//idlist.removeAll();
							//loadSailors();
							txtpid.setText(null);
							txtpname.setText(null);
							txttype.setText(null);
							txtcost.setText(null);
							txtquantity.setText(null);
							idlist.removeAll();
						} 
						catch (SQLException insertException) 
						{
							txtmsg.append(insertException.getMessage());
						}
						
					}
				});
			}
		});
		
		miView1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			//	Statement statement=connection.createStatement();
				p1.removeAll();
				frame.invalidate();
				frame.validate();
				frame.repaint();
				p1.add(txtmsg);
				frame.add(p1,BorderLayout.CENTER);
				frame.validate();
				
				try {
					ResultSet rs=statement.executeQuery("select * from product");
					String s="";
					while(rs.next())  
						s=s+(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+ rs.getInt(4)+"  "+rs.getInt(5)+"\n");
						txtmsg.setText(s);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
				
			}
		});
		

	}
}
