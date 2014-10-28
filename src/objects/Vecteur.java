package objects;

public class Vecteur {
	double x, y, z;
	
	//un vecteur 
	public Vecteur(double x, double y , double z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	//un vecteur partir de deux points
	public Vecteur(Point a, Point b){
		double x = b.getX()-a.getX();
		double y = b.getY()-a.getY();
		double z = b.getZ()-a.getZ();
		double longueur = Math.sqrt(x*x+y*y+z*z);
		this.x=x/longueur;
		this.y=y/longueur;
		this.z=z/longueur;
	}
	
	//un vecteur normal ( a partir de 2 vecteurs)
	public Vecteur(Vecteur u, Vecteur v){
		double x=u.getY()*v.getZ() - u.getZ()*v.getY();
		double y=u.getZ()*v.getX() - u.getX()*v.getZ();
		double z=u.getX()*v.getY() - u.getY()*v.getX();
		double longueur = Math.sqrt(x*x+y*y+z*z);
		this.x=x/longueur;
		this.y=y/longueur;
		this.z=z/longueur;
	}
	
	//produit scalaire : si le produit scalaire de deux vecteurs est négatif alors on affiche sinon on affiche pas
	public double prodScalaire(Vecteur v){
		return this.x*v.x+this.y*v.y+this.z*v.z;
	}
	
	private double getX(){
		return this.x;
	}
	private double getY(){
		return this.y;
	}
	private double getZ(){
		return this.z;
	}
}
