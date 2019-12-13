package gen1;

import java.awt.Graphics;

import gen1.Mwindow.DataPackage;

public class Point {
	private DataPackage t_data;
	private Graphics g;
	private int t_valY;
	
	public Point(){}
	public Point(DataPackage _data) {
		t_data=_data;
		this.convert();
	}
	
	// This Function serve to convert the data into coord
	public void convert() {
		t_valY=(int)(t_data.get_temp()*5)-190;
		System.out.println(t_valY);
		
	}
	public int getvalY() {
		return t_valY;
	}
	
}
