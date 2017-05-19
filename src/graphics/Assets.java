package graphics;
import java.awt.image.BufferedImage;

public class Assets {
		
		private static final int width = 32, height = 32;
		private static final int heroWidth = 400, heroHeight = 330;
		
		//Asets = all the images
		public static BufferedImage dirt, grass, stone, tree, rock;
		public static BufferedImage[] player_still, player_down, player_up, player_left, player_right, player_right_comb, player_left_comb;
		public static BufferedImage[] zombie_down, zombie_up, zombie_left, zombie_right;
		public static BufferedImage[] btn_start;
		public static BufferedImage[] lever;

		//Load all images at once
		public static void init(){
			
			SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/spriteSheet.png"));
			SpriteSheet playerSheet = new SpriteSheet(ImageLoader.loadImage("/textures/playerSheet.png"));
			SpriteSheet levers = new SpriteSheet(ImageLoader.loadImage("/textures/lever.png"));
			
			lever = new BufferedImage[2];
			lever[1] = levers.crop(0, 0, 120, 117);
			lever[0] = levers.crop(135, 0, 120, 117);
			
			btn_start = new BufferedImage[2];
			btn_start[0] = sheet.crop(width * 6, height * 4, width * 2, height);
			btn_start[1] = sheet.crop(width * 6, height * 5, width * 2, height);
			
			player_still = new BufferedImage[1];
			player_down = new BufferedImage[2];
			player_up = new BufferedImage[2];
			player_left = new BufferedImage[2];
			player_left_comb = new BufferedImage[2];
			player_right = new BufferedImage[2];
			player_right_comb = new BufferedImage[2];
			
			player_still[0] = playerSheet.crop(heroWidth * 3 + 360, 0, heroWidth - 100, heroHeight);
			player_down[0] = playerSheet.crop(0, 0, heroWidth - 100, heroHeight);
			player_down[1] = playerSheet.crop(0, heroHeight, heroWidth - 100, heroHeight);
			player_right[0] = playerSheet.crop(heroWidth - 55, 0, heroWidth - 160, heroHeight);
			player_right[1] = playerSheet.crop(heroWidth - 75, heroHeight, heroWidth - 160, heroHeight);
			player_left[0] = playerSheet.crop(heroWidth + 180, 0, heroWidth - 175, heroHeight);
			player_left[1] = playerSheet.crop(heroWidth + 180, heroHeight, heroWidth - 180, heroHeight);
			
			//Player Combat
			player_right_comb[0] = playerSheet.crop(heroWidth * 2 + 60, 0, heroWidth / 2 + 60, heroHeight);
			player_right_comb[1] = playerSheet.crop(heroWidth * 2 + 50, heroHeight, heroWidth / 2 + 60, heroHeight);
			player_left_comb[0] = playerSheet.crop(heroWidth * 3 + 70, 0, heroWidth / 2 + 60, heroHeight);
			player_left_comb[1] = playerSheet.crop(heroWidth * 3 + 70, heroHeight, heroWidth / 2 + 60, heroHeight);
			
			/*
			player_down[0] = sheet.crop(width * 4, 0, width, height);
			player_down[1] = sheet.crop(width * 5, 0, width, height);
			player_up[0] = sheet.crop(width * 6, 0, width, height);
			player_up[1] = sheet.crop(width * 7, 0, width, height);
			player_right[0] = sheet.crop(width * 4, height, width, height);
			player_right[1] = sheet.crop(width * 5, height, width, height);
			player_left[0] = sheet.crop(width * 6, height, width, height);
			player_left[1] = sheet.crop(width * 7, height, width, height);
			*/
			
			zombie_down = new BufferedImage[2];
			zombie_up = new BufferedImage[2];
			zombie_left = new BufferedImage[2];
			zombie_right = new BufferedImage[2];
			
			zombie_down[0] = sheet.crop(width * 4, height * 2, width, height);
			zombie_down[1] = sheet.crop(width * 5, height * 2, width, height);
			zombie_up[0] = sheet.crop(width * 6, height * 2, width, height);
			zombie_up[1] = sheet.crop(width * 7, height * 2, width, height);
			zombie_right[0] = sheet.crop(width * 4, height * 3, width, height);
			zombie_right[1] = sheet.crop(width * 5, height * 3, width, height);
			zombie_left[0] = sheet.crop(width * 6, height * 3, width, height);
			zombie_left[1] = sheet.crop(width * 7, height * 3, width, height);
			
			dirt = sheet.crop(width, 0, width, height);
			grass = sheet.crop(width * 2, 0, width, height);
			stone = sheet.crop(width * 3, 0, width, height);
			tree = sheet.crop(0, 0, width, height * 2);
			rock = sheet.crop(0, height * 2, width, height);
		}
}
