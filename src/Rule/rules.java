package Rule;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;


public class rules extends JComponent implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JFrame frame = new JFrame("Game Of Life");
	static int size = 2;
	double spacing = 0;
	static int width = 1800, height = 900;
	static int endState[][] = new int [height/size][width/size];
	static int h = height/size, w = width/size;
	static int itr = 0;
	static int rule = 30;
	static String binary = ""; 
	
	public static void main(String args[]) {
		System.out.print("Enter Rule No.: ");
		Scanner sc = new Scanner(System.in);
		rule = sc.nextInt();
		binary = Integer.toBinaryString(rule);
		binary = String.format("%08d", Integer.parseInt(binary));
		System.out.println("In Binary: "+binary);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0,0,width+6+6+6,height+29+29+3);
		frame.getContentPane().add(new rules());
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setVisible(true);
//		for(int i=0;i<w;i++) {
//			int tmp = new Random().nextInt(100);
//			if (tmp<50)
//				endState[0][i] = 1;
//			else
//				endState[0][i] = 0;
//		}
		endState[0][w/2] = 1;
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
					g2D.fill(new Rectangle2D.Double(spacing+j*size, spacing+(i+1)*size, size-2*spacing, size-2*spacing));
					continue;
				}
				else
					g2D.setColor(Color.WHITE);
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
		
		int[] next = new int [w];
		//for(int j=0;j<w;j++) {
		//	next[j] = endState[itr][j];
		//}
		int i = itr;
		next[0] = binary.charAt(7);
		next[w-1] = next[0];
		for(int j=0;j<w-2;j++) {
			if(endState[i][j]==1&&endState[i][j+1]==1&&endState[i][j+2]==1) next[j+1]=binary.charAt(0)-'0';
			else if(endState[i][j]==1&&endState[i][j+1]==1&&endState[i][j+2]==0) next[j+1]=binary.charAt(1)-'0';
			else if(endState[i][j]==1&&endState[i][j+1]==0&&endState[i][j+2]==1) next[j+1]=binary.charAt(2)-'0';
			else if(endState[i][j]==1&&endState[i][j+1]==0&&endState[i][j+2]==0) next[j+1]=binary.charAt(3)-'0';
			else if(endState[i][j]==0&&endState[i][j+1]==1&&endState[i][j+2]==1) next[j+1]=binary.charAt(4)-'0';
			else if(endState[i][j]==0&&endState[i][j+1]==1&&endState[i][j+2]==0) next[j+1]=binary.charAt(5)-'0';
			else if(endState[i][j]==0&&endState[i][j+1]==0&&endState[i][j+2]==1) next[j+1]=binary.charAt(6)-'0';
			else if(endState[i][j]==0&&endState[i][j+1]==0&&endState[i][j+2]==0) next[j+1]=binary.charAt(7)-'0';
		}
		itr++;
		if(itr>=900) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		for(int j=0;j<w;j++) {
			endState[itr][j] = next[j];
		}
		Toolkit t=Toolkit.getDefaultToolkit();  
        Image im=t.getImage("30.jpg");//.getScaledInstance(200, 100, Image.SCALE_DEFAULT); 
        g2D.drawImage(im, 3,3,this);
		run();
		//repaint();
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(0);
			repaint();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
