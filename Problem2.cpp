// Problem: Skip Iterator
//  Time Complexity : next(), hasNext(), skip - Amortized O(1)
//  Space Complexity : O(k) no of elements to be skipped
//  Any problem you faced while coding this : No

// Your code here along with comments explaining your approach in three
// sentences only
// 1. We use skipmap to record the value to be skipped and the frequency
// 2. While skipping, we take care if we are at the value that needs to skipped
// we advance in that case, otherwise we increase the skip frequency in the map
// 3. In advance, we first reset the nextel and then check if the current val is
// in skipmap, if yes we decrease the count and remove if zero, otherwise we
// change nextel = el and return

#include <bits/stdc++.h>
using namespace std;

class SkipIterator {

public:
  unordered_map<int, int> skipMap;
  optional<int> nextEl;
  vector<int>::iterator nit, end;
  SkipIterator(vector<int>::iterator begin, vector<int>::iterator end) {
    this->nit = begin;
    this->end = end;
    advance();
  }

  void advance() {
    nextEl.reset();
    while (nit != end) {
      int el = *nit;
      ++nit;
      if (!skipMap[el]) {
        nextEl = el;
        return;
      } else {
        skipMap[el]--;
        if (skipMap[el] == 0) {
          skipMap.erase(el);
        }
      }
    }
  }

  bool hasNext() { return nextEl.has_value(); }

  int next() {
    int val = nextEl.value();
    advance();
    return val;
  }

  void skip(int val) {
    if (val == nextEl.value()) {
      advance();
    } else {
      skipMap[val]++;
    }
  }
};

// Test driver
int main() {
  vector<int> data = {5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8};
  SkipIterator itr(data.begin(), data.end());
  cout << boolalpha << itr.hasNext() << endl; // true
  itr.skip(5);
  cout << itr.next() << endl; // 6
  itr.skip(5);
  cout << itr.next() << endl; // 7
  cout << itr.next() << endl; // 6
  itr.skip(8);
  itr.skip(9);
  cout << itr.next() << endl; // 5
  cout << itr.next() << endl; // 5
  cout << itr.next() << endl; // 6
  itr.skip(8);
  cout << boolalpha << itr.hasNext() << endl; // true
  // cout << itr.next() << endl;                 // 8
  // cout << boolalpha << itr.hasNext() << endl; // false
}
