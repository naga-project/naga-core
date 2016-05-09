/*
 * Note: this code is a fork of Goodow realtime-json project https://github.com/goodow/realtime-json
 */

/*
 * Copyright 2013 Goodow.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package naga.core.spi.json.java.jackson;

import naga.core.spi.json.JsonArray;
import naga.core.spi.json.listmap.ListCompositeArray;

import java.util.List;

/**
 * Server-side implementation of JsonArray.
 *
 * @author 田传武 (aka Larry Tin) - author of Goodow realtime-json project
 * @author Bruno Salmon - fork, refactor & update for the naga project
 *
 * <a href="https://github.com/goodow/realtime-json/blob/master/src/main/java/com/goodow/json/impl/JreJsonArray.java">Original Goodow class</a>
 */
final class JacksonJsonArray extends ListCompositeArray implements JacksonJsonElement, JsonArray {

    JacksonJsonArray() {
    }

    JacksonJsonArray(List array) {
        super(array);
    }

    @Override
    public String toJsonString() {
        return JacksonUtil.encode(list);
    }
}