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

package org.codehaus.groovy.tools.shell.commands

import org.codehaus.groovy.tools.shell.CommandSupport
import org.codehaus.groovy.tools.shell.Shell

/**
 * The 'classes' command.
 *
 * @version $Id$
 * @author <a href="mailto:jason@planet57.com">Jason Dillon</a>
 */
class ClassesCommand
    extends CommandSupport
{
    ClassesCommand(final Shell shell) {
        super(shell, 'classes', '\\C')
    }
    
    Object execute(final List args) {
        assertNoArguments(args)
        
        def classes = classLoader.loadedClasses
        
        if (classes.size() == 0) {
            io.out.println("No classes have been loaded") // TODO: i18n
        }
        else {
            io.out.println('Classes:') // TODO: i18n
            classes.each {
                io.out.println("  $it")
            }
        }
    }
}
