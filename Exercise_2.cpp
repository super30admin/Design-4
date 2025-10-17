// Time Complexity: O(1) --> next(), skip(), hasNext()
// Space Complexity: O(n) --> where n is length of the elements stored in the iterator 
// Were you able to solve this on Leetcode: No
// Did you face any challenges while solving the problem? No
#include <bits/stdc++.h>
using namespace std;


// SkipIterator wraps an Iterator and supports skipping values.
class SkipIterator {
    
    unordered_map<int, int> skipMap;
    optional<int> current;
    vector<int>::iterator newIt, end;

public:
    SkipIterator(vector<int>::iterator begin, vector<int>::iterator end) {
        this->newIt = begin;
        this->end = end;
        advance();
    }

    // Returns true if there is a next element (not skipped)
    bool hasNext() const { 

        return current.has_value();
        
    }

    // Returns the next element and advances the iterator
    int next() {
        int el = current.value();
        advance();
        return el;
        
    }

    // Skip the next occurrence of val
    void skip(int val) {
        // If current value matches val, skip immediately
        if (current.has_value() && current.value() == val) {
            advance();
        }
        else {
            // increment count of the skipped element in skipMap
            skipMap[val]++;
        }
        
    }
private:
    void advance() {
        // reset the current element if it exists
        current.reset();
        // check as long as the newIt iterator has not reached the end
        while (newIt != end) {
            // derference the value of the iterator
            int val = *newIt;
            // check the count of the current element in the skipmap
             // If val is in skipMap, decrement its skip count
            if (skipMap.count(val)) {
                skipMap[val]--;

                // if the count of the current element reaches zero, delete it
                if (skipMap[val] == 0)
                    skipMap.erase(val);
            } else {
                // assign current element value and increment iterator
                current = val;
                ++newIt;
                return;
            }
            // move iterator
            ++newIt;
        }
    }

};

// Example usage
int main() {
    vector<int> nums = {2, 3, 5, 6, 5, 7, 5, -1, 5, 10};

    SkipIterator skipIt(nums.begin(), nums.end());

    // skip the next two occurrences of 5
    skipIt.skip(5);
    skipIt.skip(5);

    // iterate and print values that are not skipped
    while (skipIt.hasNext()) {
        cout << skipIt.next() << " ";
    }
    cout << "\n";
    return 0;
}