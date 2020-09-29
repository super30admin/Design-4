// Time Complexity : O(n) for creating the iterator and also getting next value if there are many skipped values in worst case.
// Space Complexity : O(k) where k is the number of skipped values as we are creating an hashmap for that.
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : no issues as of now.. Learning


#include<iostream>
#include<map>
#include<vector>
using namespace std;

map<int,int> skipped;
vector<int> values;

int size = 0;
int curr = 0;
bool hasnext(){
if(curr >= size){
	return false;
}
return true;
}

int next(){


	if(skipped.find(values[curr])!= skipped.end()){
		
			while (skipped.find(values[curr])!= skipped.end()){



				skipped[values[curr]] -= 1;

				if(skipped[values[curr]] == 0){
					skipped.erase(values[curr]);
				}

				curr +=1;

			}
	}

	curr +=1;

	return values[curr-1];

}

void skip(int a){

	if(skipped.find(a)!= skipped.end()){
		skipped.insert({a,1});
	}else{
		skipped[a] +=1;
	}

}

void skipiterator(vector<int> it){
size = it.size();

	for (int i =0; i <size; i++){
		values.push_back(it[i]);
	}
}



int main(){

	skipiterator({1,2,3,4,1,3,4,5,7,2});

	cout<<hasnext()<<endl;

	cout<<next()<<endl;

	skip(2);

	cout<<hasnext()<<endl;

	cout<<next()<<endl;


	skip(4);

	cout<<hasnext()<<endl;

	cout<<next()<<endl;

	cout<<hasnext()<<endl;

	cout<<next()<<endl;

	return 0;
}