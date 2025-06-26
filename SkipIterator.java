// Time Complexity O(n) for get newsfeed
// Space Complexity O(n) wen all elements to be skipped
class SkipIterator implements Iterator<Integer> {
    Integer nextElement = null;
    HashMap<Integer, Integer> skipCount = new HashMap<Integer, Integer>();
    Iterator<Integer> it;

    public SkipIterator(Iterator<Integer> it){
        this.it = it;
        advance();
    }

    private void advance(){ //O(n)
        nextElement = null;
        while(nextElement == null && it.hasNext()){

            Integer curr = it.next();
            if(skipCount.containsKey(curr)){
                skipCount.put(curr, skipCount.get(curr) -1);
                if(skipCount.get(curr) == 0 ){
                    skipCount.remove(curr);
                }
            }else{
                nextElement = curr;
            }

        }
    }

    public void skip(int num) {  //O(n)
        if(nextElement!= null && nextElement == num){
            advance();
        }else{
            skipCount.put(num, skipCount.getOrDefault(num, 0) + 1);
        }
    }
    @Override
    public boolean hasNext() { //O(1)
        if(nextElement != null) {
            return true;
        }
        return false;
    }

    @Override
    public Integer next() { //O(n)
        Integer temp = nextElement;
        advance();
        return temp;
    }

}

public class Main {

    public static void main(String[] args) {

        SkipIterator sit = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6,8).iterator());

        System.out.println(sit.hasNext());// true
        System.out.println(sit.next()); //5   nextEl = 6
        sit.skip(5);  // will be store in map
        System.out.println(sit.next());// 6 nextEl = 7
        System.out.println(sit.next()); // 7 nextEl = 6
        sit.skip(7); // nextEl = 6
        sit.skip(9); // store in map

        System.out.println(sit.next()); // 6 nextEl = 8

        System.out.println(sit.next()); //8
        System.out.println(sit.next());// 5
        sit.skip(8); //nextEl = null
        sit.skip(5);
        System.out.println(sit.hasNext()); //true
        System.out.println(sit.next()); //6
        System.out.println(sit.hasNext()); //false
        // System.out.println(sit.next());// 5
        // it.skip(1);

        //it.skip(3);

        // System.out.println(it.hasNext()); //false

    }

}



