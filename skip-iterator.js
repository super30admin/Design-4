// Time Complexity : O(N)
// Space Complexity : O(N)
// Did this code successfully run on Leetcode : Not a LeetCode question
// Any problem you faced while coding this : Just the seekCursor method was difficult to write. Everything else was fine.

class SkipIterator {
  constructor(list) {
    this.list = list;
    this.skipCount = new Map;
    this.cursor = 0;
  }

  hasNext() {
    return this.list[this.cursor] != null;
  }

  next() {
    let element = this.list[this.cursor];
    this.seekCursor();
    return element;
  }

  skip(num) {
    if (this.list[this.cursor] == num) this.seekCursor();
    else {
      if (this.skipCount.has(num)) {
        let newCount = this.skipCount.get(num) + 1;
        this.skipCount.set(num, newCount);
      } else {
        this.skipCount.set(num, 1);
      }
    }
  }

  seekCursor() {
    let element = this.list[++this.cursor];
    while (this.cursor < this.list.length && this.skipCount.has(element)) {
      let count = this.skipCount.get(element) - 1;
      if (count == 0) this.skipCount.delete(element);
      else this.skipCount.set(element, count);
      element = this.list[++this.cursor];
    }
  }
}
