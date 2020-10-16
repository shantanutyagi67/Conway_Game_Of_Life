import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;


public class Universe extends JComponent implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JFrame frame = new JFrame("%window name%");
	static int size = 5;
	double spacing = 0.2;
	static int width = 1600, height = 900;
	static int endState[][] = new int [height/size][width/size];
	static int h = height/size, w = width/size;
	public static void main(String args[]) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0,0,width+6+6+6,height+29+29+3);
		frame.getContentPane().add(new Universe());
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setVisible(true);
		
		for(int i=0;i<h;i++) {
			for(int j=0;j<w;j++) {
				//int tmp = new Random().nextInt(100);
				//if (tmp<4)
				//	endState[i][j] = 1;
				//else
				//	endState[i][j] = 0;
				endState[i][j] = 0;
			}
		}
		endState[h/2][w/2+1] = 1;
		endState[h/2][w/2+2] = 1;
		endState[h/2+1][w/2+1] = 1;
		endState[h/2+1][w/2] = 1;
		endState[h/2+2][w/2+1] = 1;
		
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		//g2D.translate(frame.getWidth()/2, frame.getHeight()/2);		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    	rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
    	g2D.setRenderingHints(rh);
		
		for(int i=0;i<h;i++) {
			for(int j=0;j<w;j++) {
				if(endState[i][j]==1)
					g2D.setColor(Color.BLACK);
				else
					g2D.setColor(Color.WHITE);
				g2D.fill(new Rectangle2D.Double(spacing+j*size, spacing+(i+1)*size, size-2*spacing, size-2*spacing));
			}
		}
		int[][] next = new int [height/size][width/size];
		for(int i=0;i<h;i++) {
			for(int j=0;j<w;j++) {
				int cnt = 0;
				if(i-1 >= 0) cnt+= endState[i-1][j];
				if(i+1 < h) cnt+= endState[i+1][j];
				if(j-1 >= 0) cnt+= endState[i][j-1];
				if(j+1 < w) cnt+= endState[i][j+1];
				if(i-1 >= 0 && j-1 >= 0) cnt+= endState[i-1][j-1];
				if(i-1 >= 0 && j+1 < w) cnt+= endState[i-1][j+1];
				if(i+1 < h && j-1 >= 0) cnt+= endState[i+1][j-1];
				if(i+1 < h && j+1 < w) cnt+= endState[i+1][j+1];
				if(endState[i][j]==1) {// Live cell
					if(cnt<2) next[i][j] = 0;// under population death
					else if (cnt>=2&&cnt<=3) next[i][j]=1;// stay alive
					else if (cnt>3) next[i][j]=0;// over population death
				}
				else {// cell dead
					if(cnt==3) next[i][j]=1;// alive by reproduction
					else next[i][j]=0;// stay dead
				}
			}
		}
		
		for(int i=0;i<h;i++) {
			for(int j=0;j<w;j++) {
				endState[i][j] = next[i][j];
			}
		}
		
		run();
		//repaint();
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(50);
			repaint();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}