class SkipIterator implements Iterator<Integer> {

  HashMap<Integer, Integer> skipMap;
  Integer nextEl; // will give the next element of the skip iterator
  Iterator<Integer> nit;

  public SkipIterator(Iterator<Integer> it) {
    this.skipMap = new HashMap<>();
    this.nit = it;
    advance();
  }

  private void advance() { // O(n)
    //takes the skip iterator pointer to next valid location
    nextEl = null;
    while (nextEl == null && nit.hasNext()) {
      Integer el = nit.next();
      if (skipMap.containsKey(el)) {
        skipMap.put(el, skipMap.get(el) - 1);
        skipMap.remove(el, 0);
      } else {
        nextEl = el;
      }
    }
  }

  @Override
  public boolean hasNext() { // O(1)
    return nextEl != null;
  }

  @Override
  public Integer next() { //O(n)
    Integer result = nextEl;
    advance();
    return result;
  }

  /**
   * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
   * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
   */
  public void skip(int val) { //O(n)
    if (val != nextEl) {
      skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
    } else {
      advance();
    }
  }
}

public class Main {

  public static void main(String[] args) {
    SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());

    System.out.println(it.hasNext());

    it.skip(2);

    it.skip(1);

    it.skip(3);

    System.out.println(it.hasNext());
  }
}
