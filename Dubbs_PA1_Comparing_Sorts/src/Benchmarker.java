/**
 * This program runs sample data through the four simple sort algorithms 
 * (selection, insertion, bubble, and quick) to determine their runtime
 * relative to one another. The exact performance will vary from system 
 * to system. The test parameters can be configured as need be.
 * @author Hunter Dubbs
 * @version 8/29/2018
 * made for CIT360-02
 *
 */
public class Benchmarker {
	
	//parameters for the tests - change these here
	public final static int NUM_TESTS = 500;
	public final static int ARRAY_SIZE = 1000;
	public final static int ELEMENT_MAX = 255;

	/**
	 * Main method for the program; runs the tests and then outputs the results.
	 * @param args launch arguments
	 */
	public static void main(String[] args) {
		
		int[] ary = new int[ARRAY_SIZE];
		long time, longestTime;
		long[] avg = new long[4];
		String[] avgName = {"Selection Sort", "Insertion Sort", "Bubble Sort", "Quick Sort"};

		//store test durations
		long selectionSortResults[] = new long[NUM_TESTS];
		long insertionSortResults[] = new long[NUM_TESTS];
		long bubbleSortResults[] = new long[NUM_TESTS];
		long quickSortResults[] = new long[NUM_TESTS];
		
		//run configured number of tests
		for(int i = 0; i < NUM_TESTS; i++) {
			fillArray(ary);
			//Selection sort test
			time = System.nanoTime();
			selectionSort(ary.clone());
			selectionSortResults[i] = System.nanoTime() - time;
			//Insertion sort test
			time = System.nanoTime();
			insertionSort(ary.clone());
			insertionSortResults[i] = System.nanoTime() - time;
			//Bubble sort test
			time = System.nanoTime();
			bubbleSort(ary.clone());
			bubbleSortResults[i] = System.nanoTime() - time;
			//Quick sort test
			time = System.nanoTime();
			quickSort(ary.clone(), 0, ary.length - 1);
			quickSortResults[i] = System.nanoTime() - time;
		}
		
		//calculate average execution times
		avg[0] = avgTimes(selectionSortResults);
		avg[1] = avgTimes(insertionSortResults);
		avg[2] = avgTimes(bubbleSortResults);
		avg[3] = avgTimes(quickSortResults);
		longestTime = getLongestTime(avg);
		
		//output results
		System.out.println("Sort Method Benchmarking");
		System.out.println("Array size\t\t" + ARRAY_SIZE);
		System.out.println("Array Element Max\t" + ELEMENT_MAX);
		System.out.println("Tests Conducted\t\t" + NUM_TESTS);
		System.out.println("\nTest Results by Algorithm (avg in nanoseconds)");
		for(int i = 0; i < avg.length; i++) {
			System.out.println(avgName[i] + "\t" + avg[i] + "\t" + getHistogramBar(avg[i], longestTime));
		}
	}
	
	/**
	 * The selection sort algorithm has O(n^2) performance. It involves
	 * iterating through the array to find the next lowest value, one at
	 * a time.
	 * @param ary The array to be sorted
	 */
	private static void selectionSort(int[] ary) {
		int lowPos;
		for(int i = 0; i < ary.length; i++) {
			lowPos = i;
			for(int j = 1; j < ary.length; j++) {
				if(ary[j] < ary[lowPos]) {
					lowPos = j;
				}
			}
			int temp = ary[i];
			ary[i] = ary[lowPos];
			ary[lowPos] = temp;
		}
	}
	
	/**
	 * The insertion sort algorithm has O(n^2) performance. It involves iterating
	 * through the array and moving the higher numbers into place leftwards
	 * from its original position.
	 * @param ary The array to be sorted
	 */
	private static void insertionSort(int[] ary) {
		for(int i = 1; i < ary.length; i++) {
			int j = ary[i];
			int jPos = i - 1;
			while(jPos >= 0 && ary[jPos] > j) {
				ary[jPos + 1] = ary[jPos];
				jPos--;
			}
			ary[jPos + 1] = j;
		}
	}
	
	/**
	 * The bubble sort algorithm has O(n^2) performance. It involves
	 * swapping adjacent numbers when one is higher than the other
	 * while iterating through the array. It is slightly less efficient
	 * than other sorts because of more write operations.
	 * @param ary The array to be sorted
	 */
	private static void bubbleSort(int[] ary) {
		for(int i = 0; i < ary.length - 1; i++) {
			for(int j = 0; j < ary.length - 1 - i; j++) {
				if(ary[j] > ary[j + 1]) {
					int temp = ary[j + 1];
					ary[j + 1] = ary[j];
					ary[j] = temp;
				}
			}
		}
	}
	
	/**
	 * The quick sort algorithm has O(nlogn) performance. It involves
	 * recursively swapping numbers from either side of the array
	 * when they are respectively higher and lower than the midpoint
	 * value. The array is halved with each recursive step.
	 * @param ary The array to be sorted
	 * @param low The starting point of the sort
	 * @param high The ending point of the sort
	 */
	private static void quickSort(int[] ary, int low, int high) {
		int i = low;
		int j = high;
		int midVal = ary[low + (high - low) / 2];
		while(i <= j) {
			while(ary[i] < midVal) {
				i++;
			}
			while(ary[j] > midVal) {
				j--;
			}
			if(i <= j) {
				int temp = ary[j];
				ary[j] = ary[i];
				ary[i] = temp;
				i++;
				j--;
			}
		}
		if(i < high) {
			quickSort(ary, i, high);
		}
		if(j > low) {
			quickSort(ary, low, j);
		}
	}
	
	/**
	 * This function fills the provided array with random values
	 * between 0 and the configured highest value. The array remains
	 * the same size that it was initialized as.
	 * @param ary The array to be filled
	 */
	private static void fillArray(int[] ary) {
		for(int i = 0; i < ary.length; i++) {
			ary[i] = (int) Math.floor(Math.random() * ELEMENT_MAX);
		}
	}
	
	/**
	 * This function calculates the average execution time
	 * of the test result outputs.
	 * @param ary The result set to be averaged
	 * @return The average execution time
	 */
	private static long avgTimes(long[] ary) {
		long sum = 0;
		for(int i = 0; i < ary.length; i++) {
			sum += ary[i];
		}
		return sum / ary.length;
	}
	
	/**
	 * This function finds the highest execution time
	 * from the provided array of averaged results.
	 * @param ary The array containing the average results
	 * @return The highest execution time
	 */
	private static long getLongestTime(long[] ary) {
		long highest = ary[0];
		for(int i = 1; i < ary.length; i++) {
			if(ary[i] > highest) {
				highest = ary[i];
			}
		}
		return highest;
	}
	
	/**
	 * This function builds a histogram bar out of "=" characters.
	 * @param val The execution time
	 * @param max The largest execution time
	 * @return A string of "="'s that represent the provided value
	 */
	private static String getHistogramBar(long val, long max) {
		String out = "";
		for(int i = 0; i < (int) val * 50 / (float) max; i++) {
			out += "=";
		}
		return out;
	}

}
