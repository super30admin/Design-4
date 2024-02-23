// Time Complexity :
//        SkipIterator(Iterator<Integer> it): O(n)
//        advance(): O(n)
//        hasNext(): O(1)
//        next(): O(1)
//        skip(int num): O(1)
// Space Complexity :
//        SkipIterator: O(n)
//        advance(): O(1)
//        hasNext() and next(): O(1)
//        skip(int num): O(1) + O(k), where k is the number of distinct elements to skip (worst-case scenario)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

#include <iostream>
#include <vector>
#include <unordered_map>
#include <iterator>

using namespace std;

class SkipIterator {
private:
    vector<int>::const_iterator current, end;
    unordered_map<int, int> skipMap;
    int nextEl;
    bool hasNextEl;

    void advance() {
        hasNextEl = false;
        while (current != end) {
            const int& el = *current;
            if (skipMap.find(el) != skipMap.end()) {
                if (--skipMap[el] == 0) {
                    skipMap.erase(el);
                }
                ++current;
            } else {
                nextEl = el;
                hasNextEl = true;
                ++current;
                break;
            }
        }
    }

public:
    SkipIterator(const vector<int>& nums)
        : current(nums.begin()), end(nums.end()), hasNextEl(false) {
        advance();
    }

    bool hasNext() const {
        return hasNextEl;
    }

    int next() {
        int result = nextEl;
        advance();
        return result;
    }

    void skip(int num) {
        if (num == nextEl) {
            advance();
        } else {
            skipMap[num]++;
        }
    }
};

int main() {
    vector<int> nums = {2, 3, 5, 6, 5, 7, 5, -1, 5, 10};
    SkipIterator itr(nums);

    cout << boolalpha << itr.hasNext() << endl;
    cout << itr.next() << endl;
    itr.skip(5);
    cout << itr.next() << endl;
    cout << itr.next() << endl;
    cout << itr.next() << endl;
    itr.skip(5);
    itr.skip(5);
    cout << itr.next() << endl;
    cout << itr.next() << endl;
    cout << itr.next() << endl;
    cout << boolalpha << itr.hasNext() << endl;
    cout << itr.next() << endl;

    return 0;
}
