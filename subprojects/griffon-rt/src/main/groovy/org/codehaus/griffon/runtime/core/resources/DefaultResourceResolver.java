/*
 * Copyright 2010-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codehaus.griffon.runtime.core.resources;

import griffon.core.resources.NoSuchResourceException;
import org.codehaus.griffon.runtime.util.CompositeResourceBundle;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Andres Almiray
 * @since 1.1.0
 */
public class DefaultResourceResolver extends AbstractResourceResolver {
    private final String basename;
    private final Map<Locale, ResourceBundle> bundles = new ConcurrentHashMap<Locale, ResourceBundle>();

    public DefaultResourceResolver(String basename) {
        this.basename = basename;
    }

    public String getBasename() {
        return basename;
    }

    protected Object resolveResourceInternal(String key, Locale locale) throws NoSuchResourceException {
        return getBundle(locale).getObject(key);
    }

    protected ResourceBundle getBundle(Locale locale) {
        if (null == locale) locale = Locale.getDefault();
        ResourceBundle rb = bundles.get(locale);
        if (null == rb) {
            rb = CompositeResourceBundle.create(basename, locale);
            bundles.put(locale, rb);
        }
        return rb;
    }
}
