class SkipIterator{
    unordered_map<int,int>m;
    vector<int>v={2, 3, 5, 6, 5, 7, 5, -1, 5, 10};
    vector<int>::iterator it;
public SkipIterator(vector<int>::iterator it) {
    it=v.begin();
}
public bool hasNext() 
{
    if(it==NULL)
        return false;
        while(it!=NULL && m.count(*it))
        {
            m[*it]--;
            if(m[*it]==0)
                m.erase(*it);
            it++;
        }
    if(it==NULL)
        return false;
    else
        return true;
}
public int next() {
    if(hasnext())
    {
        int val=*it;
        it++;
        return val;
    }
    else
        return -9999;
}
}