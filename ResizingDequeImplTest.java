import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

public class ResizingDequeImplTest<E> {
    
    @Test
    public void sizeEmpty() {
        
        ResizingDequeImpl<E> testDeque = new ResizingDequeImpl<E>();
        
        assertEquals(0, testDeque.size());
    }
    
    @Test
    public void size1() {
        
        ResizingDequeImpl<String> testDeque = new ResizingDequeImpl<String>();
        
        testDeque.addFirst("Yuh");
        
        assertEquals(1, testDeque.size());
    }
    
    @Test
    public void sizeNoWrap() {
        
        ResizingDequeImpl<Integer> testDeque = new ResizingDequeImpl<Integer>();
        
        testDeque.addFirst(1);
        testDeque.addFirst(2);
        testDeque.addFirst(3);
        testDeque.addFirst(4);
        
        assertEquals(4, testDeque.size());
    }
    
    @Test
    public void sizeWrap() {
        
        ResizingDequeImpl<Character> testDeque = new ResizingDequeImpl<Character>();
        
        testDeque.addLast('a');
        testDeque.addLast('b');
        testDeque.addLast('c');
        testDeque.addLast('d');
        
        testDeque.pollFirst();
        testDeque.pollFirst();
        
        testDeque.addLast('e');
        testDeque.addLast('f');
        
        assertEquals(4, testDeque.size());   
    }
    
    @Test
    public void getArray() {
        
        ResizingDequeImpl<Character> testDeque = new ResizingDequeImpl<Character>();
        
        testDeque.addLast('a');
        testDeque.addLast('b');
        testDeque.addLast('c');
        testDeque.addLast('d');
        
        testDeque.pollFirst();
        testDeque.pollFirst();
        
        testDeque.addLast('e');
        testDeque.addLast('f');
        
        Character[] expectedArray = new Character[4];
        
        expectedArray[0] = 'e';
        expectedArray[1] = 'f';
        expectedArray[2] = 'c';
        expectedArray[3] = 'd';
        
        assertArrayEquals(expectedArray, testDeque.getArray());
        assertEquals(2, testDeque.headIndex);
        assertEquals(1, testDeque.tailIndex);
    }
    
    @Test
    public void addFirstInitialElem() {
        
        ResizingDequeImpl<Character> testDeque = new ResizingDequeImpl<Character>();
        
        testDeque.addFirst('w');
        
        Character[] expectedArray = new Character[2];
        
        expectedArray[0] = 'w';
        expectedArray[1] = null;
        
        assertArrayEquals(expectedArray, testDeque.getArray());
        assertEquals(0, testDeque.headIndex);
        assertEquals(0, testDeque.tailIndex);
    }
    
    @Test
    public void addFirstNonFullwZeroIndexHead() {
        
        ResizingDequeImpl<String> testDeque = new ResizingDequeImpl<String>();
        
        testDeque.addFirst("yo");
        testDeque.addFirst("ha");

        String[] expectedArray = new String[2];
        
        expectedArray[0] = "yo";
        expectedArray[1] = "ha";
        
        assertArrayEquals(expectedArray, testDeque.getArray());
        assertEquals(1, testDeque.headIndex);
        assertEquals(0, testDeque.tailIndex);
    }
    
    @Test
    public void addFirstNonFullwNonZeroIndexHead() {
        
        ResizingDequeImpl<Character> testDeque = new ResizingDequeImpl<Character>();
        
        testDeque.addLast('a');
        testDeque.addLast('b');
        testDeque.addLast('c');
        
        testDeque.pollFirst();
        
        testDeque.addFirst('w');
        
        Character[] expectedArray = new Character[4];
        
        expectedArray[0] = 'w';
        expectedArray[1] = 'b';
        expectedArray[2] = 'c';
        expectedArray[3] = null;
        
        assertArrayEquals(expectedArray, testDeque.getArray());
        assertEquals(0, testDeque.headIndex);
        assertEquals(2, testDeque.tailIndex);
    }
    
    @Test
    public void addFirstUpsizing() {
        
        ResizingDequeImpl<String> testDeque = new ResizingDequeImpl<String>();
        
        testDeque.addLast("yo");
        testDeque.addLast("ha");
        testDeque.addLast("ew");
        testDeque.addLast("fx");
        
        testDeque.pollFirst();
        
        testDeque.addLast("uh");
        
        testDeque.addFirst("wahoo");
        
        String[] expectedArray = new String[8];
        
        expectedArray[0] = "ha";
        expectedArray[1] = "ew";
        expectedArray[2] = "fx";
        expectedArray[3] = "uh";
        expectedArray[4] = null;
        expectedArray[5] = null;
        expectedArray[6] = null;
        expectedArray[7] = "wahoo";
        
        assertArrayEquals(expectedArray, testDeque.getArray());
        assertEquals(7, testDeque.headIndex);
        assertEquals(3, testDeque.tailIndex);
    }
    
