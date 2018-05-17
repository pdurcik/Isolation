package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import logika.Igra;
import logika.Polje;

@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener{
	private GlavnoOkno master;
	
	//barve
	Color temnoRjava = new Color(181, 134, 101);
	Color svetloRjava = new Color(236,215,180); 
	
	//slike konjev
	Image konjBeli = Toolkit.getDefaultToolkit().getImage("./slike/white_chess_knight.png");
	Image konjCrni = Toolkit.getDefaultToolkit().getImage("./slike/black_chess_knight.png");
	
	
	public IgralnoPolje(GlavnoOkno master) {
		super();
		setBackground(Color.white);
		this.master = master;
		this.addMouseListener(this);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(600,600);
	}
	
	private double sirinaKvadratka() {
		return Math.min(getWidth(), getHeight())/Igra.N;
	}
	
	private void paintCrni(Graphics2D g2, int i, int j) {
		double sirina = sirinaKvadratka();
		double x = sirina*i;
		double y = sirina*j;
		g2.setColor(Color.black);
		g2.fillRect((int) x, (int) y, (int)sirina, (int)sirina);
		g2.drawImage(konjCrni, (int) x, (int) y, (int)sirina, (int)sirina, this);
	}
	
	private void paintBeli(Graphics2D g2, int i, int j) {
		double sirina = sirinaKvadratka();
		double x = sirina*i;
		double y = sirina*j;
		g2.setColor(Color.white);
		g2.fillRect((int) x, (int) y, (int)sirina, (int)sirina);
		g2.setColor(Color.black);
		g2.drawRect((int) x, (int) y, (int)sirina, (int)sirina);
		g2.drawImage(konjBeli, (int) x, (int) y, (int)sirina, (int)sirina, this);
	}
	
	public void aktivnoPolje(Graphics2D g2, int i, int j) {
		double sirina = sirinaKvadratka();
		double x = sirina*i;
		double y = sirina*j;
		
		if ((i+j)%2 == 0) {
			g2.setColor(temnoRjava);
			g2.fillRect((int) x, (int) y, (int)sirina, (int)sirina);
			g2.setColor(Color.black);
			g2.drawRect((int) x, (int) y, (int)sirina, (int)sirina);
		}else {
			g2.setColor(svetloRjava);
			g2.fillRect((int) x, (int) y, (int)sirina, (int)sirina);
			g2.setColor(Color.black);
			g2.drawRect((int) x, (int) y, (int)sirina, (int)sirina);
		}
		
		
	}
	
	public void neaktivnoPolje(Graphics2D g2, int i, int j) {
		double sirina = sirinaKvadratka();
		double x = sirina*i;
		double y = sirina*j;
		g2.setColor(Color.gray);
		g2.fillRect((int) x, (int) y, (int)sirina, (int)sirina);
		g2.setColor(Color.black);
		g2.drawRect((int) x, (int) y, (int)sirina, (int)sirina);
		
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		Polje[][] plosca = master.getPlosca();
		if (plosca != null) {
			for (int i = 0; i < Igra.N; i++) {
				for (int j = 0; j < Igra.N; j++) {
					if (plosca[i][j] == Polje.AKTIVNO) {
						aktivnoPolje(g2, i, j);
					} else {neaktivnoPolje(g2, i, j);}
					}
				}
		}
		
		//nariseva igralca
		//polzaj belega
		int iBeli = master.getIgra().getPolozajBeli().getX();
		int jBeli = master.getIgra().getPolozajBeli().getY();
		// polozaj crnega
		int iCrni = master.getIgra().getPolozajCrni().getX();
		int jCrni= master.getIgra().getPolozajCrni().getY();
		
		paintBeli(g2, iBeli, jBeli);
		
		paintCrni(g2, iCrni, jCrni);
		}	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		int w = (int)(sirinaKvadratka());
		
		int i = x/w;
		int j = y/w;
		
		if (0<=i && i < Igra.N && 0 <= j && j < Igra.N) {
			master.klikniPolje(i, j);
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
