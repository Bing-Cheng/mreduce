package Bing;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class Surface extends JPanel {
static final int NCLUSTERS = 3; 
static final int DIMENSION = 3; 
    private void doDrawing(Graphics g) throws InterruptedException {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.blue);

        Dimension size = getSize();
        Insets insets = getInsets();

        int w = size.width - insets.left - insets.right;
        int h = size.height - insets.top - insets.bottom;

        Random generator = new Random();
double [][] center = new double[NCLUSTERS][DIMENSION];

String inFilenamec = "/home/cloudera/workspace/training/datasource/center";
		try {

		BufferedReader bwc = new BufferedReader(new FileReader(inFilenamec));
		String line;
		for (int i = 0; i< NCLUSTERS; i++) {
		line = bwc.readLine();
		String[] num = line.split(" ");
		center[i][0] = Double.parseDouble(num[0]);
		center[i][1] = Double.parseDouble(num[1]);
		}
		bwc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

String inFilename = "/home/cloudera/workspace/training/datasource/kmean";

		try {
			BufferedReader bw = new BufferedReader(new FileReader(inFilename));
			String line;

        for (int K = 0; K < 100; K++) {
        	for (int i = 0; i< NCLUSTERS; i++) {
        		line = bw.readLine();
        		String[] num = line.split(" ");
        		double dx = Double.parseDouble(num[0]);
        		double dy = Double.parseDouble(num[1]);
        	g2d.setColor(Color.GREEN);
         	int sP=3;
            int x = (int)Math.abs(Math.round(dx*(w-7)))+3;
            int y = (int)Math.abs(Math.round(dy*(h-7)))+3;
            g2d.drawLine(x-sP, y, x+sP, y);
            g2d.drawLine(x, y-sP, x, y+sP);
        	}
        	
        }
    	bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done");
        g2d.setColor(Color.RED);
        	for (int i = 0; i< NCLUSTERS; i++) {
         	 double dx = center[i][0];
        	 double dy = center[i][1];
            int x = (int)Math.abs(Math.round(dx*w)) % w;
            int y = (int)Math.abs(Math.round(dy*h)) % h;
            g2d.drawArc(x-5, y-5, 11, 11, 0, 360);
 
        	}
        	double [][] precenter = new double[NCLUSTERS][DIMENSION];
    		for (int i = 0; i<NCLUSTERS;i++){
    			for (int j = 0; j < DIMENSION; j++){
    				precenter[i][j] = ((double)(i + 1)) / (NCLUSTERS + 1);;
    			}
    			double dx = precenter[i][0];
           	 double dy = precenter[i][1];
               int x = (int)Math.abs(Math.round(dx*w)) % w;
               int y = (int)Math.abs(Math.round(dy*h)) % h;
    			g2d.drawRect(x-4,y-4, 9, 9);
    		}
    		
        	g2d.setColor(Color.BLUE);
        	String outFilenamepre = "/home/cloudera/workspace/training/dataoutput/";
        	for (int k = 1; k < 20; k++){
        		String outFilename = outFilenamepre + "iteration" + k +"/part-00000";
    		try {
    			BufferedReader bwo = new BufferedReader(new FileReader(outFilename));
    			String line;
    			for (int i = 0; i< NCLUSTERS; i++) {
    				line = bwo.readLine();
    				String[] num = line.split(" ");
    				String[] num0 = num[0].split("	");
    				System.out.println("num[0]=" + num[0] + " line =" +num0[1]);
    				double dxp = precenter[i][0];
    				double dyp = precenter[i][1]; 
    				int xp = (int)Math.abs(Math.round(dxp*w)) % w;
    	            int yp = (int)Math.abs(Math.round(dyp*h)) % h;
    			  	 double dx = Double.parseDouble(num0[1]);
    	        	 double dy = Double.parseDouble(num[1]);
    	        	 
    	            int x = (int)Math.abs(Math.round(dx*w)) % w;
    	            int y = (int)Math.abs(Math.round(dy*h)) % h;
    	            g2d.drawLine(xp, yp, x, y);
    				g2d.drawRect(x-4, y-4, 9, 9);
    				precenter[i][0] = dx;
    				precenter[i][1] = dy;
    				}
    			//wait(1000);
    				bwo.close();
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
        	}//k

         
       
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        try {
			doDrawing(g);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

public class Evaluation extends JFrame {

    public Evaluation() {

        initUI();
    }

    private void initUI() {
        
        setTitle("Points");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new Surface());

        setSize(600, 600);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

            	Evaluation ps = new Evaluation();
                ps.setVisible(true);
            }
        });
    }
}