package bataille_navale.contenu;

public class Pos {
	//---Variables de classe-----//
		private int x;
		private int y;
		//--------Constructeurs------//
		public Pos() {
			
		}
		public Pos(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		public String toString() {
			return "[" + x + "; " + y + "]";
		}
		
		//-------Ass et Soeurs-------//
		public int getX(){
			return x;
		}
		public int getY(){
			return y;
		}
		//-------Mutateur-------//
		public void setX(int x){
			this.x = x;
		}
		public void setY(int y){
			this.y = y;
		}
		//------Methode--------//	
}
