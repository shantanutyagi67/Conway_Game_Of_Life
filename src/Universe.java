import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;



public class Universe extends JComponent implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JFrame frame = new JFrame("Universe Simulation");
	static int size = 5;
	double spacing = 0.2;
	static int width = 1600, height = 900;
	static int endState[][] = new int [height/size][width/size];
	static int h = height/size, w = width/size;
	static double itr = 1;
	public double mx,my;
	public int pause = 0;
	public boolean start = false, change = false;
	static int con = 5;
	
	public Universe(){
		Move move = new Move();
		this.addMouseMotionListener(move);
		Click click = new Click();
		this.addMouseListener(click);
		this.addKeyListener(move);
		this.setFocusable(true);
	}
	
	public static void main(String args[]) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0,0,width+6+6+6,height+29+29+3);
		frame.getContentPane().add(new Universe());
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setVisible(true);
		
		setBoard(con);
		
	}
	public static void setBoard(int con) {
		
		for(int i=0;i<h;i++) {
			for(int j=0;j<w;j++) {
				endState[i][j] = 0;
			}
		}
		switch(con) {
		case 1:{ //gliders
			endState[h/2][w/2+1] = 1;
			endState[h/2][w/2+2] = 1;
			endState[h/2+1][w/2+1] = 1;
			endState[h/2+1][w/2] = 1;
			endState[h/2+2][w/2+1] = 1;
			break;
		}
		case 2:{ // ship
			endState[h/2][w/2+1] = 1;
			endState[h/2][w/2+2] = 1;
			endState[h/2][w/2+3] = 1;
			endState[h/2+1][w/2] = 1;
			endState[h/2+1][w/2+3] = 1;
			endState[h/2+2][w/2+3] = 1;
			endState[h/2+3][w/2+3] = 1;
			endState[h/2+4][w/2] = 1;
			endState[h/2+4][w/2+2] = 1;
			break;
		}
		case 3:{ //unbounded
			endState[h/2][w/2] = 1;
			endState[h/2][w/2+1] = 1;
			endState[h/2][w/2+2] = 1;
			endState[h/2][w/2+4] = 1;
			endState[h/2+1][w/2] = 1;
			endState[h/2+2][w/2+3] = 1;
			endState[h/2+2][w/2+4] = 1;
			endState[h/2+3][w/2+1] = 1;
			endState[h/2+3][w/2+2] = 1;
			endState[h/2+3][w/2+4] = 1;
			endState[h/2+4][w/2] = 1;
			endState[h/2+4][w/2+2] = 1;
			endState[h/2+4][w/2+4] = 1;
			break;
		}
		case 4:{ // glider gun
			int[][] gliderGun = {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
			                     {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0},
			                     {0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
			                     {0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
			                     {1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			                     {1,1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1,1,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0},
			                     {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
			                     {0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			                     {0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
			for(int i=0;i<gliderGun.length;i++) {
				for(int j=0;j<gliderGun[0].length;j++) {
					endState[h/4+i][w/4+j] = gliderGun[i][j];
				}
			}
			break;
		}
		case 5:{ // ladder
			int[][] ladder =   {{1,0,0,0,0},
                    			{1,1,1,0,0},
                    			{0,1,1,1,0},
                    			{1,0,1,1,1},
                    			{1,0,1,1,0},
                    			{0,1,1,0,0},
                    			{1,1,1,0,0},
                    			{1,0,0,0,0},
                    			{0,0,0,0,0}};
			for(int i=0;i<ladder.length;i++) {
				for(int j=0;j<ladder[0].length;j++) {
					for(int k=0;k<h/10;k++) {
						endState[10*k+i][j] = ladder[i][j];
					}
				}
			}
			break;
		}
		case 6:{ // (i+j)%2
			for(int i=0;i<h;i++) {
				for(int j=0;j<w;j++) {
					endState[i][j] = (i+j)%2;
				}
			}
			break;
		}
		case 7:{ // (i*j)%2
			for(int i=0;i<h;i++) {
				for(int j=0;j<w;j++) {
					endState[i][j] = (i*j)%2;
				}
			}
			break;
		}
		default:{
			for(int i=0;i<h;i++) {
				for(int j=0;j<w;j++) {
					int tmp = new Random().nextInt(100);
					if (tmp<30)
						endState[i][j] = 1;
					else
						endState[i][j] = 0;
				}
			}
		}
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
		if (pause!=0) {
		itr++;
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
					if(cnt<2) {
						if(con==5) next[i][j] = 1;// under population death
						else next[i][j] = 0;// under population death
					}
					else if (cnt>=2&&cnt<=3) next[i][j]=1;// stay alive
					else if (cnt>3) {
						if(con==5) next[i][j]=1;// over population death
						else next[i][j]=0;// over population death
					}
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
    	}
		
		//System.out.println(mx+" "+my);
		run();
		//repaint();
	}
	
	
	public int inBoxX() {
		for(int i=0;i<h;i++) {
			for(int j=0;j<w;j++) {
				if(mx >= spacing+j*size+7 && mx <= (j+1)*size+2);
					//return j;
			}
		}
		return (int) (mx/size);
	}
	
	public int inBoxY() {
		for(int i=0;i<h;i++) {
			for(int j=0;j<w;j++) {
				if(my >= spacing+(i+1)*size+26 && my <= 26+(i+2)*size-spacing-1);
					//return i;
			}
		}
		return (int) (my/size)-1;
	}
	
	
	public class Move implements MouseMotionListener, KeyListener{
		@Override
		public void mouseDragged(MouseEvent e) {
			
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			mx = e.getX();
			my = e.getY();
			if(start) {
				int ii = inBoxY(), jj = inBoxX();
				if(ii>=0&&jj>=0&&ii<h&&jj<w)
					endState[ii][jj] = 1;
			}
			//if(change == true) {
				//change = false;
				//start = false;
			//}
			//else if(change == false) {
				//change = true;
				//start = true;
			//}
			//repaint();
		}
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_SPACE){
				pause += 1;
				pause%=2;
			}
			//System.out.println(e.getKeyCode());
			if (e.getKeyCode()<=57&&e.getKeyCode()>=48){
				setBoard(e.getKeyCode()-48);
				con = e.getKeyCode()-48;
				repaint();
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
	public class Click implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			//System.out.println("clicked");
			if(change == true) {
				change = false;
				start = false;
			}
			else if(change == false) {
				change = true;
				start = true;
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			//System.out.println("entered");
		}
		@Override
		public void mouseExited(MouseEvent e) {
			//System.out.println("exited");
		}
		@Override
		public void mousePressed(MouseEvent e) {
			//System.out.println("pressed");
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			//System.out.println("released");
			
		} 
	}
	
	
	@Override
	public void run() {
		try {
			Thread.sleep(1);
			repaint();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
