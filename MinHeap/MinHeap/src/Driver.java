/**
 * Exam 2
 *
 * @author Hunter Dubbs
 * @version 11/13/2018
 *
 * This class is used to test the functionality of the MinHeap class.
 * It uses it to sort some Integers and some Strings.
 */
public class Driver {

    /**
     * This is the main method of the program.
     * @param args launch arguments
     */
    public static void main(String[] args) {

        //case 1
        MinHeap<Integer> minHeap1 = new MinHeap();

        String in1 = "12,19,99,45,55,67,23,1,3,900,33,56,78,65,7,901,593,22,2,15";
        String[] inArray1 = in1.split(",");
        for (String str : inArray1){
            minHeap1.insert(Integer.parseInt(str));
        }

        System.out.println("Case 1:");
        Comparable[] sorted1 = minHeap1.sort();
        for(Comparable x : sorted1){
            System.out.print(x + " ");
        }
        System.out.println("\n");

        //case 2
        MinHeap<String> minHeap2 = new MinHeap();
        String in2 = "Jim,Bob,Kelly,Sue,Jimmy,Pat,Trish,James,Andrew,Kim,Sally,Ziba,Jamil,Abe,Zangi,Gabri,Tess,David,Adam,Noush,Steph";
        String[] inArray2 = in2.split(",");
        for(String str : inArray2){
            minHeap2.insert(str);
        }

        System.out.println("Case 2:");
        Comparable[] sorted2 = minHeap2.sort();
        for(Comparable x : sorted2){
            System.out.print(x + " ");
        }
        System.out.println();
    }

}
