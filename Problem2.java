class Problem2 implements Iterator<Integer> {
    HashMap<Integer , Integer> skipMap;
    Integer nextEl;
    Iterator<Integer> nit;
    public SkipIterator(Iterator<Integer> it) {
        this.nit = it;
        this.skipMap = new HashMap<>();
        advance();
    }
    private void advance(){  //O(n)
        this.nextEl = null;
        while(nit.hasNext() && nextEl == null){
            Integer el = nit.next();
            if(skipMap.containsKey(el)){
                skipMap.put(el, skipMap.get(el) - 1);
                skipMap.remove(el, 0);
            } else {
                nextEl = el;
            }
        }
    }
    @Override
    public boolean hasNext() {  //O(1)
        return nextEl != null;
    }

    @Override
    public Integer next() { //O(n)
        Integer result = nextEl;
        advance();
        return result;
    }

    public void skip(int num) { //O(n)
        if(num == nextEl){
            advance();
        } else {
            skipMap.put(num, skipMap.getOrDefault(num, 0) + 1 );
        }
    }