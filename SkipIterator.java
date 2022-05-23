// Time Complexity : O(1) for everything else except getNewsFeed() for O(n)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : no
// Any problem you faced while coding this : no

class SkipIterator implements Iterator {

    Integer nexElement;
    Map<Integer, Integer> map;
    Iterator<Integer> it;

    SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.map = new HashMap<>();

        advance();
    }

    @Override
    boolean hasNext() {
        return nexElement != null;
    }

    @Override 
    int next() {
        int element = nexElement;
        advance();
        return element;
    }

    void skip(int val) {

        if(nexElement == val) {
            advance();
        }
        else {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }
    }

    private void advance() {

        nexElement = null;

        while(nexElement == null && it.hasNext()) {

            int element = it.next();

            if(!map.containsKey(element)) {
                nextElement = element;
            } else {
                map.put(element, map.get(element) - 1);
                map.remove(element, 0);
            }
        }
    }

}