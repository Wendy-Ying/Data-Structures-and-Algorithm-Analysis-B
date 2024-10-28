
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Collections;

public class Main {

    public static int[] arrayListToIntArray(ArrayList<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public static int[] getSortedIndices(int[] array) {
        int[][] elements = new int[array.length][2];
        for (int i = 0; i < array.length; i++) {
            elements[i][0] = array[i];
            elements[i][1] = i;
        }

        Arrays.sort(elements, Comparator.comparingInt(e -> e[0]));

        int[] sortedIndices = new int[array.length];
        for (int i = 0; i < elements.length; i++) {
            sortedIndices[i] = elements[i][1];
        }

        return sortedIndices;
    }

    private static void inOrderTraversal(int[] array, int index, int[] result, int[] resultIndex) {
        if (index >= array.length || array[index] == -1) {
            return;
        }
        inOrderTraversal(array, 2 * index, result, resultIndex);
        result[resultIndex[0]] = array[index];
        resultIndex[0]++;
        inOrderTraversal(array, 2 * index + 1, result, resultIndex);
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // first line & create space
        int n = sc.nextInt();
        ArrayList<Integer> arraylist = new ArrayList<>(Collections.nCopies(n + 1, -1));
        int[] readNumber = new int[n + 1];
        int[] index = new int[n + 1];
        int[] father = new int[n + 1];
        int[] position = new int[n + 1];

        // second line
        for (int i = 1; i <= n; i++) {
            readNumber[i] = sc.nextInt();
        }

        // subsequent n-1 line
        for (int i = 2; i <= n; i++) {
            father[i] = sc.nextInt();
            position[i] = sc.nextInt();
        }

        // fill in array
        arraylist.set(1, readNumber[1]);
        index[1] = 1;
        int[] sortedIndex = getSortedIndices(father);
        for (int i = 2; i <= n; i++) {
            index[sortedIndex[i]] = 2 * index[father[sortedIndex[i]]] + position[sortedIndex[i]];
            if (arraylist.size() <= index[sortedIndex[i]]) {
                arraylist.ensureCapacity(index[sortedIndex[i]]);
                while (arraylist.size() <= index[sortedIndex[i]]) {
                    arraylist.add(-1);
                }
            }
            arraylist.set(index[sortedIndex[i]], readNumber[sortedIndex[i]]);
        }
        int[] array = arrayListToIntArray(arraylist);

        // inputs have been correctly read till now

        // sort
        int[] result = new int[n];
        int[] resultIndex = {0};
        inOrderTraversal(array, 1, result, resultIndex);

        // inputs have been correctly inorder sorted(not by number, but by index) till now

        for (int i = 0; i < n; i++) {
            result[i] -= i + 1; // use result[i]-i to compare in case of continuous same number
        }

        int[] list = new int[n]; // save length of longest non-decreasing subsequence
        Arrays.fill(list,1);

        // compute length of longest non-decreasing subsequence for each case
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (result[i] >= result[j]) {
                    list[i] = Math.max(list[i], list[j] + 1);
                }
            }
        }

        // the biggest one in list[n] is the longest non-decreasing subsequence
        int maxLength = 0;
        for (int i = 0; i < n; i++) {
            maxLength = Math.max(maxLength, list[i]);
        }

        // edit n - max length times
        System.out.print(n-maxLength);

    }

}
