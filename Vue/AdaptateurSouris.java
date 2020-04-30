
package Vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdaptateurSouris extends MouseAdapter {
	Plateau niv;
	CollecteurEvenements control;

	AdaptateurSouris(Plateau n, CollecteurEvenements c) {
		niv = n;
		control = c;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int l = e.getY() / niv.hauteurCase();
		int c = e.getX() / niv.largeurCase();
		control.clicSouris(l, c);
	}
}
