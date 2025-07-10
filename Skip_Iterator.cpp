//Time Complexity : Amortized O(1)
//Space Complexity : O(x) x: no.of unique elements in input array
#include<bits/stdc++.h>
using namespace std;
class SkipIterator {
    unordered_map<int, int> skipMap;
    optional<int> nextele;
    vector<int>::iterator nit, end;

public:
    SkipIterator(vector<int>::iterator begin, vector<int>::iterator end) {
        this->nit = begin;
        this->end = end;
        advance();
    }

    void advance() {
        nextele.reset();
        while (nit != end) {
            int el = *nit;
            nit++;
            if (skipMap.find(el) == skipMap.end()) {
                nextele = el;
                break;
            } else {
                skipMap[el]--;
                if (skipMap[el] == 0) {
                    skipMap.erase(el);
                }
            }
        }
    }
    bool hasNext() {
        return nextele.has_value();
    }
    int next() {
        int el = nextele.value();
        advance();
        return el;
    }
    void skip(int val) {
        if (nextele.has_value() && nextele.value() == val) {
            advance();
        } else {
            skipMap[val]++;
        }
    }
};
int main() {
    vector<int> data = {10, 20, 30, 40, 50, 20, 30, 60};
    SkipIterator itr(data.begin(), data.end());
    cout << boolalpha << itr.hasNext() << endl;
    itr.skip(20);
    cout << itr.next() << endl;
    cout << itr.next() << endl;
    itr.skip(40);
    cout << itr.next() << endl;
    itr.skip(20);
    cout << itr.next() << endl;
    cout << itr.next() << endl;
    cout << boolalpha << itr.hasNext() << endl;

    return 0;
}