    @Test
    public void addLastInitialElem() {
        
        ResizingDequeImpl<Character> testDeque = new ResizingDequeImpl<Character>();
        
        testDeque.addLast('k');
        
        Character[] expectedArray = new Character[2];
        
        expectedArray[0] = 'k';
        expectedArray[1] = null;
        
        assertArrayEquals(expectedArray, testDeque.getArray());
        assertEquals(0, testDeque.headIndex);
        assertEquals(0, testDeque.tailIndex);     
    }
    
    @Test
    public void addLastNonFullwEndTail() {

        ResizingDequeImpl<String> testDeque = new ResizingDequeImpl<String>();
        
        testDeque.addLast("yo");
        testDeque.addLast("ew");
        testDeque.addLast("ha");
        testDeque.addLast("uh");
        
        testDeque.pollFirst();
        
        testDeque.addLast("wowie");
        
        String[] expectedArray = new String[4];
        
        expectedArray[0] = "wowie";
        expectedArray[1] = "ew";
        expectedArray[2] = "ha";
        expectedArray[3] = "uh";
        
        assertArrayEquals(expectedArray, testDeque.getArray());
        assertEquals(1, testDeque.headIndex);
        assertEquals(0, testDeque.tailIndex);
        
    }
    
    @Test
    public void addLastNonFullwNonEndTail() {
        
        ResizingDequeImpl<String> testDeque = new ResizingDequeImpl<String>();
        
        testDeque.addFirst("yo");
        testDeque.addFirst("ew");
        testDeque.addFirst("oh");
        
        testDeque.addLast("wowie");
        
        String[] expectedArray = new String[4];
        
        expectedArray[0] = "ew";
        expectedArray[1] = "yo";
        expectedArray[2] = "wowie";
        expectedArray[3] = "oh";
        
        assertArrayEquals(expectedArray, testDeque.getArray());
        assertEquals(3, testDeque.headIndex);
        assertEquals(2, testDeque.tailIndex);
        
    }
    
    @Test
    public void addLastUpsizing() {
        
        ResizingDequeImpl<Character> testDeque = new ResizingDequeImpl<Character>();
        
        testDeque.addLast('a');
        testDeque.addLast('b');
        testDeque.addLast('c');
        testDeque.addLast('d');
        
        testDeque.addLast('e');
        
        Character[] expectedArray = new Character[8];
        
        expectedArray[0] = 'a';
        expectedArray[1] = 'b';
        expectedArray[2] = 'c';
        expectedArray[3] = 'd';
        expectedArray[4] = 'e';
        expectedArray[5] = null;
        expectedArray[6] = null;
        expectedArray[7] = null;
        
        assertArrayEquals(expectedArray, testDeque.getArray());
        assertEquals(0, testDeque.headIndex);
        assertEquals(4, testDeque.tailIndex);
    }
    
    @Test (expected = NoSuchElementException.class)
    public void peekFirstEmptyDeque() {
        
        ResizingDequeImpl<String> testDeque = new ResizingDequeImpl<String>();
        
        testDeque.peekFirst();
    }
    
    @Test
    public void peekFirstAfterAddFirst() {
        
        ResizingDequeImpl<Character> testDeque = new ResizingDequeImpl<Character>();
        
        testDeque.addLast('b');
        testDeque.addLast('c');
        testDeque.addLast('d');
        
        testDeque.addFirst('w');
        
        Character actual = testDeque.peekFirst();
        
        assertEquals('w', (char) actual);
        assertEquals(3, testDeque.headIndex);
        assertEquals(2, testDeque.tailIndex);
    }
    
    @Test
    public void peekFirstAfterAddLast() {
        
        ResizingDequeImpl<String> testDeque = new ResizingDequeImpl<String>();
        
        testDeque.addFirst("yo");
        testDeque.addFirst("dude");
        testDeque.addFirst("bu");
        
        String before = testDeque.peekFirst();
        
        testDeque.addLast("wait");
        
        String after = testDeque.peekFirst();
        
        assertEquals(before, after);
        assertEquals(3, testDeque.headIndex);
        assertEquals(2, testDeque.tailIndex);
    }
    
    @Test
    public void peekFirstAfterPollFirst() {
        
        ResizingDequeImpl<Character> testDeque = new ResizingDequeImpl<Character>();
        
        testDeque.addLast('a');
        testDeque.addLast('b');
        testDeque.addLast('c');
        
        testDeque.pollFirst();
        
        Character actual = testDeque.peekFirst();
        
        assertEquals('b', (char) actual);
        assertEquals(1, testDeque.headIndex);
        assertEquals(2, testDeque.tailIndex);
    }
    
