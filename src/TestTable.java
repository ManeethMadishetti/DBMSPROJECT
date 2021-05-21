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

public class TestTable {

	private JPanel p1;
	private JFrame frame;

	private JMenuItem miInsert1,miUpdate1,midelete1,miView1;
	private JLabel lbltid,lblquality,lbltestmanager,lblcondition,lblspecified_in;
	private JTextField txttid,txtquality,txttestmanager,txtcondition,txtspecified_in;
	private JButton btn;
	private JTextArea txtmsg;
	//private static Statement stmt;
	private Connection connection;
	private Statement statement;
	public TestTable(JPanel p1,JFrame frame,JMenuItem miInsert1,JMenuItem miUpdate1,JMenuItem midelete1,JMenuItem miView1) {
	//tid
		//quality
		//testmanager
		//condition
		//specified_in
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
		txttid=new JTextField(20);
		txtquality=new JTextField(20);
		txttestmanager=new JTextField(20);
		txtcondition=new JTextField(20);
		txtspecified_in=new JTextField(20);
		txtmsg=new JTextArea(8,50);
		lbltid=new JLabel("Tester Id:");
		lblquality=new JLabel("Quality:");
		lbltestmanager=new JLabel("Test Manager:");
		lblcondition=new JLabel("Condition:");
		lblspecified_in=new JLabel("Specified_in:");
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
				txttid=new JTextField(20);
				txtquality=new JTextField(20);
				txttestmanager=new JTextField(20);
				txtcondition=new JTextField(20);
				txtspecified_in=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				//a grid of lbl and txtfield
				p.add(lbltid);
				p.add(txttid);
				p.add(lblquality);
				p.add(txtquality);
				p.add(lbltestmanager);
				p.add(txttestmanager);
				p.add(lblcondition);
				p.add(txtcondition);
				p.add(lblspecified_in);
				p.add(txtspecified_in);
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
							String query= "INSERT INTO test VALUES(" + txttid.getText() + ", " + "'" + txtquality.getText() + "'," + "'" + txttestmanager.getText() + "','" + txtcondition.getText() + "','"+ txtspecified_in.getText()+"')";
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
				txttid=new JTextField(20);
				txtquality=new JTextField(20);
				txttestmanager=new JTextField(20);
				txtcondition=new JTextField(20);
				txtspecified_in=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				txtmsg.setEditable(false);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT tid FROM test");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("tid"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lbltid);
				p.add(txttid);
				p.add(lblquality);
				p.add(txtquality);
				p.add(lbltestmanager);
				p.add(txttestmanager);
				p.add(lblcondition);
				p.add(txtcondition);
				p.add(lblspecified_in);
				p.add(txtspecified_in);
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
						   ResultSet rs = statement.executeQuery("SELECT * FROM test where TID ="+idlist.getSelectedItem());
							rs.next();
							txttid.setText(rs.getString("TID"));
							txtquality.setText(rs.getString("QUALITY"));
							txttestmanager.setText(rs.getString("testmanager"));
							txtcondition.setText(rs.getString("condition"));
							txtspecified_in.setText(rs.getString("specified_in"));
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
							txtmsg.append("UPDATE test "
									+ "SET quality='" + txtquality.getText() + "', "
									+ "testmanager='" + txttestmanager.getText() + "', "
									+ "condition ='"+ txtcondition.getText() + "',specified_in ='"+ txtspecified_in.getText() + "' WHERE tid = "
									+ idlist.getSelectedItem()+"\n");
							int i = statement.executeUpdate("UPDATE test "
									+ "SET quality='" + txtquality.getText() + "', "
									+ "testmanager='" + txttestmanager.getText() + "', "
									+ "condition ='"+ txtcondition.getText() + "',specified_in ='"+ txtspecified_in.getText() + "' WHERE tid = "
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
				txttid=new JTextField(20);
				txtquality=new JTextField(20);
				txttestmanager=new JTextField(20);
				txtcondition=new JTextField(20);
				txtspecified_in=new JTextField(20);
				txtmsg=new JTextArea(8,50);
				btn=new JButton();
				List idlist =new List(10);  
				try 
				{
				 ResultSet rs = statement.executeQuery("SELECT tid FROM TEST");
				  while (rs.next()) 
				  {
					  idlist.add(rs.getString("tid"));
				  }
				} 
				catch (SQLException e) 
				{ 
				  txtmsg.append(e.getMessage());
				}
				p1.add(idlist);  
				p.add(lbltid);
				p.add(txttid);
				p.add(lblquality);
				p.add(txtquality);
				p.add(lbltestmanager);
				p.add(txttestmanager);
				p.add(lblcondition);
				p.add(txtcondition);
				p.add(lblspecified_in);
				p.add(txtspecified_in);
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
						   ResultSet rs = statement.executeQuery("SELECT * FROM test where TID ="+idlist.getSelectedItem());
							rs.next();
							txttid.setText(rs.getString("TID"));
							txtquality.setText(rs.getString("QUALITY"));
							txttestmanager.setText(rs.getString("TESTMANAGER"));
							txtcondition.setText(rs.getString("CONDITION"));
							txtspecified_in.setText(rs.getString("SPECIFIED_IN"));
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
							int i = statement.executeUpdate("delete from test where tid=" + idlist.getSelectedItem());
							txtmsg.append("\nDeleted " + i + " rows successfully");
							//idlist.removeAll();
							//loadSailors();
							txttid.setText(null);
							txtquality.setText(null);
							txttestmanager.setText(null);
							txtcondition.setText(null);
							txtspecified_in.setText(null);
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
					ResultSet rs=statement.executeQuery("select * from TEST");
					String s="";
					while(rs.next())  
						s=s+(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+ rs.getString(4)+"  "+rs.getString(5)+"\n");
						txtmsg.setText(s);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
				
			}
		});
		

	}

}
