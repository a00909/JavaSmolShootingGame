package test3;

import java.awt.Image;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.AbstractMap.SimpleEntry;

public class CGameObject{

	private static final long serialVersionUID = 1L;	
	private List<Image> m_Images;
	private double m_PositionX;
	private double m_PositionY;
	private double m_RotationDegree;
	private int m_CurrentImagePointer;
	private double m_VelocityX;
	private double m_VelocityY;
	private double m_FrictionX;
	private double m_FrictionY;
	private double m_AccelerationX;
	private double m_AccelerationY;
	private int m_AccelDirectionX;
	private int m_AccelDirectionY;
	private double m_Tolerance;
	private List<SContinuousForceSetter> m_ContinuousForceSettings;
	private int m_Width;
	private int m_Height;
	
	static class SContinuousForceSetter{
		private static int m_Dispenser;
		private double m_ForceX;
		private double m_ForceY;
		private boolean m_Enable;
		private int m_SerialNo;
		
		public SContinuousForceSetter(double a_ForceX,double a_ForceY) {
			m_ForceX = a_ForceX;
			m_ForceY = a_ForceY;
			m_Enable = false;
			m_SerialNo = m_Dispenser;
			m_Dispenser ++;
		}		
		public int GetSerialNo() {
			return m_SerialNo;
		}
		public void SetEnable(boolean a_Enable) {
			m_Enable = a_Enable;
		}
		public boolean IsEnable() {
			return m_Enable;
		}
		public double GetForceX() {
			return m_ForceX;
		}
		public double GetForceY() {
			return m_ForceY;
		}
	}
	
	public CGameObject() {
		m_Images = new CopyOnWriteArrayList<Image>();
		m_PositionX = 0;
		m_PositionY = 0;
		m_RotationDegree = 0;
		m_CurrentImagePointer = 0;
		m_VelocityX = 0;
		m_VelocityY = 0;
		m_FrictionX = 0.1;
 		m_FrictionY = 0.1;
 		m_AccelerationX = 0.15;
 		m_AccelerationY = 0.15;
 		m_AccelDirectionX = 0;
 		m_AccelDirectionY = 0;
 		m_Tolerance = 0.01;
 		m_ContinuousForceSettings = new CopyOnWriteArrayList<SContinuousForceSetter>();
	}
	
	public Image GetCurrentImage() {
		if(m_Images.size()==0) {
			return null;
		}
		return m_Images.get(m_CurrentImagePointer);
	}
	
	public double X() {
		return m_PositionX;
	}
	
	public double Y() {
		return m_PositionY;
	}
	
	public int GetWidth() {
		return m_Width;
	}
	
	public int GetHeight() {
		return m_Height;
	}
	
	public double GetVelocityX() {
		return m_VelocityX;
	}
	
	public double GetVelocityY() {
		return m_VelocityY;
	}
	
	public void SetPosition(double a_X,double a_Y) {
		m_PositionX = a_X;
		m_PositionY = a_Y;
	}
	
	public void AddImage(Image a_Image) {
		m_Images.add(a_Image);		
	}
	
	public void SetCurrentImage(int a_Index) {
		if(a_Index < m_Images.size()){
			m_CurrentImagePointer = a_Index;
			m_Width = m_Images.get(m_CurrentImagePointer).getWidth(null);
			m_Height = m_Images.get(m_CurrentImagePointer).getHeight(null);
		}
		
	}
	
	/**
	 * Direction > 0 or < 0 for direction positive or negative, = 0 for no acceleration
	 * @param a_Direction
	 */
	public void SetAccelDirectX(int a_Direction) {
		m_AccelDirectionX = a_Direction;
	}
	
	/**
	 * Direction > 0 or < 0 for direction positive or negative, = 0 for no acceleration
	 * @param a_Direction
	 */
	public void SetAccelDirectY(int a_Direction) {
		m_AccelDirectionY = a_Direction;		
	}
	
	public synchronized void AddForce(double a_X,double a_Y) {
		m_VelocityX += a_X;
		m_VelocityY += a_Y;
	}
	
	private void Accel() {
		m_VelocityX += m_AccelerationX * m_AccelDirectionX;
		m_VelocityY += m_AccelerationY * m_AccelDirectionY;
	}
	
	private void Fric() {
		if(Math.abs(m_VelocityX) > Math.abs( m_FrictionX * VelocityDirectionX()))
			m_VelocityX -= m_FrictionX * VelocityDirectionX();
		else
			m_VelocityX = 0;
		
		if(Math.abs(m_VelocityY) > Math.abs( m_FrictionY * VelocityDirectionY()))
			m_VelocityY -= m_FrictionY * VelocityDirectionY();
		else
			m_VelocityY = 0;
	}
	
	private int VelocityDirectionX() {
		return m_VelocityX == 0 ? 0 : (int) (m_VelocityX/Math.abs(m_VelocityX));
	}
	private int VelocityDirectionY() {
		return m_VelocityY == 0 ? 0 : (int) (m_VelocityY/Math.abs(m_VelocityY));
	}
	private void ApplyContinuousForces() {
		SContinuousForceSetter cfs;
		for(int i = 0; i < m_ContinuousForceSettings.size();i++) {
			cfs = m_ContinuousForceSettings.get(i);
			if(cfs.IsEnable()) {
				m_VelocityX += cfs.GetForceX();
				m_VelocityY += cfs.GetForceY();
			}
		}
	}
	
	public void UpdatePosition() {
		//Accel();
		ApplyContinuousForces();
		Fric();
		m_PositionX += m_VelocityX;
		m_PositionY += m_VelocityY;
	}
	
	public int GetAccelDirectX() {
		return m_AccelDirectionX > 0 ? 1 : -1;
	}
	
	public int GetAccelDirectY() {
		return m_AccelDirectionY > 0 ? 1 : -1;
	}
	
	public double GetPositionX() {
		return m_PositionX;
	}
	
	public double GetPositionY() {
		return m_PositionY;
	}
	
	public int AddContinuousForceSetting(double a_ForceX,double a_ForceY) {
		SContinuousForceSetter cfs = new SContinuousForceSetter(a_ForceX,a_ForceY);
		m_ContinuousForceSettings.add(cfs);
		return cfs.GetSerialNo();
	}
	
	public void RemoveContinuousForceSetting(int a_SerialNo) {
		for(int i = 0;i<m_ContinuousForceSettings.size();i++) {
			if(m_ContinuousForceSettings.get(i).GetSerialNo() == a_SerialNo) {
				m_ContinuousForceSettings.remove(i);
				break;
			}
		}
	}
	
	public void SetEnableContinuousForce(int a_SerialNo,boolean a_Enable) {
		for(int i = 0;i<m_ContinuousForceSettings.size();i++) {
			if(m_ContinuousForceSettings.get(i).GetSerialNo() == a_SerialNo) {
				m_ContinuousForceSettings.get(i).SetEnable(a_Enable);;
				break;
			}
		}
	}
}
