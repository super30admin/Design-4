//
//  SkipIterator.swift
//  DSA-Practice
//
//  Created by Paridhi Malviya on 2/5/26.
//

/*
 Skip iterator -
 Iterator has functions - hasNext(), next()
 a normal iterattor is lazy loading.
 
 we can make use of native iterator.
 
 
 In constructor- a list is given. We have to put a iterator on it.
 We are given a native iterator, we want it to behave like a skip iterator.
 
 Normal iterator is - lazy loading data structure.
 */
class SkipIterator {
    
    private var papaIt: IndexingIterator<[Int]>
    private var skipMap: [Int: Int] = [:]
    private var nextEl: Int?
    
    init(_ array: [Int]) {
        self.papaIt = array.makeIterator()
        advance()
    }
    
    //sets the next element to give out
    func advance() {
        nextEl = nil
        
        while (nextEl == nil) {
            guard let el = papaIt.next() else {
                return
            }
            
            //check if the next element is present in the skipMap or not. If yes, reduce count or remove
            if let count = skipMap[el] {
                let newCount = count - 1
                if (newCount == 0) {
                    skipMap.removeValue(forKey: el)
                } else {
                    skipMap[el] = newCount
                }
            } else {
                //If not present in Skip map, then nextElement is set to el to give out in next
                nextEl = el
            }
        }
    }
    
    func hasNext() -> Bool {
        return nextEl != nil
    }
    
    func next() -> Int? {
        guard let result = nextEl else {
            return nil
        }
        advance()
        return result
    }
    
    func skip(_ num: Int) {
        if(num == nextEl) {
            advance()
        } else {
            skipMap[num, default: 0] += 1
        }
    }
    
}

class SKipIteratorImpl {
    
    init() {
        let sit = SkipIterator([5,6,7,5,6,8,9,5,5,6,8])
        print(sit.hasNext())
        print(sit.next()!)
        sit.skip(5)
        
        print(sit.next()!)
        print(sit.next()!)
    
        sit.skip(7)
        sit.skip(9)
        
        print(sit.next()!)
        print(sit.next()!)
        print(sit.next()!)
        
        sit.skip(8)
        sit.skip(5)
        
        print(sit.hasNext())
        print(sit.next()!)
        
        print(sit.hasNext())
    }
}
