public static class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> it;
    Map<Integer, Integer> skipMap;
    Integer nextElement;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        skipMap = new HashMap<>();
        nextElement = null;
        advance();
    }
    
    private void advance() {
        nextElement = null;
        while(nextElement == null && it.hasNext()) {
            int cur = it.next();
            if(skipMap.containsKey(cur)){
                skipMap.put(cur, skipMap.get(cur) - 1);
                if(skipMap.get(cur) == 0) {
                    skipMap.remove(cur);
                }
            } else {
                nextElement = cur;
            }
        }
    }

    @Override
    public boolean hasNext() {
        return nextElement != null;
    }

    @Override
    public Integer next() {
        if (!hasNext()) throw new RuntimeException("empty");
        Integer el = nextElement;
        advance();
        return el;
    }

    public void skip(int num) {
        if (!hasNext()) throw new RuntimeException("empty");
        if(nextElement != null && nextElement == num) {
            advance();
        } else {
            skipMap.put(num, skipMap.getOrDefault(num, 0) + 1);
        }
    }
}