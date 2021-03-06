/*
 * Copyright 2003-2009 the original author or authors.
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
package org.codehaus.groovy.runtime.metaclass;

/**
 * WARNING: This class is for internal use only!
 * We use this class to store information about if a default MetaClass is
 * used for certain primitives.
 * @author Jochen "blackdrag" Theodorou
 *
 */
public class DefaultMetaClassInfo {
    //---------------------------------------------
    //                  int
    //---------------------------------------------
    
    // if original Integer meta class
    private static boolean origInt = true;
    // if origInt and withoutCustomHandle
    private static boolean origIntRes = true;
    
    /**
     * @return  true if no meta class creation handle is set and if
     *          the original integer meta class is used.
     */
    public static boolean isOrigInt() {
        return origIntRes;
    }
    
    /**
     * sets if the original int meta class is used
     */
    public static void setOrigInt(boolean v) {
        origInt = v;
        origIntRes = withoutCustomHandle && origInt;
    }

    //---------------------------------------------
    //                  int[]
    //---------------------------------------------

    // if original Integer[] meta class
    private static boolean origIntArray = true;
    // if origInt and withoutCustomHandle
    private static boolean origIntArrayWCH = true;

    /**
     * @return  true if no meta class creation handle is set and if
     *          the original integer array meta class is used.
     */
    public static boolean isOrigIntArray() {
        return origIntArrayWCH;
    }
    
    /**
     * sets if the original int array meta class is used
     */
    public static void setOrigIntArray(boolean v) {
        origIntArray = v;
        origIntArrayWCH = withoutCustomHandle && origIntArray;
    }
    
    
    //---------------------------------------------
    //     custom meta class creation handle
    //---------------------------------------------

    // if a custom meta class creation handle is set
    private static boolean withoutCustomHandle = true;

    /**
     * sets if the system uses a custom meta class creation handle
     */
    public static void setWithoutCustomMetaclassCreationHandle(boolean mch) {
        withoutCustomHandle = mch;
        changeFlags(mch);
    }

    //---------------------------------------------
    //              category handle
    //---------------------------------------------
    private static boolean categoryUsed = false;
    private static boolean disabledStandardMC = false;
    
    public static void setCategoryUsed(boolean b) {
        categoryUsed = b;
        disabledStandardMC = b || !withoutCustomHandle;
    }
    
    public static boolean disabledStandardMetaClass() {
        return disabledStandardMC;
    }    


    private static void changeFlags(boolean mch) {
        if (mch) {
            disabledStandardMC = true;
            origIntArrayWCH = false;
            origIntRes = false;
        } else {
            disabledStandardMC = categoryUsed;
            origIntRes = origInt;
            origIntArrayWCH = origIntArray;
        }
        
    }
}
