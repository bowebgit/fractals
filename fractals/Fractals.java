/**
 * Fractals.java  
 * 
 * Fractals are generated in the complex plane. Consider a point c in the 
 * complex plane. Define a sequence as follows:
 * 		z_0 = c
 * 		z_n = z_(n-1)^2 + c
 * 
 * We are interested in what happens to z_n when n is large. It is known that
 * the point z_n will diverge to infinity for some values of c, and for other
 * values z_n will stay bounded within a circle of radius 2 - |z_n| <= 2.
 * 
 * Example: c = .2 + 1.3i
 * 	z_0 = .2 + 1.3i
 * 	z_1 = (.2 + 1.3i)^2 + c = (.2 + 1.3i)^2 + (.2  + 1.3i) = ...
 * 	z_2 = ((.2 + 1.3i)^2 + c)^2 + c = ...
 *  ...
 *  
 * To draw the Mandelbrot set, if a point in the complex plane stays bounded, 
 * color it black. Otherwise color the point based on how many iterations
 * before it exits a circle of radius 2.
 * 
 */

package fractals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import library.Complex;

public class Fractals {
	private static final int MAXITR = 100;
	private static final int width = 200;
	private static final int height = 200;
	
	public void generate(){
		// bimg.setRGB(20, 20, 255255255); // white
		// bimg.setRGB(21, 20, 000000000); // back
		

		// METHOD FOR PRINTING FRACTALS
		// for each pixel in the image, create its position as a complex number
		// ask if it is divergent and get the number of iterations needed to exit a radius or -1
		// color the pixel based on number of iterations to diverge
		
		int iterations = -1;
		double real = 0.0;
		double imag = 0.0;
		Complex c = new Complex(0.0, 0.0);

		BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		double xstep = 4.0/width;					// x-axis in (-2, 2)
		double ystep = 4.0/height;					// y-axis in (-2i, 2i)

		
		for(int x = 0; x < width; x++){
			real = real + xstep;
			imag = 0.0;
			for(int y = 0; y < height; y++){
				imag = imag + ystep;
				Complex pt = new Complex(real - 2, imag - 2);
				
				
				iterations = iterate(pt);
				System.out.println(pt + " " + iterations);
				if(iterations < 0){
					bimg.setRGB(x, y, 255255255);
				}else{
					bimg.setRGB(x, y,  000000000);
				}
	
			}
		}
		
		try{	
			File outputfile = new File("fractal.png");
			ImageIO.write(bimg, "png", outputfile);
		}catch(IOException e){
			//
		}
		
		
	}
	
	/**
	 * @param c		a complex number
	 * @return		the integer number of iterations before point leaves circle radius 2, or -1 if bounded
	 */
	private int iterate(Complex c){
		int iterations = -1;
		Complex z_0 = c;	
		Complex z_nmin1 = c;
		Complex z_n = c;
		
		
		for(int i = 0; i < MAXITR; i++){
			if(z_n.abs() > 2){
				iterations = i;
				break;
			}
			z_n = (z_nmin1.times(z_nmin1)).plus(z_0);
			z_nmin1 = z_n;

		}
		
		return iterations;
	}
	
	
}
