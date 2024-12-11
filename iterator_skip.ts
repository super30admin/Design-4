class SkipIterator<T> {
  items: T[];
  skipMap: Map<T, number>;
  index: number = 0;
  nextValue: T | null;
  constructor(items: T[]) {
    this.items = items;
    this.skipMap = new Map();
    this.nextValue = items[0];
  }

  // To validate dynamic ability
  insert(value: T, index: number) {
    this.items.splice(index, 0, value);
    this.setNextIfRequired();
  }

  skip(value: T) {
    if (!this.skipMap.has(value)) {
      this.skipMap.set(value, 0);
    }
    this.skipMap.set(value, this.skipMap.get(value)! + 1);
    this.setNextIfRequired();
  }

  hasNext(): boolean {
    return this.nextValue != null;
  }

  next(): T | null {
    this.setNextIfRequired();
    let result = this.nextValue;
    if (result == null) return null;
    this.index++;
    if (this.index < this.items.length) {
      this.nextValue = this.items[this.index];
      this.setNextIfRequired();
    } else {
      this.nextValue = null;
    }
    return result;
  }

  setNextIfRequired() {
    if (this.nextValue != null) {
      if (
        this.skipMap.has(this.nextValue) &&
        this.skipMap.get(this.nextValue)! > 0
      ) {
        this.skipMap.set(this.nextValue, this.skipMap.get(this.nextValue)! - 1);
        this.index++;
        if (this.index < this.items.length) {
          this.nextValue = this.items[this.index];
        } else {
          this.nextValue = null;
        }
        this.setNextIfRequired();
      }
    } else if (this.index < this.items.length) {
      this.nextValue = this.items[this.index];
      this.setNextIfRequired();
    }
  }
}

describe("Skip Iterator", () => {
  it("Happy Path", () => {
    let itr = new SkipIterator<number>([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]);
    expect(itr.hasNext()).toStrictEqual(true); // true
    expect(itr.next()).toStrictEqual(2); // returns 2
    itr.skip(5);
    expect(itr.next()).toStrictEqual(3); // returns 3
    expect(itr.next()).toStrictEqual(6); // returns 6 because 5 should be skipped
    expect(itr.next()).toStrictEqual(5); // returns 5
    itr.skip(5);
    itr.skip(5);
    expect(itr.next()).toStrictEqual(7); // returns 7
    expect(itr.next()).toStrictEqual(-1); // returns -1
    expect(itr.next()).toStrictEqual(10); // returns 10
    expect(itr.hasNext()).toStrictEqual(false); // false
    expect(itr.next()).toStrictEqual(null); // error
    itr.insert(11, 10);
    expect(itr.hasNext()).toStrictEqual(true); // true
    expect(itr.next()).toStrictEqual(11); // 11
    itr.insert(12, 11);
    expect(itr.hasNext()).toStrictEqual(true); // true
    itr.skip(12);
    expect(itr.hasNext()).toStrictEqual(false); // true
  });

  it("Negative", () => {
    let iterator = new SkipIterator<number>([1]);
    expect(iterator.hasNext()).toStrictEqual(true);
    iterator.skip(1);
    expect(iterator.next()).toStrictEqual(null);
  });

  it("Complex", () => {
    let iterator = new SkipIterator<number>([5, 5, 5, 6]);
    expect(iterator.hasNext()).toStrictEqual(true);
    iterator.skip(5);
    iterator.skip(5);
    expect(iterator.hasNext()).toStrictEqual(true);
    expect(iterator.next()).toStrictEqual(5);
  });
});
