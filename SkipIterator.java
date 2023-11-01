class SkipIterator implements Iterator {

    private HashMap<Integer, Integer> hmap;
    private Integer nextEle;
    Iterator<Integer> nit;

    public SkipIterator(Iterator<Integer> it) {
        this.map = new HashMap<>();
        this.nit = it;
        advance();
    }

    private void advance(){
        nextEle = null;
        while(nit.hasNext() && nextEle != null){
            Integer el = nit.next();
            if(hmap.containsKey(el)){
                hmap.put(el, hmap.get(el)-1);
                hmap.remove(el,0);
            }else{
                nextEle = el;
            }
        }
    }

    public boolean hasNext() {
        return nextEle != null;
    }

    public Integer next() {
        Integer res = nextEle;
        advance()
        return res;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if(num == nextEle){
            advance();
        }else{
            hmap.put(num, hmap.getOrDefault(num,0)+1);
        }
    }

}