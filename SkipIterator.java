import java.util.*;
//https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator
class SkipIterator implements Iterator<Integer> {
	
    private final Iterator<Integer> it;
    private final Map<Integer, Integer> count;
    private Integer nextEl;

  public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.count = new HashMap<>();
        advance();
    }
//time complexity : 1
    // space complexity : 1
    // did it run on leetcode : yes 
    // any doubts :  no
  @Override
    public boolean hasNext() {
        return nextEl != null;
    }
//time complexity : 1
    // space complexity : 1
    // did it run on leetcode : yes 
    // any doubts :  no
  @Override
    public Integer next() {
        if (!hasNext()) throw new RuntimeException("empty");
        Integer el = nextEl;
        advance();
        return el;

    }
    //time complexity : N
    // space complexity : N
    // did it run on leetcode : yes 
    // any doubts :  no
  public void skip(int num) {

        if (!hasNext()) throw new RuntimeException("empty");
        if (nextEl == num) {
            advance();
        } else {
            count.put(num, count.getOrDefault(num, 0) + 1);

        }

    }
    private void advance() {
        nextEl = null;
        while (nextEl == null && it.hasNext()) {
            Integer el = it.next();
            if (!count.containsKey(el)) {
                nextEl = el;
            } else {
                count.put(el, count.get(el) - 1);
                count.remove(el, 0);

            }

        }

    }
}