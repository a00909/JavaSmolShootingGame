package test3;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;

import test3.Test.Bullet_Move.Bullets;


public class Test {
	
	static JFrame frame=new JFrame("Simple Shooting Game");

	static Gpanel panel;
	static JLabel label;//main character
	static ImageIcon enemy1,enemy2;
	static double positionx,positiony=500,vecy=0,vecx=0
				  ,CU=1
			 	  ,unitplus=0.00009*CU,unitminus=0.00001*CU,lim=0.15*CU
			 	  ,bulletvec=0
			 	  ,reloadtime=65
			 	  ;
	static boolean movexp=false,moveyp=false,movexn=false,moveyn=false
				  ,anti=false,newbullet=false,isRunning=true;
	
	
	static Background BK;
	static STpanel statepanel;
	static Thread state01,
				  M_Char,
				  Yamazaki,BK_thread;
	static Opening open;
	static Starting start;
	static keyworksx keyx= new keyworksx();
	static keyworksy keyy= new keyworksy();
	static keyworkelse keyelse=new keyworkelse();
	static AudioInputStream audioInputStream;
	static Clip clip;
	static Bullet_Move Bullet_works; 
	static boolean isPlaying=false;
	
	
	static public void PlaySound(int sel) {
		
	   if(true){
		   try {
		    	if(!isPlaying&&sel==1){		    		
		    		clip.start();
		    		isPlaying=true;
		    	}
		    	if(isPlaying&&sel==0){
		    		clip.stop();		    		
		    		isPlaying=false;
		    	}
		    } catch(Exception ex) {
		        System.out.println("Error with playing sound.");
		        ex.printStackTrace();
		    }
	   }
	}
	
	public Test(){
	}
	
	public static void main(String[] args) {	
		
		frame.setSize(500, 600);
		// TODO Auto-generated method stub
		
		panel=new Gpanel();
		
	
		
	
		frame.add(panel);
		
		
		
		panel.setLayout(null);
		
		
		open=new Opening();
			
		
		
		
		frame.setVisible(true);		
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}	
	
	static class Opening implements Runnable{
		Thread open_th;
		
