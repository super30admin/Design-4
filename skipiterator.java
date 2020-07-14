//Time complexity:O(1)
//Space complexity:O(1)

class SkipIterator implements Iterator<Integer> {

    private Iterator<Integer> it;

    private Map<Integer, Integer> count;

    private Integer nextEl;

  public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.map = new HashMap<>();
        advance();

    }

  @Override

    public boolean hasNext() {
        return nextEl != null;
    }

  @Override

    public Integer next() {
        Integer el = nextEl;
        advance();
        return el;
    }

  public void skip(int num) {
        if (nextEl == num) {
            advance();
        } else {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

    }

  private void advance() {
        nextEl = null;
        while (nextEl == null && it.hasNext()) {
            Integer el = it.next();
            if (!map.containsKey(el)) {
                nextEl = el;
            } else {
                map.put(el, map.get(el) - 1);
                map.remove(el, 0)
            }
        }
    }
}