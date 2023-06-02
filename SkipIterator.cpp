#include <bits/stdc++.h>
#include <iostream>
using namespace std;
class SkipIterator : public iterator<input_iterator_tag, int>{
    unordered_map<int, int> skipMap;
    int nextElem;
    bool hasNextElem;
    vector<int>:: iterator itr;
    vector<int> nums;
    
	public: SkipIterator(vector<int>n){
        hasNextElem = false;
        nums = n;
        itr = nums.begin();
        advance();
	}

    private: void advance(){
        hasNextElem = false;
        while(!hasNextElem && itr != nums.end()){
            if(skipMap.count(*itr)){
                skipMap[*itr]--;
                if(skipMap[*itr] == 0)
                    skipMap.erase(*itr);
            }
            else{
                nextElem = *itr;
                hasNextElem = true;
            }
            itr++;
        }
    }
    
	public: bool hasNext() {
        return hasNextElem;
	}

	public: int next() {
        int elementToReturn = nextElem;
        advance();
        return elementToReturn;
	}

	public: void skip(int val) {
        if(nextElem != val)
            skipMap[val]++;
        else
            advance();
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