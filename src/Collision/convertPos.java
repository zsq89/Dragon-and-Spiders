package Collision;

public class convertPos{
	private static int[][] pos={
		{2380, 29}, {2500, 59}, {1380, 89},
        {780, 109}, {580, 139}, {680, 239}, 
        {790, 259}, {760, 50}, {790, 150},
        {980, 209}, {560, 45}, {510, 70},
        {930, 159}, {590, 80}, {530, 60},
        {940, 59}, {990, 30}, {920, 200},
        {900, 259}, {660, 50}, {540, 90},
        {810, 220}, {860, 20}, {740, 180},
        {820, 128}, {490, 170}, {700, 30}
	};

	public static void main(String[] args) {
		for(int i=0;i<pos.length;i++){
			if (i%3==0) {
				System.out.println("");
			}
			System.out.print("{"+pos[i][1]+","+pos[i][0]+"},");
		}
	}
}