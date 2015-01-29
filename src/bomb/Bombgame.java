package bomb;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Bombgame {

	private int size;
	gamestarttable start;

	public void startgame() {
		// TODO Auto-generated method stub
		JFrame selecttable = new JFrame("踩地雷初始設定");
		selecttable.setSize(800, 600);
		selecttable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		selecttable.setLayout(new GridLayout(3, 2, 3, 3));

		JLabel lsize = new JLabel("setup size:");
		JTextArea txtsize = new JTextArea("");
		JButton button = new JButton("Start");

		selecttable.add(lsize);
		selecttable.add(txtsize);
		selecttable.add(button);
		selecttable.pack();
		selecttable.setVisible(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtsize.getText().equals("") ) {
					JOptionPane.showMessageDialog(null,
							"請輸入整數", "錯誤！",
							JOptionPane.ERROR_MESSAGE);
				} else {
					size = Integer.parseInt(txtsize.getText().trim());
					selecttable.dispose();
					start = new gamestarttable(size);
					start.setVisible(true);
				}

			}
		});

	}

}
