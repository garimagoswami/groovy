import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Test numeric literal types (with and without suffixes)
 * @see org.codehaus.groovy.syntax.parser/ASTBuilder#createIntegralNumber()
 * @see org.codehaus.groovy.syntax.parser/ASTBuilder#createDecimalNumber()
 *
 * @author Brian Larson
 */
class LiteralTypesTest extends GroovyTestCase {

    void testIntegral() {
        x = 42;
        assert x instanceof Integer;

        x = 42I;
        assert x instanceof Integer;

        x = 42i;
        assert x instanceof Integer;

        x = 42L;
        assert x instanceof Long;

        x = 42G;
        assert x instanceof BigInteger;

        x = 0xFF; //Hex
        assert x instanceof Integer;
        assert x == new Integer("255");

        x = 0xFFL; //Hex
        assert x instanceof Long;
        assert x == new Long("255");

        x = 0xFFG; //Hex
        assert x instanceof BigInteger;
        assert x == new BigInteger("FF",16);

        x = 0x9000000000000000;
        assert x instanceof BigInteger;
        assert x == new BigInteger("9000000000000000",16);

        x = 077; //octal
        assert x instanceof Integer;
        assert x == new Integer("63");

        x = 077l; //octal
        assert x instanceof Long;
        assert x == new Long("63");

        x = 077g; //octal
        assert x instanceof BigInteger;
        assert x == new BigInteger("77",8);

        x = 2147483647;           // max integer value
        assert x instanceof Integer;

        x = -2147483648;          // min integer constant
        assert x instanceof Integer;
        assert x == new Long("-2147483648");

        x = -2147483649;          // min integer value - 1
        assert x == new Long("-2147483649");
        assert x instanceof Long;

        x = 2147483648;           // 1 + max integer value
        assert x == new Long("2147483648");
        assert x instanceof Long;

        x = 9223372036854775807;  // max long value
        assert x instanceof Long;

        x = -9223372036854775808; // min long value
        assert x == new Long("-9223372036854775808");
        assert x instanceof Long;

        x = -9223372036854775809; // min long value - 1
        assert x == new BigInteger("-9223372036854775809");
        assert x instanceof BigInteger;

        x = 9223372036854775808;  // 1 + max long value
        assert x == new BigInteger("9223372036854775808");
        assert x instanceof BigInteger;

    }

    void testDecimal() {
        x = 3.2;
        assert x instanceof BigDecimal;
        assert x == new BigDecimal("3.2");

        x = 3.2G;
        assert x instanceof BigDecimal;
        assert x == new BigDecimal("3.2");

        x = 3.2g;
        assert x instanceof BigDecimal;
        assert x == new BigDecimal("3.2");

        x = -3.2;
        assert x instanceof BigDecimal;
        assert x == new BigDecimal("-3.2");

        x = 3.2D;
        assert x instanceof Double;
        assert x == new Double("3.2");

        x = -3.2d;
        assert x instanceof Double;
        assert x == new Double("-3.2");

        x = 3.2F;
        assert x instanceof Float;
        assert x == new Float("3.2");

        x = -3.2f;
        assert x instanceof Float;
        assert x == new Float("-3.2");
    }

    void testExponential() {
        x = 3.1415926535e42;
        assert x instanceof BigDecimal;
        assert x == new BigDecimal("3.1415926535e42");

        x = 3.2e+2;
        assert x instanceof BigDecimal;
        assert x == new BigDecimal("3.2e+2");

        x = 3.2e-2;
        assert x instanceof BigDecimal;
        assert x == new BigDecimal("3.2e-2");

        x = 3.2e2d;
        assert x instanceof Double;
        assert x == new Double("3.2e2");

        x = 3.2e2f;
        assert x instanceof Float;
        assert x == new Float("3.2e2");
    }
}
