class SkipIterator implements Iterator {

Set<Integer> set = new HashSet<>();
Iterator<Integer> skipit;
int skippedVal = Integer.MAX_VALUE;
public SkipIterator(Iterator<Integer> it) {
    this.skipit = it;
}
public boolean hasNext() {

    while(skipit.hasNext()) {
        skippedVal = skipit.next();
        if(!set.contains(skippedVal)) return true;
    }
    return false;
}

public Integer next() {
     return skippedVal;
}

public void skip(int val) {
    set.add(val);
}
}
