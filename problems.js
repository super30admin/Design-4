// ## Problem 2: Skip Iterator(https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator)

// O(n) time, O(k) space

class SkipIterator {
    nextEl;
    constructor(it) {
        this.it = it;
        this.map = new HashMap();
        advance();
    }
    hasNext() {
        return this.nextEl !== null;
	}
	next() {
        let temp = this.nextEl;
        this.advance();
        return temp;
	}
	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	skip(val) {
        if(this.nextEl === val) this.advance();
        else this.map.set(val, (map.get(val) || 0) + 1); 
    }
    advance() {
        this.nextEl = null;
        while(this.nextEl === null && this.it.hasNext()) {
            let el = it.next();
            if(!this.map.has(el)) {
                this.nextEl = el;
            }
            else {
                this.map.set(el, this.map.get(el) - 1);
                if(this.map.get(el) === 0) this.map.delete(el);
            }
        }
    }
}

// SkipIterator itr = new SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]);
// itr.hasNext(); // true
// itr.next(); // returns 2
// itr.skip(5);
// itr.next(); // returns 3
// itr.next(); // returns 6 because 5 should be skipped
// itr.next(); // returns 5
// itr.skip(5);
// itr.skip(5);
// itr.next(); // returns 7
// itr.next(); // returns -1
// itr.next(); // returns 10
// itr.hasNext(); // false
// itr.next(); // error