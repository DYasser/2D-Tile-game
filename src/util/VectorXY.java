package util;

public class VectorXY {

	public float x, y;
	
	public static float worldX, worldY;
	 
	public VectorXY()
	{
		x = y = 0; 
	}

	public VectorXY(VectorXY vec)
	{
		new VectorXY(vec.x, vec.y);
	} 
	
	public VectorXY(float x, float y)
	{
		this.x = x;
		this.y = y; 
	}
 
	//setters
	public void setX(float x) {this.x = x;}
	public void setY(float y) {this.y = y;}
	public static void setWorldVar(float x, float y)
	{
		worldX = x;
		worldY = y;
	}
	public void setVector(VectorXY vec)
	{
		x = vec.x;
		y = vec.y;
	}
	public void setVector(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	//getters
	public static float getWorldVarX(float f){return f - worldX;}
	public static float getWorldVarY(float f){return f - worldY;}
	public VectorXY getCamVar(){return new VectorXY(x + worldX, y + worldY);}
	public VectorXY getWorldVar(){return new VectorXY(x - worldX, y - worldY);}
}
