package groovy.lang

import org.codehaus.groovy.reflection.ReflectionCache

class MixinTest extends GroovyTestCase {

    protected void setUp() {
    }

    protected void tearDown() {
        ArrayList.metaClass = null
        List.metaClass = null
    }

    void testOneClass () {
        List.mixin ListExt
        ArrayList.mixin ArrayListExt
        assertEquals 1, [0,1].swap () [0]
        assertEquals 0, [0,1].swap () [1]
        assertEquals 0, [0,1].swap ().unswap () [0]
        assertEquals 1, [0,1].swap ().unswap () [1]
    }

    void testWithList () {
        ArrayList.mixin ArrayListExt, ListExt
        assertEquals 1, [0,1].swap () [0]
        assertEquals 0, [0,1].swap () [1]
        assertEquals 0, [0,1].swap ().unswap () [0]
        assertEquals 1, [0,1].swap ().unswap () [1]
    }

    void testCombined () {
        ArrayList.mixin Combined
        assertEquals 1, [0,1].swap () [0]
        assertEquals 0, [0,1].swap () [1]
        assertEquals 0, [0,1].swap ().unswap () [0]
        assertEquals 1, [0,1].swap ().unswap () [1]
    }

    void testWithEmc () {
        ArrayList.metaClass.unswap = {
            [delegate[1], delegate[0]]
        }
        ArrayList.mixin ArrayListExt
        assertEquals 1, [0,1].swap () [0]
        assertEquals 0, [0,1].swap () [1]
        assertEquals 0, [0,1].swap ().unswap () [0]
        assertEquals 1, [0,1].swap ().unswap () [1]
    }

    void testGroovyObject () {
        def obj = new ObjToTest ()
        assertEquals "original", obj.value
        obj.metaClass.mixin ObjToTestCategory
        assertEquals "changed by category", obj.value
        assertEquals "original", new ObjToTest ().value
    }

    void testGroovyObjectWithEmc () {
        ObjToTest.metaClass.getValue = { ->
            "emc changed"
        }
        ObjToTest obj = new ObjToTest ()
        assertEquals "emc changed", obj.getValue()
        obj.metaClass.mixin ObjToTestCategory
        assertEquals "changed by category", obj.value
        assertEquals "emc changed", new ObjToTest ().value
    }

    void testFlatten () {
        Collection.metaClass.mixin FlattenAndUniqueCategory
        assertEquals ([3,2,1,4], [[3,2,[2:1,3:4]],[2,3]].flattenAndUnique () as List)
        Collection.metaClass = null
    }
}

class ArrayListExt {
    static def swap (ArrayList self) {
        [self[1], self[0]]
    }
}

class ListExt {
    static def unswap (List self) {
        [self[1], self[0]]
    }
}

class Combined {
    static def swap (ArrayList self) {
        [self[1], self[0]]
    }

    static def unswap (List self) {
        [self[1], self[0]]
    }
}

class ObjToTest {
    def getValue () {
        "original"
    }
}

class ObjToTestCategory {
    def static getValue (ObjToTest self) {
        "changed by category"
    }
}

class FlattenAndUniqueCategory {
    static Set flattenAndUnique(elements, Set addTo = null) {
        if (addTo == null)
          addTo = new LinkedHashSet()

        elements.each { element ->
            if (element instanceof Collection) {
                element.flattenAndUnique(addTo);
            } else if (element instanceof Map) {
                element.values().flattenAndUnique(addTo);
            } else {
                addTo << element;
            }
        }
        return addTo;
    }
}