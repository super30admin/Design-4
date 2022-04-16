//TC : O(n) n is size of input

class SkipIterator implements Iterator<Integer> {

    Integer nextElement;
    //map of elements to be skipped against their frequency
    HashMap<Integer, Integer> map;
    Iterator<Integer> nativeI;

	public SkipIterator(Iterator<Integer> it) {

        this.map = new HashMap();
        this.nativeI = it;
        advance();
	}

	public boolean hasNext() {
        return nextElement != null;
	}

	public Integer next() {

        int result = nextElement;
        //find next valid element
        advance();
        return result;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/
	public void skip(int val) {

        //val is in the future - store it in map
        if(val != nextElement)
            map.put(val, map.getOrDefault(val, 0)+1);
        else //find the next valid element
            advance();
	}

    //advance to next valid nextElement
    public void advance()
    {
        this.nextElement = null;
        //keep moving pointer of native iterator until nextElement does not get a value
        while(nativeI.hasNext() && nextElement == null)
        {
            Integer el = nativeI.next();
            if(map.containsKey(el))
            {
                //reduce frequency
                map.put(el, map.get(el)-1);
                //remove if freq = 0
                map.remove(el, 0);
            }
            else
            {
                nextElement = el;
            }
        }
    }
}

public class Main
{
    public static void main(String[] ar)
    {
        SkipIterator it = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6).iterator());

        System.out.println(it.hasNext());
        it.skip(6);
        it.skip(5);
        System.out.println(it.next());
        it.skip(5);
        System.out.println(it.next());
        System.out.println(it.next());
        it.skip(5);
        it.skip(8);
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
    }
}
