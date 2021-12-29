public class Driver {
	public static void main(String[] args) {
		FraudDetection obj = new FraudDetection();
		int notifications = 0;
		
		// Cases:
		int arrHR[] = {2, 3, 4, 2, 3, 6, 8, 4, 5};
		int arr3[] = {01, 128, 319, 402};
		int arr2[] = {2, 3, 4, 1, 4, 3, 2};
		int arr[] = {10, 2, 78, 4, 45, 32, 7, 11};
		int arrHR2[] = {10, 20, 30, 40, 50};
		int arrHelp[] = {3,3,5,1,6,7,3,9};
		int arrTest[] = {1, 4, 1, 3, 19, 49, 32};
		
		
		notifications = obj.getNumberOfFrauds(arrTest, 5);
		
		System.out.println("Number of times client will be notified: " + notifications);
	}
}
