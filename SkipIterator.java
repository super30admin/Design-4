class SkipIterator implements Iterator<Integer> {
    private HashMap<Integer, Integer> map;
    private Iterator<Integer> it;
    private Integer nextE;


    public SkipIterator(Iterator<Integer> it) {
        this.map = new HashMap<>();
        this.nextE = null;
        this.it = it;
        advance();
    }

    public boolean hasNext() {
        return nextE != null;
    }

    public Integer next() {
        Integer temp = nextE;
        advance();
        return temp;
    }

    private void advance() {
        nextE = null;
        while(nextE == null && it.hasNext()) {
            Integer e = it.next();
            if(!map.containsKey(e)) {
                nextE = e;
            } else {
                map.put(e, map.get(e) - 1);
                map.remove(e, 0);
            }
        }
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if(num == nextE) {
            advance();
        } else {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }
    }
}