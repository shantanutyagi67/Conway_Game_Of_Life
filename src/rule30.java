import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;


public class rule30 extends JComponent implements Runnable{
	
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
	static double itr = 1;
	
	public static void main(String args[]) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0,0,width+6+6+6,height+29+29+3);
		frame.getContentPane().add(new rule30());
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setVisible(true);
		
		for(int i=0;i<w;i++) {
			int tmp = new Random().nextInt(100);
			if (tmp<50)
				endState[0][i] = 1;
			else
				endState[0][i] = 0;
			
		}
}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		//g2D.translate(frame.getWidth()/2, frame.getHeight()/2);		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    	rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
    	g2D.setRenderingHints(rh);
    	double sum = 0;
		for(int i=0;i<h;i++) {
			for(int j=0;j<w;j++) {
				if(endState[i][j]==1) {
					g2D.setColor(Color.BLACK);
					sum++;
				}
				else
					g2D.setColor(Color.WHITE);
				g2D.fill(new Rectangle2D.Double(spacing+j*size, spacing+(i+1)*size, size-2*spacing, size-2*spacing));
			}
		}
		
		g2D.setColor(Color.RED);
		g2D.setFont(new Font("Monospaced", Font.BOLD, 30));
		if(itr>=1000)
			g2D.drawString("Generation: "+String.format("%.1f",(double)itr/1000)+"K",1200,40);
		else
			g2D.drawString("Generation: "+(int)itr,1200,40);
		BigDecimal bd = new BigDecimal(Double.toString(sum/(w*h)*100.00));
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
		g2D.drawString("Alive: "+bd.doubleValue()+"%",1200,65);
		itr++;
		
		int[][] next = new int [height/size][width/size];
		for(int i=0;i<h-1;i++) {
			for(int j=0;j<w-2;j++) {
				next[i][j]=endState[i][j];
				if(endState[i][j]==1&&endState[i][j+1]==1&&endState[i][j+2]==1) next[i+1][j+1]=0;
				else if(endState[i][j]==1&&endState[i][j+1]==1&&endState[i][j+2]==0) next[i+1][j+1]=0;
				else if(endState[i][j]==1&&endState[i][j+1]==0&&endState[i][j+2]==1) next[i+1][j+1]=0;
				else if(endState[i][j]==1&&endState[i][j+1]==0&&endState[i][j+2]==0) next[i+1][j+1]=1;
				else if(endState[i][j]==0&&endState[i][j+1]==1&&endState[i][j+2]==1) next[i+1][j+1]=1;
				else if(endState[i][j]==0&&endState[i][j+1]==1&&endState[i][j+2]==0) next[i+1][j+1]=1;
				else if(endState[i][j]==0&&endState[i][j+1]==0&&endState[i][j+2]==1) next[i+1][j+1]=1;
				else if(endState[i][j]==0&&endState[i][j+1]==0&&endState[i][j+2]==0) next[i+1][j+1]=0;
				endState[i][j] = next[i][j];
			}
		}
		
		//for(int i=0;i<h;i++) {
			//for(int j=0;j<w;j++) {
				//endState[i][j] = next[i][j];
			//}
		//}
		
		run();
		//repaint();
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(20);
			repaint();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
