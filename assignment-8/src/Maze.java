

import java.util.ArrayList; 
import java.awt.Color; 
 
/** 
 * A class for decomposing images into connected components. 
 */ 
public class Maze {
 
   private UnionFind uf; 
   private DisplayImage image;
   private int startX;   // x-coordinate for the start pixel
   private int startY;	 // y-coordinate for the start pixel
   private int endX;	 // x-coordinate for the end pixel
   private int endY;	 // y-coordinate for the end pixel
  
  /** 
    * Constructor - reads image, and decomposes it into its connected components.
    * After calling the constructor, the mazeHasSolution() and areConnected() methods
	* are expect to return a correct solution.
    * 
    * @param fileName name of image file to process. 
    */ 
   public Maze (String fileName, Color c) {
        this.startX = -1;
        this.startY = -1;
        this.endX = -1;
        this.endY = -1;
        this.image = new DisplayImage(fileName);
        this.uf = new UnionFind(this.image.width() * this.image.height());
        for (int w = 0; w < this.image.width(); w++){
            for (int h = 0; h < this.image.height(); h++){
                connect(w,h, w - 1, h);
                connect(w,h, w + 1, h);
                connect(w,h, w, h - 1);
                connect(w,h, w, h + 1);
            }
        }
   }

   /**
    * Generates a unique integer id from (x, y) coordinates. 
    * It is suggested you implement this function, in order to transform
	* pixels into valid indices for the UnionFind data structure.
    *
	* @param x x-coordinate. 
    * @param y y-coordinate. 
    * @return unique id. 
    */ 
   private int pixelToId (int x, int y) { 
        return (y* this.image.width()) + x;
   } 
 
   /** 
    * Connects two pixels if they both belong to the same image 
    * area (background or foreground), and are not already connected. 
    * It is assumed that the pixels are neighbours.
    * 
    * @param x1 x-coordinate of first pixel. 
    * @param y1 y-coordinate of first pixel. 
    * @param x2 x-coordinate of second pixel. 
    * @param y2 y-coordinate of second pixel. 
    */ 
   public void connect (int x1, int y1, int x2, int y2, Color color) {
        try {
            checkIfCoordinateIsRed(x1, y1, color);
            checkIfCoordinateIsRed(x2, y2, color);
            if (isCoordinateInRange(x2, y2) && bothHaveSameColor(x1,y1,x2,y2)){
                this.uf.union(pixelToId(x1, y1), pixelToId(x2,y2));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
   }

   private void checkIfCoordinateIsRed(int x, int y, Color color) {
       if (isCoordinateInRange(x,y)){
           if (this.image.isRed(x,y)){
               this.image.set(x,y,color);
               if (startX == -1){
                   this.startX = x;
                   this.startY = y;
               } else {
                   this.endX = x;
                   this.endY = y;
               }
           }
       }
   }

   private boolean bothHaveSameColor(int x1, int y1, int x2, int y2) {
       return this.image.isOn(x1,y1) == this.image.isOn(x2, y2);
   }

   private boolean isCoordinateInRange(int x, int y) {
       return isValueInRange(x, 0, this.image.width()) && isValueInRange(y, 0, this.image.height());
   }

   private boolean isValueInRange(int value, int start, int end) {
       return value >= start && value < end;
   }
 
   /** 
    * Checks if two pixels are connected (belong to the same component). 
    * 
    * @param x1 x-coordinate of first pixel. 
    * @param y1 y-coordinate of first pixel. 
    * @param x2 x-coordinate of second pixel. 
    * @param y2 y-coordinate of second pixel. 
    * @return true if the pixels are connected, false otherwise. 
    */ 
   public boolean areConnected (int x1, int y1, int x2, int y2) { 
		//your code comes here
		return false;
   } 
 
   /** 
    * Finds the number of components in the image. 
    * 
    * @return the number of components in the image 
    */ 
   public int getNumComponents() { 
		//your code comes here
		return 0;
   } 
 
   /**
    * Returns true if and only if the maze has a solution.
    * In this case, the start and end pixel will belong to the same connected component.
    * 
    * @return true if and only if the maze has a solution
    */
   public boolean mazeHasSolution(){
		//your code comes here
		return false;
   }
      
   /** 
    * Creates a visual representation of the connected components. 
    * 
    * @return a new image with each component colored in a random color. 
    */ 
   public DisplayImage getComponentImage() { 
 
      DisplayImage compImg = new DisplayImage (image.width(), image.height()); 
      ArrayList<Integer> usedIds = new ArrayList<Integer>(); 
      int numComponents = getNumComponents(); 
      final int MAX_COLOR = 0xffffff; 
      Color colors[] = new Color[numComponents]; 
 
      colors[0] = new Color (0, 0, 100); 
      for (int c = 1; c < numComponents; c++) 
         colors[c] = new Color ((int)(Math.random() * MAX_COLOR)); 
 
      for (int x = 0; x < image.width(); x++) 
         for (int y = 0; y < image.height(); y++) { 
            int componentId = uf.find (pixelToId (x, y)); 
            // Check if this id already exists (inefficient for a large number of components). 
            int index = -1; 
            for (int i = 0; i < usedIds.size(); i++) 
               if (usedIds.get (i) == componentId) 
                  index = i; 
            if (index == -1) { 
               usedIds.add (componentId); 
               index = usedIds.size() - 1; 
            } 
            compImg.set (x, y, colors[index]); 
         } 
 
      return compImg; 
   } 
 
   /** 
    * Various tests for the segmentation functionality. 
    * 
    * @param args command line arguments - name of file to process. 
    */ 
   public static void main (String[] args) { 
 
	  Maze maze = new Maze ("src/images/maze2.PNG", Color.black);
 
      System.out.println (maze.mazeHasSolution());
 
      System.out.println ("Number of components: " + maze.getNumComponents()); 
 
      DisplayImage compImg = maze.getComponentImage();
      compImg.show(); 
   } 
} 
