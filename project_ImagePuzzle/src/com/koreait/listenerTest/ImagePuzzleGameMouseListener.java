package com.koreait.listenerTest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class ImagePuzzleGameMouseListener extends JFrame implements ActionListener, MouseListener {

	JPanel puzzlePanel = new JPanel(new GridLayout(4, 4));
	JButton[] puzzleButton = new JButton[16];
	String[] numbers = {"1",  "2",  "3",  "4",
						"5",  "6",  "7",  "8", 
						"9",  "10", "11", "12",
						"13", "14", "15", "16"};
	JButton startButton = new JButton("섞기");
	Random random = new Random();
	Image[] images = new Image[16];
	

	public static void main(String[] args) {
		ImagePuzzleGameMouseListener startPuzzle = new ImagePuzzleGameMouseListener();
	}
	
	public ImagePuzzleGameMouseListener() {
		setTitle("숫자 퍼즐 게임");
		setBounds(800, 100, 400, 500);
		
		for (int i=0; i<images.length; i++) {// 이미지 불러오기
			String filename = String.format("./src/number/%02d.jpg", i+1);
			images[i] = Toolkit.getDefaultToolkit().getImage(filename);
		}
		
		setPuzzle();
		startButton.setFont(new Font("궁서체", Font.BOLD, 45));
		startButton.setPreferredSize(new Dimension(400, 100));
		startButton.addActionListener(this);
		add(startButton, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void setPuzzle() {// 화면에 퍼즐조각 배치
		for (int i=0; i<puzzleButton.length; i++) {
			int index = Integer.parseInt(numbers[i]) - 1;
			puzzleButton[i] = new JButton(new ImageIcon(images[index]));
			puzzleButton[i].addMouseListener(this);
			puzzlePanel.add(puzzleButton[i]);
			puzzleButton[i].setName(numbers[i]);
			if (puzzleButton[i].getName().equals("16")) {
				puzzleButton[i].setVisible(false);
			}
		}
		add(puzzlePanel, BorderLayout.CENTER);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("섞기")) {
			for (int i=0; i<100; i++) { // 숫자를 섞고
				int r = random.nextInt(15) + 1;
				String temp = numbers[0];
				numbers[0] = numbers[r];
				numbers[r] = temp;
			}
			
			for (int i=0; i<puzzleButton.length; i++) { // 기존 화면을 지운 뒤
				puzzleButton[i].setVisible(true);
				puzzlePanel.remove(puzzleButton[i]);
			}
			setPuzzle();// 새로 퍼즐조각을 배치한다
			revalidate();
		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// 퍼즐 조각에 마우스를 올리면
		Object object = e.getSource();
		JButton button = (JButton) object;
		
		int location=0;// 조각의 위치
		int loc16=0;// 빈칸(16)의 위치
		
		for (int i=0; i<puzzleButton.length; i++) {
			if(puzzleButton[i].getName().equals(button.getName())) {
				location=i;
				break;
			}
		}
		for (int i=0; i<puzzleButton.length; i++) {
			if(puzzleButton[i].getName().equals("16")) {
				loc16=i;
				break;
			}
		}
		
		// 인접하면 자리 바꾸기
		int dist=Math.abs(location-loc16);
		if((dist==1 && location/4==loc16/4) || dist==4) {
			String temp=numbers[location];
			numbers[location]=numbers[loc16];
			numbers[loc16]=temp;
			puzzleButton[loc16].setVisible(true);
			puzzleButton[loc16].setName(numbers[loc16]);
			puzzleButton[loc16].setIcon(new ImageIcon(images[Integer.parseInt(numbers[loc16])-1]));
			puzzleButton[location].setName("16");
			puzzleButton[location].setIcon(new ImageIcon(images[15]));
			puzzleButton[location].setVisible(false);
			//revalidate();
		}
		else return;
		
		
		// 퍼즐 완성 검사
		for (int i=0; i<puzzleButton.length-1; i++) 
			if (i+1 != Integer.parseInt(puzzleButton[i].getName())) 
				return;
		JOptionPane.showMessageDialog(puzzlePanel, "퍼즐 완성!");
		//System.exit(0);
	}
	
	
	// no use
	@Override
	public void mouseClicked(MouseEvent e) { }
	public void mousePressed(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
}

