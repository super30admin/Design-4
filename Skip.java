class Skip {
    HashMap<Integer,Integer> skipMap;
    Iterator<Integer> nit;
    Integer nextEle;

    public Skip(Iterator<Integer> it) {
        this.skipMap= new HashMap<>();
        nit =it;
        advance();
    }

    private void advance() {
        nextEle=null;
        while(nit.hasNext()) {
            int ele = nit.next();
            if(skipMap.containsKey(ele)) {
                skipMap.put(ele, skipMap.get(ele)-1);
                skipMap.remove(ele,0);
            }else {
                nextEle = ele;
                break;
            }
        }
    }

    public boolean hasNext() {
        return nextEle != null;
    }

    public Integer next() {
        Integer temp = nextEle;
        advance();
        return temp;
    }

    public void skip(int val) {
        if(val == nextEle) {
            advance();
        }else {
            skipMap.put(val, skipMap.getOrDefault(val,0)+1);
        }
    }
}


public void main(String[] args) {
    Skip itr = new Skip(Arrays.asList(5,6,7,5,6,8,9,5,5,6,8).iterator());
    System.out.println(itr.hasNext()); // true
    itr.skip(5);
    System.out.println(itr.next()); //6
    itr.skip(5);
    System.out.println(itr.next());  //7
    System.out.println(itr.next());  //6
    itr.skip(8);
    itr.skip(9);
    System.out.println(itr.next());  //5
    System.out.println(itr.next());  //5
    System.out.println(itr.next());  //6
    System.out.println(itr.hasNext()); // true
    System.out.println(itr.next());  //8
    System.out.println(itr.hasNext()); // false
}

