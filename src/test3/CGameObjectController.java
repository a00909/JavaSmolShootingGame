package test3;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CGameObjectController extends KeyAdapter{
	private CGameObject m_GameObject;
	private double m_UnitForce;
	private int m_ContinuousForceSettingSerialNo;
	private int[] m_ContinuousForceCtrlNo = {0,0,0,0};
	
	public CGameObjectController(CGameObject a_GameObject) {
		m_GameObject = a_GameObject;
		m_UnitForce = 0.3;
		m_ContinuousForceCtrlNo[0] = m_GameObject.AddContinuousForceSetting(m_UnitForce, 0);
		m_ContinuousForceCtrlNo[1] = m_GameObject.AddContinuousForceSetting(-m_UnitForce, 0);
		m_ContinuousForceCtrlNo[2] = m_GameObject.AddContinuousForceSetting( 0,-m_UnitForce);
		m_ContinuousForceCtrlNo[3] = m_GameObject.AddContinuousForceSetting( 0,m_UnitForce);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
    	//System.out.print("Key Down.");
        int key1 = e.getKeyCode();
        switch (key1) {    
	        case KeyEvent.VK_DOWN:
	        	m_GameObject.SetEnableContinuousForce(m_ContinuousForceCtrlNo[3], true);
	        	break;        	
	        case KeyEvent.VK_UP:
	        	m_GameObject.SetEnableContinuousForce(m_ContinuousForceCtrlNo[2], true);
	        	break;     
	        case KeyEvent.VK_LEFT:
	        	m_GameObject.SetEnableContinuousForce(m_ContinuousForceCtrlNo[1], true);
	        	break;        	
	        case KeyEvent.VK_RIGHT:
	        	m_GameObject.SetEnableContinuousForce(m_ContinuousForceCtrlNo[0], true);
	        	break;	        
        }   
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key1 = e.getKeyCode();
	        switch (key1) {    
	        case KeyEvent.VK_DOWN:
	        	m_GameObject.SetEnableContinuousForce(m_ContinuousForceCtrlNo[3], false);
	        	break;        	
	        case KeyEvent.VK_UP:
	        	m_GameObject.SetEnableContinuousForce(m_ContinuousForceCtrlNo[2], false);
	        	break;     
	        case KeyEvent.VK_LEFT:
	        	m_GameObject.SetEnableContinuousForce(m_ContinuousForceCtrlNo[1], false);
	        	break;        	
	        case KeyEvent.VK_RIGHT:
	        	m_GameObject.SetEnableContinuousForce(m_ContinuousForceCtrlNo[0], false);
	        	break;  
	        
        }        
	}
}
