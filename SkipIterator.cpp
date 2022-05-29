// Time Complexity : O(1)
// Space Complexity : O(n)
//    where n : number of unique skipped elements.
// Did this code successfully run on Leetcode : yes

// Three line explanation of solution in plain english
/* Maintain native vector iterator. This native iterator always points to a next valid element.
 * Advance the pointer when current element pointed by iterator needs to be skipped.
 * Perform this operation repeatedly till we get a valid element or reach end of i/p vector.
 * Call this advance operation whenever skip method or next method is called which requires iteartor to be updated.
 */

#include <bits/stdc++.h>

using namespace std;

class SkipIterator{
    int nextEle;
    vector<int> nums;
    vector<int>::iterator itr;
    map<int, int> skipEle;

public:
    SkipIterator(vector<int> nums)
    {
        nextEle = -1;
        this->nums = nums;
        itr = this->nums.begin();
        advance();
    }

    bool hasNext()
    {
        return nextEle != -1;
    }

    int next()
    {
        int temp = nextEle;
        advance();
        return temp;
    }

    void skip(int val)
    {
        if (val == nextEle)
        {
            advance();
        }
        else
        {
            if (skipEle.find(val) == skipEle.end())
            {
                skipEle.insert({val, 0});
            }
            skipEle.find(val)->second++;
        }
    }

    void advance()
    {
        nextEle = -1;
        while (nextEle == -1 && itr != nums.end())
        {
            if (skipEle.find(*itr) != skipEle.end())
            {
                auto i = skipEle.find(*itr);
                i->second--;

                if (i->second == 0)
                {
                    skipEle.erase(*itr);
                }
            }
            else
            {
                nextEle = *itr;
            }
            itr++;
        }

    }
};
int main() {
    vector<int> input = {2, 2, 3, 5, 6, 7, 8};
    SkipIterator skipItr(input);

    skipItr.skip(2);
    cout << std::boolalpha << skipItr.hasNext() << endl;
    cout << skipItr.next() << endl;

    skipItr.skip(3);
    cout << std::boolalpha << skipItr.hasNext() << endl;
    cout << skipItr.next() << endl;

    skipItr.skip(5);
    skipItr.skip(5);
    cout << std::boolalpha << skipItr.hasNext() << endl;
    cout << skipItr.next() << endl;
    cout << std::boolalpha << skipItr.hasNext() << endl;

    skipItr.skip(8);
    cout << std::boolalpha << skipItr.hasNext() << endl;
    cout << skipItr.next() << endl;
}