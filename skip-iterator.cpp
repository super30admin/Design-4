// Time Complexity : O(mn)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes

#include <iostream>
#include <unordered_map>
#include <vector>

class SkipIterator {
private:
    std::vector<int>::const_iterator it;
    std::unordered_map<int, int> count;
    int nextEl;

public:
    SkipIterator(std::vector<int>::const_iterator it) : it(it), nextEl(0) {
        advance();
    }

    bool hasNext() const {
        return nextEl != 0;
    }

    int next() {
        if (!hasNext())
            throw std::runtime_error("empty");
        int el = nextEl;
        advance();
        return el;
    }

    void skip(int num) {
        if (!hasNext())
            throw std::runtime_error("empty");
        if (nextEl == num) {
            advance();
        } else {
            count[num]++;
        }
    }

private:
    void advance() {
        nextEl = 0;
        while (nextEl == 0 && it != end) {
            int el = *it++;
            if (count[el] > 0) {
                count[el]--;
            } else {
                nextEl = el;
            }
        }
    }
};

int main() {
    std::vector<int> nums = {1, 2, 3};
    SkipIterator it(nums.begin());

    std::cout << it.hasNext() << std::endl;

    it.skip(2);
    it.skip(1);
    it.skip(3);

    std::cout << it.hasNext() << std::endl;

    return 0;
}
