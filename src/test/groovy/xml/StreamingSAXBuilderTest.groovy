/*
 * Copyright 2003-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package groovy.xml

import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import groovy.xml.StreamingSAXBuilder
import org.xml.sax.ContentHandler

public class StreamingSAXBuilderTest extends GroovyTestCase {

    public void testDefaultSerialization() {
        def handler = TransformerFactory.newInstance().newTransformerHandler()
        def outstream = new ByteArrayOutputStream()
        handler.setResult(new StreamResult(outstream))

        def doc = new StreamingSAXBuilder().bind {
            document {
                item(to: 'world', 'hello')
            }
        }
        def expected = '<document><item to="world">hello</item></document>'

        doc(handler)
        def actualMinusXmlDecl = outstream.toString().replaceFirst(/<[^>]*>/, '')
        assert actualMinusXmlDecl == expected
    }

    public void testCustomHandler() {
        def visited = []
        def handler = [
                startDocument: {->
                    visited << 'startDocument'
                },
                startElement: {uri, localName, qName, atts ->
                    visited << 'startElement'
                },
                characters: {chars, start, end -> /* ignore */ },
                endElement: {uri, localName, qName ->
                    visited << 'endElement'
                },
                endDocument: {->
                    visited << 'endDocument'
                },
        ] as ContentHandler

        def doc = new StreamingSAXBuilder().bind {
            document {
                item(to: 'world', 'hello')
            }
        }
        def expected = ["startDocument", "startElement", "startElement",
                "endElement", "endElement", "endDocument"]

        doc(handler)
        assert visited == expected
    }
}
