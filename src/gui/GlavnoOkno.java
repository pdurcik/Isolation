package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logika.Igra;
import logika.Polje;
import logika.Poteza;

@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener{
	private IgralnoPolje polje;
	
	private JLabel status;
	
	private Igra igra;
	
	private Strateg strategBELI;
	
	private Strateg strategCRNI;
	
	private JMenuItem nova_igra;
	
	public GlavnoOkno() {
		this.setTitle("Isolation");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		//menu
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		JMenu igra_menu = new JMenu("Igra");
		menu_bar.add(igra_menu);
		nova_igra = new JMenuItem("Nova igra");
		igra_menu.add(nova_igra);
		nova_igra.addActionListener(this);
		
		// igralno polje
		polje = new IgralnoPolje(this);
		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(polje, polje_layout);
		
		// statusna vrstica za sporoèila
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
				status.getFont().getStyle(),
				20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		// zaènemo novo igro
		nova_igra();
		
	}
	
	// trenutna igralna plošèa ali null, èe igra ni aktivna
	public Polje[][] getPlosca(){
		return (igra == null ? null : igra.getPlosca());
	}
	
	public Igra getIgra() {
		return igra;
	}
	
	
	
	public void nova_igra() {
		if (strategBELI != null) { strategBELI.prekini();}
		if (strategCRNI != null) { strategCRNI.prekini();}
		this.igra = new Igra();
		strategBELI = new Clovek(this);
		strategCRNI = new Clovek(this);
		// tistemu ki je na potezi to povemo
		switch(igra.stanje()) {
		case NA_POTEZI_BELI: strategBELI.na_potezi();break;
		case NA_POTEZI_CRNI: strategCRNI.na_potezi();break;
		default:break;
		}
		osveziGUI();
		repaint();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nova_igra) {
			nova_igra();
		}
	}
		
	public void osveziGUI() {
		if (igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch (igra.stanje()) {
			case NA_POTEZI_BELI: status.setText("Na potezi je beli.");break;
			case NA_POTEZI_CRNI: status.setText("Na potezi je èrni.");break;
			case ZMAGA_BELI: status.setText("Zmagal je beli."); break;
			case ZMAGA_CRNI: status.setText("Zmagal je èrni.");break;
			}
		}
		polje.repaint();
	}
	
	public void klikniPolje(int i, int j) {
		if (igra != null) {
			switch(igra.stanje()) {
			case NA_POTEZI_BELI:
				strategBELI.klik(i, j);
				break;
			case NA_POTEZI_CRNI:
				strategCRNI.klik(i, j);
				break;
			default:
				break;
			}
		}
	}
	
	public Igra copyIgra() {
		return new Igra(igra);
	}

	public void odigraj(Poteza p) {
		igra.odigraj(p);
		osveziGUI();
		switch (igra.stanje()) {
		case NA_POTEZI_BELI: strategBELI.na_potezi();break;
		case NA_POTEZI_CRNI: strategCRNI.na_potezi();break;
		case ZMAGA_BELI: break;
		case ZMAGA_CRNI: break;
		}
	}

}
