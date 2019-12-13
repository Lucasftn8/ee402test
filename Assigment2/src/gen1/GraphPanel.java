package gen1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import gen1.Mwindow.DataPackage;

public class GraphPanel extends JPanel{
	private boolean erasing = false;
	private VisualSetting t_set = new VisualSetting();
	
	//Testing value//
	private int scaleX,scaleY=10;
	private int t_maxValue;
	private int t_minValue;
	private int t_avgValue;
	private int t_base_x=50, t_base_y=650;
	private ArrayList<Point> points = new ArrayList<Point>();
  
	public VisualSetting get_set() {return this.t_set;}
  public GraphPanel() {
  }

  public void paintComponent(Graphics g){
	int i=0;
	Point nextPoint;
	t_avgValue=0;
	scaleX=800/(t_set.get_nb_value());
    g.setColor(t_set.get_backcol());
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    initGraph(g);
    if(this.erasing) {
    	this.erasing = false;
    }
    else {
    	if(!points.isEmpty()) {t_minValue=points.get(0).getvalY();}
    	for(Point p : this.points)
    	{
    		t_avgValue+=p.getvalY();
    		if(p.getvalY()>t_maxValue) {
    			t_maxValue=p.getvalY();
    		}
    		else if(p.getvalY()<t_minValue) {
    			t_minValue=p.getvalY();
    		}
    		//Need to find a way to calculate their coords based on the init coord of the graphe <--done
    		g.setColor(t_set.get_col());
    		if(t_set.get_form()=="SQUARE") {
    			g.fillRect(t_base_x+(scaleX*i)-(t_set.get_size()/2),t_base_y-p.getvalY()*scaleY-(t_set.get_size()/2),t_set.get_size(),t_set.get_size());
    		}
    		else {
    			System.out.println("value of point n*"+ i + " is Y:" + p.getvalY() );
    			g.fillOval(t_base_x+(scaleX*i)-(t_set.get_size()/2),t_base_y-p.getvalY()*scaleY-(t_set.get_size()/2),t_set.get_size(),t_set.get_size());
    		}
    		if(i<points.size()-1) {//
    			nextPoint=points.get(i+1);
    			g.setColor(t_set.get_lineCol());
        		g.drawLine(t_base_x+(scaleX*i), t_base_y-p.getvalY()*scaleY,t_base_x+(scaleX*(i+1)), t_base_y-nextPoint.getvalY()*scaleY);
    		}i++;//increment the index value
    	}
    }
    //paint the max line, min line and avg
    if(!points.isEmpty()) {t_avgValue/=points.size();}
    if(t_minValue!=0) {
        g.setColor(t_set.get_minCol());
        g.fillRect(t_base_x, (int) (t_base_y-t_minValue*scaleY), 800, 3);//minimum
    }
    if(t_maxValue!=0) {
    g.setColor(t_set.get_maxCol());
    g.fillRect(t_base_x, (int) (t_base_y-t_maxValue*scaleY), 800, 3);//maximum
    }
    if(t_avgValue!=0) {
    g.setColor(t_set.get_avgCol());
    g.fillRect(t_base_x, (int) (t_base_y-t_avgValue*scaleY), 800, 3);//average
    }
  }
  public void initGraph(Graphics g){
	    //paint the axis of the graph
	    g.setColor(Color.BLACK);
	    g.fillRect(t_base_x, 650, 800, 3);//X axis
	    g.fillRect(t_base_x, 50, 3, 600);//Y axis
	    //Grid
	    for(int i=0;i<=600/scaleY;i++) {
		    g.fillRect(t_base_x, 650-(i*scaleY), 800, 1);//Y Grid
		    if(i%5==0) {
			    g.fillRect(t_base_x-5, 650-(i*scaleY), 5, 1);
		    }
	    }
	    for(int i=0;i<=800/scaleX;i++) {
		    g.fillRect(t_base_x+(i*scaleX), 50, 1, 600);//X Grid
		    if(i%5==0) {
		    	g.fillRect(t_base_x+(i*scaleX), 650, 1, 10);
		    }
	    }
  }
  public void addPoint(DataPackage _data) {
	  if(points.size()==t_set.get_nb_value()) {//if they are already all values
		  points.remove(0);
	  }
	  this.points.add(new Point(_data));
  }
  
  public void erase() {
	  this.erasing = true;
	  this.points = new ArrayList<Point>();
	  this.t_maxValue=0;
	  this.t_minValue=0;
	  repaint();
  }
  class VisualSetting{
	  //Color Settings//
		private Color t_backCol= Color.GRAY;
		private Color t_lineCol= Color.RED;
		private Color t_col=Color.RED;
		private Color t_maxCol= Color.ORANGE ;
		private Color t_minCol= Color.GREEN ;
		private Color t_avgCol= Color.YELLOW ;
	  //Graph Setting//
		private int t_max_nb_value=20;
		private int t_size = 9;
		private String t_form= "CIRCLE";
		private boolean t_line= true;
		
		public VisualSetting() {}
		public VisualSetting(Color _backCol,Color _lineCol,int _size,int _maxValue, Color _col , String _form ) {
			this.t_size=_size;
			this.t_lineCol=_lineCol;
			this.t_max_nb_value=20;
			this.t_col=_col;
			this.t_backCol=_backCol;
			this.t_form=_form;
		}
		
		// GETTER
		public int get_size() { return this.t_size;}
		public int get_nb_value() {return t_max_nb_value;}
		public Color get_col() { return this.t_col;}
		public Color get_backcol() { return this.t_backCol;}
		public Color get_lineCol() {return this.t_lineCol;}
		public Color get_maxCol() {return this.t_maxCol;}
		public Color get_minCol() {return this.t_minCol;}
		public Color get_avgCol() {return this.t_avgCol;}
		public String get_form() { return this.t_form;}
		public boolean get_line() {return this.t_line;}
		
		//SETTER
		public void set_size(int _size) {  this.t_size=_size;}
		public void set_nb_value(int _maxValue) {this.t_max_nb_value=_maxValue;}
		public void set_col(Color _col) {  this.t_col=_col;}
		public void set_backcol(Color _backCol) {  this.t_backCol=_backCol;}
		public void set_linecol(Color _lineCol) {  this.t_lineCol=_lineCol;}
		public void set_maxcol(Color _maxCol) {  this.t_maxCol=_maxCol;}
		public void set_mincol(Color _minCol) {  this.t_lineCol=_minCol;}
		public void set_avgcol(Color _avgCol) {  this.t_lineCol=_avgCol;}
		public void set_form(String _form) {  this.t_form=_form;}
		public void toggle() {this.t_line= !this.t_line;}

		public void ModifySetting(VisualSetting _copy) {
			//This function will give all new settings after closnig option GUI
			this.t_size=_copy.get_size();
			this.t_max_nb_value=_copy.get_nb_value();
			this.t_col=_copy.get_col();
			this.t_backCol=_copy.get_backcol();
			this.t_form=_copy.get_form();
		}
	}
}
