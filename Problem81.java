// https://leetcode.com/discuss/interview-question/341818
// Space Complexity - O(n)
class SkipIterator implements Iterator<Integer> {

    Iterator<Integer> iter;
    Map<Integer, Integer> count;
    Integer nextElement;

    public SkipIterator(Iterator<Integer> it) {     // O(1)
        count = new HashMap<>();
        iter = it;
        advance();
    }

    @Override
    public boolean hasNext() {              // O(1)
        return nextElement != null;
    }

    @Override
    public Integer next() {             // O(n)
        Integer result = nextElement;
        advance();
        return result;
    }

    public void skip(int num) {             // O(n)
        if(nextElement == num){
            advance();
        }else{
            count.put(num,count.getOrDefault(num,0)+1);
        }
    }

    private void advance(){             // O(n)
        nextElement = null;
        while(nextElement == null && iter.hasNext()){
            Integer el = iter.next();
            if(count.containsKey(el)){
                count.put(el, count.get(el) - 1);
                count.remove(el, 0);
            }else{
                nextElement = el;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
        System.out.println(it.hasNext());
        it.skip(2);
        it.skip(1);
        it.skip(3);
        System.out.println(it.hasNext());
    }
}