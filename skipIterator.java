import java.util.HashMap;
import java.util.Map;



class skipIterator {
    private int index = 0; // Pointer to track the current position in the array
    private int[] nums = null; // Array of numbers to iterate over
    private Map<Integer, Integer> map = null; // Stores numbers to be skipped and their counts

    /**
     * Constructor: Initializes the iterator with the given array.
     */
    public skipIterator(int[] nums) {
        this.nums = nums;
        map = new HashMap<>(); // Initialize the skip map
    }

    /**
     * Returns true if there are more elements to iterate over.
     */
    public boolean hasNext() {
        return index < nums.length;
    }

    /**
     * Returns the next element in the iteration, skipping any numbers in the map.
     */
    public Integer next() {
        // Fetch the next valid number
        Integer value = nums[index++];
        checkSkipped(); // Check if the next value should be skipped
        return value;
    }

    /**
     * This method checks if the current element is in the skip map.
     * If it is, it decrements the count or removes it from the map and moves to the next element.
     */
    private void checkSkipped() {
        while (index < nums.length && map.containsKey(nums[index])) {
            if (map.get(nums[index]) == 1) {
                map.remove(nums[index]); // Remove the number if it's the last occurrence to be skipped
            } else {
                map.put(nums[index], map.get(nums[index]) - 1); // Decrement the count of occurrences to be skipped
            }
            ++index; // Move to the next number
        }
    }

    /**
     * Skips the next occurrence of `num` in the iteration.
     * If `skip(5)` is called twice, it means two `5`s should be skipped.
     */
    public void skip(int num) {
        map.put(num, map.getOrDefault(num, 0) + 1); // Add `num` to skip map or increase its count
        checkSkipped(); // Check if we need to move past the skipped elements
    }

    public static void main(String[] args) {
        skipIterator it = new skipIterator(new int[]{2, 3, 5, 6, 5, 7, 5, 8, 9});

        System.out.println(it.next()); // 2
        it.skip(5);  // Skip the next occurrence of 5
        System.out.println(it.next()); // 3
        System.out.println(it.next()); // 6 (5 was skipped)
        System.out.println(it.next()); // 5 (this one was not skipped)
        it.skip(5);  // Skip the next occurrence of 5
        System.out.println(it.next()); // 7
        System.out.println(it.next()); // 8 (5 was skipped)
        System.out.println(it.next()); // 9
    }
}