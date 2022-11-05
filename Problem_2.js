//## Problem 2: Skip Iterator(https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator)

// next() & skip()
// Time Complexity : O(n)
// Space Complexity : O(n)

// hasNext()
// Time Complexity : O(1)
// Space Complexity : O(1)

// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach

// Custom native iterator with methods next and hasnext
class Iterator {
    constructor(arr) {
        this.it = (function* makeIerator() {
            yield* [...arr];
        })();
        this.nextEle = this.it.next();
    }
    next() {
        let data = this.nextEle;
        this.nextEle = this.it.next();
        return data;
    }
    hasNext() {
        return !(this.nextEle.done);
    }
}

// SkipIterator with a skipMAp which maintains the elements to skip and their frequency
class SkipIterator {
    constructor(itr) {
        this.itr = itr;
        this.skipMap = new Map();
        this.nextEle = null;
        this.advance();
    };

    next() {
        // Save the element whose value is to be returned
        let data = this.nextEle;
        // Advance to next valid element
        this.advance();
        return data?.value === undefined ? null : data?.value;
    }

    hasNext() {
        // If iterator has reached last value, return false
        return this.nextEle?.done === undefined ? false : !this.nextEle?.done;
    }
    skip(val) {
        // If value to be skipped is current value, advance 
        if (val === this.nextEle.value) {
            this.advance();
        } else {
            // Add to map
            if (this.skipMap.has(val)) {
                this.skipMap.set(val, this.skipMap.get(val) + 1)
            } else {
                this.skipMap.set(val, 1)
            }
        }
    }
    advance() {
        this.nextEle = null;
        // Iterate while not reached a valid element and native iterator has elements left
        while (this.nextEle === null && this.itr.hasNext()) {
            // Set value to next iterator
            this.nextEle = this.itr.next();
            let mapVal = this.nextEle.value;
            // If needs to be skipped, skip and update map
            if (this.skipMap.has(mapVal)) {
                let cnt = this.skipMap.get(mapVal);
                cnt--;
                if (cnt === 0) {
                    this.skipMap.delete(mapVal);
                } else {
                    this.skipMap.set(mapVal, cnt);
                }
                this.nextEle = null;
            }
        }
    }
}


let arr = [2, 4, 3, 3, 4, 5, 6, 7, 8];

let itr = new SkipIterator(new Iterator(arr));

console.log(itr.next());
console.log(itr.hasNext());
itr.skip(4);
itr.skip(3);
itr.skip(5);
console.log(itr.next());
console.log(itr.next());
console.log(itr.hasNext());
itr.skip(7);
console.log(itr.next());
console.log(itr.hasNext());
console.log(itr.next());
console.log(itr.hasNext());