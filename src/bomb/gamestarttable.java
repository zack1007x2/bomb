package bomb;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class gamestarttable extends JFrame implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6290309374958199843L;
	private int size;
	private int bombnum;
	private JPanel centerPanel;
	private int[][] map;
	private boolean[][] known;
	JButton[][] button;
	JButton restart;
	int counter = 0;

	public gamestarttable(int size) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		this.size = size;
		setSize(800, 800);
		init();
	}

	// 建立隨機地圖
	private void init() {
		button = new JButton[size][size];
		known = new boolean[size][size];
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(size, size));
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				button[i][j] = new JButton("");
				button[i][j].addMouseListener(this);
				centerPanel.add(button[i][j]);
			}
		}
		bombnum = (int) size * size / 9;
		System.out.println(size * size - bombnum + "@@@@" + bombnum);
		add(centerPanel, BorderLayout.CENTER);
		restart = new JButton("Restart");
		restart.addMouseListener(this);
		add(restart, BorderLayout.SOUTH);
		boolean bombmap[][] = new boolean[size][size];
		map = new int[size][size];
		int r[] = new int[bombnum];
		int c[] = new int[bombnum];
		for (int i = 0; i < bombnum; i++) {
			r[i] = (int) (Math.random() * size);
			c[i] = (int) (Math.random() * size);
			if (!bombmap[r[i]][c[i]]) {
				// 判為有炸彈
				bombmap[r[i]][c[i]] = true;
			} else {
				i--;
			}
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (bombmap[i][j]) {
					map[i][j] = -100;
				}
				for (int k = 0; k < bombnum; k++) {
					if ((Math.abs(r[k] - i) == 1 || Math.abs(c[k] - j) == 1)
							&& Math.abs(r[k] - i) < 2 && Math.abs(c[k] - j) < 2) {
						map[i][j] += 1;
					}
				}
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (restart == e.getComponent()) {
				this.dispose();
				Bombgame game = new Bombgame();
				game.startgame();
			}

			// if click雷區
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (button[i][j] == e.getComponent()) {
						if (button[i][j].getText() != "H") {
							// 沒死
							if (map[i][j] > 0 && !known[i][j]) {
								int num = map[i][j];
								String number = Integer.toString(num);
								button[i][j].setText(number);
								known[i][j] = true;
								counter++;
								wincheck();
							} else if (map[i][j] == 0 && !known[i][j]) {// BFS擴張掃雷
								int[][] scan = { { -1, -1 }, { 0, -1 },
										{ 1, -1 }, { -1, 0 }, { 1, 0 },
										{ -1, 1 }, { 0, 1 }, { 1, 1 } };
								int[] queue_x = new int[size * size];
								int[] queue_y = new int[size * size];
								int push = 0;
								int pop = 0;
								// 點下去的座標存於陣列首
								queue_x[push] = i;
								queue_y[push] = j;
								push++;// 指標後移
								counter++;
								wincheck();
								while (pop < push) {
									int cur_x = queue_x[pop];// 目前處理
									int cur_y = queue_y[pop];
									// 如果是處理0的格子就擴散
									button[cur_x][cur_y].setText("0");
									known[cur_x][cur_y] = true;
									pop++;
									for (int k = 0; k < 8; k++) {
										// 左上開始跑迴圈
										int scan_x = cur_x + scan[k][0];
										int scan_y = cur_y + scan[k][1];
										// 沒猜過就顯示數字
										if (inRange(scan_x, scan_y)) {
											if (!known[scan_x][scan_y]) {
												int num = map[scan_x][scan_y];
												String number = Integer
														.toString(num);
												button[scan_x][scan_y]
														.setText(number);
												counter++;
												wincheck();
												known[scan_x][scan_y] = true;
												// 如果等於0則加入queue
												if (num == 0) {
													// System.out.println("("+scan_x+","+scan_y+")");
													queue_x[push] = scan_x;
													queue_y[push] = scan_y;
													push++;
												}
											}
										}
									}
								}
							}

							else if (map[i][j] < 0) {
								// 輸了
								JOptionPane.showMessageDialog(this, "踩到地雷囉");
								lose();
							}
						}
					}
				}
			}

		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (button[i][j] == e.getComponent()) {
						if (button[i][j].getText() != "H") {
							button[i][j].setText("H");
						} else {
							button[i][j].setText("");
						}
					}
				}
			}
		}

	}

	private void wincheck() {
		if (counter == size * size - bombnum) {
			JOptionPane.showMessageDialog(this, "您獲勝了");
		}

	}

	private void lose() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int num = map[i][j];
				String number;
				if (num < 0) {
					number = "*";
				} else if (num == 0) {
					number = "";
				} else {
					number = Integer.toString(num);
				}
				button[i][j].setText(number);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	private boolean inRange(int x, int y) {
		return (x >= 0 && x < size && y >= 0 && y < size);
	}
}
