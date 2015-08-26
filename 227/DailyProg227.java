import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DailyProg227 {

	
	public static void main(String args[]) throws Exception {
		
		DailyProg227 challenge = new DailyProg227();
		
		// read field
		FieldReader fieldReader = challenge.new SystemInputFieldReader();
		Field field = fieldReader.readField();
		
		// search field for chains
		int maxChainNum = 0;
		for(int y = 0; y < field.getHeight(); y++) {    // go thru rows
			for(int x = 0; x < field.getWidth(); x++) { // go thru column
				
				Point p = field.getPoint(x, y);
				if(p != null) {
					if(p.getChainNum() == null) {
						
						// new chain
						maxChainNum++;
						challenge.setPointChainNumAndAllNeighbours(x, y, 
								maxChainNum, field);
						
					} 
				}
				
			}
			
		}
		
		System.out.println(maxChainNum);
	}
	
	
	/**
	 * Recursively set chain number on individual points
	 * @param x
	 * @param y
	 * @param chainNum
	 * @param field
	 */
	private void setPointChainNumAndAllNeighbours(int x, int y, int chainNum, 
			Field field) {
		
		Point p = field.getPoint(x, y);
		p.setChainNum(chainNum);
		
		// try left neighbour
		if(x != 0) {
			Point leftNeighbour = field.getPoint(x-1, y);
			if(leftNeighbour != null && leftNeighbour.getChainNum() == null) {
				setPointChainNumAndAllNeighbours(x-1, y, chainNum, field);
			}
		}
		
		// try right neighbour
		if(x < (field.getWidth() -1)){
			Point rightNeighbour = field.getPoint(x+1, y);
			if(rightNeighbour != null && rightNeighbour.getChainNum() == null) {
				setPointChainNumAndAllNeighbours(x+1, y, chainNum, field);
			}	
		}
		
		// try top neighbour
		if(y != 0) {
			Point topNeighbour = field.getPoint(x, y-1);
			if(topNeighbour != null && topNeighbour.getChainNum() == null) {
				setPointChainNumAndAllNeighbours(x, y-1, chainNum, field);
			}
		}
		
		// try bottom neighbour
		if(y < (field.getHeight() -1)) {
			Point bottomNeighbour = field.getPoint(x, y + 1);
			if(bottomNeighbour != null&&bottomNeighbour.getChainNum() == null) {
				setPointChainNumAndAllNeighbours(x, y+1, chainNum, field);
			}
		}
	}
	
	/**
	 * class to represent point in field
	 */
	public class Point {
		
		private Integer chainNum;
		
		// CONSTRUCTORS
		public Point() {
		}
		
		public Point(int chainNum) {
			this.chainNum = chainNum;
		}
		
		// GETTERS & SETTERS
		public Integer getChainNum() {
			return chainNum;
		}
		public void setChainNum(Integer chainNum) {
			this.chainNum = chainNum;
		}
		
		
		
	}
	
	/**
	 * class representing field
	 */
	public class Field {
		
		// 2D Array List
		private List<List<Point>> points;
		
		public Field () {
			points = new ArrayList<List<Point>>();
		}
		
		private int width;
		private int height;
		
		public int getWidth() {
			return width + 1;
		}
		
		public int getHeight() {
			return height + 1;
		}
		
		/**
		 * get point at index
		 * @param x
		 * @param y
		 * @return
		 */
		public Point getPoint(int x, int y) {
			// check that row and col aren't null
			if(points == null) { return null; }
			if(points.size() - 1 < x) { return null; }
			// add new point
			return points.get(x).get(y);
		}
		
		/**
		 * add a new point at index. Will replace current point if one exists
		 * @param x
		 * @param y
		 */
		public void addPoint(int x, int y) {
			setPoint(x, y, new Point());
		}
		
		/**
		 * set point at index, will repleace current point if one exists
		 * @param x
		 * @param y
		 * @param p
		 */
		public void setPoint(int x, int y, Point p) {
			// check that row and col aren't null
			if(points == null) {
				points = new ArrayList<List<Point>>();
			}
			if(points.size() - 1 < x) {
				points.add(x, new ArrayList<Point>());
			}
			
			if(points.get(x).size() -1 >= y) {
				points.get(x).set(y, p);	// replace
			} else {
				points.get(x).add(y, p);	// add new
			}
			
			// fix dimensions
			if(x > width)  { width = x; }
			if(y > height) { height = y; }
		}
		
		
		public String toString() {
			
			StringBuilder field = new StringBuilder();
			for(int i = 0 ; i < this.getHeight(); i++) {
				for(int j = 0; j < this.getWidth(); j++) {
					field.append(this.getPoint(j, i) != null ? "x" : " ");
				}
				field.append("\n");
			}
			return field.toString();
		}
		
		public String toChainNumString() {
			
			StringBuilder field = new StringBuilder();
			for(int i = 0 ; i < this.getHeight(); i++) {
				for(int j = 0; j < this.getWidth(); j++) {
					Point p = this.getPoint(j, i);
					field.append(p == null ? " " 
							: p.getChainNum() == null ? "." :  p.getChainNum());
				}
				field.append("\n");
			}
			return field.toString();
		}
	}
	
	/**
	 * Interface for reading field 
	 */
	public interface FieldReader {
		public Field readField() throws Exception;
	}
	
	
	/**
	 * Implementation for reading field from standard in
	 */
	public class SystemInputFieldReader implements FieldReader {
		
		/**
		 * read field from standard in
		 * @return Field
		 */
		public Field readField() throws Exception {
			
			Field field = new Field();
			int[] fieldSize = this.readFieldSize();
			Scanner scanner = new Scanner(System.in);
			
			for(int y = 0; y < fieldSize[0]; y++) {			// loop thru row
				String fieldLine = scanner.nextLine();
				for(int x = 0; x < fieldSize[1]; x++) {		// loop thru cols
					if(fieldLine.charAt(x) == 'x') {
						field.addPoint(x, y);				// create points
					} else {
						field.setPoint(x, y, null);
					}
				}
			}
		
			scanner.close();
			return field;
		}
		
		/**
		 * read field size from standard in
		 * @return [field height, field width]
		 * @throws Exception if input is not in correct format "h w"
		 */
		private int[] readFieldSize() throws Exception {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			String[] fieldSize = scanner.nextLine().trim().split(" ");
			int fieldHeight = Integer.parseInt(fieldSize[0]);
			int fieldWidth = Integer.parseInt(fieldSize[1]);
			//scanner.close();
			return new int[] {fieldHeight, fieldWidth};
		}
		
	}
	
}
