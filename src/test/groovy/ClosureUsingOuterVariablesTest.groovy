/** 
 * @author <a href="mailto:james@coredevelopers.net">James Strachan</a>
 * @version $Revision$
 */
class ClosureUsingOuterVariablesTest extends GroovyTestCase {

    void testUseOfOuterVariable() {
        
        x = 123
        y = "hello"
        
        closure = { i | 
            println("x ${x}")
            println("y ${y}")
            println("i ${i}")
                
            assert x == 123
            assert y == 'hello'
        }
        closure.call(321)
	}

     void testInnerVariablesVisibleInOuterScope() {
        
        closure = { z = 456 } 
        closure.call(321)
        
        assert z == 456
    }
    
    void testModifyingOuterVariable() {
        
        m = 123
        
        closure = { m = 456 } 
        closure.call(321)
        
        assert m == 456
    }
}
