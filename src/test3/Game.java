package test3;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Game{
	
	public static void main(String[] args) {		
		CGameEngine gameEngine = new CGameEngine();
		CGameObject gameObject = new CGameObject();
		BufferedImage cubeImage = null;
		try {
			cubeImage = ImageIO.read(Game.class.getResource("yamazaki.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gameObject.AddImage(cubeImage);
		gameObject.SetCurrentImage(0);
		gameObject.SetPosition(50,50);
		gameEngine.AddGameObject(gameObject);
		gameEngine.SetMainGameObject(gameObject);
		gameEngine.AddKeyListener(new CGameObjectController(gameObject));
		
		gameEngine.ActiveFrame();
	}	
}