		JLabel opening;
		Opening(){
			
			//借放//////////////////////////////
			Bullet_works=new Bullet_Move(1000);
			   try {
			    	audioInputStream = AudioSystem.getAudioInputStream(new File("sound\\Keep_on_m2.wav").getAbsoluteFile());
			        clip = AudioSystem.getClip();		
			        clip.open(audioInputStream);
			    } catch(Exception ex) {
			        System.out.println("Error with playing sound.");
			        ex.printStackTrace();
			    }
			 
			
			keyx= new keyworksx();
			keyy= new keyworksy();
			keyelse=new keyworkelse();
			movexp=false;moveyp=false;movexn=false;moveyn=false
					  ;anti=false;newbullet=false;isRunning=true;
					  positiony=500;vecy=0;vecx=0
							  ;CU=1
						 	  ;unitplus=0.00009*CU;unitminus=0.00001*CU;lim=0.15*CU
						 	  ;bulletvec=0
						 	  ;reloadtime=65
						 	  ;
			///////////////////////////////
			opening = new JLabel("~OPENING~",JLabel.CENTER);
			open_th=new Thread(this);
			System.out.println("new Opening");
			
			
			opening.setSize(frame.getSize());
			
			opening.setLocation(0,0);			
			opening.setOpaque(true);//贒�逸詰abel遧ｧ���
			opening.setForeground(Color.BLACK);
			
			opening.setBackground(Color.BLACK);
			
			open_th.start();
			
			
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub			
			panel.add(opening,new Integer(500));
			int colorpara=1;
			boolean colorplus=true;
			Color fontcolor=new Color(colorpara,colorpara,colorpara);
			while(isRunning){
				fontcolor=new Color(colorpara,colorpara,colorpara);
				opening.setForeground(fontcolor);
				if(colorplus)
					colorpara++;
				if(!colorplus)
					colorpara--;
				panel.repaint();
//				try {
//					Thread.sleep(10);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				if(colorpara==255){
					colorplus=false;
				}
				if(colorpara==0){
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					panel.remove(opening);
					panel.repaint();
					
					start=new Starting();
					
					break;
				}					
			}
		}		
	}

	static class Starting extends KeyAdapter implements Runnable{
		Thread Starting = new Thread(this);
		JLabel Menu=new JLabel("",JLabel.CENTER);
		JLabel pressenter=new JLabel("PRESS ENTER TO START",JLabel.CENTER);
		JLabel backG_start=new JLabel("",JLabel.CENTER);
		
		Starting(){
			System.out.println("new Menu");
			panel.add(Menu,new Integer(500));			
			Menu.setSize(frame.getWidth(),frame.getHeight()+350);		
			Menu.setFont(new Font("arial", Font.BOLD, 28));
			Menu.setLocation(0,-350);			
		//	Menu.setBackground(Color.BLACK);
			Menu.setOpaque(false);//贒�逸詰abel遧ｧ���
			Menu.setForeground(Color.WHITE);
			Menu.setText("sImplE SHOOTING GAME");
				 
			panel.add( pressenter,new Integer(500));			
			pressenter.setSize(frame.getWidth(),50);					
			pressenter.setLocation(0,400);			
			pressenter.setBackground(new Color(50,50,50));			
			pressenter.setOpaque(true);//贒�逸詰abel遧ｧ���
			pressenter.setForeground(new Color(60,60,60));
			
			
			panel.add(backG_start,new Integer(500));			
			backG_start.setSize(frame.getWidth(),frame.getHeight());		
			//backG_start.setFont(new Font("arial", Font.BOLD, 28));
			backG_start.setLocation(0,0);		
			backG_start.setIcon(new ImageIcon(Test.class.getResource("background.jpg")));
			backG_start.setBackground(Color.BLACK);
			backG_start.setOpaque(true);//贒�逸詰abel遧ｧ���
			//backG_start.setForeground(new Color(60,60,60));
			
			Starting.start();
	
//        	Yamazaki=new Thread(new Enemy_Yamazaki());
//        	Yamazaki.start();			
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			if(!start){
				frame.addKeyListener(this);
				int i=60,trig=1;
				while(!start){
					if(trig==0)
						i--;
					if(trig==1)
						i++;
					if(i<=60){
						trig=1;
						 try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
					if(i==200){
						trig=0;
						 try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
					 pressenter.setForeground(new Color(i,i,i));
					 try {
							Thread.sleep(18);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}
			else if(start){
				int level=1;
				JLabel level1=new JLabel("",JLabel.CENTER);				
				panel.add(level1,new Integer(500));
				level1.setSize(frame.getSize());
				level1.setForeground(Color.WHITE);
				while(level<=1){
					if(!pause){
						level1.setText("LEVEL"+level);
												
						if(level==1){
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							level1.setText("START!");
							try {
								Thread.sleep(1700);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							panel.remove(level1);
							States_Jud(1);	
							level++;
						}
					}
					else{
						//pausing
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				
					
				}
				
				
			}
		}

		static boolean pause=false,start=false,dead=false;
		public static boolean win=false;
		public void keyPressed(KeyEvent e) {//�坤�翼遧ｮ�圉
	        int key1 = e.getKeyCode();
	        switch (key1) {        
	        //借放///////////////////////
	        case KeyEvent.VK_O:
	        	if(STpanel.life.length()>0)
	        	STpanel.life=STpanel.life.substring(0,STpanel.life.length()-1);
	        	break;
	        case KeyEvent.VK_P:
	        	STpanel.life+="|";
	        	break;
	        ///////////////////////////
	        }
		}
		public void keyReleased(KeyEvent e) {//�坤�翼遧ｮ�圉
	        int key1 = e.getKeyCode();
	        if(!dead){
	        switch (key1) {
	  
	     
	        case KeyEvent.VK_ENTER:
	        	if(!start){//start
	        		
	        		States_Jud(0);	
	        		try {
						Thread.sleep(450);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        		panel.remove(Menu);
	        		panel.remove(pressenter);
	        		panel.remove(backG_start);
	        		
	        		try {
						Thread.sleep(50);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        		Starting=new Thread(this);
	        		Starting.start();
		        	start=true;	        	
	        	}
	        	else if(start&&!pause){
	        		
	        		pause=true;	        		
	        	}
	        	else if(start&&pause){
	        		
	        		pause=false;	 
	        	}
	        	break;   
	       
	        }
	        }
		}		
	}
	
	static class STpanel implements Runnable{		
		static long time;
		private long inner_currenttime=0,inner_starttime=0;
		public static String life;
		static public String bosslife;
		final static int unit_boss_RLtime=70;
		static int bosslifeN;
		public static int score;
		
		
		boolean trig=true;
		long cooldowntime=0;
		static JLabel timer=new JLabel(),
			   scorer=new JLabel(),
			   bulleter=new JLabel(),
			   deader=new JLabel(),
			   lifer=new JLabel(),
			   bosslifer=new JLabel();
		public static boolean bornBossLife;
			   
		STpanel(){
			super();				
			time=0;
			bosslife="|||||||||||||||||";
			life="|||||||||||";
			score=0;
			bosslifeN=unit_boss_RLtime*17;
			bornBossLife =false;
		}
		public static void score_up(){
			int scoreuppara=600*600/(int)(positiony*positiony);
			score+=Math.pow(scoreuppara,2)*9;
			subbosslife(scoreuppara);
			
		}
		public static void subbosslife(int subpara){
			if(bosslifeN>unit_boss_RLtime*subpara&&!Starting.win){
				bosslifeN-=unit_boss_RLtime*subpara;
				bosslife=bosslife.substring(0,bosslife.length()-subpara);
			}
			else if(bosslifeN<=unit_boss_RLtime*subpara&&!Starting.win){
				bosslifeN=0;
				bosslife="";
			}
		}

		@Override
		public void run() {
			
			// TODO Auto-generated method stub
			timer.setSize(500, 30);
			panel.add(timer,new Integer(501));
			timer.setLocation(10, 0);			
			
			bulleter.setSize(120,30);
			panel.add(bulleter,new Integer(501));
			bulleter.setLocation(20, 490);
			
			scorer.setSize(120,30);
			panel.add(scorer,new Integer(501));
			scorer.setLocation(415,0);
			
			deader.setSize(120, 30);
			panel.add(deader,new Integer(501));
			deader.setLocation(416,30);
			
			lifer.setSize(600,30);
			panel.add(lifer,new Integer(501));
			lifer.setLocation(20, 520);;
			
			bosslifer.setSize(500,30);
			panel.add(bosslifer,new Integer(501));
			bosslifer.setVisible(false);
		//	bosslifer.setLocation(220, 50);;
			
			
			//========================================deader錡�
			deader.setText("");
			while(isRunning){
				if(!Starting.pause){
					
					
					if(!anti){
						timer.setForeground(Color.WHITE);
						bulleter.setForeground(Color.WHITE);
						scorer.setForeground(Color.WHITE);
						lifer.setForeground(Color.WHITE);
						deader.setForeground(Color.WHITE);
						bosslifer.setForeground(Color.WHITE);
					}
					if(anti){
						timer.setForeground(new Color((int)(Math.random()*2)*255,0,(int)(1-Math.random()*2)*255));
						timer.setLocation((int)(10+(Math.random()*10)*0.3), (int)((Math.random()*10)*0.3));	
						bulleter.setForeground(Color.BLACK);
						scorer.setForeground(Color.BLACK);
						lifer.setForeground(Color.BLACK);
						deader.setForeground(Color.BLACK);
						bosslifer.setForeground(Color.BLACK);
					}
					
					//借放/////////////////////////////////
					if(Starting.dead){
						JLabel[] shitai=new JLabel[24];
						double position[][]=new double[24][4];
						int shitaikatsu=0;
						for(int i=0;i<24;i++,shitaikatsu++){
							position[i][0]=positionx+5;
							position[i][1]=positiony+5;
							position[i][2]=((Math.random()*100)-50)*0.008;
							position[i][3]=((Math.random()*100)-50)*0.008;
							shitai[i]=new JLabel();
							if(!anti)
								shitai[i].setIcon(panel.main);
							if(anti)
								shitai[i].setIcon(panel.main2);
							shitai[i].setSize(5,5);
							shitai[i].setLocation((int)position[i][0],(int)position[i][1]);
							panel.add(shitai[i],new Integer(100));
						}
						int breaker=0;
						
						
						while(true){
							if(isRunning){
								for(int i=0;i<24;i++){
									position[i][0]+=(+0.22*Math.cos(1*(i+1)*Math.PI/12))*CU;
									position[i][1]+=(+0.23*Math.sin(1*(i+1)*Math.PI/12))*CU;	
									position[i][1]+=position[i][2]+0.6*CU*Math.sin(Math.atan2(positiony-Enemy_Yamazaki.centerpy,positionx-Enemy_Yamazaki.centerpx));
									position[i][0]+=position[i][3]+0.6*CU*Math.cos(Math.atan2(positiony-Enemy_Yamazaki.centerpy,positionx-Enemy_Yamazaki.centerpx));
									shitai[i].setLocation((int)position[i][0],(int)position[i][1]);
									
								}
								try {
									Thread.sleep((long)1);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								for(int i=0;i<24;i++){
									if(!((position[i][0]>550||position[i][0]<-50)&&(position[i][1]>650||position[i][1]<-50))){
										breaker=0;
										break;
									}
									else if(i==23){
										breaker=1;
										System.out.println("Dead");
									}
								}
							}
							if(!isRunning)
								breaker=1;
							if(breaker==1){
								for(int i=0;i<24;i++){
									panel.remove(shitai[i]);
								}
								break;
							}
						}
						
						
						
						break;
					}
					//////////////////////////
		
					
					
					//=======================================timer錡�
					inner_currenttime=System.currentTimeMillis();
					timer.setText("Time: "+String.valueOf(time-1));
					
					if(inner_currenttime>inner_starttime+999/CU)				
					{					
						time++;
						inner_starttime=System.currentTimeMillis();
					}				
					
					//========================================bulleter錡�
					bulleter.setText("Bullets: "+(999-Bullet_Move.bulletconsumed)+"/999");
					//========================================scorer
					scorer.setText("Score: "+score);
					//========================================lifer錡�
					lifer.setText("Life: "+life);
					if(life.equals("")){
						States_Jud(2);
						PlaySound(0);
						
					}
					//========================================bosslifer錡�
			
					bosslifer.setText("Life: "+bosslife);
					
					
					if(bosslifeN<unit_boss_RLtime*40&&time>12&&!Starting.win){
						
						bosslifeN++;
						if(bosslifeN%unit_boss_RLtime==0)
							bosslife+="|";
						
					}
					if(bornBossLife )
						bosslifer.setVisible(true);
					
					if(bosslife.equals("")){
						Starting.win=true;
						States_Jud(3);
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					
					if(deader.getText().equals("Attacked")){
						if(trig){
							cooldowntime=System.currentTimeMillis();						
							trig=false;
						}
						if((!trig)&&inner_currenttime>cooldowntime+300/CU){
							deader.setText("");
							panel.vibrate=false;
							trig=true;
						}
						
					}
					
					
					
				
				}
				else{
					//pausing
				}
				
				//========================================sleeper錡�
				try {
					Thread.sleep((long) (1));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	static class Gpanel extends JLayeredPane implements Runnable {//============諶ｨ釤乞桴鉸ｲ�坩賰｡逸ｫ�ｿｽ�ｿｽ釚､
		public boolean burst=false,vibrate=false;
		static Long startTime =(long) 0,currentTime=(long) 0;
		static ImageIcon main,main2;
	
		Gpanel(){			
			super();	
			System.out.println("new Game Panel");
			main=new ImageIcon(Test.class.getResource("cube.png"));
			main2=new ImageIcon(Test.class.getResource("cube_anti.png"));	
			this.setBackground(Color.BLACK);
			this.setOpaque(true);
			
			label=new JLabel();
			
			label.setLocation((int)(positionx=frame.getWidth()/2-8), (int)(positiony));	
			label.setVisible(false);
			
			label.setIcon(main);
			label.setSize(main.getIconWidth(),main.getIconHeight());
			
		}		
	
		double sleeptime=1;
		int turn=0;
		

	
		public void Vibrate(){			
			if(burst&&newbullet||vibrate){
				if(turn%4==0)
					positionx+=2;
				if(turn%4==1)
					positionx-=2;
				if(turn%4==2)
					positiony+=2;
				if(turn%4==3)
					positiony-=2;
				
				turn++;
				if(turn>=4){
					turn=0;
				}
			}
		}
		public void Friction() throws InterruptedException{
			if(Test.vecx>0)//friction
			{
				Test.vecx-=Test.unitminus;
				Thread.sleep((long) sleeptime);
			}
			if(Test.vecy>0)
			{
				Test.vecy-=Test.unitminus;
				Thread.sleep((long) sleeptime);
			}
			if(Test.vecx<0)
			{
				Test.vecx+=Test.unitminus;
				Thread.sleep((long)sleeptime);
			}
			if(Test.vecy<0)
			{
				Test.vecy+=Test.unitminus;
				Thread.sleep((long)sleeptime);
			}
		}
		public void Move() throws InterruptedException{
			if(Test.movexp&&Test.vecx*Test.CU<Test.lim)//move
			{				
				Test.vecx+=Test.unitplus;
				Thread.sleep((long)sleeptime);
			}
			if(Test.movexn&&Test.vecx*Test.CU>-Test.lim)
			{
				Test.vecx-=Test.unitplus;
				Thread.sleep((long)sleeptime);
			}
			if(Test.moveyp&&Test.vecy*Test.CU<Test.lim)
			{
				Test.vecy+=Test.unitplus;
				Thread.sleep((long)sleeptime);
			}
			if(Test.moveyn&&Test.vecy*Test.CU>-Test.lim)
			{
				Test.vecy-=Test.unitplus;		
				Thread.sleep((long)sleeptime);
			}
		}
		public void Boundary(){
			double tempx,tempy;
			if(Test.positionx>=Test.panel.getWidth()-main.getIconWidth()||Test.positionx<=0 )	//bound	
			{
				
				if(vecx>0)
					positionx-=1;
				else
					positionx+=1;
				Test.vecx=-Test.vecx;
				
			}
			if(Test.positiony>=Test.panel.getHeight()-main.getIconHeight()||Test.positiony<=0 )			
			{	
				if(vecy>0)
					positiony+=1;
				else
					positiony-=1;
							
				Test.vecy=-Test.vecy;
				
			}
		}
		public void The_World(){
			if(Test.anti){    		//the world
		 	 	Test.CU=0.25;
	        	Test.panel.setBackground(Color.WHITE);	        	
	        	Test.label.setIcon(main2);	        	
	    	}
		 	if(!Test.anti){
		 		Test.CU=1;
	        	Test.panel.setBackground(Color.BLACK);	        	
	        	Test.label.setIcon(main);	        	
		 	}		
		}
		public void Set_Parameters(){
			Test.unitplus=0.0025*Test.CU;//??
        	Test.unitminus=0.0007*Test.CU;
        	Test.lim=0.5*Test.CU;       
        	sleeptime= (0.000001/CU);
		}
		Thread[] thread_bullet=new Thread[9999];
//		public void Shoot(){
//			if(newbullet){//bullet
//		 		
//		 		currentTime=System.currentTimeMillis();
//		 		
//		 		if(currentTime>startTime+(reloadtime/(CU))){
//		 			try{
//		 				keyworkelse.bullets[keyworkelse.bulletnum]=new Bullets();
//		 				thread_bullet[keyworkelse.bulletnum]= new Thread(keyworkelse.bullets[keyworkelse.bulletnum]);
//			        	thread_bullet[keyworkelse.bulletnum].start();	        	
//			        	keyworkelse.bulletnum++;
//			        	if(keyworkelse.bulletnum>=9998)
//			        		keyworkelse.bulletnum=0;
//		 			}
//		 			catch(Exception e10){
//		 				System.out.println("Something wrong");
//		 			}
//		        	System.out.println(keyworkelse.bulletnum);	
//		        	startTime=System.currentTimeMillis();
//		 		}       	
//		 	}		 	
//		}
//		public void paint(Graphics g){
//			super.paint(g);	
//			Friction();
//			Boundary();
//		The_World();
//		Set_Parameters();		
//		Shoot(); 
//		 	Move();
//		 	
//			super.repaint();
//		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(isRunning){
				
				if(!Starting.pause){
					
					
					try {
						Friction();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}				
					try {
						Move();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(!Starting.dead){
						Test.label.setLocation((int)(Test.positionx+=Test.vecx*Test.CU), (int)(Test.positiony-=Test.vecy*Test.CU));
						Vibrate();
						Boundary();
						The_World();
						Set_Parameters();		
						
					}
//				
					
				}
				else{
					//pausing
				}
				frame.repaint();
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	
	static class Bullet_Move extends JLabel implements Runnable{
		
		public static long bulletconsumed=0;

		static LinkedList<Bullet_data> bulletdata;
		
//		int bulletnum;
		int y=1,x=0;
//		int currentbullet=0,lastbullets=0;
		long currentTime,startTime;
		double para1=40,para2=0.01;
		static Image bullet=null;
	//	static boolean paint_or_loop=false;//true is loop, false is paint
//		Bullets[] bullets;
//		double b_position[][]// 0=x ,1=y
//				,b_speedpara[];
//		boolean ifinpanel[];
		Graphics2D g2d;
		Thread bulletThread;
		Bullet_Move(int totalbullets){
			super();
			bulletdata=new LinkedList<Bullet_data>();
			this.setOpaque(false);
			this.setSize(frame.getSize());
			panel.add(this,new Integer(100));
			bulletThread=new Thread(this);
//			bulletnum=totalbullets;
//			bullets=new Bullets[bulletnum];
//			b_position=new double[bulletnum][2];
//			b_speedpara=new double[bulletnum];
//			bullets=new Bullets[bulletnum];
//			ifinpanel=new boolean[bulletnum];
			bulletThread.start();
		}
		public static void CleanList(){
			bulletdata=null;
		}
		
		public void paintComponent(Graphics g){
			
			 
				 try{
					 super.paintComponent(g);
					 g2d = (Graphics2D)g;			 
					 for(int i=0;i<bulletdata.size();i++){
						 g2d.drawImage(bullet,(int)(bulletdata.get(i).position[x]),(int)(bulletdata.get(i).position[y]),Bullets.ic_bullet.getIconWidth(),Bullets.ic_bullet.getIconHeight(),null);
					
					 }
				 }catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						System.out.println("Wrong");
					}
				 
				
			
			 			 
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(isRunning){
				currentTime=System.currentTimeMillis();	
				if(!Starting.pause){	
					
					if(newbullet){//新子彈
									 		
				 		if(currentTime>startTime+(reloadtime/(CU))){
				 			bulletconsumed++;
				 			bulletdata.add(new Bullet_data((Math.random()*10-5)*0.5+positionx+0.5*Gpanel.main.getIconWidth()-0.5*Bullets.ic_bullet.getIconWidth(),positiony-8,(Math.random()*para1)*para2-(Math.random()*para1)*para2, (Math.random()*para1)*para2-(Math.random()*para1)*para2));

				 			bulletdata.get(bulletdata.size()-1).bullet=new Bullets();
//				 			b_speedpara[currentbullet]=(Math.random()*para1)*para2-(Math.random()*para1)*para2;
//				 			b_position[currentbullet][x]=(Math.random()*10-5)*0.5+positionx+0.5*panel.main.getIconWidth()-0.5*bullets[currentbullet].ic_bullet.getIconWidth();
//				 			b_position[currentbullet][y]=positiony-8;
//				 			bulletdata.get(bulletdata.size()-1).bullet.setLocation((int)(b_position[currentbullet][x]),(int)(b_position[currentbullet][y]));
//				 			bullets[currentbullet].setIcon(Bullets.ic_bullet);				 			
//				 			panel.add(bulletdata.get(bulletdata.size()-1).bullet,new Integer(100));				 			
//				 			System.out.println("new bullet :"+currentbullet);
//				 			currentbullet++;
//				 			if(currentbullet==bulletnum-1)
//			 					currentbullet=0;					        	
				        	startTime=System.currentTimeMillis();
				 		}    				 		
					}//新子彈_______________
					
					for(int i=0;i<bulletdata.size();i++){						
//					System.out.println("bullet ("+currentbullet+") moves");
//						if(bulletdata.get(i).bullet.clip_kick.getFramePosition()>=bulletdata.get(i).bullet.clip_kick.getFrameLength()-1)							
						if(anti)
							bullet=Bullets.ic_bullet_anti.getImage() ;
						if(!anti)
							bullet=Bullets.ic_bullet.getImage() ;

						
						bulletdata.get(i).position[y]-=(1-bulletdata.get(i).speedpara[y])*CU;
						bulletdata.get(i).position[x]+=0.5*bulletdata.get(i).speedpara[x]*CU;
//						bulletdata.get(i).bullet.setLocation((int)(bulletdata.get(i).position[x]),(int)(bulletdata.get(i).position[y]));
						 if(bulletdata.get(i).position[y]<=0||(!Starting.win&&bulletdata.get(i).position[x]>Enemy_Yamazaki.centerpx-20&&bulletdata.get(i).position[x]<Enemy_Yamazaki.centerpx+40&&bulletdata.get(i).position[y]>Enemy_Yamazaki.centery-1&&bulletdata.get(i).position[y]<Enemy_Yamazaki.centery+1)){
								if((!Starting.win&&bulletdata.get(i).position[x]>Enemy_Yamazaki.centerpx-20&&bulletdata.get(i).position[x]<Enemy_Yamazaki.centerpx+40&&bulletdata.get(i).position[y]>Enemy_Yamazaki.centery-1&&bulletdata.get(i).position[y]<Enemy_Yamazaki.centery+1)){
									STpanel.score_up();
								}
								bulletdata.remove(i);							
//								panel.remove(bulletdata.get(i).bullet);		
//								System.out.println("bullet removed from list");							
//								System.out.println("done");
//								System.out.println("current list size: "+bulletdata.size());
//								
							}
					
						
					
						
					}	
				}
				else{}
				//this.repaint();
				try {
					Thread.sleep((long) (1));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		static class Bullet_data{
			private Bullets bullet;
			private double position[]=new double[2];
			private double speedpara[]=new double[2];
			Bullet_data(double position_x,double position_y,double speedpara_x,double speedpara_y){
				position[0]=position_x;
				position[1]=position_y;				
				speedpara[0]=speedpara_x;
				speedpara[1]=speedpara_y;
			}
		}	
		
		
		static class Bullets implements Runnable{
			static ImageIcon ic_bullet=new ImageIcon(Test.class.getResource("bullet.png")); ;
			static ImageIcon ic_bullet_anti=new ImageIcon(Test.class.getResource("bullet_anti.png")); ;
//			double b_positionx,b_positiony;
//			boolean inisRunning=true;
			static boolean firstnew=true;
			static int musicbuff=0;
			static AudioInputStream audioInputStream_kick;
			static Clip[] clip_kick;
			int clipnum=40;
			static int currentkick=0;
			Thread Close_kick=new Thread(this);
			AudioClip kick;
			Bullets(){
				
				super();
				try {
					if(firstnew){
						Close_kick=new Thread(this);
						clip_kick=new Clip[clipnum];//宣告定量音檔
						for(int i=0;i<clipnum;i++){//初始化音檔
							audioInputStream_kick = AudioSystem.getAudioInputStream(new File("sound\\kick4.wav").getAbsoluteFile());
							clip_kick[i] = AudioSystem.getClip();	
							clip_kick[i].open(audioInputStream_kick);
						}			        
					firstnew=false;
					}
			     } catch(Exception ex) {
			        System.out.println("Error with playing sound.");
			        ex.printStackTrace();
			    }
				musicbuff++;
				if(musicbuff==4*4/reloadtime)//避免爆音
					musicbuff=0;				
				clip_kick[currentkick].stop();					
				clip_kick[currentkick].setFramePosition(0);

				Close_kick.start();
//				this.setIcon(ic_bullet);
//				this.setSize(ic_bullet.getIconWidth(),ic_bullet.getIconHeight());
//				kickclose.start();
//				this.setVisible(false);
//				panel.add(this,new Integer(100));
//				this.setLocation((int)(b_positionx=(Math.random()*10-5)*0.5+positionx+0.5*panel.main.getIconWidth()-0.5*ic_bullet.getIconWidth()),(int)(b_positiony=positiony-8));		
			}
//			double para1=40,para2=0.01;
		
			@Override
			
			public void run() {
				   
		    	if(panel.burst&&musicbuff%(4*4/reloadtime)==0){
		    		clip_kick[currentkick].start();
		    		currentkick++;					
				}
				if(!panel.burst){					
					clip_kick[currentkick].start();	
					currentkick++;
				}				
				if(currentkick==clipnum)
					currentkick=0;
//				// TODO Auto-generated method stub
//				double speedpara=(Math.random()*para1)*para2-(Math.random()*para1)*para2;//				
//				System.out.println("run done");			
//				while(isRunning&&inisRunning){	
//					this.setVisible(true);
//					if(!Starting.pause){					
//						if(anti)
//							this.setIcon(ic_bullet_anti);
//						if(!anti)
//							this.setIcon(ic_bullet);
//						this.setLocation((int)(b_positionx),(int)(b_positiony));
//						b_positiony-=(1-speedpara)*CU;	
//						if(!Starting.win&&b_positionx>Enemy_Yamazaki.centerpx-20&&b_positionx<Enemy_Yamazaki.centerpx+40&&b_positiony>Enemy_Yamazaki.centery-1&&b_positiony<Enemy_Yamazaki.centery+1){
//							inisRunning=false;
//							panel.remove(this);	
//							STpanel.score_up();//							
//							break;
//						}
//						if(b_positiony<=0){
//							inisRunning=false;
//							panel.remove(this);					
//							break;
//						}//						
//					}
//					else{
//						//pausing
//					}
//					try {
//						Thread.sleep((long) (1));
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
			}		
			
		}
		
	}
	
	static class Enemy_Yamazaki extends JLabel implements Runnable{
		static ImageIcon enemy1;
		static ImageIcon enemy2;
		double Yamazaki_px=0,Yamazaki_py=0;
		Thread Boss=new Thread(this);
		static public Image Im_en;
		Enemy_Yamazaki(){
			
			
			super();
			centerx=250;centery=-100;centervecy=0;
			enemy1=new ImageIcon(Test.class.getResource("lanpi.png"));
			enemy2=new ImageIcon(Test.class.getResource("yamazaki_anti.png"));
			centerpx=centerx-10;//+enemy1.getIconWidth()*0.5;
			centerpy=109;
			
			Im_en=null;
			//this.setIcon(enemy1);
			this.setSize(frame.getWidth(),frame.getHeight());	
			panel.add(this,new Integer(200));
			this.setVisible(false);
			this.setLocation(0,(int)0);
			Boss.start();
			
			
			radius=Math.sqrt(Math.pow(enemy1.getIconWidth(), 2)+Math.pow(enemy1.getIconHeight(), 2));			
	
			
		}
		public void paintComponent(Graphics g){
			 super.paintComponent(g);
			 Graphics2D g2d;
			 g2d = (Graphics2D)g;
			 
			

			 
			 this.setIcon(null);
			 g2d.rotate(-angle-Math.PI*1/4,Yamazaki_px,Yamazaki_py);	
			 
			 g2d.drawImage(Im_en,(int)(Yamazaki_px),(int)(Yamazaki_py),enemy1.getIconWidth(),enemy1.getIconHeight(),null);

			 
			}
		double angle=0,radius,anglevec=0;

		static double centerx=250,centery=-100,centervecy=0,centerpx,centerpy;
		
		static class Attack implements Runnable{
			EnemyBullets[] Bullets=new EnemyBullets[999];
			double BNum,exBNum=0;
			int type;
			Thread atk=new Thread(this);
			public void attack(int type_){
				type=type_;
				atk=new Thread(this);
				atk.start();
			}
			Attack(){
				super();
				BNum=0;
			}
			int Ctime,Stime,x=0,y=1;
			double b_p_t=1,atktimes=1,b_v,anglepara=5,sleeptime=100,bigsleeptime=600,ranagnle;
			
		
			@Override      //總攻擊次數               //每圈攻擊次數
			
			public void run() {
				// TODO Auto-generated method stub
				
				ranagnle=(Math.random()*30)/10;
				int i;
				while(isRunning){					
					if(!Starting.pause){
						if(type==1){	
							
							b_p_t=6;
							atktimes=20;
							anglepara=5;
							b_v=0.1+0.03*ranagnle;
							Enemybullets_Move move=new Enemybullets_Move((int)(b_p_t*atktimes));
							
							int inneri=0;
							sleeptime=475;
							for(i=0;i<b_p_t*atktimes;){
								if(!Starting.pause){
									if(Starting.win||!isRunning){
										break;
									}
									move.e_b_data.get(i).ebullet.angle=(inneri)/(anglepara*b_p_t)+0.09*ranagnle;//max=1/one round									
									move.e_b_data.get(i).ebullet.b_vecx=b_v;
									move.e_b_data.get(i).ebullet.b_vecy=b_v;
									if((i+1)%(b_p_t)==0){
										inneri=0;
										ranagnle=(Math.random()*30)/10;
										for(int ii=0;ii<sleeptime&&isRunning;ii++){
											try {
												Thread.sleep((long) (1/CU));
											} catch (InterruptedException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									}
									BNum++;
									if(BNum==999){
										BNum=0;		
									}										
									i++;
									inneri++;				
								}	
								else{}
								try {
									Thread.sleep((long) (1));
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							for(int ii=0;ii<bigsleeptime;ii++){
								try {
									Thread.sleep((long) (1/CU));
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							System.out.println("Bullets consumed: "+BNum);
							canattack=true;	
							break;
						}	
						if(type==0){
							b_p_t=18;
							atktimes=4;
							anglepara=1;
							sleeptime=200;
							b_v=0.1+0.03*ranagnle;
							ranagnle=0;
							Enemybullets_Move move=new Enemybullets_Move((int)(b_p_t*atktimes));
							for(i=0;i<b_p_t*atktimes;){
								if(!Starting.pause){
									if(Starting.win||!isRunning){
										break;
									}
								
									move.e_b_data.get(i).ebullet.angle=(i)/(anglepara*b_p_t)+ranagnle;//max=1/one round
								
									move.e_b_data.get(i).ebullet.b_vecx=b_v;
									move.e_b_data.get(i).ebullet.b_vecy=b_v;
								if((i+1)%(b_p_t)==0){
									ranagnle-=0.03;
									for(int ii=0;ii<sleeptime&&isRunning;ii++){
										try {
											Thread.sleep((long) (1/CU));
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}
								BNum++;
								if(BNum==999){
									BNum=0;		
									
								}	
								try {
									Thread.sleep((long) (1));
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								i++;
							}
								else{
									try {
										Thread.sleep((long) (1));
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								
																								
							}
							for(int ii=0;ii<bigsleeptime;ii++){
								try {
									Thread.sleep((long) (1/CU));
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							System.out.println("Bullets consumed: "+BNum);

							canattack=true;	
							break;
						}
						if(type==2){	
							b_p_t=13;
							atktimes=5;
							anglepara=1;
							sleeptime=270;
							b_v=0.1+0.03*ranagnle;
							Enemybullets_Move move=new Enemybullets_Move((int)(b_p_t*atktimes));
							int angleplus=1;
							for(i=0;i<b_p_t*atktimes;){
								if(!Starting.pause){
									if(Starting.win||!isRunning){
										break;
									}
									move.e_b_data.get(i).ebullet.angle=(i)/(anglepara*b_p_t)+angleplus*2*Math.PI*(360/5)/360;//max=1/one round
									move.e_b_data.get(i).ebullet.b_vecx=b_v;
									move.e_b_data.get(i).ebullet.b_vecy=b_v;
								if((i+1)%(b_p_t)==0){
									angleplus++;
									ranagnle=(Math.random()*30)/10;
									for(int ii=0;ii<sleeptime&&isRunning;ii++){
										try {
											Thread.sleep((long) (1/CU));
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}
								BNum++;
								if(BNum==999){
									BNum=0;		
									
								}	
								try {
									Thread.sleep((long) (1));
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								i++;
								}
								else{
									try {
										Thread.sleep((long) (1));
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
																								
							}
							for(int ii=0;ii<bigsleeptime;ii++){
								try {
									Thread.sleep((long) (1/CU));
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							System.out.println("Bullets consumed: "+BNum);

							canattack=true;	
							break;
						}
						if(type==3){	
							b_p_t=10;
							atktimes=5;
							anglepara=1;
							sleeptime=150;
							b_v=0.2+0.03*ranagnle;
							Enemybullets_Move move=new Enemybullets_Move((int)(b_p_t*atktimes));
							int angleplus=1;
							for(i=0;i<b_p_t*atktimes;){
								if(!Starting.pause){
									if(Starting.win||!isRunning){
										break;
									}
									move.e_b_data.get(i).ebullet.angle=(i)/(anglepara*b_p_t)+angleplus*2*Math.PI*(360/5)/360;//max=1/one round
									
									move.e_b_data.get(i).ebullet.b_vecx=b_v;
									move.e_b_data.get(i).ebullet.b_vecy=b_v;
								if((i+1)%(b_p_t)==0){
									angleplus-=2;
									ranagnle=(Math.random()*30)/10;
									for(int ii=0;ii<sleeptime&&isRunning;ii++){
										try {
											Thread.sleep((long) (1/CU));
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}
								BNum++;
								if(BNum==999){
									BNum=0;		
									
								}	
								try {
									Thread.sleep((long) (1));
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								i++;
								}
								else{
								try {
									Thread.sleep((long) (1));
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								}
								
																								
							}
							for(int ii=0;ii<bigsleeptime;ii++){
								try {
									Thread.sleep((long) (1/CU));
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							System.out.println("Bullets consumed: "+BNum);
							canattack=true;	
							break;
						}						
						if(type==4){	
							b_p_t=1;
							atktimes=15;
							anglepara=1;
							sleeptime=100;
							bigsleeptime=500;
							b_v=0.65+0.03*ranagnle;
							Enemybullets_Move move=new Enemybullets_Move((int)(b_p_t*atktimes));
							for(i=0;i<b_p_t*atktimes;){
								if(!Starting.pause){
									if(Starting.win||!isRunning){
										break;
									}
								
								//if(Math.atan((positiony-Enemy_Yamazaki.centerpy)/(positionx-Enemy_Yamazaki.centerpx))<=Math.PI*3/4&&(positiony-Enemy_Yamazaki.centerpy)/(positionx-Enemy_Yamazaki.centerpx)>=-Math.PI)
									move.e_b_data.get(i).ebullet.angle=Math.atan2((positiony-Enemy_Yamazaki.centerpy),(positionx-Enemy_Yamazaki.centerpx))/(2*Math.PI);//max=1/one round
//								else
//									//Bullets[(int)BNum].angle=(Math.atan((positiony-Enemy_Yamazaki.centerpy)/(positionx-Enemy_Yamazaki.centerpx))+Math.PI)/(2*Math.PI);;
//									Bullets[(int)BNum].angle=0;
									move.e_b_data.get(i).ebullet.b_vecx=b_v;
									move.e_b_data.get(i).ebullet.b_vecy=b_v;
								if((i+1)%(b_p_t)==0){
									ranagnle=(Math.random()*30)/10;
									for(int ii=0;ii<sleeptime&&isRunning;ii++){
										try {
											Thread.sleep((long) (1/CU));
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}
								BNum++;
								if(BNum==999){
									BNum=0;		
									
								}	
								try {
									Thread.sleep((long) (1));
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								i++;
								}
								else{
									try {
										Thread.sleep((long) (1));
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
																								
							}
							for(int ii=0;ii<bigsleeptime;ii++){
								try {
									Thread.sleep((long) (1/CU));
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							System.out.println("Bullets consumed: "+BNum);

							canattack=true;	
							break;
						}		
					
					}
					
					else{}
										
					try {
						Thread.sleep((long) (1));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
			}
		}
		static boolean canattack=true;
		int attacknum=0;
		@Override
		
		public void run() {
			// TODO Auto-generated method stub
			
			System.out.println("new enemy");
			
			Attack atk=new Attack();
			this.setVisible(true);
			boolean jud=true;
			int counter=0,timecounter=0;
			while(isRunning){
				if(!Starting.pause){
				
					if(!Starting.win){
						if(anti)
							Im_en=enemy2.getImage();
						if(!anti)
							Im_en=enemy1.getImage();
					}
					if(STpanel.time>=10&&centery<=10){
						centervecy+=0.0015;
						
					}
					else if(centery>10&&centervecy>0){
						centervecy-=0.0015;
						
					}
					else if(centery>20&&anglevec==0){
						centervecy=0;	
						
						if(timecounter<1000)
							timecounter++;
						if(timecounter==1000)
							anglevec=0.0001;
					}				
					else if(anglevec>=0.0001&&anglevec<=0.015){
						anglevec+=0.00001*CU;
					}
					if(STpanel.time>=16&&!Starting.dead&&!Starting.win&&isRunning){
						if(canattack&&attacknum%2==1){						
							atk.attack((int)(4));
							attacknum++;
							canattack=false;
						}
						else if(canattack){						
							atk.attack((int)(Math.random()*4));//Math.random()*4
							attacknum++;
							canattack=false;
							
						}
					}
					
					//借放/////////////////////////////////
					if(Starting.win){
						JLabel[] shitai=new JLabel[24];
						double position[][]=new double[24][4];
						double refpositionx=positionx,refpositiony=positiony;
						for(int i=0;i<24;i++){
							position[i][0]=Enemy_Yamazaki.centerpx;
							position[i][1]=Enemy_Yamazaki.centerpy;
							position[i][2]=((Math.random()*100)-50)*0.008;
							position[i][3]=((Math.random()*100)-50)*0.008;
							shitai[i]=new JLabel();
							if(!anti)
								shitai[i].setIcon(panel.main);
							if(anti)
								shitai[i].setIcon(panel.main2);
							shitai[i].setSize(8,8);
							shitai[i].setLocation((int)position[i][0],(int)position[i][1]);
							panel.add(shitai[i],new Integer(100));
						}
						int breaker=0;
						
						
						while(true){
							if(isRunning){
								for(int i=0;i<24;i++){
									position[i][0]+=(+0.22*Math.cos(1*(i+1)*Math.PI/12))*CU;
									position[i][1]+=(+0.23*Math.sin(1*(i+1)*Math.PI/12))*CU;	
									position[i][1]+=CU*position[i][2]+0.6*CU*Math.sin(Math.atan2(-(refpositiony-Enemy_Yamazaki.centerpy),-(refpositionx-Enemy_Yamazaki.centerpx)));
									position[i][0]+=CU*position[i][3]+0.6*CU*Math.cos(Math.atan2(-(refpositiony-Enemy_Yamazaki.centerpy),-(refpositionx-Enemy_Yamazaki.centerpx)));
									shitai[i].setLocation((int)position[i][0],(int)position[i][1]);
									if(!anti)
										shitai[i].setIcon(panel.main);
									if(anti)
										shitai[i].setIcon(panel.main2);
									
								}
								try {
									Thread.sleep((long)1);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								for(int i=0;i<24;i++){
									if(!((position[i][0]>550||position[i][0]<-50)&&(position[i][1]>650||position[i][1]<-50))){
										breaker=0;
										break;
									}
									else if(i==23){
										breaker=1;
										System.out.println("Enemy Dead");
									}
								}
							}
							if(!isRunning)
								breaker=1;
							if(breaker==1){
								for(int i=0;i<24;i++){
									panel.remove(shitai[i]);
									}
								break;
							}
						}
						
						
						
						break;
					}
					//////////////////////////
				
					STpanel.bosslifer.setLocation(200, (int)Enemy_Yamazaki.centery-60);
					angle+=anglevec*CU;
					
					Yamazaki_py=(centery+=centervecy)+radius/2*Math.sin(angle);
					Yamazaki_px=centerx-radius/2*Math.cos(angle);
				}
				else{}
			
			
				
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	static class Enemybullets_Move extends JLabel implements Runnable{
		public LinkedList<E_Bullets_Data> e_b_data;
		Graphics2D g2d;
		static Image e_bullet=EnemyBullets.ball.getImage();;
		int bulletnum;
		Thread bmove;
		Enemybullets_Move(int bnum){
		super();
		bmove=new Thread(this);
		e_b_data=new LinkedList<E_Bullets_Data>();
		bulletnum=bnum;
		for(int i=0;i<bnum;i++){
			e_b_data.add(new E_Bullets_Data());			
		}
			
		this.setSize(frame.getSize());
		this.setOpaque(false);
		panel.add(this,new Integer(100));
		System.out.println("There are "+bnum+" bullets");
		bmove.start();
		}
		
		
		
		public void paintComponent(Graphics g){
			 super.paintComponent(g);
			 
				 try{
					 g2d = (Graphics2D)g;			 
					 for(int i=0;i<e_b_data.size();i++){
						 if(e_b_data.get(i).ebullet.b_vecx==0){
							 g2d.drawImage(null,(int)(e_b_data.get(i).ebullet.originx),(int)(e_b_data.get(i).ebullet.originy),(EnemyBullets.ball.getIconWidth()),EnemyBullets.ball.getIconHeight(),null);

						 }
						 else
							 g2d.drawImage(e_bullet,(int)(e_b_data.get(i).ebullet.originx),(int)(e_b_data.get(i).ebullet.originy),(EnemyBullets.ball.getIconWidth()),EnemyBullets.ball.getIconHeight(),null);
//						 System.out.println("draw");
					 }
					 
				 }catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					 	System.out.println("Wrong with draw");
					}
		}
		private void close(){
			System.out.println("Size :"+bulletnum);
			
			e_b_data=new LinkedList<E_Bullets_Data>();
			panel.remove(this);
			System.gc();
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(isRunning){
				if(!Starting.pause){
				
					if(anti){
						e_bullet=EnemyBullets.ball_anti.getImage();
					}
					if(!anti){
						e_bullet=EnemyBullets.ball.getImage();
					}
					
					for(int i =0;i<e_b_data.size();i++){
						
					//	e_b_data.get(i).ebullet.setVisible(true);
						e_b_data.get(i).ebullet.originx+=e_b_data.get(i).ebullet.b_vecx*CU*Math.cos(2*e_b_data.get(i).ebullet.angle*Math.PI);
						e_b_data.get(i).ebullet.originy+=e_b_data.get(i).ebullet.b_vecy*CU*Math.sin(2*e_b_data.get(i).ebullet.angle*Math.PI);
						
						
					//	e_b_data.get(i).ebullet.setVisible(true);
						if((!Starting.dead)&&e_b_data.get(i).ebullet.jud_on&&Math.sqrt(Math.pow((positionx+panel.main.getIconWidth()/2)-(e_b_data.get(i).ebullet.originx+e_b_data.get(i).ebullet.ball.getIconWidth()/2),2)+Math.pow((positiony+panel.main.getIconHeight()/2)-(e_b_data.get(i).ebullet.originy+e_b_data.get(i).ebullet.ball.getIconHeight()/2),2))<=16){
							System.out.println("Game over");
							e_b_data.get(i).ebullet.jud_on=false;
							vecy-=0.9*Math.sin(Math.atan2((positiony+0.5*Gpanel.main.getIconHeight())-(e_b_data.get(i).ebullet.originy+0.5*e_b_data.get(i).ebullet.ball.getIconHeight()),(positionx+0.5*Gpanel.main.getIconWidth())-(e_b_data.get(i).ebullet.originx+0.5*e_b_data.get(i).ebullet.ball.getIconWidth())));
							vecx+=0.9*Math.cos(Math.atan2((positiony+0.5*Gpanel.main.getIconHeight())-(e_b_data.get(i).ebullet.originy+0.5*e_b_data.get(i).ebullet.ball.getIconHeight()),(positionx+0.5*Gpanel.main.getIconWidth())-(e_b_data.get(i).ebullet.originx+0.5*e_b_data.get(i).ebullet.ball.getIconWidth())));
							statepanel.deader.setText("Attacked");
							if(statepanel.life.length()>=1)
								statepanel.life=statepanel.life.substring(0, statepanel.life.length()-1);
							
						}
						if((!Starting.dead)&&!e_b_data.get(i).ebullet.jud_on&&Math.sqrt(Math.pow((positionx+panel.main.getIconWidth()/2)-(e_b_data.get(i).ebullet.originx+e_b_data.get(i).ebullet.ball.getIconWidth()/2),2)+Math.pow((positiony+panel.main.getIconHeight()/2)-(e_b_data.get(i).ebullet.originy+e_b_data.get(i).ebullet.ball.getIconHeight()/2),2))>16){
							e_b_data.get(i).ebullet.jud_on=true;
						}
						if(e_b_data.get(i).ebullet.originx>=550||e_b_data.get(i).ebullet.originx<=-50||e_b_data.get(i).ebullet.originy>650||e_b_data.get(i).ebullet.originy<-50){
							e_b_data.get(i).ebullet.originx=Enemy_Yamazaki.centerpx;
							e_b_data.get(i).ebullet.originy=Enemy_Yamazaki.centerpy;
							e_b_data.get(i).ebullet.b_vecx=0;
							e_b_data.get(i).ebullet.b_vecy=0;
							bulletnum--;
							System.out.println("Size :"+bulletnum);
							
						}						
					}
					if(bulletnum==0){	
						close();					
						break;
					}
					if(Starting.win){
						boolean breaker=false;
						for(int i=0;i<e_b_data.size();i++){
							if(e_b_data.get(i).ebullet.b_vecx!=0){
								break;
							}
							else if(i==e_b_data.size()-1){
								close();
							}
						}
					}
					
					
				}
				else{}
				try {
					Thread.sleep((long)(1));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		//inner class////////
		
		
		static class E_Bullets_Data{
			static final int x=0,y=1;
			private double[] speedpara=new double[2];
			private double[] position=new double[2];
			private double angle;
			private EnemyBullets ebullet;
			E_Bullets_Data(EnemyBullets eb,double px,double py,double sx,double sy){
				ebullet=eb;
				position[x]=px;
				position[y]=py;
				speedpara[x]=sx;
				speedpara[y]=sy;				
			}
			E_Bullets_Data(){
				ebullet=new EnemyBullets();
				position[x]=0;
				position[y]=0;
				speedpara[x]=0;
				speedpara[y]=0;	
				angle=0;
			}
		}
		/////////////////////
		
	}
	
	static class EnemyBullets extends JLabel{
		static ImageIcon ball=new ImageIcon(Test.class.getResource("ball.png"));
		static ImageIcon ball_anti=new ImageIcon(Test.class.getResource("ball_anti.png"));
	//	Thread E_bullets=new Thread(this);
		EnemyBullets(){
			super();
		//	this.setIcon(ball);
		//	this.setSize(ball.getIconWidth(),ball.getIconHeight());
		//	this.setVisible(false);
		//	panel.add(this,new Integer(100));
			System.out.println("new ebullet");
		}
		double originx=Enemy_Yamazaki.centerpx,originy=Enemy_Yamazaki.centerpy;
		public double angle=0.0,b_vecx=0,b_vecy=0;
		boolean jud_on=true;
		
		
		public void run() {
			// TODO Auto-generated method stub
			while(isRunning){
				
				if(!Starting.pause){
					
//					if(anti){
//						this.setIcon(ball_anti);
//					}
//					if(!anti){
//						this.setIcon(ball);
//					}
//					this.setLocation((int)(originx+=b_vecx*CU*Math.cos(2*angle*Math.PI)),(int)(originy+=b_vecy*CU*Math.sin(2*angle*Math.PI)));

//					if((!Starting.dead)&&jud_on&&Math.sqrt(Math.pow((positionx+panel.main.getIconWidth()/2)-(originx+ball.getIconWidth()/2),2)+Math.pow((positiony+panel.main.getIconHeight()/2)-(originy+ball.getIconHeight()/2),2))<=16){
//						System.out.println("Game over");
//						jud_on=false;
//						vecy-=0.9*Math.sin(Math.atan2((positiony+0.5*Gpanel.main.getIconHeight())-(originy+0.5*ball.getIconHeight()),(positionx+0.5*Gpanel.main.getIconWidth())-(originx+0.5*ball.getIconWidth())));
//						vecx+=0.9*Math.cos(Math.atan2((positiony+0.5*Gpanel.main.getIconHeight())-(originy+0.5*ball.getIconHeight()),(positionx+0.5*Gpanel.main.getIconWidth())-(originx+0.5*ball.getIconWidth())));
//						statepanel.deader.setText("Attacked");
//						if(statepanel.life.length()>=1)
//							statepanel.life=statepanel.life.substring(0, statepanel.life.length()-1);
//						
//					}
//					if((!Starting.dead)&&!jud_on&&Math.sqrt(Math.pow((positionx+panel.main.getIconWidth()/2)-(originx+ball.getIconWidth()/2),2)+Math.pow((positiony+panel.main.getIconHeight()/2)-(originy+ball.getIconHeight()/2),2))>16){
//						jud_on=true;
//					}
//					if(originx>=550||originx<=-50||originy>650||originy<-50){
//						panel.remove(this);
//						break;
//					}
				
				}
				else{}
				try {
					Thread.sleep((long)(1));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	
	
	
	static class Background extends JLabel implements Runnable{
		ImageIcon background=new ImageIcon(Test.class.getResource("BK.png"));
		ImageIcon background_anti=new ImageIcon(Test.class.getResource("BK_anti.png"));
		Image Im_bk=background.getImage();
		Image Im_bk_anti=background_anti.getImage();
		double angle=0,backposx=-500,backposy=-500,picpox,picpoy,picdisx,picdisy,radius,para1=300,para2=170;
		//picpo:center of rotate;picdis:distence between
		Background(){
			super();
			//this.setIcon(background);
			System.out.println("new Background");
			this.setSize(500,600);
			//this.setLocation(panel.getWidth()/2-background.getIconWidth()/2, panel.getHeight()/2-background.getIconHeight()/2);
			
			radius=Math.sqrt(Math.pow(background.getIconWidth(), 2)+Math.pow(background.getIconHeight(), 2));			
			picpox=para1;
			picpoy=para2;
			picdisx=para1;
			picdisy=para2;
			//宇宙大借放
			gameover.setSize(frame.getSize());
			panel.add(gameover,new Integer(500));
			gameover.setVisible(false);
			////////
		}
		public void paintComponent(Graphics g){
			 super.paintComponent(g);
			 Graphics2D g2d;
			 g2d = (Graphics2D)g;
			 g2d.rotate(-angle-Math.PI*1/4,picpox,picpoy);			 
			 g2d.drawImage(Im_bk,(int)(picpox),(int)(picpoy),background.getIconWidth(),background.getIconHeight(),null);
			 
			}
		static public JLabel gameover=new JLabel("",JLabel.CENTER);
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("new Background thread");
			boolean jud=true,jud2=true,judanti=true;;
			int counter=0,counter2=0;
			
			while(true){
				if(!isRunning){
					panel.remove(gameover);
					break;
				}
				if(isRunning){
					if(!Starting.pause){
						//借放
						if(clip.getFramePosition()>=clip.getFrameLength()-1){
							PlaySound(0);
							clip.setFramePosition(0);
							PlaySound(1);
							
						}
						
						
						if(!anti&&judanti){
							gameover.setForeground(Color.WHITE);
							try {
								Thread.sleep(1);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							judanti=false;
						}
						if(anti&&!judanti){
							gameover.setForeground(Color.BLACK);
							try {
								Thread.sleep(1);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							judanti=true;
						}
						if(jud&&Starting.dead){
							if(counter>2000){
								if(Starting.win)
									gameover.setText("YOU DIED");
								if(!Starting.win)
									gameover.setText("GAME OVER");
								
								
								gameover.setVisible(true);
								
							}
							if(counter==6000){
								jud=false;
								gameover.setText("PRESS R TO RESTART");
							}
							if(counter<=6000)
								counter++;
						}
						
						if(jud2&&Starting.win&&!Starting.dead){
							if(counter2==2000){
								gameover.setText("YOU WIN");
								
								gameover.setVisible(true);
								
							}
							if(counter2==6000){
								jud=false;
								gameover.setText("PRESS R TO RESTART");
							}
							if(counter<=6000)
								counter2++;
						}
						////////////////////
						if(anti)
							Im_bk=background_anti.getImage();
						if(!anti)
							Im_bk=background.getImage();
						angle+=0.00007*CU;				
						picpoy=picdisy+radius/2*Math.sin(angle);
						picpox=picdisx-radius/2*Math.cos(angle);
						picdisy+=0.005*CU*Math.sin(angle);
						picdisx+=0.005*CU*Math.cos(angle);
						
					if(angle>=2*Math.PI)
						angle=0;
					}
					else{}
					
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
		}		
	}
	
	@SuppressWarnings("deprecation")
	static void States_Jud(int state){

		 final int prepare=0,start=1,dead=2,win=3;
		 
		 if(state==prepare){
			 	
				M_Char=new Thread(panel);
				BK=new Background();							
				BK_thread=new Thread(BK);
				M_Char.start();
				BK_thread.start();
				frame.addKeyListener(keyx);
	        	frame.addKeyListener(keyy);
	        	statepanel=new STpanel();
	        	state01=new Thread(statepanel);
	        	state01.start();
	        	panel.add(BK,new Integer(0));	        	
	        	label.setVisible(true);;
	        	panel.add(label,new Integer(200));
	        	System.out.println("already prepared");
	        	
		 }
		 if(state==start){				
	        	frame.addKeyListener(keyelse);
	        	System.out.println("started");
	        	Enemy_Yamazaki yamazaki=new Enemy_Yamazaki();
	        	STpanel.bornBossLife=true;
	        	PlaySound(1);
		 }
		 if(state==dead){
			 label.setVisible(false);;
			 Starting.dead=true;
			 anti=false;
			 newbullet=false;
			 CU=1;
		 }
		 if(state==win){
			Enemy_Yamazaki.Im_en=null;
			panel.remove(STpanel.bosslifer);
		 }

	}	
	
	static class keyworksy extends KeyAdapter{//===================�ｿｽ�ｿｽ﨤溥涎��宅亅�坤�翼�ｿｽ�圉
		public void keyPressed(KeyEvent e) {//�坤�翼遧ｮ�圉
	        int key1 = e.getKeyCode();
	        switch (key1) {       

	        case KeyEvent.VK_DOWN:
	        	Test.moveyn=true;
	        	break;        	
	        case KeyEvent.VK_UP:
	        	Test.moveyp=true;
	        	break;     
	        case KeyEvent.VK_S:
	        	Test.moveyn=true;
	        	break;        	
	        case KeyEvent.VK_W:
	        	Test.moveyp=true;
	        	break;     
	        }   
		}
		public void keyReleased(KeyEvent e) {//�坤�翼遧ｮ�圉
	        int key1 = e.getKeyCode();
	        switch (key1) {        
	        case KeyEvent.VK_R:
	        	if(Starting.dead||Starting.win){//restart
	        		panel.removeAll();
	        		panel.repaint();
	           		frame.remove(panel);
	           		clip.close();
	           		isPlaying=false;
	        		isRunning=false;
	        		Bullet_Move.CleanList();
	        		keyworkelse.bulletnum=0;
	        		frame.removeKeyListener(start);
	        		frame.removeKeyListener(keyx);
	        		frame.removeKeyListener(keyy);
	        		frame.removeKeyListener(keyelse);
	        		System.gc();
	        		try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        		

	        		isRunning=true;     		
	        		
	        		Starting.dead=false;	        		
	        		Starting.win=false;
	        		Starting.start=false;
	        		Starting.pause=false;
	        		
	        		frame.setSize(500, 600);
	        		// TODO Auto-generated method stub
	        		panel=new Gpanel();
	        		
	        					
	        			
	     
	        		frame.add(panel);

	        		panel.setLayout(null);
	        		
	        		
	        		open=new Opening();
	        			
	        		
	        		
	        		
	        		frame.setVisible(true);		
	        		frame.setResizable(false);
	        		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        			
	        		
	        	}
	        	break; 
	      
	        case KeyEvent.VK_DOWN:
	        	Test.moveyn=false;
	        	break;        	
	        case KeyEvent.VK_UP:
	        	Test.moveyp=false;
	        	break;     
	        case KeyEvent.VK_S:
	        	Test.moveyn=false;
	        	break;        	
	        case KeyEvent.VK_W:
	        	Test.moveyp=false;
	        	break;   
	        }   
		}
	}
	
	static class keyworksx extends KeyAdapter{//========================�ｿｽ�ｿｽ釚ｷ釗ｵ��宅亅�坤�翼�ｿｽ�圉
		public void keyPressed(KeyEvent e) {//�坤�翼遧ｮ�圉
			int key1 = e.getKeyCode();
	        switch (key1) {        
	        case KeyEvent.VK_LEFT:
	        	Test.movexn=true;
	        	break;        	
	        case KeyEvent.VK_RIGHT:
	        	Test.movexp=true;
	        	break;     
	        case KeyEvent.VK_A:
	        	Test.movexn=true;
	        	break;        	
	        case KeyEvent.VK_D:
	        	Test.movexp=true;
	        	break;   
	        }           
		}
	
		public void keyReleased(KeyEvent e) {//�坤�翼遧ｮ�圉
	        int key1 = e.getKeyCode();
	        switch (key1) {        
	        case KeyEvent.VK_LEFT:
	        	Test.movexn=false;
	        	break;        	
	        case KeyEvent.VK_RIGHT:
	        	Test.movexp=false;
	        	break;     
	        case KeyEvent.VK_A:
	        	Test.movexn=false;
	        	break;        	
	        case KeyEvent.VK_D:
	        	Test.movexp=false;
	        	break;          
	        }   
		}
	}

	static class keyworkelse extends KeyAdapter{//=============================赶ｺ﨣ゐ亅�坤�翼�ｿｽ�圉
		static int bulletnum=0;
//		static Bullets[] bullets=new Bullets[9999];	
		
		public void keyPressed(KeyEvent e) {//�坤�翼遧ｮ�圉
	        int key1 = e.getKeyCode();
	        if(!Starting.dead){
	        switch (key1) {       
	     
	        case KeyEvent.VK_X://the world
	        	
	        	Test.anti=true;
	        	try {
					Thread.sleep(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	break;
	        case KeyEvent.VK_Z:
	        	newbullet=true;
	        	
	        	break;
	        
	        }
		}
		}
		public void keyReleased(KeyEvent e) {//�坤�翼遧ｮ�圉
	        int key1 = e.getKeyCode();
	        if(!Starting.dead&&(Starting.start)){
	        switch (key1) {   
	     
	       
	        case KeyEvent.VK_X:
	        	try {
					Thread.sleep(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	
	        	Test.anti=false;
	        	break;
	        case KeyEvent.VK_Z:
	        	newbullet=false;
	        	
	        	break;
	        case KeyEvent.VK_M:
	        	if(!Starting.pause&&(Starting.start)){
	        		if(reloadtime!=1){
		        		reloadtime=1;	        		
		        		panel.burst=true;
		        	}
		        	else{
		        		reloadtime=65;
		        		panel.burst=false;
		        	}
	        	}
	        	break;
	        }
	        }
		}	
	}	
}











