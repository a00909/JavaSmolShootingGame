package test3;

import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.concurrent.CopyOnWriteArrayList;

public class CGameEngine implements Runnable{
	
	private List<Runnable> m_Runables;
	private JFrame m_GameFrame;
	private CGamePanel m_GamePanel;
	private List<CGameObject> m_GameObjects;
	private Thread m_Thread;
	private boolean m_IsRunning;
	private CGameObject m_MainGameObject;
	
		
	void Init(int a_FrameWidth,int a_FrameHeight,String a_FrameName) {
		m_GameFrame = new JFrame(a_FrameName);
		m_GameFrame.setSize(a_FrameWidth, a_FrameHeight);
		m_GamePanel = new CGamePanel();
		m_GameObjects = new CopyOnWriteArrayList<CGameObject>();
		m_IsRunning = false;
	}
	
	public boolean ActiveFrame() {
		if(m_GameFrame!=null) {
			m_GameFrame.add(m_GamePanel);		
			
			m_GameFrame.setVisible(true);		
			m_GameFrame.setResizable(false);
			m_GameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			m_GameFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			    @Override
			    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
			        StopGame();
			    }
			});
			StartGame();
			return true;
		}
		return false;
	}
	
	private void StartGame() {
		m_IsRunning = true;
		m_Thread = new Thread(this);
		m_Thread.start();
	}
	
	private void StopGame() {
		if(m_Thread == null) return;
		m_IsRunning = false;
		try {
			m_Thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CGameEngine() {
		Init(400,600,"GameFrame");
	}
	
	public CGameEngine(int a_FrameWidth,int a_FrameHeight,String a_FrameName) {
		Init(a_FrameWidth,a_FrameHeight,a_FrameName);
	}
	
	public void AddGameObject(CGameObject a_GameObject) {
		m_GamePanel.AddGameObject(a_GameObject);
		m_GameObjects.add(a_GameObject);
	}
	
	public void GameObjPosControl() {
		for(int i =0;i<m_GameObjects.size();i++) {
				m_GameObjects.get(i).UpdatePosition();
		}
		
	}
	
	public void AddKeyListener(KeyAdapter a_KeyAdapter) {
		m_GameFrame.addKeyListener(a_KeyAdapter);
	}
	
	public void SetMainGameObject(CGameObject a_Main) {
		m_MainGameObject = a_Main;
	}
	
	private void DetectBoundaryCollision() {
		double FSumX = 0,FSumY = 0;
		if(m_MainGameObject.X()<0 ||
		   m_MainGameObject.X() + m_MainGameObject.GetWidth() > m_GameFrame.getWidth()	) {
			FSumX = -m_MainGameObject.GetVelocityX() * 2;
		}
		
		if(m_MainGameObject.Y()<0 ||
		   m_MainGameObject.Y() + m_MainGameObject.GetHeight() > m_GameFrame.getHeight()) {
			FSumY = -m_MainGameObject.GetVelocityY() * 2;
		}
		if(Math.abs(FSumX)>0) {
			m_MainGameObject.SetPosition(m_MainGameObject.X() + FSumX/Math.abs(FSumX),m_MainGameObject.Y());
		}
		if(Math.abs(FSumY)>0) {
			m_MainGameObject.SetPosition(m_MainGameObject.X(),m_MainGameObject.Y()+ FSumY/Math.abs(FSumY));
		}
		
		m_MainGameObject.AddForce(FSumX,FSumY);
	}
	
	private void DetectPositionExccedBoundary() {
		int x=0,y=0;
		if(m_MainGameObject.X()<0) {		   
			
		}
		else if(m_MainGameObject.X() + m_MainGameObject.GetWidth() > m_GameFrame.getWidth()){
			x= m_GameFrame.getWidth() - m_MainGameObject.GetWidth();
		}
		else {
			x = (int) m_MainGameObject.X();
		}
		
		if(m_MainGameObject.Y()<0) {
			
		}
		else if(m_MainGameObject.Y() + m_MainGameObject.GetHeight() > m_GameFrame.getHeight()) {
			y= m_GameFrame.getHeight() - m_MainGameObject.GetHeight();
		}
		else {
			y = (int) m_MainGameObject.Y();
		}
		m_MainGameObject.SetPosition(x, y);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long framePerSec = 75;
		long timeStart;
		long timeFinished;
		long totalTimeMillisec;
		long secPerFrameMillisec = 1000/framePerSec;
		while(m_IsRunning) {
			timeStart = System.nanoTime();
			
			//main operations
			DetectBoundaryCollision();
			GameObjPosControl();
			//DetectPositionExccedBoundary();
			m_GamePanel.repaint();
			
			timeFinished = System.nanoTime();
			totalTimeMillisec =  (long) ((timeFinished-timeStart)/1000000);
			//System.out.print("("+totalTimeMillisec +","+secPerFrameMillisec+")\n");
			
			//detect sleep time
			if(totalTimeMillisec>=secPerFrameMillisec) continue;
			try {
				
				Thread.sleep(secPerFrameMillisec - totalTimeMillisec);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
}
