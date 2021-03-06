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

package org.codehaus.griffon.runtime.core;

import griffon.core.GriffonView;
import griffon.core.GriffonViewClass;
import groovy.util.FactoryBuilderSupport;

/**
 * Base implementation of the GriffonView interface for Script based views
 *
 * @author Andres Almiray
 * @since 0.9.1
 */
public abstract class AbstractGriffonViewScript extends AbstractGriffonMvcArtifactScript implements GriffonView {
    private FactoryBuilderSupport builder;

    public AbstractGriffonViewScript() {
        super(GriffonViewClass.TYPE);
    }

    public FactoryBuilderSupport getBuilder() {
        return builder;
    }

    public void setBuilder(FactoryBuilderSupport builder) {
        this.builder = builder;
    }
}
