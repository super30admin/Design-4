#include<iostream>
#include<bits/stdc++.h>
#include<vector>
using namespace std;
/*
This SkipIterator lets you skip values dynamically while iterating.
We use a skipMap to track which numbers to skip and how many times.
The advance() method always sets nextEl to the next valid unskipped number
*/
class SkipIterator{
    unordered_map<int, int> skipMap;
    optional<int> nextEl;
    vector<int>::iterator nit, end;
public:
    SkipIterator(vector<int>::iterator begin, vector<int>::iterator end){
        this->nit = begin;
        this->end = end;
        advance();
    }

    void advance(){
        nextEl.reset();
        while(nit != end){
            int el = *nit;
            ++nit;
            if(skipMap.find(el) == skipMap.end()){
                nextEl = el;
                break;
            }else{
                skipMap[el]--;
                if(skipMap[el] == 0) skipMap.erase(el);
            }
        }
    }

    bool hasNext(){
        return nextEl.has_value();
    }

    int next(){
        int el = nextEl.value();
        advance();
        return el;
    }

    void skip(int val){
        if(nextEl.has_value() && nextEl.value() == val){
            advance();
        }else{
            skipMap[val]++;
        }
    }
};

int main(){
    vector<int> data = {5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8};
    SkipIterator itr(data.begin(), data.end());
    cout << boolalpha << itr.hasNext() << endl;
    itr.skip(5);
    cout << itr.next() << endl;
    itr.skip(5);
    cout << itr.next() << endl;
    cout << itr.next() << endl;
    itr.skip(8);
    itr.skip(9);
    cout << itr.next() << endl;
    cout << itr.next() << endl;
    cout << itr.next() << endl;
    cout << boolalpha << itr.hasNext() << endl;
    cout << itr.next() << endl;
    cout << boolalpha << itr.hasNext() << endl;
}
