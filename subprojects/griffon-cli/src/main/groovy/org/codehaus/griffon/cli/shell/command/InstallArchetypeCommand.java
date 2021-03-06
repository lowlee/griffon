/*
 * Copyright 2012-2013 the original author or authors.
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

package org.codehaus.griffon.cli.shell.command;

import org.codehaus.griffon.cli.shell.AbstractGriffonCommand;
import org.codehaus.griffon.cli.shell.Argument;
import org.codehaus.griffon.cli.shell.Command;
import org.codehaus.griffon.cli.shell.Option;

/**
 * @author Andres Almiray
 * @since 0.9.5
 */
@Command(scope = "griffon",
        name = "install-archetype",
        description = "Installs an archetype for the given URL or name and version")
public class InstallArchetypeCommand extends AbstractGriffonCommand {
    @Option(name = "--repository",
            description = "Name of an specific repository where the search will be performed.",
            required = false)
    private String repository;

    @Option(name = "--force-upgrade",
            description = "Forces upgrade if the archetype to be installed has a bigger major version than the currently installed (if there's one archetype installed that matches the same name).",
            required = false)
    private String forceUpgrade;

    @Argument(index = 0,
            name = "name",
            description = "The name of the archetype to install. You may specify an URL, a zip file or a regular name.",
            required = true)
    private String name;

    @Argument(index = 1,
            name = "version",
            description = "The version of the archetype to install. May only be specified if an archetype name is supplied.",
            required = false)
    private String version;
}