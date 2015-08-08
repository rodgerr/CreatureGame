package console.view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import accessory.CLog;
import accessory.CLogContainer;
import accessory.Command;

public class ConsoleFrame extends JFrame implements CLogContainer, KeyListener{
	private JTextArea textArea;
	private JTextField textField_1;

	private CommandHandler cHandler;
	private String lastCommand;
	
	public ConsoleFrame(CommandHandler commandHandler)  {
		this.cHandler = commandHandler;
		this.lastCommand = "";
		
		setVisible(true);
		//setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		textArea.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
		textField_1 = new JTextField();
		textField_1.setBorder(BorderFactory.createBevelBorder(EtchedBorder.LOWERED));
		getContentPane().add(textField_1, BorderLayout.SOUTH);
		textField_1.addKeyListener(this);
		
		final ConsoleFrame instance = this;
		
		cHandler.addCommand("log", new Command() {
			
			@Override
			public String execute(String[] args) {
				CLog.addLogContainer(instance);
				return "start printing log output from user "+System.getProperty("user.name");
			}
		});
		
		cHandler.addCommand("system", new Command() {
			
			@Override
			public String execute(String[] args) {
				return CLog.getInfo();
			}
		});
		
		cHandler.addCommand("printlogstack", new Command() {
			
			@Override
			public String execute(String[] args) {
				for(String s : CLog.getLogStack()){
					printText(s);
				}
				return " === log end ";
			}
		});
		
		printText("Console initialized");
	}

	@Override
	public void printText(String text) {
		textArea.setText(textArea.getText()+"> " + text + " \n");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			
			String[] input = textField_1.getText().split(" ");		
			cHandler.executeCommand(input[0],input ,this);
			
			lastCommand = textField_1.getText();
			textField_1.setText("");
		} else if(e.getKeyCode() == KeyEvent.VK_UP){
			textField_1.setText(lastCommand);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
	

}
