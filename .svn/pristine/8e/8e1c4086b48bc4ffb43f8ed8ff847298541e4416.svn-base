package game.models;

import accessory.cres.CResNode;

public class Creature {

	//base attributes
	private String name;
	private int level;
	
	//health
	private int baseHP;
	private int currentHP;
	private int hpGain;

	//mana
	private int baseMP;
	private int currentMP;
	private int mpGain;
	
	//phys
	private double attackPower;
	private double attackPowerGain;
	private double armor;
	private double armorGain;
	
	
	//magical
	private double spellPower;
	private double spellPowerGain;
	private double magicResistance;
	private double magicResistanceGain;
	
	//speed
	private double speed;
	private double speedGain;
	
	//visualisation
	private String sprite;
	private CResNode portraitNode;
	
	
	public Creature() {
		this("???",0,0,0,1,1);
	}
	
 
	
	
	public Creature(String name, int level, int baseHP, int currentHP,
			int baseMP, int currentMP) {
		super();
		this.name = name;
		this.level = level;
		this.baseHP = baseHP;
		this.currentHP = currentHP;
		this.baseMP = baseMP;
		this.currentMP = currentMP;
	}




	public int getBaseHP() {
		return baseHP;
	}
	public void setBaseHP(int baseHP) {
		this.baseHP = baseHP;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name+"("+baseHP+"HP)";
	}
	
	public int getCurrentHP() {
		return currentHP;
	}
	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}
	public int getLevel() {
		return level;
	}




	public Creature(String name, int level, int baseHP, int currentHP,
			int baseMP, int currentMP, double attackPower, double armor,
			double spellPower, double magicResistance, double speed,
			String sprite, CResNode portraitNode) {
		super();
		this.name = name;
		this.level = level;
		this.baseHP = baseHP;
		this.currentHP = currentHP;
		this.baseMP = baseMP;
		this.currentMP = currentMP;
		this.attackPower = attackPower;
		this.armor = armor;
		this.spellPower = spellPower;
		this.magicResistance = magicResistance;
		this.speed = speed;
		this.sprite = sprite;
		this.portraitNode = portraitNode;
	}




	public double getAttackPower() {
		return attackPower;
	}




	public void setAttackPower(double attackPower) {
		this.attackPower = attackPower;
	}




	public double getArmor() {
		return armor;
	}




	public void setArmor(double armor) {
		this.armor = armor;
	}




	public double getSpellPower() {
		return spellPower;
	}




	public void setSpellPower(double spellPower) {
		this.spellPower = spellPower;
	}




	public double getMagicResistance() {
		return magicResistance;
	}




	public void setMagicResistance(double magicResistance) {
		this.magicResistance = magicResistance;
	}




	public double getSpeed() {
		return speed;
	}




	public void setSpeed(double speed) {
		this.speed = speed;
	}




	public void setPortraitNode(CResNode portraitNode) {
		this.portraitNode = portraitNode;
	}




	public int getBaseMP() {
		return baseMP;
	}




	public void setBaseMP(int baseMP) {
		this.baseMP = baseMP;
	}




	public int getCurrentMP() {
		return currentMP;
	}




	public void setCurrentMP(int currentMP) {
		this.currentMP = currentMP;
	}




	public void setLevel(int level) {
		this.level = level;
	}




	public String getSprite() {
		if(sprite == null){
			return CreatureFactory.DEFAULT_SPRITE;
		}
		return sprite;
	}




	public void setSprite(String sprite) {
		this.sprite = sprite;
	}




	public void setPortraitProperties(CResNode portraitNode) {
		this.portraitNode = portraitNode;
		
	}

	public CResNode getPortraitNode() {
		return portraitNode;
	}

	
	
	
}