    @Test
    public void peekFirstAfterPollLast() {
        
        ResizingDequeImpl<String> testDeque = new ResizingDequeImpl<String>();
        
        testDeque.addLast("yo");
        testDeque.addLast("bru");
        testDeque.addLast("howdy");
        
        String before = testDeque.peekFirst();
        
        testDeque.pollLast();
        
        String after = testDeque.peekFirst();
        
        assertEquals(before, after);
        assertEquals(0, testDeque.headIndex);
        assertEquals(1, testDeque.tailIndex);
    }
    
    @Test (expected = NoSuchElementException.class)
    public void peekLastEmptyDeque() {
        
        ResizingDequeImpl<String> testDeque = new ResizingDequeImpl<String>();
        
        testDeque.peekLast();
    }
    
    @Test
    public void peekLastAfterAddFirst() {
        
        ResizingDequeImpl<Character> testDeque = new ResizingDequeImpl<Character>();
        
        testDeque.addLast('b');
        testDeque.addLast('c');
        testDeque.addLast('d');
        
        Character before = testDeque.peekLast();
        
        testDeque.addFirst('w');
        
        Character after = testDeque.peekLast();
        
        assertEquals('d', (char) before);
        assertEquals('d', (char) after);
        assertEquals(3, testDeque.headIndex);
        assertEquals(2, testDeque.tailIndex);
    }
    
    @Test
    public void peekLastAfterAddLast() {
        
        ResizingDequeImpl<String> testDeque = new ResizingDequeImpl<String>();
        
        testDeque.addFirst("duh");
        testDeque.addFirst("ew");
        testDeque.addFirst("yo");
        
        testDeque.addLast("wowie");
        
        String actual = testDeque.peekLast();
        
        assertEquals("wowie", actual);
        assertEquals(3, testDeque.headIndex);
        assertEquals(2, testDeque.tailIndex);
    }
    
    @Test
    public void peekLastAfterPollFirst() {
        
        ResizingDequeImpl<Character> testDeque = new ResizingDequeImpl<Character>();
        
        testDeque.addLast('a');
        testDeque.addLast('b');
        testDeque.addLast('c');
        
        Character before = testDeque.peekLast();
        
        testDeque.pollFirst();
        
        Character after = testDeque.peekLast();
        
        assertEquals('c', (char) before);
        assertEquals('c', (char) after);
        assertEquals(1, testDeque.headIndex);
        assertEquals(2, testDeque.tailIndex);
    }
    
    @Test
    public void peekLastAfterPollLast() {
        
        ResizingDequeImpl<String> testDeque = new ResizingDequeImpl<String>();
        
        testDeque.addLast("yo");
        testDeque.addLast("ew");
        testDeque.addLast("ha");
        
        testDeque.pollLast();
        
        String actual = testDeque.peekLast();

        assertEquals("ew", actual);
        assertEquals(0, testDeque.headIndex);
        assertEquals(1, testDeque.tailIndex);
    }
    
    @Test (expected = NoSuchElementException.class)
    public void pollFirstEmptyDeque() {
        
        ResizingDequeImpl<String> testDeque = new ResizingDequeImpl<String>();
        
        testDeque.pollFirst();
    }
    
    @Test
    public void pollFirstOnlyElem() {
        
        ResizingDequeImpl<String> testDeque = new ResizingDequeImpl<String>();
        
        testDeque.addLast("yo");
        
        testDeque.pollFirst();
        
        assertEquals(0, testDeque.size());
        assertEquals(-1, testDeque.headIndex);
        assertEquals(-1, testDeque.tailIndex);
    }
    
    @Test
    public void pollFirstHeadLastIndex() {
        
        ResizingDequeImpl<String> testDeque = new ResizingDequeImpl<String>();
        
        testDeque.addLast("yo");
        testDeque.addLast("uh");
        testDeque.addFirst("bruh");
        
        String actual = testDeque.pollFirst();
        
        assertEquals("bruh", actual);
        assertEquals(0, testDeque.headIndex);
        assertEquals(1, testDeque.tailIndex);
    }
    
    @Test
    public void pollFirstHeadNonLastIndex() {
        
        ResizingDequeImpl<String> testDeque = new ResizingDequeImpl<String>();
        
        testDeque.addLast("bruh");
        testDeque.addLast("yo");
        testDeque.addLast("uh");
        testDeque.addLast("he");
        
        testDeque.pollFirst();
        
        String actual = testDeque.pollFirst();
        
        assertEquals("yo", actual);
        assertEquals(2, testDeque.headIndex);
        assertEquals(3, testDeque.tailIndex);
    }
    
