package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import entity.Projectile;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Boots;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Rock;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class Player extends Entity {
	

	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	int standanimationCounter = 0;
	public boolean attackCancelled = false;
	public int coinCounter = 0;
	public int KeyCounter = 0;
	public boolean HasKey = false;

	
	
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp);
		 
		
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle();
		solidArea.x = 12;
		solidArea.y = 20;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y; 
		solidArea.width = 24;
		solidArea.height = 27;
		
		//attackArea now in obj classes
	//	attackArea.width = 36;
	//	attackArea.height = 36;
		
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
	}
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		
//		worldX = gp.tileSize * 12;
//		worldY = gp.tileSize * 11;
		defaultSpeed = 4;
		speed = defaultSpeed;
		direction = "down";
		
		// PLAYER STATUS
		level = 1;
		maxLife = 6;
		// 2 lives is one heart (half heart one life)
		//3 hearts total
		life = maxLife;
		maxMana = 4;
		mana = maxMana;
		ammo = 10;
		strength = 0; //default val used to be 1 //more strength for weapon more damage given to mobs 
		dexterity = 0;//default value used to be 1 //more dexterity for weapon less damage recieve      
		exp = 0;
		nextLevelExp = 5;
		coin = 690;
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentSheild = new OBJ_Shield_Wood(gp);
		currentKey = new OBJ_Key(gp);
		projectile = new OBJ_Fireball(gp);
		//projectile = new OBJ_Rock(gp);
		attack = getAttack(); // total attack value
		defense = getDefense(); // total defense value
		
		
		
	}
	public void setDefaultPositions() {
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		
//		worldX = gp.tileSize * 10;
//		worldY = gp.tileSize * 15;
		direction = "down";
		
	}
	public void restoreLifeAndMana() {
		
		life = maxLife;
		mana = maxMana;
		invincible = false;
	}
	public void setItems() {
		
		inventory.clear();
		inventory.add(currentWeapon);
		inventory.add(currentSheild);
		
	}
	public int getAttack() {
		attackArea = currentWeapon.attackArea;
		return attack = strength + currentWeapon.attackValue; //used to be multiplied as well
		
	}
	public int getDefense() {
		return defense = dexterity + currentSheild.defenseValue; //used to be dex * currS.defval
	}
	
	public void getPlayerImage() {
		
		
		up1 = setup("/player/funguy#2_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/funguy#2_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/funguy#2_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/funguy#2_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/funguy#2_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/funguy#2_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/funguy#2_right_1", gp.tileSize, gp.tileSize);
		
		
		up1 = setup("/player/mush_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/mush_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/mush_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/mush_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/mush_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/mush_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/mush_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/mush_right_2", gp.tileSize, gp.tileSize);
		
		
		

		
			
	}
	
	public void getPlayerAttackImage() {
		
		if(currentWeapon.type == type_sword) {
		attackUp1 = setup("/player/mush_attack_up_1", gp.tileSize, gp.tileSize *2);
		attackUp2 = setup("/player/mush_attack_up_2", gp.tileSize, gp.tileSize *2);
		attackDown1 = setup("/player/mush_attack_down_1", gp.tileSize, gp.tileSize *2);
		attackDown2 = setup("/player/mush_attack_down_2", gp.tileSize, gp.tileSize *2);
		attackLeft1 = setup("/player/mush_attack_left_1", gp.tileSize*2, gp.tileSize);
		attackLeft2 = setup("/player/mush_attack_left_2", gp.tileSize*2, gp.tileSize);
		attackRight1 = setup("/player/mush_attack_right_1", gp.tileSize*2, gp.tileSize);
		attackRight2 = setup("/player/mush_attack_right_2", gp.tileSize*2, gp.tileSize);
		}
		if(currentWeapon.type == type_axe) {
			attackUp1 = setup("/player/axeattack_up_1", gp.tileSize-5, gp.tileSize *2);
			attackUp2 = setup("/player/axeattack_up_2", gp.tileSize-5, gp.tileSize *2);
			attackDown1 = setup("/player/axeattack_down_1", gp.tileSize-5, gp.tileSize *2);
			attackDown2 = setup("/player/axeattack_down_2", gp.tileSize-5, gp.tileSize *2);
			attackLeft1 = setup("/player/axeattack_left_1", gp.tileSize*2, gp.tileSize - 5);
			attackLeft2 = setup("/player/axeattack_left_2", gp.tileSize*2, gp.tileSize - 5);
			attackRight1 = setup("/player/axeattack_right_1", gp.tileSize*2, gp.tileSize - 5);
			attackRight2 = setup("/player/axeattack_right_2", gp.tileSize*2, gp.tileSize - 5);
		 }
		}
	
	
	public void update() {
		
		if(attacking == true) {
			attacking();
		}
		
		else if(keyH.upPressed == true || keyH.downPressed == true ||
				keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
			
		
		
		if(keyH.upPressed == true) {
			direction = "up";
			
			//0,0 is in the top left corner for java
		}
		else if(keyH.downPressed == true) {
			direction = "down";
			
	
		}
		else if(keyH.leftPressed == true) {
			direction = "left";
			
			
		}
		else if (keyH.rightPressed == true) {
			direction = "right";
			
		}
		
		//CHECK TILE COLLISION
		collisionOn = false;
		gp.cChecker.checkTile(this);
		
		// check object collision
		int objIndex = gp.cChecker.checkObject(this, true);
		pickUpObject(objIndex);
		
		//CHECKNPCCOLLISION
		int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
		interactNPC(npcIndex);
		
		//CHECK MONSTER COLLISION
		int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
		contactMonster(monsterIndex);
		
		//Check Interactive tile COLLISION
		int iTileIndex = gp.cChecker.checkEntity(this,  gp.iTile);
		
		//CHECK EVENT
		gp.eHandler.checkEvent();
		
		
		
	// IF COLLISION IS FALSE PLAYER CAN MOVE
		if(collisionOn == false && keyH.enterPressed == false) { 
			switch(direction) {
			case "up":
				worldY -= speed;
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;
					
			}
		}
		
		if(keyH.enterPressed == true && attackCancelled == false) {
			gp.playSE(7);
			attacking = true;
			spriteCounter = 0;
		}
		attackCancelled = false;
		
		gp.keyH.enterPressed = false;
		
		spriteCounter++;
		if(spriteCounter > 12) {
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum == 2) {
				spriteNum = 1;
				
			}
			spriteCounter = 0;
		 }
	  }
		else {
			standanimationCounter ++;
			
			if(standanimationCounter == 20) {
				spriteNum = 1;
				standanimationCounter = 0;
			}
			
		}
		
		if(gp.keyH.shotKeyPressed == true && projectile.alive == false 
		&& shotAvailableCounter == 60 && projectile.haveResource(this) == true) {
			
			//Set default coordinates, direction and user (this) is the playrt
			projectile.set(worldX, worldY, direction, true, this);
			//subtract
			projectile.subtractResource(this);
			// add to list
//			gp.projectileList.add(projectile);
			
			// CHECK VACANCY
			for(int i = 0; i < gp.projectile[1].length; i++) {
				
			    if(gp.projectile[gp.currentMap][i] == null){
			        gp.projectile[gp.currentMap][i] = projectile;
			        break;
			    }
			}
			
			shotAvailableCounter = 0;
			
			gp.playSE(17);
		}
		
		// this is to be outside of the key if statement
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		
		if(shotAvailableCounter < 60) {
			shotAvailableCounter++;
		}
		if(life > maxLife) {
			life = maxLife;
		}
		if(mana > maxMana) {
			mana = maxMana;
		}
		if(life <= 0) {
			gp.gameState = gp.gameOverState;
			gp.ui.commandNum = -1;
			gp.playSE(19);
			gp.stopMusic();
		}
		
	}
	public void attacking() {
		
		spriteCounter++;
		
		if(spriteCounter <=5) {
			spriteNum = 1;
		}
		if(spriteCounter > 5 && spriteCounter <=25) {
			spriteNum = 2;
			
			//save the current world x, world y and solidArea
			
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			//adjust players world x and y for the attack area
			switch(direction) {
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += attackArea.height; break;
			case "left": worldX -= attackArea.width; break;
			case "right": worldX += attackArea.width; break;
			}
			
			//atackarea becomes solid area
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			
			//check monster collision with the updated worldx, worldy and solidarea
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex, attack, currentWeapon.knockBackPower);
			//after checking collision, reset to original
			
			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
			damageInteractiveTile(iTileIndex);
			
			int projectileIndex = gp.cChecker.checkEntity(this,  gp.projectile);
			damageProjectile(projectileIndex);
			
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
			
		}
		if(spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
		if(life > maxLife) {
			life = maxLife;
		}
		if(mana > maxMana) {
			mana = maxMana;
		}
	}
	//here
	public void pickUpObject(int i) {
		
		if(i != 999) {
			
			String text;
			
			//pickuponlyitems
			if(gp.obj[gp.currentMap][i].type == type_pickupOnly) {
				gp.obj[gp.currentMap][i].use(this);
				gp.obj[gp.currentMap][i] = null;
				
				
			}
			//OBSTACLE
			else if(gp.obj[gp.currentMap][i].type == type_obstacle) {
				if(keyH.enterPressed == true) {
					gp.obj[gp.currentMap][i].interact();
				}
			}
			else {
				if(inventory.size() != maxinventorySize && gp.obj[gp.currentMap][i].type == type_key) {
					inventory.add(gp.obj[gp.currentMap][i]);
			//		KeyCounter ++;
					gp.playSE(1);
					text = "You got a Key!";
					//add key to inventory
				}
				
				
				
				else if(inventory.size() != maxinventorySize && gp.obj[gp.currentMap][i].type != type_door) {
					
					inventory.add(gp.obj[gp.currentMap][i]);
					gp.playSE(1);
					text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
					
					//below if statement for coincounter can get rid of it personal addon
					
					
				
				
					
				}
				else {
					text = "Your inventory is full!";
					
				}
				
				
					
					
				//if not door object disappear
				if(gp.obj[gp.currentMap][i].type != type_door) {
				gp.ui.addMessage(text);
				gp.obj[gp.currentMap][i] = null;
				
				}

				else if(gp.obj[gp.currentMap][i].type == type_door && HasKey == true) {
					gp.obj[gp.currentMap][i] = null;
					gp.ui.addMessage("You opened a door!");
					HasKey = false;
					
				//	KeyCounter --;
					gp.playSE(3);
					//remove the key from inventory
					inventory.remove(currentKey);
					//if opens door they do not have a key selected anymore and HasKey is now false (cannot open doors)
					//used key removed from inventory
					
					
					
				}

			}
			}
			//inventory only
			
	}

	public void interactNPC(int i) {
		
		if(gp.keyH.enterPressed == true) {
			
		if(i != 999) {
			attackCancelled = true;
			gp.gameState = gp.dialogueState;
			gp.npc[gp.currentMap][i].speak();
			
			
		 }	
		
		}
	}
	public void contactMonster(int i) {
		if(i != 999) {
			
			if(invincible == false && gp.monster[gp.currentMap][i].dying == false) {
				gp.playSE(9);
				
				int damage = gp.monster[gp.currentMap][i].attack - defense;
				if(damage < 0) {
					damage = 0;
				}
				
				life -= damage;
				invincible = true;
			}
			
			
		}
	}
	public void damageMonster(int i, int attack, int knockBackPower) {

		if(i != 999) {
			
			
			if(gp.monster[gp.currentMap][i].invincible == false) {
				gp.playSE(10);
				
				if(knockBackPower > 0) {
					knockBack(gp.monster[gp.currentMap][i], knockBackPower);
				}
				
				
				int damage = attack - gp.monster[gp.currentMap][i].defense;
				if(damage < 0) {
					damage = 0;
				}
				
				gp.monster[gp.currentMap][i].life -= damage;
				gp.ui.addMessage(damage + " damage!");
				
				gp.monster[gp.currentMap][i].invincible = true;
				gp.monster[gp.currentMap][i].damageReaction();
				
				if(gp.monster[gp.currentMap][i].life <= 0) {
					gp.monster[gp.currentMap][i].dying = true;
					gp.ui.addMessage("killed the " + gp.monster[gp.currentMap][i].name + "!");
					gp.ui.addMessage("XP + "  + gp.monster[gp.currentMap][i].exp);
					exp += gp.monster[gp.currentMap][i].exp;
					checkLevelUp();
				}
			}
		}
		
		
	}
	public void knockBack(Entity entity, int knockBackPower) {
		
		entity.direction = direction;
		entity.speed += knockBackPower;
		entity.knockBack = true;
	}
	public void damageInteractiveTile(int i) {
		
		if(i != 999 && gp.iTile[gp.currentMap][i].destructable == true 
				&& gp.iTile[gp.currentMap][i].isCorrectItem(this) == true && gp.iTile[gp.currentMap][i].invincible == false) {
			
			gp.iTile[gp.currentMap][i].playSe();
			gp.iTile[gp.currentMap][i].life--;
			gp.iTile[gp.currentMap][i].invincible = true;
			
			generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]); 
			
			if(gp.iTile[gp.currentMap][i].life == 0) {
			gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
			}
		}
	}
	public void damageProjectile(int i) {
		
		if(i != 999) {
			Entity projectile = gp.projectile[gp.currentMap][i];
			projectile.alive = false;
			generateParticle(projectile, projectile);
		}
	}
	public void checkLevelUp() {
		
		if(exp >= nextLevelExp) {
			level++;
			nextLevelExp = nextLevelExp *3;
			maxLife += 2;
			strength += 1;
			dexterity += 1;
			attack = getAttack();
			defense = getDefense();
			
			gp.playSE(13);
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "You are now level " + level + " now!\n" 
					+ "You are getting stronger...";
		}
	}
	public void selectItem() {
		
		int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol,gp.ui.playerSlotRow);
		if(itemIndex < inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);
			
			if(selectedItem.type == type_sword || selectedItem.type == type_axe) {
				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
			}
			if(selectedItem.type == type_shield) {
				
				currentSheild = selectedItem;
				defense = getDefense();
			}
			if(selectedItem.type == type_consumable) {
				
				selectedItem.use(this);
				inventory.remove(itemIndex);
			}
			if(selectedItem.type == type_key) {
			  //  KeyCounter++;
			// if key is selcted player has a key to open a door
				HasKey = true;
				
				currentKey = selectedItem;
				
				}
				
			}
			
		}

		

	
	public void draw(Graphics2D g2) {

		//g2.setColor(Color.white);
		
		//g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		
		int tempScreenX = screenX;
		int tempScreenY = screenY;

		switch(direction) {
		case "up":
			if(attacking == false) {
			if(spriteNum == 1) {image = up1;}
			if(spriteNum == 2) {image = up2;}
			}
			if(attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if(spriteNum == 1) {image = attackUp1;}
				if(spriteNum == 2) {image = attackUp2;}
			}
			break;
			
			
		case "down":
			if(attacking == false) {
			if(spriteNum == 1) {image = down1;}
			if(spriteNum == 2) {image = down2;}
			}
			if(attacking == true) {
			if(spriteNum == 1) {image = attackDown1;}
			if(spriteNum == 2) {image = attackDown2;}
			}
			break;
			
			
		case "left":
			if(attacking == false) {
			if(spriteNum == 1) {image = left1;}
			if(spriteNum == 2) {image = left2;}
			}
			if(attacking == true) {
			tempScreenX = screenX - gp.tileSize;
			if(spriteNum == 1) {image = attackLeft1;}
			if(spriteNum == 2) {image = attackLeft2;}
			}
			break;
			
			
		case "right":
			if(attacking == false) {
			if(spriteNum == 1) {image = right1;}
			if(spriteNum == 2) {image = right2;}
			}
			if(attacking == true) {
			if(spriteNum == 1) {image = attackRight1;}
			if(spriteNum == 2) {image = attackRight2;}
			}
			break;
			
			
		}
		if(invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		}
		g2.drawImage(image, tempScreenX, tempScreenY, null); 
		
		// reset alpha (opacity)
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		//Debug
		//g2.setFont(new Font("Arial", Font.PLAIN, 26));
		//g2.setColor(Color.white);
		//g2.drawString("invincible:" + invincibleCounter, 10, 400);
	}
	
}
