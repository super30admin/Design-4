/*
Time:

	hasNext(): O(n) n - number of elements int the input array
	next(): O(n)
	skip(): O(1)

Space - O(n) 
*/

class SkipIterator : public Iterator<int> {
	
	vector<int>::iterator start, end;
	unordered_map<int,int> mp;

	SkipIterator(vector<int> it) {
		start = it.begin();
		end = it.end();
	}

	boolean hasNext() {
		while(start!=end && mp.find(*start)!=mp.end()){
			mp[*start]--;
			if(mp[*start]==0) mp.erase(*start);
			start++;
		}
		return start!=end;
	}

	int next() {
		if(hasNext()){
			int ret = *start;
			start++;
			return ret;
		}
	}

	void skip(int val) {
		mp[val]++;
	}
}