    @Test
    public void pollFirstDownsizing() {
        
        ResizingDequeImpl<Character> testDeque = new ResizingDequeImpl<Character>();
        
        testDeque.addLast('a');
        testDeque.addLast('b');
        testDeque.addLast('c');
        testDeque.addLast('d');
        testDeque.addLast('e');
        
        testDeque.pollFirst();
        testDeque.pollFirst();
        testDeque.pollFirst();
        
        Character actual = testDeque.pollFirst();
        
        Character[] expected = new Character[4];
        
        expected[0] = null;
        expected[0] = null;
        expected[0] = null;
        expected[0] = 'e';

        assertArrayEquals(testDeque.getArray(), expected);
        assertEquals('d', (char) actual);
        assertEquals(0, testDeque.headIndex);
        assertEquals(0, testDeque.tailIndex);
        
        
    }
    
    @Test (expected = NoSuchElementException.class)
    public void pollLastEmptyDeque() {
        
        ResizingDequeImpl<String> testDeque = new ResizingDequeImpl<String>();
        
        testDeque.pollLast();
    }
    
    @Test
    public void pollLastOnlyElem() {
        
        ResizingDequeImpl<String> testDeque = new ResizingDequeImpl<String>();
        
        testDeque.addLast("bro");
        
        testDeque.pollLast();
        
        assertEquals(0, testDeque.size());
        assertEquals(-1, testDeque.headIndex);
        assertEquals(-1, testDeque.tailIndex);
    }
    
    @Test
    public void pollLastTailFirstIndex() {
        
        ResizingDequeImpl<String> testDeque = new ResizingDequeImpl<String>();
        
        testDeque.addLast("yo");
        testDeque.addLast("ew");
        testDeque.addLast("uh");
        testDeque.addLast("bro");
        
        testDeque.pollFirst();
        
        testDeque.addLast("ok");
        
        String actual = testDeque.pollLast();
        
        assertEquals("ok", actual);
        assertEquals(1, testDeque.headIndex);
        assertEquals(3, testDeque.tailIndex);
    }
    
    @Test
    public void pollLastTailNonFirstIndex() {
        
        ResizingDequeImpl<String> testDeque = new ResizingDequeImpl<String>();
        
        testDeque.addLast("yuh");
        testDeque.addLast("ah");
        testDeque.addLast("wow");
        
        String actual = testDeque.pollLast();
        
        assertEquals("wow", actual);
        assertEquals(0, testDeque.headIndex);
        assertEquals(1, testDeque.tailIndex);
    }
    
    @Test
    public void pollLastDownsizing() {
        
        ResizingDequeImpl<Character> testDeque = new ResizingDequeImpl<Character>();
        
        testDeque.addLast('a');
        testDeque.addLast('b');
        testDeque.addLast('c');
        testDeque.addLast('d');
        testDeque.addLast('e');
        
        testDeque.pollLast();
        testDeque.pollLast();
        testDeque.pollLast();
        
        Character actual = testDeque.pollLast();
        
        Character[] expected = new Character[4];
        
        expected[0] = 'a';
        expected[1] = null;
        expected[2] = null;
        expected[3] = null;
        
        assertEquals('b', (char) actual);
        assertArrayEquals(testDeque.getArray(), expected);
        assertEquals(0, testDeque.headIndex);
        assertEquals(0, testDeque.tailIndex);
    }    
    
    @Test (expected = NoSuchElementException.class)
    public void iteratorWrapping() {
        
        ResizingDequeImpl<Character> testDeque = new ResizingDequeImpl<Character>();
        
        testDeque.addLast('a');
        testDeque.addLast('b');
        testDeque.addLast('c');
        testDeque.addLast('d');
        testDeque.addLast('e');
        testDeque.addLast('f');
        testDeque.addLast('g');
        testDeque.addLast('h');
        
        testDeque.pollFirst();
        testDeque.pollFirst();
        testDeque.pollFirst();
        
        testDeque.addLast('i');
        testDeque.addLast('j');
        
        assertEquals(1, testDeque.tailIndex);
        
        Iterator<Character> iter = testDeque.iterator();
        
        assertEquals('d', (char) iter.next());
        assertEquals('e', (char) iter.next());
        assertEquals('f', (char) iter.next());
        assertEquals('g', (char) iter.next());
        assertEquals('h', (char) iter.next());
        assertEquals('i', (char) iter.next());
        assertEquals('j', (char) iter.next());
        assertFalse(iter.hasNext());
        iter.next();
    }
}