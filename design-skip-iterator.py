# An iterator is an object that lets you loop through a collection of data (like a list, array, or set) 
# one element at a time, without needing to know how that collection is structured under the hood.


class SkipIterator:
    def __init__(self, it):
        self.it = iter(it) # Converts list/iterable to an iterator
        self.count = {}    # Stores numbers to skip and their frequencies
        # count={1:-1}
        # nextEl =N
        self.next_el = None
        self._advance()

    def has_next(self) -> bool:
        return self.next_el is not None

    def next(self) -> int:
        if not self.has_next():
            raise RuntimeError("empty")
        
        el = self.next_el
        self._advance()
        return el

    def skip(self, num: int) -> None:
        if not self.has_next():
            raise RuntimeError("empty")
            
        if self.next_el == num:
            self._advance()
        else:
            self.count[num] = self.count.get(num, 0) + 1

    def _advance(self) -> None:
        self.next_el = None
        # 1,
        while self.next_el is None:
            # built in python function that retrieves next item from an interator, None avoids error
            el = next(self.it, None)
            
            if el is None:
                break # Reached the end of the underlying iterator
                
            if el not in self.count:
                self.next_el = el
            else:
                self.count[el] -= 1
                if self.count[el] == 0:
                    del self.count[el]

# --- Main Driver Test ---
if __name__ == "__main__":
    nums = [1, 2, 3]
    it = SkipIterator(nums)
    
    print(it.has_next()) # Expected: True
    it.skip(2)
    it.skip(1)
    it.skip(3)
    print(it.has_next()) # Expected: False



class SkipIterator:
    def __init__(self, it):
        print(f"\n🚀 [__init__] Initializing SkipIterator")
        self.it = iter(it) 
        self.count = {}    
        self.next_el = None
        print(f"  -> Initial state: next_el = {self.next_el}, skip_count = {self.count}")
        print(f"  -> Calling initial _advance() to pre-load the first element...")
        self._advance()

    def has_next(self) -> bool:
        result = self.next_el is not None
        print(f"🔍 [has_next] Checking if next element exists? {result} (next_el is currently: {self.next_el})")
        return result

    def next(self) -> int:
        print(f"\n🏃‍♂️ [next()] Requesting the next element")
        if not self.has_next():
            raise RuntimeError("empty")
        
        el = self.next_el
        print(f"  -> Serving current next_el: {el}")
        print(f"  -> Advancing the pointer to find the *following* element...")
        self._advance()
        print(f"  -> Returning: {el}")
        return el

    def skip(self, num: int) -> None:
        print(f"\n🛑 [skip({num})] Requesting to skip number: {num}")
        if not self.has_next():
            raise RuntimeError("empty")
            
        if self.next_el == num:
            print(f"  -> Match found! Current next_el ({self.next_el}) equals skip target ({num}).")
            print(f"  -> Dropping {num} and advancing to find a fresh element...")
            self._advance()
        else:
            print(f"  -> No match. Current next_el is {self.next_el}, but we want to skip {num}.")
            self.count[num] = self.count.get(num, 0) + 1
            print(f"  -> Added {num} to future skip list. Current skip_count map: {self.count}")

    def _advance(self) -> None:
        print(f"  🔄 [_advance] Core loop started")
        self.next_el = None
        
        while self.next_el is None:
            el = next(self.it, None)
            print(f"    • Pulled item from raw iterator: {el}")
            
            if el is None:
                print(f"    • Raw iterator exhausted (hit None). Breaking loop.")
                break 
                
            if el not in self.count:
                self.next_el = el
                print(f"    • {el} is NOT in skip list. Successfully loaded next_el = {self.next_el} ✅")
            else:
                self.count[el] -= 1
                print(f"    • {el} IS in skip list! Skipping it. Decrementing skip count to {self.count[el]}")
                if self.count[el] == 0:
                    print(f"    • Skip count for {el} hit 0. Removing from skip map completely.")
                    del self.count[el]
        
        print(f"  🔄 [_advance] Core loop finished. Final state -> next_el: {self.next_el}, skip_count: {self.count}")


# --- Main Driver Test ---
if __name__ == "__main__":
    print("=== STARTING TRACE ===")
    nums = [1, 2, 3]
    print(f"Input source list: {nums}")
    
    # 1. Initialize
    it = SkipIterator(nums)
    
    # 2. Check has_next
    print("\n--- Running: it.has_next() ---")
    it.has_next() 
    
    # 3. Skip 2 (Future skip registration)
    print("\n--- Running: it.skip(2) ---")
    it.skip(2)
    
    # 4. Skip 1 (Immediate match validation)
    print("\n--- Running: it.skip(1) ---")
    it.skip(1)
    
    # 5. Skip 3 (Processes future skips)
    print("\n--- Running: it.skip(3) ---")
    it.skip(3)
    
    # 6. Check final has_next
    print("\n--- Running: it.has_next() ---")
    it.has_next() 
    
    print("\n=== TRACE END ===")