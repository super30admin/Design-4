
// https://leetcode.com/discuss/interview-question/341818
//vector<int>::iterator i;
class SkipIterator {
public:
    map<int, int> m;
    vector<int>::iterator iter;
    vector<int>::iterator iter_end;
    int next_el;

    SkipIterator(vector<int>::iterator it, vector<int>::iterator it_end) {
        iter =it;
        iter_end = it_end;
        advance();
    }

     bool hasNext() {
        if(next_el) return true;
        
        return false;
    }

    int next() {
       int temp = next_el;
        advance();
        return temp;
    }

    void skip(int num) {
        if(next_el == num)
            advance();
        else{
            if(m.find(num)==m.end()){
                m[num]=1;
            }
            else
                m[num]++;
        }
       
    }

    void advance() {
        next_el = 0; 
        while(next_el==0 && iter.end()!=lists.size()){
            int curr_el = iter.next();
            if(m.find(curr_el)==m.end()){
                next_el = curr_el;
            }
            else{
                m[curr_el]--;
                if(m[curr_el]==0)
                    m.erase(curr_el);
            }
        }
       
    }
}

int main() {
        static void main(string args[]) {
        vector<int> list = {1,2,3};
        SkipIterator *it = new SkipIterator(list::iterator,list.end());
        cout << it->hasNext();
        it->skip(2);
        it->skip(1);
        it->skip(3);
        cout << it->hasNext();
    }
}