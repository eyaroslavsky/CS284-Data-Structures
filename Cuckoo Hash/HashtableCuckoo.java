package CuckooHash;

import java.util.AbstractMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class HashtableCuckoo<K, V> implements KWHashMap<K, V> {

    
    private Entry<K, V>[] table1;
    private Entry<K, V>[] table2;
    private List<Entry<K, V> > overflow;
    private static final int START_CAPACITY = 100;
    private double LOAD_THRESHOLD = 0.6;
    private int tableSize = (int) (START_CAPACITY*LOAD_THRESHOLD);
    private int numKeys;
 
    public HashtableCuckoo() {
	this.table1 = new Entry[tableSize];
	this.table2 = new Entry[tableSize];
	this.overflow = new ArrayList<Entry<K, V>>();
    }
  
    /** Contains key-value pairs for a hash table. */
    public static class Entry<K, V> implements Map.Entry<K, V> {

        /** The key */
        private K key;
        /** The value */
        private V value;

        /**
         * Creates a new key-value pair.
         * @param key The key
         * @param value The value
         */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Retrieves the key.
         * @return The key
         */
        @Override
        public K getKey() {
            return key;
        }

        /**
         * Retrieves the value.
         * @return The value
         */
        @Override
        public V getValue() {
            return value;
        }

        /**
         * Sets the value.
         * @param val The new value
         * @return The old value
         */
        @Override
        public V setValue(V val) {
            V oldVal = value;
            value = val;
            return oldVal;
        }

	/**
         * Return a String representation of the Entry
         * @return a String representation of the Entry
         *         in the form key = value
         */
        @Override
        public String toString() {
            return key.toString() + "," + value.toString();
        }
        
    }
    
    /**
     * Returns the number of elements in the Cuckoo Hashtable
     * @return an int of the number of elements in the Cuckoo Hashtable
     */
    @Override
    public int size() {
    	int count = 0;
    	//First table
    	for (Entry<K, V> x: this.table1) {
	    if (x != null) {
		count++;
	    }
    	}
    	//Second table
    	for (Entry<K, V> y: this.table2) {
	    if (y != null) {
		count++;
	    }
    	}
    	//Overflow
    	for (int z = 0; z < this.overflow.size(); z++) {
	    if (this.overflow.get(z) != null) {
		count++;
	    }
    	}
    	return count;
    }
    
    /**
     * Returns whether the Cuckoo Hashtable is empty or not
     * @return a boolean of true if the table is empty and false if not empty
     */
    @Override
    public boolean isEmpty() {
        if (this.size() == 0) {
	    return true;
        }
        return false;
    }
    
    /**
     * Returns the hash after the hashcode is manipulated
     * @param o The object we are getting the hash for
     * @param num The int to determine which method of hashing we use
     * @return an int of the hash after the hashcode is manipulated
     */
    private int getHash(Object o, int num){
        if (num != 0 && num != 1) {
	    return -1;
        }
        else if (num == 0) { //hash for first table
	    return o.hashCode() % this.tableSize;
        }
        else { //hash for second table 
	    int hashcode = o.hashCode();
	    hashcode = hashcode << 16 | hashcode >>> 16;
	    hashcode = hashcode % this.tableSize;
	    if (hashcode < 0) {
		hashcode += this.tableSize;
	    }
	    return hashcode;
        }
    }
    
    /**
     * Returns the value of the object by finding its location
     * @param key The key of the object we are looking for
     * @return the value of the object
     */
    @Override
    public V get(Object key) {
    	//First Table
        int index = getHash(key, 0);
        if (this.table1[index] != null && this.table1[index].getKey().equals(key)) {
	    return this.table1[index].getValue();
        }
        //Second Table
        index = getHash(key, 1);
        if (this.table2[index] != null && this.table2[index].getKey().equals(key)) {
	    return this.table2[index].getValue();
        }
        //Overflow
        for (int x = 0; x < this.overflow.size(); x++) {
	    if (this.overflow.get(x).getKey().equals(key)) {
		return this.overflow.get(x).getValue();
	    }
        }
        return null;
    }
    
    /**
     * Places an Entry into the Cuckoo Hashtable
     * @param key The key that our Entry has
     * @param value The value that our Entry has
     * @return the oldValue of the position we are adding our new Entry 
     */
    @Override
    public V put(K key, V value) {
    	//Initializes Arraylists to help determine if there is a cycle
    	//Initializes the Entries that will be bumped
    	ArrayList<Integer> list1 = new ArrayList<Integer>();
    	ArrayList<Integer> list2 = new ArrayList<Integer>();
    	Entry<K, V> bump1;
    	Entry<K, V> bump2;
    	
    	//Starts the first loop of the bumped Entries
    	int index = this.getHash(key, 0);
    	if (this.table1[index] == null) {
	    this.table1[index] = new Entry<K, V>(key, value);
	    this.numKeys++;
	    return null;
    	}
    	else if (this.table1[index].getKey().equals(key)) {
	    return this.table1[index].setValue(value);	     
    	}
    	else { //Entry will be bumped
	    bump1 = this.table1[index];
	    this.table1[index] = new Entry<K, V>(key, value);
	    list1.add(index);
    		
	    index = this.getHash(bump1.getKey(), 1);
	    if (this.table2[index] == null) { //Moving over the bumped Entry to table2
		this.table2[index] = bump1;
		this.numKeys++;
		return bump1.getValue();
	    }
	    else if (this.table2[index].getKey().equals(key)) {
		return this.table2[index].setValue(value);
	    }
	    else {
		bump2 = this.table2[index];
		this.table2[index] = bump1;
		list2.add(index);
	    }
    	}
    	
    	//Starting the next sequences of loops for the bumping
    	while (true) {
	    index = this.getHash(bump2.getKey(), 0);
    		
	    //Checks to see if a position is repeated in table1 to determine a cycle
	    for (Integer x: list1) {
		if (x == index) {
		    for (Entry<K, V> e: this.overflow) {
			if (e.getKey().equals(key)) {
			    return e.setValue(value);
			}
		    }
		    this.overflow.add(bump2);
		    return null;
		}
	    }
    		
	    if (this.table1[index] == null) { //Moving over the bumped Entry to table1
		this.table1[index] = bump2;
		this.numKeys++;
		return bump2.getValue();
	    }
	    else if (this.table1[index].getKey().equals(key)) {
		return this.table1[index].setValue(value);
	    }
	    else {
		bump1 = this.table1[index];
		this.table1[index] = bump2;
		list1.add(index);
    			
		index = this.getHash(bump1.getKey(), 1);
    			
		//Checks to see if a position is repeated in table2 to determine a cycle
		for (Integer y: list2) {
		    if (y == index) {
			for (Entry<K, V> e: this.overflow) {
			    if (e.getKey().equals(key)) {
				return e.setValue(value);
			    }
			}
			this.overflow.add(bump1);
			return null;
		    }
		}
    			
		if (this.table2[index] == null) { //Moving over the bumped Entry back to table2
		    this.table2[index] = bump1;
		    this.numKeys++;
		    return bump1.getValue();
		}
		else if (this.table2[index].getKey().equals(key)) {
		    return this.table2[index].setValue(value);
		}
		else {
		    bump2 = this.table2[index];
		    this.table2[index] = bump1;
		    list2.add(index);
		}
	    }
    	}   	
    }
    
    /**
     * Removes an Entry from the Cuckoo Hashtable
     * @param key The key of the object we want to remove
     * @return The value of the object we removed
     */
    @Override
    public V remove(Object key) {
    	V value = this.get(key);

    	int index = getHash(key, 0);
    	if (this.table1[index] != null && this.table1[index].getKey().equals(key)) { //Checks to see if the Entry is found from table1
	    this.table1[index] = null;
	    for (int x = 0; x < this.overflow.size(); x++) { //Tries to add an Entry from the overflow
		if (getHash(this.overflow.get(x).getKey(), 0) == index) {
		    this.table1[index] = this.overflow.remove(x);
		    return value;
		}
	    }
	    return value;
    	}
    	index = getHash(key, 1);
    	if (this.table2[index] != null && this.table2[index].getKey().equals(key)) { //Checks to see if the Entry is found from table2
	    this.table2[index] = null;
	    for (int y = 0; y < this.overflow.size(); y++) { //Tries to add an Entry from the overflow
		if (getHash(this.overflow.get(y).getKey(), 1) == index) {
		    this.table2[index] = this.overflow.remove(y);
		    return value;
		}
	    }
	    return value;
    	}
	
    	for (int z = 0; z < this.overflow.size(); z++) { //Checks to see if the Entry is found from the overflow
	    if (this.overflow.get(z).getKey().equals(key)) {
		this.overflow.remove(z);
		return value;
	    }
    	}
    	return value;
    }
    
    /**
     * Returns a String of the Cuckoo Hashtable
     * @return the String conversion of the Cuckoo Hashtable
     */
    @Override
    public String toString() {
    	String str = "";
    	for (int x = 0; x < this.tableSize; x++) {
	    if (this.table1[x] != null) {
		str += "[" + String.valueOf(x) + "," + this.table1[x].toString() + ",table1]\n";
	    }
    	}
    	for (int y = 0; y < this.tableSize; y++) {
	    if (this.table2[y] != null) {
		str += "[" + String.valueOf(y) + "," + this.table2[y].toString() + ",table2]\n";
	    }
    	}
    	for (int z = 0; z < this.overflow.size(); z++) {
	    str += "[" + String.valueOf(z) + "," + this.overflow.get(z).toString() + ",overflow]\n";
    	}
    	return str;
    }
    
}
