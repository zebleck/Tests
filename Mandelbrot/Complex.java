package Mandelbrot;
public class Complex
{
     
    public double x, y;
     
    public Complex(float x, float y)
    {
       this.x = x;
       this.y = y;
    }
     
    public void pow(float e) {
         
    }
     
    public void multiply(Complex c) {
    	double newX = x*c.x - y*c.y;
    	double newY = x*c.y + y*c.x;
        this.x = newX;
        this.y = newY;
    }
     
    public void add(Complex c) {
        x = x + c.x;
        y = y + c.y;
    }
     
    public double abs() {
        return Math.sqrt(x*x+y*y);
    }
}