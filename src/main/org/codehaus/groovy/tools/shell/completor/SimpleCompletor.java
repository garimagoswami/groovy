/*
 * Copyright 2003-2007 the original author or authors.
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

package org.codehaus.groovy.tools.shell.completor;

import java.util.List;
import java.util.Iterator;
import java.util.SortedSet;

/**
 * Support for simple completors.
 *
 * @version $Id$
 * @author <a href="mailto:jason@planet57.com">Jason Dillon</a>
 */
public class SimpleCompletor
    extends jline.SimpleCompletor
{
    public SimpleCompletor(final String[] candidates) {
        super(candidates);
    }
    
    public SimpleCompletor() {
        this(new String[0]);
    }
    
    public void add(final String candidate) {
        addCandidateString(candidate);
    }
    
    //
    // NOTE: Duplicated (and augumented) from JLine sources to make it call getCandidates() to make the list more dynamic
    //

    public int complete(final String buffer, final int cursor, final List clist) {
        String start = (buffer == null) ? "" : buffer;

        SortedSet matches = getCandidates().tailSet(start);

        for (Iterator i = matches.iterator(); i.hasNext();) {
            String can = (String) i.next();

            if (!(can.startsWith(start))) {
                break;
            }
            
            String delim = getDelimiter();
            
            if (delim != null) {
                int index = can.indexOf(delim, cursor);

                if (index != -1) {
                    can = can.substring(0, index + 1);
                }
            }

            clist.add(can);
        }

        if (clist.size() == 1) {
            clist.set(0, ((String) clist.get(0)) + " ");
        }

        // the index of the completion is always from the beginning of the buffer.
        return (clist.size() == 0) ? (-1) : 0;
    }
}
