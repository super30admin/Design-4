/*
Time: follow, unfollow, tweet O(1) and getfeed f * log(f) [where f = friendlist size] 
Space: t*u (t = max tweet count per user, u = max user count) 
*/

class Twitter {
public:
    struct node{
        int t;
        int tid;

        node(int _t, int _tid){
            t = _t;
            tid = _tid;
        }

        bool operator <(const node& n) const{
            return t < n.t;
        }
    };

    const static int maxn = 505;
    set<int> follows[maxn];
    vector<node> tweets[maxn];
    int t = 0;


    Twitter() {
        for(int i=0;i<505;i++)
            follows[i].insert(i);
    }
    
    void postTweet(int uid, int tid) {
        tweets[uid].push_back(node(t,tid));
        t++;
    }
    
    //The same logic as merge k sorted lists
    //First insert all followees' recent most tweet into the priority_queue
    //pick and pop the root of pq (most recent first), then whichever followee it belonged to, push his previous tweet (if there's any) into the pq
    vector<int> getNewsFeed(int uid) {
        //pq node: uid, tweet index, timestamp

        auto cmp =  [](vector<int> &a, vector<int> &b){
                return a[2] < b[2];
            };
        priority_queue<vector<int>, vector<vector<int>>, decltype(cmp)> pq(cmp);

        for(auto &f: follows[uid]){
            
            if(tweets[f].empty())  continue;
            int sz = tweets[f].size();
            node t = tweets[f][sz-1];

            pq.push({f,sz-1,t.t});
        }

        //cout<<uid<<endl;

        vector<int> ret;
        while(!pq.empty() && ret.size()<10){
            auto v = pq.top();
            pq.pop();

            int f = v[0], idx = v[1];
            node t = tweets[f][idx];
            ret.push_back(t.tid);

            //cout<<f<<" "<<t.tid<<endl;

            if(idx>0)  pq.push({f,idx-1,tweets[f][idx-1].t});
        }

        return ret;
    }
    
    void follow(int me, int f) {
        follows[me].insert(f);
    }
    
    void unfollow(int me, int f) {
        follows[me].erase(f);
    }
};

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter* obj = new Twitter();
 * obj->postTweet(userId,tweetId);
 * vector<int> param_2 = obj->getNewsFeed(userId);
 * obj->follow(followerId,followeeId);
 * obj->unfollow(followerId,followeeId);
 */
