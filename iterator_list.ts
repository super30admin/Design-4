class ListIterator<T> {
  items: T[];
  index: number = 0;
  constructor() {
    this.items = [];
  }

  set(values: T[]) {
    this.items = values;
  }
  insert(value: T, index: number) {
    this.items.splice(index, 0, value);
  }

  hasNext(): boolean {
    return this.index < this.items.length;
  }
  next(): T | null {
    let item = this.items[this.index];
    this.index++;
    return item;
  }
}

describe("List Iterators", () => {
  it("Happy Path", () => {
    let iterator = new ListIterator<number>();
    iterator.set([1, 2, 3]);
    expect(iterator.hasNext()).toStrictEqual(true);
    expect(iterator.next()).toStrictEqual(1);
    expect(iterator.hasNext()).toStrictEqual(true);
    expect(iterator.next()).toStrictEqual(2);
    expect(iterator.hasNext()).toStrictEqual(true);
    expect(iterator.next()).toStrictEqual(3);
    expect(iterator.hasNext()).toStrictEqual(false);
    iterator.insert(4, 3);
    expect(iterator.hasNext()).toStrictEqual(true);
    expect(iterator.next()).toStrictEqual(4);
    expect(iterator.hasNext()).toStrictEqual(false);
  });
});
