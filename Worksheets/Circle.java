public class Circle 
{   // Start of the class
    // Declare the radius of the circle
    private double radius;
    
    public Circle (double r) // Specify the data type of the radius parameter
    {
        this.radius = r;
    }

    public double getRadius() // Method to get the radius of the circle
    {
        return this.radius;
    }

    public double area() // Method to calculate the area of the circle
    {
        return Math.PI * this.radius * this.radius;
    }
}
