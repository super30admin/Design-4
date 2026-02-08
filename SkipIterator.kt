
// SkipMap contains the count of elements to be skipped. When we call skip, we check if the next element is the one to be skipped. If it is, we call advance to move to the next element. If it is not, we increment the count of that element in the skipMap.
// When we call next, we return the next element and call advance to move to the next element.
// The advance function checks if the next element is in the skipMap and if it is, it decrements the count of that element in the skipMap and continues to the next element until it finds an element that is not in the skipMap.
// Time Complexity : O(1) for next and hasNext, O(1) for skip in average case and O(n) in worst case when all elements are the same and we call skip on that element n times.
// Space Complexity : O(k)where k is the number of skipped elements tracked in skipMap 



class SkipIterator(private val it: Iterator<Int>) : Iterator<Int> {
    private val skipMap = mutableMapOf<Int, Int>()
    
    private var nextEl: Int? = null
    
    init {
        advance()
    }
    
    private fun advance() {
        nextEl = null
        while(it.hasNext()) {
            val el = it.next()
            val toSkip = skipMap[el]
            
            if(toSkip == null) {
                nextEl = el
                 break
            } else {
                if(toSkip == 1) skipMap.remove(el) else skipMap[el] = toSkip - 1
            }
        }
    }
    
    override fun hasNext(): Boolean = nextEl != null
    
    override fun next(): Int {
        val el = nextEl ?: throw NoSuchElementException()
        advance()
        return el
    }
    
    fun skip(value: Int) {
        if(nextEl != null && nextEl == value) {
            advance()
        } else {
            skipMap[value] = (skipMap[value] ?: 0) + 1
        }
    }
}

fun main() {
    val itr = SkipIterator(listOf(5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8).iterator())

    println(itr.hasNext())      // true
    itr.skip(5)
    println(itr.next())         // 6
    itr.skip(5)
    println(itr.next())         // 7
    println(itr.next())         // 6
    itr.skip(8)
    itr.skip(9)
    println(itr.next())         // 5
    println(itr.next())         // 5
    println(itr.next())         // 6
    println(itr.hasNext())      // true
    println(itr.next())         // 8
    println(itr.hasNext())      // false
}
