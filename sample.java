class SkipIterator implements Iterator<Integer> {
    private Iterator<Integer> it;
    Integer nextEl;
    HashMap<Integer, Integer> map;
    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        map = new HashMap<>();
        advance();

    }

    public boolean hasNext() {
        return nextEl != null;
    }

    public Integer next() {
        Integer temp = nextEl;
        advance();
        return temp;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if(val == nextEl){
            map.put(num, map.getOrDefault(num, 0) + 1);
        } else {
            advance();
        }
    }
    public void advance(){
        nextEl = null;
        while(nextEl == null && it.hasNext()){
            Integer curr = it.next();
            if(map.containskey(curr)){
                map.put(curr, map.getOrDefault(curr, 0) -1);
                map.remove(curr, 0);
            }
            else{
                nextEl = curr;
            }
        }
    }
}