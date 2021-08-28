package test3;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JPanel;

class CGamePanel extends JPanel implements Runnable{

	private List<CGameObject> m_GameObjects;
	private boolean m_IsRunning;
	private Thread m_Thread;

	public CGamePanel() {
		m_GameObjects = new CopyOnWriteArrayList<CGameObject>();
		m_IsRunning = false;
		
	}
	
	public void AddGameObject(CGameObject a_GameObject) {
		m_GameObjects.add(a_GameObject);
	}
	
	public void StartDrawing() {
		m_IsRunning = true;
		m_Thread = new Thread(this);
		m_Thread.start();
	}
	
	public void StopDrawing() {
		if(m_Thread == null) return;
		
		m_IsRunning = false;
		try {
			m_Thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override	
	public void paint(Graphics g) {
	    super.paint(g);
	    g.setColor(new Color(100,150,100,127));
	    g.fillRect(0, 0, getWidth(), getHeight());
	    for(int i = 0; i < m_GameObjects.size(); i++) {
	    	g.drawImage(
	    			m_GameObjects.get(i).GetCurrentImage(),
	    			(int)m_GameObjects.get(i).X(),
	    			(int)m_GameObjects.get(i).Y(),
	    			null
	    	);
	    }
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}	
}

