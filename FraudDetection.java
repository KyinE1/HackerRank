public class FraudDetection {
	/** Swap the elements specified in the array.
	 * 
	 * @param arr: Array containing the elements.
	 * @param a: Element to be swapped in array.
	 * @param b: Element to be swapped in array.
	 */
	private static void swap(int[] arr, int a, int b) {
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}
	
	/** Sort the array.
	 * 
	 * @param arr: Array containing the elements.
	 */
	private static void insertionSort(int[] arr) {
		// Process the elements.
		for (int i = 1; i < arr.length; ++i) {
   			int j = i;
   			
   			// Get current item (j) into correct index.
   			while (j > 0 && arr[j] < arr[j - 1]) {
      			swap(arr, j, j - 1);
      			--j;
  			}
		}
	}
	
	/** Recursively sort sub arrays given the pivot. 
	 *
	 * @param arr: Array containing the elements.
	 * @param first: First element of the array.
	 * @param last: Last element of the array.
	 */
	private static void quickSort(int[] arr, int first, int last) {
		// Threshold where insertion sort is more efficient.
		if (arr.length <= 20 && arr.length >= 10)
			insertionSort(arr);

		// Check if sort is complete (0 or 1 elements). 
		else if (first >= last)
			return;

		// Recursive call to partition and quicksort.
		else {
			int pivotIndex = partition(arr, first, last);
			quickSort(arr, first, pivotIndex);
			quickSort(arr, pivotIndex + 1, last);
		} 
	}

	/** Partitions an array. Pivot is the median value.
	 *	
	 * @param arr: Potential array entries to be used as a pivot.
	 * @param first: First element of the array.
	 * @param last: Last element of the array.
	 * @return: Index of the pivot.
	 */
	private static int partition(int[] arr, int first, int last) {
	   int mid = first + (last - first) / 2;
	   int pivot = arr[mid];
	   boolean flag = false;
	   
	   // Get pivot into its proper position.
	   while (!flag) {
	      while (arr[first] < pivot)
	         first += 1;
	    
	      while (pivot < arr[last])
	         last -= 1;
	    
	      // Less than 2 elements remain, end partition.
	      if (first >= last)
	         flag = true;

	      else {
	         swap(arr, first, last);
	       
	         // Update changed position of indices.
	         first += 1;
	         last -= 1;
	     }
	   }
	 
	   return last;
	}

	/** Find the key's index.
	 *	
	 * @param arr: Array containing the elements.
	 * @param key: The element to be found.
	 * @return: The entry's index.
	 */
	private static int binarySearch(int[] arr, int key) {
   		int mid = 0;
   		int low = 0;
   		int high = arr.length - 1;
   		
   		// Process elements until low is greater than high.
   		while (high >= low) {
      		mid = (high + low) / 2;
      		
         	// Restrict to right side of array.
      		if (arr[mid] <= key)
         		low = mid + 1;

         	// Restrict to left side of array.
    	  	else if (arr[mid] > key)
         		high = mid - 1; 
   		}
   		
   		// Index of key's sorted position.
   		return low;
	}

	/** Determines the number of notifications received over d days.
	 *
	 * @param dailyExpendituresL The daily expenditure of a client. 
	 * @param d: The number of trailing days (minimum data of spending habits).
	 * @return: Number of times client will get a notification.
	 */
	public int getNumberOfFrauds(int[] dailyExpenditures, int d) {	
		int[] trail = new int[d]; 		// Trailing expenditures.
		float median = 0;				// Median over trailing expenditures.
		int notice = 0;					// Number of notices sent to client.

		// Process array data for fraud detection.
		for (int i = 0; i < dailyExpenditures.length; i++) {
			// Assign the minimum amount of trailing days.
			while (i < d) {
				trail[i] = dailyExpenditures[i];
				
				// Sort trailing expenditures in ascending order.
				if (i == d - 1)
					quickSort(trail, 0, d - 1);
				i++;
			}
 
			// Calculate median among trailing expenditures.
			if (d % 2 != 0)
				median = trail[d / 2];

			// Even amount of trailing days. Average the two middle values.
			else {
				median = (trail[d / 2 - 1] + trail[d / 2]);
				median /= 2;
			}
		
			// Alert twice the median spending has been reached. 
			if (dailyExpenditures[i] >= 2 * median)
				notice++;

			// Get index for oldest entry. 
			int index = binarySearch(trail, dailyExpenditures[i - d]) - 1;
			
			// Shift elements left by 1 index. 
			if (dailyExpenditures[i] > trail[index]) {
				while (d - 1 > index && dailyExpenditures[i] > trail[index]) {
					// Insertion point for current spending.
					if (dailyExpenditures[i] < trail[index + 1])
						break;
					
					trail[index] = trail[index + 1];
					index++;
				}
				
				trail[index] = dailyExpenditures[i];
			}

			// Shift elements right by 1 index.
			else if (dailyExpenditures[i] < trail[index - 1]) {
				while (dailyExpenditures[i] < trail[index]) {
					// Insertion point for current spending.
					if (dailyExpenditures[i] > trail[index - 1]) {
						trail[index] = dailyExpenditures[i];
						break;
					}
					
					trail[index] = trail[index - 1];
					index--;
				}
			}
			
			// Overwrite the oldest day spending with current day spending.
			else
				trail[index] = dailyExpenditures[i];
		}

		return notice; 
	}
}
