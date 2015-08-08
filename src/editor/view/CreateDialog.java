package editor.view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JButton;

public class CreateDialog extends JFrame {
	
	private int WIDTH = 200;
	private int HEIGHT = 200;
	private JTextField widthTextField;
	private JTextField heightTextField;	
	
	public CreateDialog(ActionListener confirmListener){
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblWidth = new JLabel("width");
		getContentPane().add(lblWidth);
		
		widthTextField = new JTextField();
		widthTextField.setText("50");
		getContentPane().add(widthTextField);
		widthTextField.setColumns(10);
		
		JLabel lblHeight = new JLabel("height");
		getContentPane().add(lblHeight);
		
		heightTextField = new JTextField();
		heightTextField.setText("30");
		getContentPane().add(heightTextField);
		heightTextField.setColumns(10);
		
		JButton btnCreate = new JButton("create");
		btnCreate.addActionListener(confirmListener);
		getContentPane().add(btnCreate);
		
		JButton btnCancel = new JButton("cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		getContentPane().add(btnCancel);
		pack();
	}

	public int getEnteredWidth(){
		return Integer.parseInt(widthTextField.getText());
	}
	
	public int getEnteredHeight(){
		return Integer.parseInt(heightTextField.getText());
	}

}
