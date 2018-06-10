package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


import logika.Igra;
import logika.Igralec;
import logika.Polje;
import logika.Poteza;

@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener{
	//atributi
	private IgralnoPolje polje;	
	private JLabel status;//statusna vrstica
	
	private Igra igra;	
	private Strateg strategBELI;//strateg, ki premika belega konja	
	private Strateg strategCRNI;
	
	
	//rezultati (stevilo zmag posameznega igralca)
	private double zmageBeli;
	private double zmageCrni;
	
	//atribut globina, kjer uravnavamo težavnost
	private int globina = 8;
	
	
	//izbire v menuju
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	private JMenuItem igraClovekClovek;
	private JMenuItem igraRacunalnikRacunalnik;
	
	private JMenuItem lahko;
	private JMenuItem tezko;
	private JMenuItem skrajnoTezko;
	
	private JMenuItem pravila = new JMenuItem("Pravila igre");
	private JMenuItem izhod = new JMenuItem("Izhod");
	
	
	
	
	public GlavnoOkno() {
		this.setTitle("Isolation");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
	
		// menu
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		
		JMenu igra_menu = new JMenu("Igra");
		menu_bar.add(igra_menu);
		
		JMenu nastavitve = new JMenu("Nastavitve igre");
		menu_bar.add(nastavitve);
		
		JMenu tezavnost = new JMenu("Težavnost igre");
		nastavitve.add(tezavnost);
		
		igraClovekRacunalnik = new JMenuItem("Človek - računalnik");
		igra_menu.add(igraClovekRacunalnik);
		igraClovekRacunalnik.addActionListener(this);
		
		igraRacunalnikClovek = new JMenuItem("Računalnik - človek");
		igra_menu.add(igraRacunalnikClovek);
		igraRacunalnikClovek.addActionListener(this);

		igraRacunalnikRacunalnik = new JMenuItem("Računalnik - računalnik");
		igra_menu.add(igraRacunalnikRacunalnik);
		igraRacunalnikRacunalnik.addActionListener(this);

		igraClovekClovek = new JMenuItem("Človek - človek");
		igra_menu.add(igraClovekClovek);
		igraClovekClovek.addActionListener(this);
		
		lahko = new JMenuItem("Lahko");
		tezavnost.add(lahko);
		lahko.addActionListener(this);
		
		tezko = new JMenuItem("Težko");
		tezavnost.add(tezko);
		tezko.addActionListener(this);
		
		skrajnoTezko = new JMenuItem("Skrajno težko");
		tezavnost.add(skrajnoTezko);
		skrajnoTezko.addActionListener(this);
		
		pravila = new JMenuItem("Pravila igre");
		menu_bar.add(pravila);
		pravila.setMaximumSize(new Dimension(150,150));
		pravila.addActionListener(this);
		
		izhod = new JMenuItem("Izhod");
		menu_bar.add(Box.createHorizontalGlue());
		izhod.setMaximumSize(new Dimension(150,1500));
		menu_bar.add(izhod);
		izhod.addActionListener(this);
		
		// igralno polje
		polje = new IgralnoPolje(this);
		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(polje, polje_layout);
		
		// statusna vrstica za sporočila
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
				status.getFont().getStyle(),
				20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		// začnemo novo igro človeka proti računalniku
		novaIgra(new Clovek(this, Igralec.BELI),
		new Racunalnik(this, Igralec.CRNI, globina));
		
	}
		
	
	
	// trenutna igralna plošča ali null, če igra ni aktivna
	public Polje[][] getPlosca(){
		return (igra == null ? null : igra.getPlosca());
	}
	
	public Igra getIgra() {
		return igra;
	}
	
	/**
	 * Začni igrati novo igro. Metodo lahko pokličemo kadarkoli in
	 * bo pravilno ustavila morebitno trenutno igro.
	 */
	public void novaIgra(Strateg noviSrategBELI, Strateg noviStrategCRNI) {
		// Prekinemo stratege
		if (strategBELI != null) { strategBELI.prekini(); }
		if (strategCRNI != null) { strategCRNI.prekini(); }
		// Ustvarimo novo igro
		this.igra = new Igra();
		// Ustvarimo nove stratege
		strategBELI = noviSrategBELI;
		strategCRNI = noviStrategCRNI;
		// Tistemu, ki je na potezi, to povemo
		switch (igra.stanje()) {
		case NA_POTEZI_BELI: strategBELI.na_potezi(); break;
		case NA_POTEZI_CRNI: strategCRNI.na_potezi(); break;
		default: break;
		}
		osveziGUI();
		repaint();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == igraClovekRacunalnik) {
			novaIgra(new Clovek(this, Igralec.BELI),
					  new Racunalnik(this, Igralec.CRNI, globina));
		}
		else if (e.getSource() == igraRacunalnikClovek) {
			novaIgra(new Racunalnik(this, Igralec.BELI, globina),
					  new Clovek(this, Igralec.CRNI));
		}
		else if (e.getSource() == igraRacunalnikRacunalnik) {
			novaIgra(new Racunalnik(this, Igralec.BELI, globina),
					  new Racunalnik(this, Igralec.CRNI, globina));
		}
		else if (e.getSource() == igraClovekClovek) {
			novaIgra(new Clovek(this, Igralec.BELI),
			          new Clovek(this, Igralec.CRNI));
		}
		else if (e.getSource() == lahko) {
		    this.setGlobina(1);
		    
		}else if (e.getSource() == tezko) {
		    this.setGlobina(8);
		    
		}else if (e.getSource() == skrajnoTezko) {
		    this.setGlobina(16);
		    
		}else if (e.getSource() == pravila) {
			
			JFrame oknoPravil = new JFrame();
			oknoPravil.setTitle("Pravila igre Isolation");
			oknoPravil.setFont(new Font(status.getFont().getName(),
				    status.getFont().getStyle(),
				    14));
			final int sirinaOkenca = 300;
			final int visinaOkenca = 250;
			oknoPravil.setPreferredSize(new Dimension(sirinaOkenca, visinaOkenca));
			JLabel besedilo = new JLabel("<html>"
					+ "<h2 style='padding: 8pt;'>Pravila igre Isolation </h2>"
					+ "Isolation je igra z dvema igralcema, kjer igralca </br>"
					+ "premikata svojo figuro. Figuri premikata kot konja</br>"
					+ "pri šahu. Začetna položaja igralcev sta določena</br>"
					+ "naključno. Kadarkoli igralec zasede polje, se to polje za</p>"
					+ "preostanek igre blokira. Prvi igralec, ki nima več </br>"
					+ "možnih potez, izgubi in nasprotnik je razglašen"
					+ "za zmagovalca. </br>"
					+ "</html>");
			oknoPravil.getContentPane().add(besedilo);
		
			
			oknoPravil.setLocationRelativeTo(this);
			oknoPravil.setLocation(oknoPravil.getX()-sirinaOkenca/2, oknoPravil.getY()-visinaOkenca/2);
			
			oknoPravil.pack();
			oknoPravil.setVisible(true);
			
		} else if (e.getSource() == izhod) {
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		
	}
		
	public void osveziGUI() {
		if (igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch (igra.stanje()) {
			case NA_POTEZI_BELI: status.setText("Na potezi je beli.");break;
			case NA_POTEZI_CRNI: status.setText("Na potezi je črni.");break;
			case ZMAGA_BELI: 
				status.setText("Zmagal je beli.");
				break;
			case ZMAGA_CRNI: 
				status.setText("Zmagal je črni.");
				break;
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
	
	//vrne kopijo igre
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



	public int getGlobina() {
		return globina;
	}



	public void setGlobina(int globina) {
		this.globina = globina;
	}



	public double getZmageBeli() {
		return zmageBeli;
	}



	public void setZmageBeli(double d) {
		this.zmageBeli = d;
	}



	public double getZmageCrni() {
		return zmageCrni;
	}



	public void setZmageCrni(double zmageCrni) {
		this.zmageCrni = zmageCrni;
	}



}