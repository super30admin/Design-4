class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> map;
    Iterator<Integer> it;
    Integer nextEl;
    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.map = new HashMap<>();
    }

    public boolean hasNext() {
        return nextEl!=null;
    }

    public Integer next() {
        if (!hasNext()) throw new RuntimeException("empty");
        Integer temp = nextEl;
        advance();
        return temp;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if (!hasNext()) throw new RuntimeException("empty");
        if( val == nextEl)
            advance();
        else
            map.put(val,map.getOrDefault(map, 0)+1);
    }

    public void advance(){
        nextEl = null;
        while(nextEl==null && it.hasNext()){
            Integer el = it.next();
            if(map.containsKey(el)){
                map.put(el, map.get(el)-1);
                map.remove(el, 0);
            }
            else
                nextEl = el;
        }
    }
}