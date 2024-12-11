class CompressedStringIterator {
  str: string;
  stringIndex: number;
  charValue: string | null;
  charCount: number;
  nextValue: string | null;

  constructor(str: string) {
    this.str = str;
    this.stringIndex = 0;
    this.charCount = 0;
    this.charValue = null;
    this.nextValue = null;
    this.updateNextValue();
  }

  next(): string | null {
    let result = this.nextValue;
    this.updateNextValue();
    return result;
  }

  hasNext(): boolean {
    return this.nextValue != null;
  }

  private updateNextValue() {
    this.charCount--;
    if (this.charCount <= 0) {
      this.charValue = this.str.charAt(this.stringIndex);
      this.stringIndex++;
      this.charCount = this.buildNumber();
    }
    this.nextValue = this.charValue;
  }

  private buildNumber(): number {
    let result = 0;
    let index: number = this.stringIndex;
    let zeroNumberCode = "0".charCodeAt(0);
    let nineNumberCode = "9".charCodeAt(0);
    while (
      index < this.str.length &&
      this.str.charCodeAt(index) >= zeroNumberCode &&
      this.str.charCodeAt(index) <= nineNumberCode
    ) {
      let charCode = this.str.charCodeAt(index);
      let value = charCode - zeroNumberCode;
      result = result * 10 + value;
      index++;
    }
    this.stringIndex = index;
    return result;
  }
}

describe("Compress String Iterator", () => {
  it("Happy Path", () => {
    let itr = new CompressedStringIterator("L1e2t1C1o1d1e1");
    expect(itr.hasNext()).toStrictEqual(true);
    expect(itr.next()).toStrictEqual("L");
    expect(itr.hasNext()).toStrictEqual(true);
    expect(itr.next()).toStrictEqual("e");
    expect(itr.hasNext()).toStrictEqual(true);
    expect(itr.next()).toStrictEqual("e");
    expect(itr.hasNext()).toStrictEqual(true);
    expect(itr.next()).toStrictEqual("t");
  });

  it("Complex - Number with double digits", () => {
    let itr = new CompressedStringIterator("L10e20t1");
    for (let index = 0; index < 10; index++) {
      expect(itr.hasNext()).toStrictEqual(true);
      expect(itr.next()).toStrictEqual("L");
    }

    for (let index = 0; index < 20; index++) {
      expect(itr.hasNext()).toStrictEqual(true);
      expect(itr.next()).toStrictEqual("e");
    }
    expect(itr.next()).toStrictEqual("t");
  });
});
