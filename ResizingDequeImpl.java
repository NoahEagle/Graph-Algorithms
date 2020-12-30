import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingDequeImpl<E> implements ResizingDeque<E> {
    
    E[] theArray; // A field representing the underlying array
    
    int headIndex; // The index of the start of the deque
    int tailIndex; // The index of the end of the deque
    
    /**
     * Creates a resizable deque with an initial size of 2 with the head and tail pointers pointing
     * to -1 (to signal that the deque is empty)
     */
    ResizingDequeImpl() {
        
        theArray = (E[]) new Object[2];
        
        headIndex = -1;
        tailIndex = -1;
    }
    
    /**
     * Returns the size of this list.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        
        // If the head and tail are -1, it's empty
        if (headIndex == -1 && tailIndex == -1) {
            
            return 0;
        
        // If the head is less than or equal to the tail, we can just count from head to tail
        } else if (headIndex <= tailIndex) {
            
            return tailIndex - headIndex + 1;
        
        /*
         *  If the tail is less than the head, we have to count from head to end and then from start
         *  to tail
         */
        } else {
            
            return theArray.length - headIndex + tailIndex + 1;
        }
    }
    
    /**
     * @return The underlying array of the deque implementation directly.
     */
    @Override
    public E[] getArray() {
        
        return theArray;
    }
    
    /**
     * Doubles the size of the underlying array (because we were at capacity), keeps the same
     * elements with the head now being at index 0 and the tail being at whatever the old size of
     * the array was - 1
     * 
     * It doesn't return anything, it just updates the underlying array to be this new expanded one
     * and resets the head to index 0 and the tail to whatever the original last index was in the
     * old array
     */
    void doubleCapacity() {
        
        int oldSize = theArray.length;
        
        E[] doubledArray = (E[]) new Object[oldSize * 2];
        
        int index = 0;
        
        // Iterate from the head to the end of the old array, filling in the vals for the new one
        for (int i = headIndex; i < theArray.length; i++) {
            
            doubledArray[index] = theArray[i];
            index++;
        }
        
        /*
         *  If the curr index in the doubled array isn't now one past the final index in the old
         *  array (meaning it's equal to the size of the old arary), we have to now start adding
         *  from the index 0 to the tail (to catch the wrapped around elements)
         */
        if (index != oldSize) {
            
            for (int j = 0; j <= tailIndex; j++) {
                
                doubledArray[index] = theArray[j];
                index++;
            }
        }
        
        theArray = doubledArray;
        headIndex = 0;
        tailIndex = oldSize - 1;
    }
    
    /**
     * Inserts the specified element at the front of this deque.
     *
     * @param e the element to add
     */
    @Override
    public void addFirst(E e) {
        
        // If we need to expand, expand
        if (size() == theArray.length) {
            
            doubleCapacity();
        }
        
        // If the head is at the very start of the array, add this new thing at the very end of it
        if (headIndex == 0) {
            
            headIndex = theArray.length - 1;
            theArray[headIndex] = e;
        
        /*
         *  If the deque was empty, just make the head and tail point to index 0 and put in the new
         *  element at index 0    
         */
        } else if (size() == 0) {
                
            headIndex = 0;
            tailIndex = 0;
            theArray[headIndex] = e;
        
        // Otherwise, add this new thing in the index immediately before the head    
        } else {
            
            headIndex--;
            theArray[headIndex] = e;
        } 
    }
    
    /**
     * Inserts the specified element at the end of this deque.
     *
     * @param e the element to add
     */
    @Override
    public void addLast(E e) {
        
        // If we need to expand, expand
        if (size() == theArray.length) {
            
            doubleCapacity();
        }
        
        // If the tail is at the very end of the array, add this new thing at the very start of it
        if (tailIndex == theArray.length - 1) {
            
            tailIndex = 0;
            theArray[tailIndex] = e;
            
        /*
         *  If the deque was empty, just make the head and tail point to index 0 and put in the new
         *  element at index 0
         */
        } else if (size() == 0) {
            
            headIndex = 0;
            tailIndex = 0;
            theArray[tailIndex] = e;
         
        // Otherwise, add this new thing in the index immediately after the tail    
        } else {
            
            tailIndex++;
            theArray[tailIndex] = e;
        }
    }
    
    /**
     * Cuts down the size of the underlying array by half. It keeps the same elements in the same
     * order, it just shifts it so that the head is at index 0 and and flows until the tail (in a 
     * straight fashion)
     * 
     * It doesn't return anything, it just updates the underlying array and head and tail pointers
     * to their new position
     */
    void halveCapacity() {
        
        int oldSize = theArray.length;
        
        E[] halvedArray = (E[]) new Object[oldSize / 2];
        
        /*
         *  If it's a straight shot from head to tail (no wrap around), just iterate through and
         *  make the head now start at 0 in the halved array
         */
        if (headIndex <= tailIndex) {
            
            int index = 0;
            
            for (int i = headIndex; i <= tailIndex; i++) {
                
                halvedArray[index] = theArray[i];
                index++;
            }
        /*
         *  Otherwise, there is wrap around (so iterate through from head to end of array and then
         *  from start of array to tail)    
         */
        } else {
            
            int index = 0;
            
            for (int i = headIndex; i < theArray.length; i++) {
                
                halvedArray[index] = theArray[i];
                index++;
            }
            
            for (int j = 0; j <= tailIndex; j++) {
                
                halvedArray[index] = theArray[j];
                index++;
            }
        }
        
        headIndex = 0;
        tailIndex = (oldSize / 4) - 2;
        
        if (tailIndex < 0) {
            
            tailIndex = 0;
        }
        
        theArray = halvedArray;
    }
    
    /**
     * Retrieves and removes the first element of this deque.
     *
     * @throws NoSuchElementException if the deque is empty.
     */
    @Override
    public E pollFirst() {
        
        if (size() == 0) {
            
            throw new NoSuchElementException();
        }
        
        E oldHead = peekFirst();
        
        // If there's one element left, remove it and set the head and tail to -1 (for empty)
        if (headIndex == tailIndex) {
           
            theArray[headIndex] = null;
            headIndex = -1;
            tailIndex = -1;
        
        // If the head is the very last index in the array, remove the elem and make head index 0
        } else if (headIndex == theArray.length - 1) {
            
            theArray[headIndex] = null;
            headIndex = 0;
        
        // Otherwise, remove the head elem and push the head forward by 1 
        } else {
            
            theArray[headIndex] = null;
            headIndex++;       
        }
        
        // If we now have an empty deque, update accordingly (bc halveCapacity bugs for this sit)
        if (size() == 0) {
            
            E[] halvedArray = (E[]) new Object[2];
            headIndex = -1;
            tailIndex = -1;
            
            theArray = halvedArray;
            
            return oldHead;
        }
        
        // If after we removed the first elem, we need to resize down, resize down
        if (size() < theArray.length / 4 && theArray.length > 2) {
            
            halveCapacity();
        }
        
        return oldHead;
    }
    
    /**
     * Retrieves and removes the last element of this deque.
     *
     * @throws NoSuchElementException if the deque is empty.
     */
    @Override
    public E pollLast() {
        
        if (size() == 0) {
            
            throw new NoSuchElementException();
        }
        
        E oldTail = peekLast();
        
        // If there's only one element left, remove it and set the head and tail to -1 (for empty)
        if (headIndex == tailIndex) {
            
            theArray[tailIndex] = null;
            headIndex = -1;
            tailIndex = -1;
            
        /*
         *  If the tail is the very first index in the array, remove the elem and make tail the last
         *  possible index  
         */
        } else if (tailIndex == 0) {
            
            theArray[tailIndex] = null;
            tailIndex = theArray.length - 1;
            
        // Otherwise, remove the tail elem and push the tail back by 1    
        } else {
            
            theArray[tailIndex] = null;
            tailIndex--;
        }
        
        // If we now have an empty deque, update accordingly (bc halveCapacity bugs for this sit)
        if (size() == 0) {
            
            E[] halvedArray = (E[]) new Object[2];
            headIndex = -1;
            tailIndex = -1;
            
            theArray = halvedArray;
            
            return oldTail;
        }
        
        // If after we removed the last elem, we need to resize down, resize down
        if (size() < theArray.length / 4 && theArray.length > 2) {
            
            halveCapacity();
        }
        
        return oldTail;
    }
    
    /**
     * Retrieves, but does not remove, the first element of this deque.
     *
     * @throws NoSuchElementException if the deque is empty.
     */
    @Override
    public E peekFirst() {
        
        if (size() == 0) {
            
            throw new NoSuchElementException();
        }
        
        return theArray[headIndex];
    }
    
    /**
     * Retrieves, but does not remove, the last element of this deque
     *
     * @throws NoSuchElementException if the deque is empty.
     */
    @Override
    public E peekLast() {
        
        if (size() == 0) {
            
            throw new NoSuchElementException();
        }
        
        return theArray[tailIndex];
    }
    
    /**
     * Returns an iterator over the elements in this deque, ordered from
     * first to last.
     *
     * @return an iterator over the elements in this deque
     */
    @Override
    public Iterator<E> iterator() {
        
        return new Iterator<E>() {
            
            int currentIndex = headIndex - 1;
            
            // As long as we're not at the tail, the deque has a next
            @Override
            public boolean hasNext() {
                
                return currentIndex != tailIndex;
                
            }
            
            /*
             *  As long as the deque has a next, we can grab it
             */
            @Override
            public E next() {
                
                // If there's not a next or the index is - 2 (meaning empty deque), throw NSEE
                if (!hasNext() || currentIndex == -2) {
                    
                    throw new NoSuchElementException();
                }
                
                currentIndex++;
                
                // If we can keep going forward in the array, do it
                if (currentIndex != theArray.length) {
                
                    return theArray[currentIndex];
                
                // If we need to wrap around, do it    
                } else {
                    
                    currentIndex = 0;
                    
                    return theArray[currentIndex];
                }
            
            }
        };
    }
}