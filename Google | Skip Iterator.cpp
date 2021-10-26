// Time Complexity : O(N)
// Space Complexity :O(1)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

#include <iostream>
#include <unordered_map>
#include <vector>

using namespace std;
class skipIterator{
    public:
    skipIterator(vector<int> &vals){
        begins = vals.cbegin();
        ends = vals.cend();
    }
    
    bool hasNext(){
        if(begins != ends)
            return true;
        else
            return false;    
    }
    
    int next(){
        while(hasNext()){
            int nextVal = *(begins++);
            if(count.find(nextVal) == count.end())
                return nextVal;
            else{
                count[nextVal]--;
                if(count[nextVal] == 0)
                    count.erase(nextVal);
            }
        }
        return 0;
    }
    
    void skip(int val){
        count[val]++;
    }
    
    private:
    unordered_map< int , int > count;
    vector <int>:: const_iterator begins;
    vector <int>:: const_iterator ends;
};
int main()
{
    vector<int> values = {5, 6, 7, 7, 7};
    skipIterator* itr = new skipIterator(values);
    cout<<boolalpha<<itr->hasNext()<<endl;
    cout<<itr->next()<<endl;
    itr->skip(7);
    itr->skip(7);
    itr->skip(7);
    cout<<itr->next()<<endl;
    return 0;
}
