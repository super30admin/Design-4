/*
Intuition: Make a count of the numbers to be skip using a map.

#####################################################################
next Method: Getting the next element
Time Complexity : O(N)
#####################################################################
hasNext Method: Checking if the next element is null or not which is set by the advance() method
Time Complexity : O(1)
#####################################################################
skip Method: Adding a number to the hashmap
Time Complexity : O(1)
#####################################################################
advance Method: Has to iterate through the elements
Time Complexity : O(N)
#####################################################################
Total Space Complexity: O(N), N = total numbers of elements to be skipped
*/

class SkipIterator{
    public:
        int nextEl = NULL;
        map<int, int> count;
        vector<int>::iterator it;
        SkipIterator(vector<int>::iterator it){
            this->it = it;
            advance();
        }
    
    void advance()overide{
        nextEl = NULL;
        while ( nextEl == NULL and it.hasNext() != NULL){
            int num = it.next();
            if ( count.find(num) == count.end()){
                nextEl = num;
            }
            else{
                count[num] -= 1;
                if ( count[num] == 0){
                    count.erase(num);
                }
            }
        }
    }
    void skip(int num){
        
        if ( num == nextEl){
            advance();
        }
        else{
            if ( count.find(num) != count.end()){
                count[num] += 1;
            }
            else{
                count[num] = 1;
            }
        }
    }
    bool hasNext(){
        if ( nextEl == NULL) return false;
        else return true;
    }
    
    int next(){
        int result = nextEl;
        advance();
        return result;
    }
    
};
class mock{
    int num;
    mock(int num){
        this->num = num;
    }
};
int main() {
    std::cout << "Hello World!\n";
    vector<int> nums = {1,2,3,4,5,6};
    auto iterator = new SkipIterator(nums.begin());
    iterator->hasNext();
    cout <<iterator->next()<<endl;
    iterator->skip(2);
    cout <<iterator->hasNext()<<endl;
    cout<< iterator->hasNext()<<endl;
}