package Bing;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.io.BufferedWriter;
import java.io.File;
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
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.blue);

        Dimension size = getSize();
        Insets insets = getInsets();

        int w = size.width - insets.left - insets.right;
        int h = size.height - insets.top - insets.bottom;

        Random generator = new Random();
double [][] center = new double[NCLUSTERS][DIMENSION];
center[0][0] = 0.52;
center[0][1] = 0.27;

center[1][0] = 0.29;
center[1][1] = 0.72;

center[2][0] = 0.62;
center[2][1] = 0.62;
double var = 0.1;
String outFilenamec = "/home/cloudera/workspace/training/datasource/center";
File filec = new File(outFilenamec);
	
	if (!filec.exists()) {
		try {
			filec.createNewFile();
			FileWriter fwc;
		fwc = new FileWriter(filec.getAbsoluteFile());
		BufferedWriter bwc = new BufferedWriter(fwc);
		for (int i = 0; i< NCLUSTERS; i++) {
		bwc.write(center[i][0] + " " + center[i][1]);bwc.newLine();
		}
		bwc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	
String outFilename = "/home/cloudera/workspace/training/datasource/kmean";
	File file = new File(outFilename);
		System.out.println(outFilename);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		FileWriter fw;
		try {
			fw = new FileWriter(file.getAbsoluteFile());

		BufferedWriter bw = new BufferedWriter(fw);

		double dx=0,dy=0;
        for (int K = 0; K < 100; K++) {
        	for (int i = 0; i< NCLUSTERS; i++) {
        		
        	
        	 dx = generator.nextGaussian()*var + center[i][0];
        	 dy = generator.nextGaussian()*var + center[i][1];
        	if (dx<0) dx = 0;
        	if (dx>1) dx = 1;
        	if (dy<0) dy = 0;
        	if (dy>1) dy = 1;
        	int sP=3;
            int x = (int)Math.abs(Math.round(dx*(w-7)))+3;
            int y = (int)Math.abs(Math.round(dy*(h-7)))+3;
            g2d.drawLine(x-sP, y, x+sP, y);
            g2d.drawLine(x, y-sP, x, y+sP);
            bw.write(dx + " " + dy);bw.newLine();
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
       
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}

public class generateData extends JFrame {

    public generateData() {

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

            	generateData ps = new generateData();
                ps.setVisible(true);
            }
        });
    }
}