package util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyOnlyNumAdapter extends KeyAdapter{
		
	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar()<48 ||e.getKeyChar()>57)
			e.consume();
	}

}
