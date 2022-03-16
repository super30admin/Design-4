import java.util.*;

class SkipIterator implements Iterator<Integer> {

  int[] nums;
  Queue<Integer> skipQueue;
  int index;
  int size;

	public SkipIterator(int[] nums) {
    this.nums= nums;
    skipQueue = new LinkedList<>();
    index = 0;
    size = nums.length;
	}

	public boolean hasNext() {
    return index != size;
	}

	public Integer next() {
    if(hasNext()){
        while(!skipQueue.isEmpty() && skipQueue.peek() == nums[index]){
          skipQueue.poll();
          index++;
        }
        return nums[index++];
      }
      else{
        throw new NoSuchElementException("No elements in Iterator to return");
      }
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/
	public void skip(int val) {
    skipQueue.add(val);
	}

  public static void main(String[] args){
    SkipIterator itr = new SkipIterator(new int[] {2, 3, 5, 6, 5, 7, 5, -1, 5, 10});
    itr.hasNext(); // true
    System.out.println(itr.next()); // returns 2
    itr.skip(5);
    System.out.println(itr.next()); // returns 3
    System.out.println(itr.next()); // returns 6 because 5 should be skipped
    System.out.println(itr.next()); // returns 5
    itr.skip(5);
    itr.skip(5);
    System.out.println(itr.next()); // returns 7
    System.out.println(itr.next()); // returns -1
    System.out.println(itr.next()); // returns 10
    itr.hasNext(); // false
    itr.next(); // error

  }
}
