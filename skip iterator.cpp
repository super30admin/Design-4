#include <iostream>
#include <vector>
#include <map>
#include <iterator>

class SkipIterator {
private:
    std::vector<int>::iterator it;
    std::vector<int>::iterator end;
    std::map<int, int> count;
    int nextEl;
    bool hasNextVal;

    void advance() {
        hasNextVal = false;
        nextEl = 0;
        while (it != end) {
            int el = *it++;
            if (count.find(el) == count.end()) {
                nextEl = el;
                hasNextVal = true;
                break;
            } else {
                count[el]--;
                if (count[el] == 0) {
                    count.erase(el);
                }
            }
        }
    }

public:
    SkipIterator(std::vector<int>::iterator begin, std::vector<int>::iterator end) : it(begin), end(end) {
        advance();
    }

    bool hasNext() {
        return hasNextVal;
    }

    int next() {
        if (!hasNext()) {
            throw std::runtime_error("empty");
        }
        int el = nextEl;
        advance();
        return el;
    }

    void skip(int num) {
        if (!hasNext()) {
            throw std::runtime_error("empty");
        }
        if (nextEl == num) {
            advance();
        } else {
            count[num]++;
        }
    }
};

int main() {
    std::vector<int> nums = {1, 2, 3};
    SkipIterator it(nums.begin(), nums.end());

    std::cout << std::boolalpha << it.hasNext() << std::endl;

    it.skip(2);
    it.skip(1);
    it.skip(3);

    std::cout << std::boolalpha << it.hasNext() << std::endl;

    return 0;
}