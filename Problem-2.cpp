// https://leetcode.com/discuss/post/341818/google-skip-iterator-by-sithis-mlws/
// Time Complexity : 
// hasNext() : O(1)
// next() : O(1)
// skip() : O(1)
// Space Complexity : O(n) where n is the number of elements
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach


#include <iostream>
#include <vector>
#include <unordered_map>

using namespace std;

class SkipIterator {
private:
    vector<int>::iterator curr, end;
    unordered_map<int, int> skipMap;

    void advance() {
        while (curr != end && skipMap[*curr] > 0) {
            skipMap[*curr]--;  
            curr++;
        }
    }

public:
    SkipIterator(vector<int>& nums) {
        curr = nums.begin();
        end = nums.end();
        advance(); // Ensure first valid element is at curr
    }

    bool hasNext() {
        return curr != end;
    }

    int next() {
        if (!hasNext()) throw runtime_error("No more elements");
        int val = *curr;
        curr++;
        advance(); 
        return val;
    }

    void skip(int val) {
        if (curr != end && *curr == val) {
            curr++;  
            advance();
        } else {
            skipMap[val]++;  
        }
    }
};

// **Example Usage**
int main() {
    vector<int> nums = {2, 3, 5, 6, 5, 7, 5, 8, 9};
    SkipIterator it(nums);

    cout << it.next() << endl;  // 2
    it.skip(5);                 // Skip next '5'
    cout << it.next() << endl;  // 3
    cout << it.next() << endl;  // 6
    cout << it.next() << endl;  // 7 (skipped 5)
    it.skip(8);
    cout << it.next() << endl;  // 5
    cout << it.next() << endl;  // 9 (skipped 8)
    
    return 0;
}
