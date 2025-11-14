
class SkipIterator implements Iterator<Integer> {
    private Integer nextEL;
    private Integer<Integer> papaIt;
    private HashMap<Integer, Integer> skipMap;

    public SkipIterator(Iterator<Integer> it) {
        this.papaIt = it;
        this.skipMap = new hashmap<>();
        advance();
    }

    private void advance() {
        // set the next valid nextEL
        nextEL = null;
        while (nextEL == null && papaIt.hasNext()) {
            Integer el = papaIt.next();
            if (!skipMap.containsKey(el)) {
                nextEL = el;
            } else {
                skipMap.put(el, skipMap.get(el) - 1);
                skipMap.remove(el, 0);
            }
        }
    }

    public void skip(int num){
        if(num == nextEL){
            advance();
        }else{
            skipMap.put(num,skipMap.getOrDefault((num,0)+1));
        }
    }

    @Override
    public boolean hashNext() {
        return nextEL != null;
    }

    @Override
    public Integer next() {
        Integer temp = nextEL;
        advance();
        return temp;
    }
}